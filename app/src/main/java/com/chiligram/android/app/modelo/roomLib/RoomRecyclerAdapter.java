package com.chiligram.android.app.modelo.roomLib;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chiligram.android.app.Controlador.Chilligram;
import com.chiligram.android.app.R;
import com.chiligram.android.app.modelo.Message;
import com.chiligram.android.app.modelo.Room;

import java.text.SimpleDateFormat;
import java.util.List;

public class RoomRecyclerAdapter extends RecyclerView.Adapter {

    private List<Room> roomList;
    private Context context;
    private int selectedPosition;

    public RoomRecyclerAdapter(Context context,List<Room> roomList){
        this.context = context;
        this.roomList = roomList;
    }


    public int getSelectedPosition(){
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new RoomRecyclerAdapter.roomHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
        Room r = roomList.get(i);
        ((RoomRecyclerAdapter.roomHolder) holder).bind(r);
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }



    private class roomHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewLastMessage;
        ImageView imageViewRoom;
        LinearLayout l;

        roomHolder(View itemView) {
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.textViewRoomName);
            textViewLastMessage = (TextView) itemView.findViewById(R.id.textViewLastMessage);
            imageViewRoom = (ImageView) itemView.findViewById(R.id.imageViewRoom);
        }

        void bind(Room room) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            textViewName.setText(room.getName());
            imageViewRoom.setImageResource(R.mipmap.ic_launcher_round);
            //roomLogo.setImageBitmap(room.getRoomAvatar());

            Message m = room.getLastMessage();
            String lastMessage = "";

            if(m.getType().equals("m.room.member")) {
                if (m.getContent().getMembership().equals("join")) {
                    lastMessage = m.getSenderUserName() + " se unió";
                } else if (m.getContent().getMembership().equals("leave")) {
                    lastMessage = m.getSenderUserName() + " salió";
                } else if (m.getContent().getMembership().equals("invite"))
                    lastMessage = m.getSenderUserName() + " invitó a " + m.getContent().getDisplayName();
            }else if(m.getType().equals("m.room.name")){
                if(m.getContent().getName().equals("")){
                    lastMessage = m.getSenderUserName()+" borró el nombre de la sala";
                }else{
                    lastMessage = m.getSenderUserName()+" cambió el nombre de la sala a "+m.getContent().getName();
                }
            }else if(m.getType().equals("m.room.message")) {

                if (m.getSender().equals(Chilligram.getUnicaInstancia().getCurrentUser().getUserId())) {
                    lastMessage = "Tú : " + m.getContent().getBody();
                } else {
                    lastMessage = m.getSenderUserName() + " : " + m.getContent().getBody();
                }
            }

            if(lastMessage.length() > 30){
                String aux = lastMessage.substring(0,27)+"...";
                lastMessage = aux;
            }

            textViewName.setTextColor(Color.BLACK);
            textViewName.setText(room.getName());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                textViewLastMessage.setText(Html.fromHtml(lastMessage,  Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
            } else {
                textViewLastMessage.setText(Html.fromHtml(lastMessage), TextView.BufferType.SPANNABLE);
            }
        }
    }
}
