package com.chiligram.android.app.modelo.messagesLib;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chiligram.android.app.R;
import com.chiligram.android.app.modelo.Message;

public class ControlMessageHolder extends RecyclerView.ViewHolder {
    TextView messageText;

    ControlMessageHolder(View itemView) {
        super(itemView);

        messageText = (TextView) itemView.findViewById(R.id.text_message_body);
    }

    void bind(Message m) {
        System.out.println("IDPETADO :" + m.getEventId() + m.getType());
        if (m.getType().equals("m.room.member")) {
            if (m.getContent().getMembership().equals("join")) {
                messageText.setText(m.getSenderUserName() + " "+itemView.getContext().getString(R.string.join));
            } else if (m.getContent().getMembership().equals("leave")) {
                messageText.setText(m.getSenderUserName() + " "+itemView.getContext().getString(R.string.leave));
            } else if (m.getContent().getMembership().equals("invite"))
                messageText.setText(m.getSenderUserName() + " "+itemView.getContext().getString(R.string.invite)+" " + m.getContent().getDisplayName());
        } else if (m.getType().equals("m.room.name")) {
            if (m.getContent().getName().equals("")) {
                messageText.setText(m.getSenderUserName() + " "+itemView.getContext().getString(R.string.delete_room_name));
            } else {
                messageText.setText(m.getSenderUserName() + " "+itemView.getContext().getString(R.string.room_name_change)+" " + m.getContent().getName());
            }
        }else if(m.getType().equals("m.room.avatar")) {
            messageText.setText(m.getSenderUserName() + " "+itemView.getContext().getString(R.string.room_avatar_change));

        }else if(m.getType().equals("m.room.create")){
            messageText.setText(m.getSenderUserName() + " "+itemView.getContext().getString(R.string.room_create));
        } else {
            //messageText.setText("Mensaje no contemplado de tipo : " + m.getType());
            messageText.setVisibility(View.GONE);
        }
    }
}