package com.chiligram.android.app.activities;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.chiligram.android.app.Controlador.Chilligram;
import com.chiligram.android.app.R;
import com.chiligram.android.app.apiTools.APIUtils;
import com.chiligram.android.app.modelo.Content;
import com.chiligram.android.app.modelo.JoinedMembers;
import com.chiligram.android.app.modelo.JoinedRoom;
import com.chiligram.android.app.modelo.Member;
import com.chiligram.android.app.modelo.Message;
import com.chiligram.android.app.modelo.Room;
import com.chiligram.android.app.modelo.SyncRooms;
import com.chiligram.android.app.modelo.roomLib.RoomAdapter;
import com.chiligram.android.app.modelo.RoomMessages;
import com.chiligram.android.app.modelo.Rooms;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomsActivity extends AppCompatActivity {
    private ListView roomRecycler;
    TextView noRoomsText;
    private LinkedList<Room> salas = new LinkedList<>();
    private RoomAdapter adapter;
    ProgressDialog pd;
    Context context = this;
    Timer timer;
    public static NotificationManagerCompat notmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        notmanager =  NotificationManagerCompat.from(this);
        roomRecycler = (ListView)findViewById(R.id.roomList);
        noRoomsText = (TextView)findViewById(R.id.noUsersText);



        final Handler handler = new Handler();
        timer = new Timer();
        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                            APIUtils.getAPIService().getSyncRooms(Chilligram.getUnicaInstancia().getAccessToken()).enqueue(new Callback<SyncRooms>() {
                                @Override
                                public void onResponse(Call<SyncRooms> call, Response<SyncRooms> response) {
                                    SyncRooms s = response.body();
                                    Chilligram.getUnicaInstancia().setSyncRooms(s);
                                    int notsApi = 0;
                                    int currentNots = 0;

                                    for(Room sala : salas){
                                        currentNots+=sala.getUnreadNotifications().getNotificationCount();
                                    }
                                    for (JoinedRoom j : s.getRooms().getJoin().values()){
                                        notsApi+=j.getUnreadNotifications().getNotificationCount();
                                    }
                                    System.out.println("NOTIFICACIONES HAY ? : "+notsApi);
                                    if(notsApi != currentNots || Chilligram.getUnicaInstancia().isWrited() ){
                                        System.out.print("Numero de notificaciones diferente actualizamos salas");
                                        cargarAsincrono();
                                        if(notsApi > 0) {

                                            Intent notifyIntent = new Intent(context, RoomsActivity.class);
                                            notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                                                    context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
                                            );
                                            Notification notification = createBasicNotification("Mensajes nuevos", "Tienes " + notsApi + " mensajes nuevos", NotificationCompat.PRIORITY_DEFAULT,notifyPendingIntent);
                                            notmanager.notify(0,notification);

                                        }
                                        Chilligram.getUnicaInstancia().setWrited(false);
                                    }
                                }

                                @Override
                                public void onFailure(Call<SyncRooms> call, Throwable t) {

                                }
                            });
                    }
                });
            }
        };

        timer.schedule(task,0,3000);

        cargarAsincrono();


        noRoomsText.setVisibility(View.GONE);
        roomRecycler.setVisibility(View.VISIBLE);
        /*
        adapter = new RoomAdapter(this,salas);
        roomList.setAdapter(adapter);
        */



        adapter = new RoomAdapter(this, salas);
        roomRecycler.setAdapter(adapter);


        System.out.println("SALAS SIZE : "+salas.size());


        roomRecycler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Chilligram.getUnicaInstancia().setCurrentRoom(salas.get(position));
                        startActivity(new Intent(RoomsActivity.this, ChatActivity.class));
                    }
        });

        /*
        roomRecycler.addOnItemTouchListener(
                new AdaptadorOnClickListener(this, roomRecycler ,new AdaptadorOnClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Room r = salas.get(roomRecycler.getChildAdapterPosition(view));
                        Chilligram.getUnicaInstancia().setCurrentRoom(r);
                        startActivity(new Intent(RoomsActivity.this, ChatActivity.class));
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever;
                    }
                })
        );
        */
    }


    private Notification createBasicNotification(String title, String content, int priority,PendingIntent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getString(R.string.channel_id))
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(content)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(content))
                .setPriority(priority)
                .setAutoCancel(true)
                .setContentIntent(intent);

        return builder.build();
    }


    public void onClickedMessage(View view){

    }

    public void onClickedMain(View view){

    }

    public void onClickedSetting(View view){

    }

    private void cargarAsincrono() {
            APIUtils.getAPIService().rooms(Chilligram.getUnicaInstancia().getAccessToken()).enqueue(new Callback<Rooms>() {
                @Override
                public void onResponse(Call<Rooms> call, Response<Rooms> response) {
                    Rooms rooms = response.body();
                    Chilligram.getUnicaInstancia().clearRooms();
                    salas.clear();
                    for (final String sala : rooms.getJoinedRooms()) {
                        APIUtils.getAPIService().getRoomEvent(sala, "m.room.name", Chilligram.getUnicaInstancia().getAccessToken()).enqueue(new Callback<Content>() {
                            @Override
                            public void onResponse(Call<Content> call, Response<Content> response) {
                                Content roomContent = response.body();
                                final String roomName;
                                if (roomContent != null) {
                                    roomName = roomContent.getName();
                                } else {
                                    roomName = "";
                                }
                                final Room r = new Room(roomName, sala);


                                //Call para coger los miembros de la sala
                                APIUtils.getAPIService().getJoinedMembers(sala, Chilligram.getUnicaInstancia().getAccessToken()).enqueue(new Callback<JoinedMembers>() {
                                    @Override
                                    public void onResponse(Call<JoinedMembers> call, Response<JoinedMembers> response) {
                                        JoinedMembers members = response.body();

                                        for (final Member m : members.getJoined().values()) {
                                            r.addMember(m);
                                        }
                                        APIUtils.getAPIService().getRoomMessages(sala,"1","b", Chilligram.getUnicaInstancia().getAccessToken()).enqueue(new Callback<RoomMessages>() {
                                            @Override
                                            public void onResponse(Call<RoomMessages> call, Response<RoomMessages> response) {
                                               final LinkedList<Message> msg = new LinkedList<>(response.body().getMessageList());
                                                                APIUtils.getAPIService().getSyncRooms(Chilligram.getUnicaInstancia().getAccessToken()).enqueue(new Callback<SyncRooms>() {
                                                                    @Override
                                                                    public void onResponse(Call<SyncRooms> call, Response<SyncRooms> response) {

                                                                        Collections.sort(msg);
                                                                        r.setLastMessage(msg.getFirst());
                                                                        Chilligram.getUnicaInstancia().addRoom(r.getId(), r);
                                                                        r.setUnreadNotifications(response.body().getRooms().getJoin().get(r.getId()).getUnreadNotifications());
                                                                        salas.add(r);
                                                                        Collections.sort(salas);
                                                                        adapter.notifyDataSetChanged();
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<SyncRooms> call, Throwable t) {

                                                                    }
                                                                });

                                            }

                                            @Override
                                            public void onFailure(Call<RoomMessages> call, Throwable t) {
                                                System.out.println(t.fillInStackTrace());
                                            }
                                        });
                                    }


                                    @Override
                                    public void onFailure(Call<JoinedMembers> call, Throwable t) {

                                    }
                                });

                            }

                            @Override
                            public void onFailure(Call<Content> call, Throwable t) {

                            }
                        });

                    }


                }

                @Override
                public void onFailure(Call<Rooms> call, Throwable t) {

                }
            });






    }



}

