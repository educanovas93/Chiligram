package com.chiligram.android.app.modelo.messagesLib;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class SentVideoMessageHolder extends RecyclerView.ViewHolder {

    TextView timeText;
    ImageView body;
    ImageButton download,play;


    public SentVideoMessageHolder(View itemView) {
        super(itemView);
        body = (ImageView) itemView.findViewById(R.id.video_message_body);
        timeText = (TextView) itemView.findViewById(R.id.message_time);
        download = (ImageButton)itemView.findViewById(R.id.download);
        play = (ImageButton) itemView.findViewById(R.id.play);
    }

    void bind(final Message message) {
        // Format the stored timestamp into a readable String using method.
        timeText.setText(Utils.formatTime(message.getOriginServerTs()));


        //Cogemos el thumbnail si lo hay
        if(message.getContent().getInfo() != null && message.getContent().getInfo().getThumbnailUrl() != null) {
            String url = message.getContent().getInfo().getThumbnailUrl();
            String serverName = url.split("mxc://")[1].split("/")[0];
            String mediaId = url.split("mxc://")[1].split("/")[1];
            APIUtils.getAPIService().getMedia(serverName, mediaId, Chilligram.getUnicaInstancia().getAccessToken()).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    InputStream is = response.body().byteStream();
                    Bitmap bmp = BitmapFactory.decodeStream(is);
                    Utils.displayImageFromBitMap(itemView.getContext(), bmp, body);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }

        File video = new File(Chilligram.getUnicaInstancia().getVideos(), message.getContent().getBody());
        if (video.exists()) {
            play.setVisibility(View.VISIBLE);
            download.setVisibility(View.GONE);
        } else {
            download.setVisibility(View.VISIBLE);
            play.setVisibility(View.GONE);

        }

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(itemView.getContext(), "El video se está descargando", Toast.LENGTH_SHORT).show();
                String url = message.getContent().getUrl();
                String serverName = url.split("mxc://")[1].split("/")[0];
                String mediaId = url.split("mxc://")[1].split("/")[1];
                APIUtils.getAPIService().getMedia(serverName, mediaId, Chilligram.getUnicaInstancia().getAccessToken()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        InputStream is = response.body().byteStream();
                        File f = new File(Chilligram.getUnicaInstancia().getVideos(), message.getContent().getBody());
                        try {
                            f.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Utils.isIntoFile(is, f);
                        Toast.makeText(itemView.getContext(), "El vídeo se ha descargado correctamente", Toast.LENGTH_SHORT).show();
                        play.setVisibility(View.VISIBLE);
                        download.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File video = new File(Chilligram.getUnicaInstancia().getVideos(), message.getContent().getBody());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(video.getPath()));
                intent.setDataAndType(Uri.parse(video.getPath()), "video/*");
                itemView.getContext().startActivity(intent);
            }
        });

    }
        //ahora si le damos al boton play, descarga el video y lo reproduce
}
