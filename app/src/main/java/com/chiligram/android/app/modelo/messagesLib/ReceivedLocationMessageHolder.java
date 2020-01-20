package com.chiligram.android.app.modelo.messagesLib;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chiligram.android.app.Controlador.Chilligram;
import com.chiligram.android.app.R;
import com.chiligram.android.app.modelo.Message;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ReceivedLocationMessageHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {

    TextView timeText,nameText;
    ImageView profile;
    MapView map;
    GoogleMap mMap;
    Message message;


    public ReceivedLocationMessageHolder(View itemView) {
        super(itemView);

        timeText = (TextView)itemView.findViewById(R.id.message_time);
        map = (MapView)itemView.findViewById(R.id.map);
        profile = (ImageView)itemView.findViewById(R.id.image_profile);
        nameText = (TextView)itemView.findViewById(R.id.name);

        if(map != null){
            map.onCreate(null);
            map.onResume();
            map.getMapAsync(this);
        }
    }

    void bind(final Message message) {
        this.message = message;

        timeText.setText(Utils.formatTime(message.getOriginServerTs()));
        nameText.setTextColor(Color.parseColor(Chilligram.getUnicaInstancia().getColor(message.getSenderUserName())));
        nameText.setText(message.getSenderUserName());

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String aux = message.getContent().getGeoUri().split(":")[1];
        String lat = aux.split(",")[0];
        String lng = aux.split(",")[1];
        LatLng latLng = new LatLng(Double.valueOf(lat), Double.valueOf(lng));
        // Add Marker
        mMap.addMarker(new MarkerOptions().title("Ubicación de "+message.getSenderUserName()).position(latLng));
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
