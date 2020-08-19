package com.offgrid.coupler.core.service;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.Observer;

import com.offgrid.coupler.core.model.jni.EtherMessageJNI;
import com.offgrid.coupler.core.proxy.LoraProxy;
import com.offgrid.coupler.data.entity.Chat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.offgrid.coupler.core.model.Constants.SERVICE_MESSAGE;
import static com.offgrid.coupler.core.model.Constants.SERVICE_REGISTER_CLIENT;
import static com.offgrid.coupler.core.model.Constants.SERVICE_UNREGISTER_CLIENT;

public class LoraService extends LifecycleService implements Observer<List<Chat>> {
    private static final String LOGTAG = "LoraService";

    private static boolean isRunning = false;

    private Map<String, Messenger> clientMap = new HashMap<>();

    private final Messenger mMessenger = new Messenger(new IncomingMessageHandler());


    @Override
    public void onCreate() {
        super.onCreate();

        isRunning = true;
        LoraProxy.shmInit();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        EtherMessageJNI ether = (EtherMessageJNI) LoraProxy.etherMessage();
                        if (ether != null) {
                            sendMessageToClient(ether);
                        }
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        Log.e(LOGTAG, e.toString());
                    }
                }
            }
        }).start();

        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        return mMessenger.getBinder();
    }


    public static boolean isRunning() {
        return isRunning;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(LOGTAG, "Service Stopped.");
        LoraProxy.shmFree();
        isRunning = false;
    }


    @Override
    public void onChanged(@Nullable final List<Chat> chats) {
        Log.e(LOGTAG, "[onChanged] chats.size(): " + chats.size());
    }


    private Boolean sendMessageToClient(EtherMessageJNI ether) {
        String clientId = ether.getGid();

        Messenger messenger = clientMap.get(clientId);
        if (messenger != null) {
            try {
                // Send data as a String
                Bundle bundle = new Bundle();
                bundle.putString("message", ether.getData());

                Message msg = Message.obtain(null, SERVICE_MESSAGE, clientId);
                msg.setData(bundle);
                messenger.send(msg);

                return Boolean.TRUE;

            } catch (RemoteException e) {
                // The client is dead. Remove it from the list.
                clientMap.remove(clientId);
            }
        }

        return Boolean.FALSE;
    }

    private Boolean sendMessageToLora(String data, String clientId) {
        LoraProxy.sendMessage(clientId, data);
        return Boolean.TRUE;

    }

    private class IncomingMessageHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SERVICE_REGISTER_CLIENT:
                    String clientId = msg.obj.toString();
                    clientMap.put(clientId, msg.replyTo);
                    break;
                case SERVICE_UNREGISTER_CLIENT:
                    clientMap.remove(msg.obj.toString());
                    break;
                case SERVICE_MESSAGE:
                    String message = msg.getData().getString("message");
                    sendMessageToLora(message, msg.obj.toString());
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

}
