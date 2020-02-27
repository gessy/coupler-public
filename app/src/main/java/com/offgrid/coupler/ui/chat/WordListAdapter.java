package com.offgrid.coupler.ui.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.data.domain.Chat;

import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    class WordViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView message;

        private WordViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.chat_list_title);
            message = itemView.findViewById(R.id.chat_list_message);
        }

        public void update(Chat chat) {
            this.title.setText(chat.getTitle());
            this.message.setText(chat.getMessage());

        }

    }

    private final LayoutInflater mInflater;
    private List<Chat> chats;

    WordListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.chat_list_item, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        if (chats != null) {
            Chat current = chats.get(position);
            holder.update(current);
        } else {
            // Covers the case of data not being ready yet.
            holder.update(Chat.getEmpty());
        }
    }

    void setWords(List<Chat> chats){
        this.chats = chats;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (chats != null)
            return chats.size();
        else return 0;
    }
}
