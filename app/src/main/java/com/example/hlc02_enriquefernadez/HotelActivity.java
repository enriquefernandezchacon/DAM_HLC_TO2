package com.example.hlc02_enriquefernadez;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;


public class HotelActivity extends AppCompatActivity {

    //ESTOS SON LOS ELEMENTOS DE LA ACTIVITY QUE SON UTILIZADOS EN DISTINTOS METODOS
    private ImageButton ibSonido;
    private ImageButton ibMapa;
    private ImageButton ibTelefono;
    private Button bMasInfo;
    private Intent intent;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);
        //INSTRUCCIONES PARA MANTENER SIEMPRE LA ORIENTACIÓN VERTICAL
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //OBTENEMOS LOS DATOS DEL INTENT
        intent = getIntent();
        //CON LOS DATOS DEL INTENT ASIGNAMOS CADA UNO A SU RECURSO CORRESPONDIENTE
        gestionarDatos();
        //METODO PARA ASIGNAR LOS EVENTOS
        asignarEventosClick();
    }

    //METODO PARA QUE AL PARAR LA ACTIVITY, SE PARE EL SONIDO
    @Override
    protected void onPause(){
        super.onPause();
        stopSonido();
    }

    //METODO DE CONTROL DE EVENTOS
    private void asignarEventosClick(){
        //EVENTO DE LA AUDIOGUIA
        ibSonido.setOnClickListener(view -> {
        try {
            //ESTA PRIMERA CONDICION, HACE QUE SI EL SONIDO SE ESTE REPRODUCIENDO, EL MISMO
            //BOTON SIRVA PARA PARARLO
            if (mediaPlayer != null && mediaPlayer.isPlaying()){
                stopSonido();
            } else {
                //OBTENEMOS EL NOMBRE DEL ARCHIVO DEL INTENT
                String fileName = intent.getStringExtra("AUDIO_HOTEL");
                //SI EL MEDIAPLAYER YA EXISTE, LO LIBERAMOS
                if(mediaPlayer != null)
                {
                    mediaPlayer.release();
                }
                //ASIGNAMOS EL RECURSO AL MEDIAPLAYER
                mediaPlayer=MediaPlayer.create( HotelActivity.this , obtenerIdRecurso(fileName, "raw"));
                //REPRODUCIMOS LE SONIDO
                mediaPlayer.start();
            }
        }catch(Exception e){e.printStackTrace();}
        });

        //EVENTO PARA LE MAPA
        ibMapa.setOnClickListener(view -> {
            //PARAMOS EL SONIDO SI LO HUBIESE
            stopSonido();
            //OBTENEMOS LOS DATOS DE GEOLOCALIZACIÓN DCEL INTENT
            String latitude = intent.getStringExtra("MAPA_HOTEL_LATITUD");
            String longitude = intent.getStringExtra("MAPA_HOTEL_LONGITUD");
            //GENERAMOS LA URL
            String strUri = "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude ;
            //CREAMOUS UN NUEVO INTENT PARA EL MAP
            Intent intentMap = new Intent(Intent.ACTION_VIEW , Uri.parse(strUri));
            intentMap.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            //INICIO DEL MAP
            startActivity(intentMap);

        });

        //ESTE EVENTO GESTIONA LA POSIBILDIAD DE LLAMAR, HA REQUERIDO PEDIR PERMISOS EN EL
        //ANDRODID MANIFEST
        ibTelefono.setOnClickListener(view -> {
            //PÀRAMOS EL SONIDO SI LO HUBIESE
            stopSonido();
            //OBTENEMOS EL NUMERO DEL INTENT
            String phoneNo = intent.getStringExtra("TELEFONO_HOTEL");
            String dial = "tel:" + phoneNo;
            //LANZAMOS LA ACTIVITY DE LLAMADA
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
        });

        //EVENTO PARA MAS INFORMACION
        bMasInfo.setOnClickListener(view -> {
            //PARAMOS EL SONIDO SI LO HUBIESE
            stopSonido();
            //CREAMOS EL NUEVO INTENT
            Intent intentReserva = new Intent(view.getContext(), InformacionActivity.class);
            //AÑADIMOS EL NOMBRE DEL HOTEL AL INTENT
            intentReserva.putExtra("TITULO_HOTEL", intent.getStringExtra("TITULO_HOTEL"));
            //LANZAMOS LA NUEVA VENTANA
            startActivity(intentReserva);
        });
    }

    //METODO QUE ASIGNA LOS DATOS DEL INTENT A LOS DIFERENTES ELEMENTOS
    private void gestionarDatos(){
        //LOS ELEMENTOS DECLARADOS SOLO EN ESTE METODO HA SIDO POR RECOMENDACION DE ANDROID STUDIO
        //DEBIDO A QUE INDICA QUE SI SOLO SE USA EN UN MÉTODO NO DEBEN DECLARARSE COMO ATRIBUTOS DE
        //CLASE

        //ASIGNAMOS LOS RECURSOS A CADA ELEMENTO
        TextView tvTitulo = findViewById(R.id.tvTitulo);
        ImageView ivFoto = findViewById(R.id.ivFotoPrincipal);
        ibSonido = findViewById(R.id.ibSonido);
        ibMapa = findViewById(R.id.ibMapa);
        ibTelefono = findViewById(R.id.ibTelefono);
        YouTubePlayerView youtubeView = findViewById(R.id.youtube_player_view);
        bMasInfo = findViewById(R.id.bMasInformacion);
        //ESTABLECEMOS EL TITULO
        tvTitulo.setText(intent.getStringExtra("TITULO_HOTEL"));
        //ESTABLECEMOS LA IMAGEN DEL HOTEL
        ivFoto.setImageResource(obtenerIdRecurso(intent.getStringExtra("IMAGEN_HOTEL"), "drawable"));
        //ESTABLECEMOS EL VIDEO DE YOUTUBE, PARA ESTO HEMOS IMPORTADO UNA LIBRERÍA DE GITHUB ABIERTA
        getLifecycle().addObserver(youtubeView);
        AbstractYouTubePlayerListener listenerYoutube = new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                youTubePlayer.cueVideo(intent.getStringExtra("VIDEO_HOTEL"), 0);
            }
        };
        youtubeView.addYouTubePlayerListener(listenerYoutube);
    }

    //METODO AUXILIAR PARA OBTENER LA ID DE LOS RECURSOS BUSCADOS, SE DEBE PASAR EL NOMBRE DEL
    //RECURSO Y SU CARPETA
    private int obtenerIdRecurso(String recurso, String paquete) {
        Resources res = getResources();
        return res.getIdentifier(recurso, paquete, getPackageName());
    }

    //METODO PARA PARA EL SONIDO
    private void stopSonido(){
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    //METODO PARA VOLVER HACIA ATRAS
    @Override
    public void onBackPressed() {
        //PARAMOS EL SONIDO SI LO HUBIESE
        stopSonido();
        Intent volverAMain = new Intent(this, MainActivity.class);
        startActivity(volverAMain);
    }
}