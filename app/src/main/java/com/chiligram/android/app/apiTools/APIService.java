package com.chiligram.android.app.apiTools;

import com.chiligram.android.app.modelo.Content;
import com.chiligram.android.app.modelo.JoinedMembers;
import com.chiligram.android.app.modelo.Login;
import com.chiligram.android.app.modelo.LoginRequest;
import com.chiligram.android.app.modelo.Marker;
import com.chiligram.android.app.modelo.Member;
import com.chiligram.android.app.modelo.RoomMessages;
import com.chiligram.android.app.modelo.Rooms;
import com.chiligram.android.app.modelo.Message;
import com.chiligram.android.app.modelo.SyncRooms;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;


public interface APIService {


    //POST FOR LOGIN
    @POST("/_matrix/client/unstable/login")
    Call<Login> login(@Body LoginRequest request);

    // GET Collection rooms
    @GET("/_matrix/client/unstable/joined_rooms")
    Call<Rooms> rooms(@Query("access_token") String access_token);

    // GET Mensajes de un chat
    @GET("/_matrix/client/unstable/rooms/{roomId}/messages")
    Call<RoomMessages> getRoomMessages(@Path("roomId")String roomId,
                                       @Query("limit") String limit,
                                       @Query("dir") String dir,
                                       @Query("access_token") String access_token);

    @PUT("/_matrix/client/unstable/rooms/{roomId}/send/m.room.message/{txnId}")
    Call<Message> sendMessage(@Path("roomId")String roomId,
                              @Path("txnId")String txnId,
                              @Query("access_token")String access_token,
                              @Body Content c);


    @Headers("Content-Type: application/json")
    @GET("/_matrix/client/unstable/rooms/{roomId}/state/{eventType}")
    Call<Content> getRoomEvent(@Path("roomId")String roomId,
                              @Path("eventType")String eventType,
                              @Query("access_token")String access_token);



    @Headers("Content-Type: application/json")
    @GET("/_matrix/client/unstable/rooms/{roomId}/joined_members")
    Call<JoinedMembers> getJoinedMembers(@Path("roomId")String roomId,
                                         @Query("access_token")String access_token);



    @GET("_matrix/media/r0/download/{serverName}/{mediaId}")
    Call<ResponseBody> getMedia(@Path("serverName")String serverName,
                                @Path("mediaId")String mediaId,
                                @Query("access_token")String access_token);

    @GET("_matrix/media/r0/thumbnail/{serverName}/{mediaId}")
    Call<ResponseBody> getThumbnail(@Path("serverName")String serverName,
                                @Path("mediaId")String mediaId,
                                @Query("access_token")String access_token);


    @GET("_matrix/client/unstable/profile/{userId}")
    @Streaming
    Call<Member> getProfileInfo(@Path("userId")String userId,
                          @Query("access_token")String access_token);



    @GET("/_matrix/client/r0/sync")
    Call<SyncRooms> getSyncRooms(@Query("access_token")String access_token);

    @POST("/_matrix/client/r0/rooms/{roomId}/read_markers")
    Call<Message> readMessage(@Path("roomId")String roomId,
                              @Query("access_token")String accessToken,
                              @Body Marker marker);


    @Headers("Content-Type: application/json")
    @POST("/_matrix/media/r0/upload")
    Call<Content> uploadMedia(@Query("filename")String filename,
                              @Query("access_token")String accessToken,
                              @Body RequestBody body);

}
