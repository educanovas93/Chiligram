package com.chiligram.android.app.modelo.messagesLib;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chiligram.android.app.Controlador.Chilligram;
import com.chiligram.android.app.R;
import com.chiligram.android.app.modelo.Message;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_TEXT_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_TEXT_MESSAGE_RECEIVED = 2;
    private static final int VIEW_TYPE_MESSAGE_CONTROL = 3;
    private static final int VIEW_TYPE_IMAGE_MESSAGE_SENT = 4;
    private static final int VIEW_TYPE_IMAGE_MESSAGE_RECEIVED = 5;
    private static final int VIEW_TYPE_AUDIO_MESSAGE_SENT = 6;
    private static final int VIEW_TYPE_AUDIO_MESSAGE_RECEIVED = 7;
    private static final int VIEW_TYPE_VIDEO_MESSAGE_SENT = 8;
    private static final int VIEW_TYPE_VIDEO_MESSAGE_RECEIVED = 9;
    private static final int VIEW_TYPE_LOCATION_MESSAGE_SENT = 10;
    private static final int VIEW_TYPE_LOCATION_MESSAGE_RECEIVED = 11;
    private static final int VIEW_TYPE_FILE_MESSSAGE_SENT = 12;
    private static final int VIEW_TYPE_FILE_MESSAGE_RECEIVED = 13;
    private static final int NULL = 0;


    private Context mContext;
    private List<Message> mMessageList;

    public MessageListAdapter(Context context, List<Message> messageList) {
        mContext = context;
        mMessageList = messageList;
        System.out.print("SIZEMENSAJES :"+messageList.size());
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        Message m = mMessageList.get(position);
        System.out.println("LLEGAMOSTIPOCONTROL"+m.getType());

        if (m.getSender().equals(Chilligram.getUnicaInstancia().getCurrentUser().getUserId()) && m.getType().equals("m.room.message")) {
            // If the current user is the sender of the message
            switch (m.getContent().getMsgtype()){
                case "m.text":
                    return VIEW_TYPE_TEXT_MESSAGE_SENT;
                case "m.image":
                    return VIEW_TYPE_IMAGE_MESSAGE_SENT;
                case "m.video":
                    return VIEW_TYPE_VIDEO_MESSAGE_SENT;
                case "m.audio":
                    return VIEW_TYPE_AUDIO_MESSAGE_SENT;
                case "m.location":
                    return VIEW_TYPE_LOCATION_MESSAGE_SENT;
                case "m.file":
                    return VIEW_TYPE_FILE_MESSSAGE_SENT;
            }
            return VIEW_TYPE_MESSAGE_CONTROL;
        } else if(m.getType().equals("m.room.message") && !m.getSender().equals(Chilligram.getUnicaInstancia().getCurrentUser().getUserId())) {
            // If some other user sent the message
            switch (m.getContent().getMsgtype()){
                case "m.text":
                    return VIEW_TYPE_TEXT_MESSAGE_RECEIVED;
                case "m.image":
                    return VIEW_TYPE_IMAGE_MESSAGE_RECEIVED;
                case "m.video":
                    return VIEW_TYPE_VIDEO_MESSAGE_RECEIVED;
                case "m.audio":
                    return VIEW_TYPE_AUDIO_MESSAGE_RECEIVED;
                case "m.location":
                    return VIEW_TYPE_LOCATION_MESSAGE_RECEIVED;
                case "m.file":
                    return VIEW_TYPE_FILE_MESSAGE_RECEIVED;
            }
            return VIEW_TYPE_MESSAGE_CONTROL;
        }else {
            return VIEW_TYPE_MESSAGE_CONTROL;
        }
    }

    // Inflates the appropriate layout according to the ViewType.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case VIEW_TYPE_TEXT_MESSAGE_SENT:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_message_sent, parent, false);
                return new SentTextMessageHolder(view);
            case VIEW_TYPE_TEXT_MESSAGE_RECEIVED:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_message_received, parent, false);
                return new ReceivedTextMessageHolder(view);
            case VIEW_TYPE_IMAGE_MESSAGE_SENT:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.image_sent, parent, false);
                return new SentImageMessageHolder(view);
            case VIEW_TYPE_IMAGE_MESSAGE_RECEIVED:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.image_received, parent, false);
                return new ReceivedImageMessageHolder(view);
            case VIEW_TYPE_VIDEO_MESSAGE_SENT:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.video_sent, parent, false);
                return new SentVideoMessageHolder(view);
            case VIEW_TYPE_VIDEO_MESSAGE_RECEIVED:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.video_received, parent, false);
                return new ReceivedVideoMessageHolder(view);
            case VIEW_TYPE_AUDIO_MESSAGE_SENT:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.audio_sent, parent, false);
                return new SentAudioMessageHolder(view);
            case VIEW_TYPE_AUDIO_MESSAGE_RECEIVED:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.audio_received, parent, false);
                return new ReceivedAudioMessageHolder(view);
            case VIEW_TYPE_LOCATION_MESSAGE_SENT:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.location_sent, parent, false);
                return new SentLocationMessageHolder(view);
            case VIEW_TYPE_LOCATION_MESSAGE_RECEIVED:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.location_received, parent, false);
                return new ReceivedLocationMessageHolder(view);
            case VIEW_TYPE_FILE_MESSSAGE_SENT:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.file_sent, parent, false);
                return new SentFileMessageHolder(view);
            case VIEW_TYPE_FILE_MESSAGE_RECEIVED:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.file_received, parent, false);
                return new ReceivedFileMessageHolder(view);
            case VIEW_TYPE_MESSAGE_CONTROL:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_message_control,parent,false);
                return new ControlMessageHolder(view);

        }
        return null;
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = mMessageList.get(position);
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_TEXT_MESSAGE_SENT:
                ((SentTextMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_TEXT_MESSAGE_RECEIVED:
                ((ReceivedTextMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_IMAGE_MESSAGE_SENT:
                ((SentImageMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_IMAGE_MESSAGE_RECEIVED:
                ((ReceivedImageMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_VIDEO_MESSAGE_SENT:
                ((SentVideoMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_VIDEO_MESSAGE_RECEIVED:
                ((ReceivedVideoMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_AUDIO_MESSAGE_SENT:
                ((SentAudioMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_AUDIO_MESSAGE_RECEIVED:
                ((ReceivedAudioMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_LOCATION_MESSAGE_SENT:
                ((SentLocationMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_LOCATION_MESSAGE_RECEIVED:
                ((ReceivedLocationMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_CONTROL:
                ((ControlMessageHolder) holder).bind(message);
                break;
        }
    }

}