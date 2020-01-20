package com.chiligram.android.app.modelo.messagesLib;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chiligram.android.app.Controlador.Chilligram;
import com.chiligram.android.app.R;
import com.chiligram.android.app.apiTools.APIUtils;
import com.chiligram.android.app.modelo.Message;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SentAudioMessageHolder extends RecyclerView.ViewHolder {



    TextView timeText,duracion;
    ImageButton play,stop;
    MediaPlayer mPlayer;
    SeekBar seekBar;
    Timer timer;
    File audio;



    public SentAudioMessageHolder(View itemView) {
        super(itemView);
        timeText = (TextView) itemView.findViewById(R.id.message_time);
        play = (ImageButton) itemView.findViewById(R.id.play);
        stop = (ImageButton) itemView.findViewById(R.id.stop);
        seekBar = (SeekBar) itemView.findViewById(R.id.seekbar);
        duracion = (TextView)itemView.findViewById(R.id.duration);
     }

     private String getTime(int duration){

         String time = String.format("%02d:%02d",
                 TimeUnit.MILLISECONDS.toMinutes(duration),
                 TimeUnit.MILLISECONDS.toSeconds(duration) -
                         TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))
         );
         return time;
     }

    void bind(final Message message) {


        timeText.setText(Utils.formatTime(message.getOriginServerTs()));

        audio = new File(Chilligram.getUnicaInstancia().getAudios(), message.getContent().getBody());

        String url = message.getContent().getUrl();
        String serverName = url.split("mxc://")[1].split("/")[0];
        String mediaId = url.split("mxc://")[1].split("/")[1];
        if(!audio.exists()) {
            APIUtils.getAPIService().getMedia(serverName, mediaId, Chilligram.getUnicaInstancia().getAccessToken()).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    InputStream is = response.body().byteStream();
                    audio = new File(Chilligram.getUnicaInstancia().getAudios(), message.getContent().getBody());
                    try {
                        audio.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Utils.isIntoFile(is, audio);
                    mPlayer = MediaPlayer.create(itemView.getContext(),Uri.fromFile(audio));
                    seekBar.setMax(mPlayer.getDuration());
                    duracion.setText("00:00/"+getTime(mPlayer.getDuration()));
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mPlayer != null && fromUser){
                    mPlayer.seekTo(progress * 1000);
                }
            }
        });





        timer = new Timer();
        final Handler handler = new Handler();
        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(mPlayer != null){
                            int mCurrentPosition = mPlayer.getCurrentPosition() / 1000;
                            seekBar.setProgress(mCurrentPosition);
                            //duracion.setText(mPlayer.getCurrentPosition()/1000+"/"+mPlayer.getDuration()/1000);
                            duracion.setText(getTime(mPlayer.getCurrentPosition())+"/"+getTime(mPlayer.getDuration()));
                        }
                    }
                });
            }
        };
        timer.schedule(task,0,1000);




        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer = MediaPlayer.create(itemView.getContext(),Uri.fromFile(audio));
                seekBar.setMax(mPlayer.getDuration());
                duracion.setText("00:00/"+getTime(mPlayer.getDuration()));
                mPlayer.start();
                stop.setVisibility(View.VISIBLE);
                play.setVisibility(View.INVISIBLE);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.stop();
                duracion.setText("00:00/"+getTime(mPlayer.getDuration()));
                stop.setVisibility(View.INVISIBLE);
                play.setVisibility(View.VISIBLE);
            }
        });

    }
}
