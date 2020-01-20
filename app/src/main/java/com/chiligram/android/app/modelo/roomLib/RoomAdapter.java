package com.chiligram.android.app.modelo.roomLib;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chiligram.android.app.Controlador.Chilligram;
import com.chiligram.android.app.R;
import com.chiligram.android.app.modelo.Message;
import com.chiligram.android.app.modelo.Room;
import com.chiligram.android.app.modelo.messagesLib.Utils;

import java.util.List;

public class RoomAdapter extends BaseAdapter {
    Activity context;
    List<Room> rooms;
    private static LayoutInflater inflater = null;

    public RoomAdapter(Activity context,List<Room> rooms){
        this.context = context;
        this.rooms = rooms;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return rooms.size();
    }

    @Override
    public Object getItem(int position) {
        return rooms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, final View convertView, final ViewGroup parent) {
        View itemView = convertView;
        itemView = (itemView == null) ? inflater.inflate(R.layout.list_item,null): itemView;
        TextView textViewName = (TextView) itemView.findViewById(R.id.textViewRoomName);
        TextView textViewLastMessage = (TextView) itemView.findViewById(R.id.textViewLastMessage);
        final ImageView imageViewRoom = (ImageView) itemView.findViewById(R.id.imageViewRoom);
        TextView notifications = (TextView) itemView.findViewById(R.id.notifications);
        TextView lastMessageTime = (TextView) itemView.findViewById(R.id.lastMessageTime);
        Room selectedRoom = rooms.get(position);


        if(selectedRoom.getUnreadNotifications().getNotificationCount() <= 0){
            notifications.setVisibility(View.GONE);
        }else{
            notifications.setVisibility(View.VISIBLE);
            notifications.setText(String.valueOf(selectedRoom.getUnreadNotifications().getNotificationCount()));
        }

        textViewName.setText(selectedRoom.getName());
        Message m = selectedRoom.getLastMessage();
        //textViewLastMessage.setText(m.getSender()+m.getContent().getBody());
        String name = "<strong>"+selectedRoom.getName()+"</strong>";
        //String lastMessage = "Tu: ultimo mensaje";
        String lastMessage = "";

        System.out.println("TIPOOOO : "+m.getType());
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
        }else if(m.getType().equals("m.room.avatar")){
                lastMessage = m.getSenderUserName()+" cambió el icono de la sala";
        }

        if(lastMessage.length() > 30){
            String aux = lastMessage.substring(0,27)+"...";
            lastMessage = aux;
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textViewName.setTextColor(Color.BLACK);
            textViewName.setText(Html.fromHtml(name,  Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
            textViewLastMessage.setText(Html.fromHtml(lastMessage,  Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
        } else {
            textViewName.setTextColor(Color.BLACK);
            textViewName.setText(Html.fromHtml(name), TextView.BufferType.SPANNABLE);
            textViewLastMessage.setText(Html.fromHtml(lastMessage), TextView.BufferType.SPANNABLE);
        }

        lastMessageTime.setText(Utils.formatTime(m.getOriginServerTs()));
        imageViewRoom.setImageResource(R.mipmap.ic_launcher_round);

        /*
        APIUtils.getAPIService().getRoomEvent(selectedRoom.getId(),"m.room.avatar",Chilligram.getUnicaInstancia().getAccessToken()).enqueue(new Callback<Content>() {
            @Override
            public void onResponse(Call<Content> call, Response<Content> response) {
                if(response.body() != null) {
                    if (response.body().getUrlRoomAvatar() != null) {
                        String url = response.body().getUrlRoomAvatar();

                        String serverName = url.split("mxc://")[1].split("/")[0];
                        String mediaId = url.split("mxc://")[1].split("/")[1];

                        APIUtils.getAPIService().getMedia(serverName, mediaId, Chilligram.getUnicaInstancia().getAccessToken()).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                InputStream is = response.body().byteStream();
                                Bitmap bmp = BitmapFactory.decodeStream(is);
                                Utils.displayRoundImageFromBitMap(context, bmp, imageViewRoom);
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                    }
                }else{
                    imageViewRoom.setImageResource(R.mipmap.ic_launcher_round);
                }

            }

            @Override
            public void onFailure(Call<Content> call, Throwable t) {

            }
        });
        */






















        return itemView;
    }
}
