package com.chiligram.android.app.modelo.messagesLib;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chiligram.android.app.Controlador.Chilligram;
import com.chiligram.android.app.R;
import com.chiligram.android.app.modelo.Message;

import java.io.File;

public class ReceivedTextMessageHolder extends RecyclerView.ViewHolder {
    TextView messageText, timeText, nameText;
    ImageView profileImage;
    File avatar;

    ReceivedTextMessageHolder(View itemView) {
        super(itemView);

        messageText = (TextView) itemView.findViewById(R.id.text_message_body);
        timeText = (TextView) itemView.findViewById(R.id.text_message_time);
        nameText = (TextView) itemView.findViewById(R.id.text_message_name);
        profileImage = (ImageView) itemView.findViewById(R.id.image_message_profile);
    }

    void bind(final Message message) {
        if(message.getContent().getBody() == null){
            messageText.setTextColor(Color.parseColor("#C0C0C0"));
            messageText.setText("∅ eliminó este mensaje");
        }else{
            messageText.setText(message.getContent().getBody());
        }

        // Format the stored timestamp into a readable String using method.
        timeText.setText(Utils.formatTime(message.getOriginServerTs()));
        nameText.setTextColor(Color.parseColor(Chilligram.getUnicaInstancia().getColor(message.getSenderUserName())));
        nameText.setText(message.getSenderUserName());

        /*
        for (Member m : Chilligram.getUnicaInstancia().getCurrentRoom().getMembers()){
            if(m.getDisplayName().equals(message.getSenderUserName())){
                Bitmap bmp = BitmapFactory.decodeStream(m.getAvatarStream());
                Utils.displayRoundImageFromBitMap(itemView.getContext(), bmp, profileImage);
            }
        }
        */



        /*
        APIUtils.getAPIService().getProfileInfo(message.getSender(),Chilligram.getUnicaInstancia().getAccessToken()).enqueue(new Callback<Member>() {
            @Override
            public void onResponse(Call<Member> call, Response<Member> response) {
                if(response.body().getAvatarUrl() != null) {
                    String url = response.body().getAvatarUrl();

                    String serverName = url.split("mxc://")[1].split("/")[0];
                    String mediaId = url.split("mxc://")[1].split("/")[1];

                    APIUtils.getAPIService().getMedia(serverName, mediaId, Chilligram.getUnicaInstancia().getAccessToken()).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            InputStream is = response.body().byteStream();
                            Bitmap bmp = BitmapFactory.decodeStream(is);
                            Utils.displayRoundImageFromBitMap(itemView.getContext(), bmp, profileImage);
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                }else{
                    Bitmap icon = BitmapFactory.decodeResource(itemView.getResources(),R.drawable.noav);
                    Utils.displayRoundImageFromBitMap(itemView.getContext(),icon,profileImage);

                }

            }

            @Override
            public void onFailure(Call<Member> call, Throwable t) {

            }
        });
        */

    }
}