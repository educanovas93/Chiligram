package com.chiligram.android.app.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.chiligram.android.app.BuildConfig;
import com.chiligram.android.app.Controlador.Chilligram;
import com.chiligram.android.app.R;
import com.chiligram.android.app.apiTools.APIUtils;
import com.chiligram.android.app.modelo.Content;
import com.chiligram.android.app.modelo.Marker;
import com.chiligram.android.app.modelo.Message;
import com.chiligram.android.app.modelo.RoomMessages;
import com.chiligram.android.app.modelo.UnreadNotifications;
import com.chiligram.android.app.modelo.messagesLib.MessageListAdapter;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    private final int VIDEO_REQUEST_CODE = 100;
    private final int IMAGE_REQUEST_CODE = 101;
    private RecyclerView MessageRecycler;
    private MessageListAdapter adapter;
    private Chilligram controlador = Chilligram.getUnicaInstancia();
    private List<Message> messageList= new LinkedList<>();
    private Context context = this;
    private TextView mNuevos;
    private Button scrollButton;
    private TextView roomName;
    private ImageView roomAvatar;
    private TextView botonChats;
    private ImageView attachButton;
    private PopupMenu menu;
    private Location currentLocation;
    private LocationManager lm;
    private LocationListener locationListener;
    private MediaRecorder mRecorder;
    private ImageButton recordAudio;
    private String mFileName;
    private String videoFileName;
    private String imageFileName;
    private File video;
    private File image;
    Button sendButton;
    EditText messageArea;
    Timer timer;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_message_list);

        botonChats = (TextView) findViewById(R.id.botonChats);
        roomAvatar = (ImageView) findViewById(R.id.roomAvatar);
        roomName = (TextView) findViewById(R.id.roomName);
        scrollButton = (Button) findViewById(R.id.button_scroll);
        mNuevos = (TextView) findViewById(R.id.mensajes_nuevos);
        attachButton = (ImageButton) findViewById(R.id.attach);



        MessageRecycler = (RecyclerView) findViewById(R.id.reyclerview_message_list);

        sendButton = (Button)findViewById(R.id.button_chatbox_send);
        messageArea = (EditText)findViewById(R.id.edittext_chatbox);
        recordAudio = (ImageButton)findViewById(R.id.record_audio);

        botonChats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        messageArea.addTextChangedListener(new TextWatcher() {

            // the user's changes are saved here
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                if(!messageArea.getText().toString().matches("")) {
                    attachButton.setVisibility(View.GONE);
                    recordAudio.setVisibility(View.GONE);
                }else{
                    recordAudio.setVisibility(View.VISIBLE);
                    attachButton.setVisibility(View.VISIBLE);
                    System.out.println("Estamos dentro y esto deberia estar visible");
                }
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
            }
        });

        recordAudio.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                //if Button is Pressed.! or user Id Holding Button
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    startRecording();

                    Toast.makeText(context, "Mantén pulsado para grabar", Toast.LENGTH_SHORT).show();

                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

                    //Do Nothing
                    stopRecording();

                    Toast.makeText(context, "Audio grabado.!", Toast.LENGTH_SHORT).show();
                }


                return false;
            }
        });


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO}, 2);
        }

        MessageRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public final void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (!recyclerView.canScrollVertically(-1)) {
                    onScrolledToTop();
                } else if (!recyclerView.canScrollVertically(1)) {
                    onScrolledToBottom();
                }
                if (dy < 0) {
                    onScrolledUp(dy);
                } else if (dy > 0) {
                    onScrolledDown(dy);
                }
            }

            private void onScrolledDown(int dy){

            }

            private void onScrolledUp(int dy) {
                scrollButton.setVisibility(View.VISIBLE);
            }

            private void onScrolledToTop() {
            }
            

            public void onScrolledToBottom() {
                scrollButton.setVisibility(View.GONE);
                mNuevos.setVisibility(View.GONE);
                markRead();
            }
            
        });

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentLocation = location;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };



        initLocation();


        enLocation();


        adjuntos();



        scrollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageRecycler.post(new Runnable() {
                    @Override
                    public void run() {
                        MessageRecycler.scrollToPosition(adapter.getItemCount() - 1);
                    }
                });
            }
        });


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();

                if(!messageText.equals("")) {
                    messageArea.setText("");

                    Content c = new Content();
                    c.setBody(messageText);
                    c.setMsgtype("m.text");
                    //c.setGeoUri("geo:48.85828,2.29449");
                    Random r = new Random();
                    Chilligram.getUnicaInstancia().setWrited(true);
                    int n = r.nextInt(1000000);
                    APIUtils.getAPIService().sendMessage(controlador.getCurrentRoom().getId(),String.valueOf(n),controlador.getAccessToken(),c).enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response){
                            System.out.println("CODIGO : "+response.code()+"otro : "+response.body().getEventId());
                            refreshRoom();
                        }

                        @Override
                        public void onFailure(Call<Message> call, Throwable t) {
                            System.out.println(t.fillInStackTrace());
                        }
                    });
                }
            }
        });

        final Handler handler = new Handler();
        timer = new Timer();
        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        UnreadNotifications u = controlador.getSyncRooms().getRooms().getJoin().get(controlador.getCurrentRoom().getId()).getUnreadNotifications();
                        mNuevos.setText(String.valueOf(u.getNotificationCount()));
                        if (scrollButton.getVisibility() == View.VISIBLE && u.getNotificationCount()>0) {
                            mNuevos.setVisibility(View.VISIBLE);
                        }
                        if(u.getNotificationCount() > 0) {
                            refreshRoomPeriodic();
                            System.out.println("TENEMOS NOTIFICACIONES EN LA SALA : "+u.getNotificationCount());

                        }
                    }
                });
            }
        };

        timer.schedule(task,1000,1000);

        botonChats.setText("< Chats");
        roomName.setText(controlador.getCurrentRoom().getName());
        roomAvatar.setImageResource(R.mipmap.ic_launcher_round);
        attachButton.setImageResource(R.drawable.clipo);


        refreshRoom();
        adapter = new MessageListAdapter(context, messageList);
        LinearLayoutManager lm = new LinearLayoutManager(context);
        lm.setStackFromEnd(true);
        MessageRecycler.setLayoutManager(lm);
        MessageRecycler.setAdapter(adapter);
        
    }

    public void refreshRoom(){
        APIUtils.getAPIService().getRoomMessages(controlador.getCurrentRoom().getId(),"0","b",controlador.getAccessToken()).enqueue(new Callback<RoomMessages>() {
            @Override
            public void onResponse(Call<RoomMessages> call, Response<RoomMessages> response) {
                List<Message> msg = new LinkedList<>(response.body().getMessageList());
                Collections.sort(msg);
                //Chilligram.getUnicaInstancia().setOldMessageList(msg);
                messageList.clear();
                for(Message m : msg){
                    messageList.add(m);
                    adapter.notifyDataSetChanged();
                    System.out.println(m.getSenderUserName()+" TIPO "+m.getType());
                }
                    MessageRecycler.post(new Runnable() {
                        @Override
                        public void run() {
                            MessageRecycler.scrollToPosition(adapter.getItemCount() - 1);
                        }
                    });
                markRead();
            }


            @Override
            public void onFailure(Call<RoomMessages> call, Throwable t) {
                System.out.println(t.fillInStackTrace());
            }
        });
    }
    public void refreshRoomPeriodic(){
        APIUtils.getAPIService().getRoomMessages(controlador.getCurrentRoom().getId(),"0","b",controlador.getAccessToken()).enqueue(new Callback<RoomMessages>() {
            @Override
            public void onResponse(Call<RoomMessages> call, Response<RoomMessages> response) {
                List<Message> msg = new LinkedList<>(response.body().getMessageList());
                Collections.sort(msg);
                //Chilligram.getUnicaInstancia().setOldMessageList(msg);
                messageList.clear();
                for (Message m : msg) {
                    messageList.add(m);
                    adapter.notifyDataSetChanged();
                }
                if (!MessageRecycler.canScrollVertically(1)) {
                    MessageRecycler.post(new Runnable() {
                        @Override
                        public void run() {
                            MessageRecycler.scrollToPosition(adapter.getItemCount() - 1);
                            markRead();
                        }
                    });
                }else{
                    //Aparece textview con notificaciones actuales y el onclick para bajar la el scroll
                    scrollButton.setVisibility(View.VISIBLE);
                }
            }


            @Override
            public void onFailure(Call<RoomMessages> call, Throwable t) {
                System.out.println(t.fillInStackTrace());
            }
        });
    }

    private void markRead(){
        Marker m = new Marker(messageList.get(messageList.size()-1).getEventId(),messageList.get(messageList.size()-1).getEventId());
        APIUtils.getAPIService().readMessage(controlador.getCurrentRoom().getId(),controlador.getAccessToken(),m).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {

            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                System.out.println(t.getCause());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        enLocation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        disLocation();
    }




    private void enLocation(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            currentLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            //Register for updates
            int time = 3000;
            float distance = 5;
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, time, distance, locationListener);
        }
    }

    private void disLocation(){
        lm.removeUpdates(locationListener);
    }

    private void adjuntos(){
        menu = new PopupMenu(this, attachButton);
        MenuInflater menuInflater = menu.getMenuInflater();
        menuInflater.inflate(R.menu.attach_menu, menu.getMenu());

        attachButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.show();
            }
        });

        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                try {
                    switch (item.getItemId()){
                        case R.id.location_item:
                            enviarUbicacion();
                            return true;
                        case R.id.imagen:
                            capturarImagen();
                            return true;
                        case R.id.video:
                            capturarVideo();
                            return true;
                        default:return false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        switch (requestCode){
            case VIDEO_REQUEST_CODE:
                if(resultCode == RESULT_OK){
                    Toast.makeText(context,"Video grabado correctamente", Toast.LENGTH_SHORT).show();
                    enviarVideo();
                }else{
                    Toast.makeText(context,"Captura de video fallida", Toast.LENGTH_SHORT).show();
                }
                break;
            case IMAGE_REQUEST_CODE:
                if(resultCode == RESULT_OK){
                    Toast.makeText(context,"Imagen tomada correctamente", Toast.LENGTH_SHORT).show();
                    enviarImagen();

                }else{
                    Toast.makeText(context,"Captura de imagen fallida", Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }

    private void capturarVideo(){
        videoFileName = Chilligram.getUnicaInstancia().getVideos().getAbsolutePath();
        String id = new String(UUID.randomUUID().toString());
        videoFileName += "/"+id + ".mp4";

        video = new File(videoFileName);
        try {
            video.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Uri video_uri = Uri.fromFile(video);
        Uri video_uri = FileProvider.getUriForFile(ChatActivity.this, BuildConfig.APPLICATION_ID + ".provider",video);
        Intent camera_intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        camera_intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);



        camera_intent.putExtra(MediaStore.EXTRA_OUTPUT,video_uri);
        camera_intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,0);
        camera_intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 549152000L);
        startActivityForResult(camera_intent,VIDEO_REQUEST_CODE);
    }



    private void enviarVideo(){
        RequestBody body = RequestBody.create(MediaType.parse("video/mp4"), video);
        APIUtils.getAPIService().uploadMedia(video.getName(),Chilligram.getUnicaInstancia().getAccessToken(),body).enqueue(new Callback<Content>() {
            @Override
            public void onResponse(Call<Content> call, Response<Content> response) {
                Content c = new Content();
                c.setBody(video.getName());
                c.setMsgtype("m.video");
                System.out.println("VIDEO URL "+response.body().getContentUri());
                c.setUrl(response.body().getContentUri());
                Random r = new Random();
                Chilligram.getUnicaInstancia().setWrited(true);
                int n = r.nextInt(1000000);
                //Toast.makeText(context, "Enviando video...", Toast.LENGTH_SHORT).show();
                final ProgressDialog pd = new ProgressDialog(context);
                pd.show();
                pd.setTitle("Eviando video");
                APIUtils.getAPIService().sendMessage(controlador.getCurrentRoom().getId(),String.valueOf(n),controlador.getAccessToken(),c).enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response){
                        pd.dismiss();
                        Toast.makeText(context, "Video enviado correctamente", Toast.LENGTH_SHORT).show();
                        refreshRoom();
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        System.out.println(t.fillInStackTrace());
                        Toast.makeText(context, "Envío de video fallido", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<Content> call, Throwable t) {

            }
        });
    }


    private void capturarImagen(){
        imageFileName = Chilligram.getUnicaInstancia().getImages().getAbsolutePath();
        String id = new String(UUID.randomUUID().toString());
        imageFileName += "/"+id + ".png";

        image = new File(imageFileName);
        try {
            image.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Uri video_uri = Uri.fromFile(video);
        Uri image_uri = FileProvider.getUriForFile(ChatActivity.this, BuildConfig.APPLICATION_ID + ".provider",image);
        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camera_intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        camera_intent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        startActivityForResult(camera_intent,IMAGE_REQUEST_CODE);
    }

    private void enviarImagen(){
        RequestBody body = RequestBody.create(MediaType.parse("image/png"), image);
        APIUtils.getAPIService().uploadMedia(image.getName(),Chilligram.getUnicaInstancia().getAccessToken(),body).enqueue(new Callback<Content>() {
            @Override
            public void onResponse(Call<Content> call, Response<Content> response) {
                Content c = new Content();
                c.setBody(image.getName());
                c.setMsgtype("m.image");
                System.out.println("VIDEO URL "+response.body().getContentUri());
                c.setUrl(response.body().getContentUri());
                Random r = new Random();
                Chilligram.getUnicaInstancia().setWrited(true);
                int n = r.nextInt(1000000);
                Toast.makeText(context, "Enviando imagen...", Toast.LENGTH_SHORT).show();

                APIUtils.getAPIService().sendMessage(controlador.getCurrentRoom().getId(),String.valueOf(n),controlador.getAccessToken(),c).enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response){
                        Toast.makeText(context, "Imagen enviada correctamente", Toast.LENGTH_SHORT).show();
                        refreshRoom();
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        System.out.println(t.fillInStackTrace());
                        Toast.makeText(context, "Envío de imagen fallido", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<Content> call, Throwable t) {

            }
        });

    }

    private void enviarUbicacion(){
        Content c = new Content();
        c.setBody("Ubicación de "+controlador.getCurrentUser().getDisplayName());
        c.setMsgtype("m.location");
        System.out.println("LOCALIZACIONNNNNNNNN "+currentLocation.getLongitude()+" "+currentLocation.getLatitude());
        c.setGeoUri("geo:"+currentLocation.getLatitude()+","+currentLocation.getLongitude());
        Random r = new Random();
        Chilligram.getUnicaInstancia().setWrited(true);
        int n = r.nextInt(1000000);
        Toast.makeText(this, "Enviando ubicación...", Toast.LENGTH_SHORT).show();

        APIUtils.getAPIService().sendMessage(controlador.getCurrentRoom().getId(),String.valueOf(n),controlador.getAccessToken(),c).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response){
                System.out.println("CODIGO : "+response.code()+"otro : "+response.body().getEventId());
                Toast.makeText(context, "Ubicación enviada correctamente", Toast.LENGTH_SHORT).show();
                refreshRoom();
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                System.out.println(t.fillInStackTrace());
            }
        });

    }

    private void initLocation(){
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Gestor de localización");

            builder.setMessage("La aplicación quiere usar el GPS pero esta desactivado\n"+"Deseas activarlo ahora ?");
            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Launch settings, allowing user to make a change
                    Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(i);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //no hacemos nada
                }
            });
            builder.create().show();
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 6);
        }
    }


    private void startRecording() {

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        // Record to the external cache directory for visibility
        mFileName = Chilligram.getUnicaInstancia().getAudios().getAbsolutePath();

        //getExternalCacheDir().getAbsolutePath();

        String id = new String(UUID.randomUUID().toString());

        mFileName += "/"+id + ".mp3";
        //File audioFile = new File(Chilligram.getUnicaInstancia().getAudios(), id+".mp3");
        mRecorder.setOutputFile(mFileName);

        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;

        File f = new File(mFileName);
        enviarAudio(f);



        //enviarAudio(String );
        Log.e("1", "stopRecording:Calling ");

    }

    private void enviarAudio(final File f){
        RequestBody body = RequestBody.create(MediaType.parse("audio/mp3"), f);
        APIUtils.getAPIService().uploadMedia(f.getName(),Chilligram.getUnicaInstancia().getAccessToken(),body).enqueue(new Callback<Content>() {
            @Override
            public void onResponse(Call<Content> call, Response<Content> response) {
                Content c = new Content();
                c.setBody(f.getName());
                c.setMsgtype("m.audio");
                System.out.println("AUDIO URL "+response.body().getContentUri());
                c.setUrl(response.body().getContentUri());
                Random r = new Random();
                Chilligram.getUnicaInstancia().setWrited(true);
                int n = r.nextInt(1000000);
                Toast.makeText(context, "Enviando audio...", Toast.LENGTH_SHORT).show();

                APIUtils.getAPIService().sendMessage(controlador.getCurrentRoom().getId(),String.valueOf(n),controlador.getAccessToken(),c).enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response){
                        Toast.makeText(context, "Audio enviado correctamente", Toast.LENGTH_SHORT).show();
                        refreshRoom();
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        System.out.println(t.fillInStackTrace());
                    }
                });
            }

            @Override
            public void onFailure(Call<Content> call, Throwable t) {

            }
        });
    }
}