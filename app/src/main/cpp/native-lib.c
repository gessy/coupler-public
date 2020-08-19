#define TAG "coupler-native"

#include <jni.h>

#include <sys/stat.h>
#include <sys/file.h>
#include <string.h>
#include <sys/mman.h>

#include <unistd.h>
#include <stdlib.h>

#include "common.h"
#include "logger.h"


shm_info_t shm = {.shm_fd = -1, .lock_fd = -1};

static void file_mutex_lock(int fd) {
    lockf(fd, F_LOCK, 0);
}

static void file_mutex_unlock(int fd) {
    lockf(fd, F_ULOCK, 0);
}


static jobject cether_to_jether(ether_msg_t *msg, JNIEnv *env) {
    jclass clazz = (*env)->FindClass(env, "com/offgrid/coupler/core/model/jni/EtherMessageJNI");
    jmethodID constructor = (*env)->GetMethodID(env, clazz, "<init>", "()V");
    jobject obj = (*env)->NewObject(env, clazz, constructor);

    jfieldID fid_data = (*env)->GetFieldID(env, clazz, "data", "Ljava/lang/String;");

    jstring data = (*env)->NewStringUTF(env, msg->data);
    (*env)->SetObjectField(env, obj, fid_data, data);

    jfieldID fid_gid = (*env)->GetFieldID(env, clazz, "gid", "Ljava/lang/String;");
    jstring gid = (*env)->NewStringUTF(env, msg->uid);
    (*env)->SetObjectField(env, obj, fid_gid, gid);

    jfieldID fid_type = (*env)->GetFieldID(env, clazz, "type", "I");
    jint number = msg->type;
    (*env)->SetIntField(env, obj, fid_type, number);

    return obj;
}



JNIEXPORT jobject JNICALL
Java_com_offgrid_coupler_core_proxy_LoraProxy_etherMessage(JNIEnv *env, jclass thiz) {
    jobject object = NULL;

    file_mutex_lock(shm.lock_fd);

    if (shm.map->cmd == SHM_MSG_FOR_IN) {
        object = cether_to_jether(&(shm.map->msg), env);
        shm.map->cmd = SHM_MSG_NONE;
    }

    file_mutex_unlock(shm.lock_fd);

    return object;
}


JNIEXPORT jboolean JNICALL
Java_com_offgrid_coupler_core_proxy_LoraProxy_sendMessage(JNIEnv *env, jclass thiz,
                                                                    jstring j_uid, jstring j_data) {

    if (shm.map->cmd != SHM_MSG_NONE) {
        return JNI_FALSE;
    }

    file_mutex_lock(shm.lock_fd);

    const char *c_uid = (*env)->GetStringUTFChars(env, j_uid, 0);
    const char *c_data = (*env)->GetStringUTFChars(env, j_data, 0);

    shm_data_t data;
    data.cmd = SHM_MSG_FOR_OUT;
    data.msg.type = 2;
    memcpy(data.msg.uid, c_uid, strlen(c_uid));
    memcpy(data.msg.data, c_data, strlen(c_data));

    memcpy(shm.map, &data, sizeof(shm_data_t));

    file_mutex_unlock(shm.lock_fd);

    (*env)->ReleaseStringUTFChars(env, j_uid, c_uid);
    (*env)->ReleaseStringUTFChars(env, j_data, c_data);

    return JNI_TRUE;
}


JNIEXPORT jboolean JNICALL
Java_com_offgrid_coupler_core_proxy_LoraProxy_shmInit(JNIEnv *env, jclass thiz) {
    shm.lock_fd = open(SHM_LOCK_FILE, O_RDWR);
    shm.shm_fd = open(SHM_DATA_FILE, O_RDWR);
    shm.map = (shm_data_t *) mmap(NULL, sizeof(shm_data_t), PROT_READ | PROT_WRITE, MAP_SHARED,
                                  shm.shm_fd, 0);
}


JNIEXPORT void JNICALL
Java_com_offgrid_coupler_core_proxy_LoraProxy_shmFree(JNIEnv *env, jclass thiz) {
    munmap(shm.map, sizeof(shm_data_t));
    close(shm.lock_fd);
    close(shm.shm_fd);
}

