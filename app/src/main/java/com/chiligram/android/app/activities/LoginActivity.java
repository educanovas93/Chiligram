package com.chiligram.android.app.activities;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.chiligram.android.app.R;
import com.chiligram.android.app.apiTools.APIUtils;
import com.chiligram.android.app.Controlador.Chilligram;
import com.chiligram.android.app.modelo.Login;
import com.chiligram.android.app.modelo.LoginRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChannel();

        setContentView(R.layout.activity_login);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

        if(Chilligram.getUnicaInstancia().getCurrentUser() != null){
            Intent roomsIntent = new Intent(LoginActivity.this, RoomsActivity.class);
            startActivity(roomsIntent);
        }

        findViewById(R.id.signin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText usuario = findViewById(R.id.user);
                EditText pass = findViewById(R.id.pass);
                String user = usuario.getText().toString();
                String password = pass.getText().toString();
                login(user,password);
            }
        });

    }

    protected void login(String user,String password) {

        LoginRequest request = new LoginRequest(user, password);

        APIUtils.getAPIService().login(request).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                switch (response.code()) {
                    case 200:
                        Login login = response.body();

                        Chilligram.getUnicaInstancia().setCurrentUser(login);
                        Chilligram.getUnicaInstancia().setAccessToken(login.getAccessToken());
                        System.out.println("Access token : " + login.getAccessToken());
                        Intent roomsIntent = new Intent(LoginActivity.this, RoomsActivity.class);
                        startActivity(roomsIntent);
                        break;
                    case 403:
                        //si el codigo es 403 es forbidden y por tanto el usuario o contraseña esta mal
                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(LoginActivity.this);
                        dlgAlert.setMessage("Usuario o Contraseña no válidos, vuelve a intentarlo");
                        dlgAlert.setTitle("Error...");
                        dlgAlert.setPositiveButton("OK", null);
                        dlgAlert.setCancelable(true);
                        dlgAlert.create().show();

                        dlgAlert.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                        break;

                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {

            }
        });
    }



    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(getString(R.string.channel_id), name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
