package me.wilsonmitchell.yakyik;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by wilsonmitchell on 11/5/14.
 */
public class MessageAdapter extends ArrayAdapter<Message> {
    private LayoutInflater mInflater;

    public MessageAdapter(Context context, List<Message> listItems) {
        super(context, 0, listItems);
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Message message = getItem(position);
        final MessageViewHolder holder;
        if (convertView == null) {
            RelativeLayout container = (RelativeLayout) mInflater.inflate(R.layout.message_list_row, parent, false);
            holder = MessageViewHolder.create(container);
            container.setTag(holder);
        }
        else {
            holder = (MessageViewHolder)convertView.getTag();
        }
        holder.authorTextView.setText(message.getAuthor());
        holder.messageTextView.setText(message.getMessage());
        holder.scoreTextView.setText(String.valueOf(message.getScore()));
        holder.timePostedTextView.setText("Posted at " + message.getTimePostedMills());
        holder.upvoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message.incrementScore();
                holder.scoreTextView.setText(String.valueOf(message.getScore()));
            }
        });
        holder.downvoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message.decrementScore();
                holder.scoreTextView.setText(String.valueOf(message.getScore()));
            }
        });
        return holder.rootView;
    }

    private static class MessageViewHolder {
        public final RelativeLayout rootView;
        public final TextView authorTextView;
        public final TextView messageTextView;
        public final TextView scoreTextView;
        public final TextView timePostedTextView;
        public final Button upvoteButton;
        public final Button downvoteButton;

        private MessageViewHolder(RelativeLayout rootView, TextView authorTextView, TextView messageTextView,
                                  TextView scoreTextView, TextView timePostedTextView, Button upvoteButton, Button downvoteButton) {
            this.rootView = rootView;
            this.authorTextView = authorTextView;
            this.messageTextView = messageTextView;
            this.scoreTextView = scoreTextView;
            this.timePostedTextView = timePostedTextView;
            this.upvoteButton = upvoteButton;
            this.downvoteButton = downvoteButton;
        }

        public static MessageViewHolder create(RelativeLayout rootView) {
            TextView authorTextView = (TextView)rootView.findViewById(R.id.authorTextView);
            TextView messageTextView = (TextView)rootView.findViewById(R.id.messageTextView);
            TextView scoreTextView = (TextView)rootView.findViewById(R.id.scoreTextView);
            TextView timePostedTextView = (TextView)rootView.findViewById(R.id.timePostedTextView);
            Button upvoteButton = (Button)rootView.findViewById(R.id.upvoteButton);
            Button downvoteButton = (Button)rootView.findViewById(R.id.downvoteButton);
            return new MessageViewHolder(rootView, authorTextView, messageTextView, scoreTextView, timePostedTextView, upvoteButton, downvoteButton);
        }
    }
}
