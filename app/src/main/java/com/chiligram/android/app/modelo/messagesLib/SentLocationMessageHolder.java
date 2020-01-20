package com.chiligram.android.app.modelo.messagesLib;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chiligram.android.app.R;
import com.chiligram.android.app.modelo.Message;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;



public class SentLocationMessageHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {
    TextView timeText;
    ImageView messageBody;
    MapView map;
    GoogleMap mMap;
    Message message;


    public SentLocationMessageHolder(View itemView) {
        super(itemView);
        messageBody = (ImageView) itemView.findViewById(R.id.message_body);
        timeText = (TextView)itemView.findViewById(R.id.message_time);
        map = (MapView)itemView.findViewById(R.id.map);
        if(map != null){
            map.onCreate(null);
            map.onResume();
            map.getMapAsync(this);
        }
    }

    void bind(final Message message) {
        this.message = message;

        timeText.setText(Utils.formatTime(message.getOriginServerTs()));

        /*
        messageBody.setImageResource(R.drawable.ic_launcher_foreground);
        messageBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(message.getContent().getGeoUri()));
                itemView.getContext().startActivity(intent);
            }
        });
        */

        /*
        String url = message.getContent().getInfo().getThumbnailUrl();
        String serverName = url.split("mxc://")[1].split("/")[0];
        String mediaId = url.split("mxc://")[1].split("/")[1];
        APIUtils.getAPIService().getMedia(serverName, mediaId, Chilligram.getUnicaInstancia().getAccessToken()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                InputStream is = response.body().byteStream();
                Bitmap bmp = BitmapFactory.decodeStream(is);
                Utils.displayImageFromBitMap(itemView.getContext(), bmp, messageBody);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        */

        /*
        messageBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(message.getContent().getGeoUri()));
                itemView.getContext().startActivity(intent);
            }
        });
        */

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String aux = message.getContent().getGeoUri().split(":")[1];
        String lat = aux.split(",")[0];
        String lng = aux.split(",")[1];
        LatLng latLng = new LatLng(Double.valueOf(lat), Double.valueOf(lng));
        // Add Marker
        mMap.addMarker(new MarkerOptions().title("Tu ubicación").position(latLng));
        // Center map on the marker
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(latLng, 16.0f);
        mMap.animateCamera(yourLocation);

        /*
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                // Make a snapshot when map's done loading
                mMap.snapshot(new GoogleMap.SnapshotReadyCallback() {
                    @Override
                    public void onSnapshotReady(Bitmap bitmap) {
                        messageBody.setImageBitmap(bitmap);
                        //map.removeAllViews();
                    }
                });
            }
        });
        */
    }
}
