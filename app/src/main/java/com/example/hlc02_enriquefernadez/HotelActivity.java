package com.example.hlc02_enriquefernadez;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;


public class HotelActivity extends AppCompatActivity {

    private TextView tvTitulo;
    private ImageView ivFoto;
    private ImageButton ibSonido;
    private ImageButton ibMapa;
    private ImageButton ibTelefono;
    private YouTubePlayerView youtubeView;
    private Button bMasInfo;
    private Intent intent;
    private MediaPlayer mediaPlayer;

    private AbstractYouTubePlayerListener listenerYoutube;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(HotelActivity.this, "Created", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_hotel);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        intent = getIntent();
        gestionarDatos();

        asignarEventosClick();
    }

    private void asignarEventosClick(){
        ibSonido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            try {
                String fileName = intent.getStringExtra("AUDIO_HOTEL");

                int resourceID=getResources().getIdentifier(fileName, "raw", getPackageName());
                if(mediaPlayer != null)
                {
                    mediaPlayer.release();
                }
                mediaPlayer=MediaPlayer.create( HotelActivity.this , obtenerIdRecurso(fileName, "raw"));
                mediaPlayer.start();

            }catch(Exception e){e.printStackTrace();}
            }
        });

        ibMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopSonido();

                String latitude = intent.getStringExtra("MAPA_HOTEL_LATITUD");
                String longitude = intent.getStringExtra("MAPA_HOTEL_LONGITUD");

                String strUri = "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude ;
                Intent intentMap = new Intent(android.content.Intent.ACTION_VIEW , Uri.parse(strUri));
                intentMap.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intentMap);

            }
        });

        ibTelefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopSonido();
                String phoneNo = intent.getStringExtra("TELEFONO_HOTEL");
                String dial = "tel:" + phoneNo;
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
            }
        });

        bMasInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopSonido();
                Intent intentReserva = new Intent(view.getContext(), InformacionActivity.class);
                intentReserva.putExtra("TITULO_HOTEL", intent.getStringExtra("TITULO_HOTEL"));
                startActivity(intentReserva);
            }
        });
    }

    private void gestionarDatos(){
        tvTitulo = findViewById(R.id.tvTitulo);
        ivFoto = findViewById(R.id.ivFotoPrincipal);
        ibSonido = findViewById(R.id.ibSonido);
        ibMapa = findViewById(R.id.ibMapa);
        ibTelefono = findViewById(R.id.ibTelefono);
        youtubeView = findViewById(R.id.youtube_player_view);
        bMasInfo = findViewById(R.id.bMasInformacion);

        tvTitulo.setText(intent.getStringExtra("TITULO_HOTEL"));

        ivFoto.setImageResource(obtenerIdRecurso(intent.getStringExtra("IMAGEN_HOTEL"), "drawable"));

        getLifecycle().addObserver(youtubeView);
        listenerYoutube = new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                youTubePlayer.cueVideo(intent.getStringExtra("VIDEO_HOTEL"), 0);
            }
        };
        youtubeView.addYouTubePlayerListener(listenerYoutube);
    }

    private int obtenerIdRecurso(String recurso, String paquete) {
        Resources res = getResources();
        return res.getIdentifier(recurso, paquete, getPackageName());
    }

    private void stopSonido(){
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onBackPressed() {
        stopSonido();
        Intent volverAMain = new Intent(this, MainActivity.class);
        startActivity(volverAMain);
    }
}