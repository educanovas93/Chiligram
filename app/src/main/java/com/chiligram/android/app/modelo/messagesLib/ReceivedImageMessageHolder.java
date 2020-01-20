package com.chiligram.android.app.modelo.messagesLib;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chiligram.android.app.BuildConfig;
import com.chiligram.android.app.Controlador.Chilligram;
import com.chiligram.android.app.R;
import com.chiligram.android.app.apiTools.APIUtils;
import com.chiligram.android.app.modelo.Message;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReceivedImageMessageHolder extends RecyclerView.ViewHolder {

    TextView timeText, nameText;
    ImageView profileImage,body;
    File img;

    public ReceivedImageMessageHolder(View itemView) {
        super(itemView);
        body = (ImageView) itemView.findViewById(R.id.image_message_body);
        timeText = (TextView) itemView.findViewById(R.id.text_message_time);
        nameText = (TextView) itemView.findViewById(R.id.name);
        profileImage = (ImageView) itemView.findViewById(R.id.image_message_profile);
    }

    void bind(final Message message) {
        // Format the stored timestamp into a readable String using method.
        timeText.setText(Utils.formatTime(message.getOriginServerTs()));
        nameText.setTextColor(Color.parseColor(Chilligram.getUnicaInstancia().getColor(message.getSenderUserName())));
        nameText.setText(message.getSenderUserName());

        img = new File(Chilligram.getUnicaInstancia().getImages(), message.getContent().getBody());

        if(img.exists()){
            Utils.displayImageFromFile(itemView.getContext(),img,body);
        }else{
            String url = message.getContent().getUrl();

            String serverName = url.split("mxc://")[1].split("/")[0];
            String mediaId = url.split("mxc://")[1].split("/")[1];
            APIUtils.getAPIService().getMedia(serverName, mediaId, Chilligram.getUnicaInstancia().getAccessToken()).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    InputStream is = response.body().byteStream();
                    try {
                        img.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Utils.isIntoFile(is, img);
                    Utils.displayImageFromFile(itemView.getContext(),img,body);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }


        body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri imgUri = FileProvider.getUriForFile(itemView.getContext(), BuildConfig.APPLICATION_ID + ".provider", img);
                Intent intent = new Intent(Intent.ACTION_VIEW, imgUri).addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(imgUri, "image/*");
                itemView.getContext().startActivity(intent);
            }
        });
    }
}
