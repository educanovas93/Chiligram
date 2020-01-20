package com.chiligram.android.app.modelo.messagesLib;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chiligram.android.app.R;
import com.chiligram.android.app.modelo.Message;

public class SentTextMessageHolder extends RecyclerView.ViewHolder {
    TextView messageText, timeText;

    SentTextMessageHolder(View itemView) {
        super(itemView);

        messageText = (TextView) itemView.findViewById(R.id.text_message_body);
        timeText = (TextView) itemView.findViewById(R.id.text_message_time);
    }

    void bind(Message message) {


        if(message.getContent().getBody()== null) {
            messageText.setTextColor(Color.parseColor("#C0C0C0"));
            messageText.setText("∅ eliminaste este mensaje");
        }else {
            messageText.setText(message.getContent().getBody());
        }

        // Format the stored timestamp into a readable String using method.
        timeText.setText(Utils.formatTime(message.getOriginServerTs()));
    }
}