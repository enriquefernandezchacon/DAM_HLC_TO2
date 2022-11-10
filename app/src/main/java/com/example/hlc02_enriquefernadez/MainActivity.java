package com.example.hlc02_enriquefernadez;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    private CardView cardHotelBarcelo;
    private CardView cardHotelElba;
    private CardView cardGranHotel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        cardGranHotel = findViewById(R.id.cardHotelGranHotel);
        cardHotelBarcelo = findViewById(R.id.cardHotelBarcelo);
        cardHotelElba = findViewById(R.id.cardHotelElba);

        asignarEventosClick();
    }

    private void asignarEventosClick(){
        cardHotelBarcelo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(), HotelActivity.class);
                intent.putExtra("TITULO_HOTEL", getString(R.string.hotel_barcelo));
                intent.putExtra("IMAGEN_HOTEL", getString(R.string.fotoHotel1));
                intent.putExtra("AUDIO_HOTEL", getString(R.string.sonido1));
                intent.putExtra("MAPA_HOTEL_LATITUD", getString(R.string.latitud1));
                intent.putExtra("MAPA_HOTEL_LONGITUD", getString(R.string.longitud1));
                intent.putExtra("TELEFONO_HOTEL", getString(R.string.telefono1));
                intent.putExtra("VIDEO_HOTEL", getString(R.string.video_hotel_barcelo));
                startActivity(intent);
            }
        });

        cardGranHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(), HotelActivity.class);
                intent.putExtra("TITULO_HOTEL", getString(R.string.hotel_gran));
                intent.putExtra("IMAGEN_HOTEL", getString(R.string.fotoHotel2));
                intent.putExtra("AUDIO_HOTEL", getString(R.string.sonido2));
                intent.putExtra("MAPA_HOTEL_LATITUD", getString(R.string.latitud2));
                intent.putExtra("MAPA_HOTEL_LONGITUD", getString(R.string.longitud2));
                intent.putExtra("TELEFONO_HOTEL", getString(R.string.telefono2));
                intent.putExtra("VIDEO_HOTEL", getString(R.string.video_hotel_gran_hotel));
                startActivity(intent);
            }
        });

        cardHotelElba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(), HotelActivity.class);
                intent.putExtra("TITULO_HOTEL", getString(R.string.hotel_elba));
                intent.putExtra("IMAGEN_HOTEL", getString(R.string.fotoHotel3));
                intent.putExtra("AUDIO_HOTEL", getString(R.string.sonido3));
                intent.putExtra("MAPA_HOTEL_LATITUD", getString(R.string.latitud3));
                intent.putExtra("MAPA_HOTEL_LONGITUD", getString(R.string.longitud3));
                intent.putExtra("TELEFONO_HOTEL", getString(R.string.telefono3));
                intent.putExtra("VIDEO_HOTEL", getString(R.string.video_hotel_elba));
                startActivity(intent);
            }
        });
    }

    //METODO PARA MOSTRAR Y OCULTAR MENU
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menumain, menu);
        return true;
    }

    //METODO PARA ASIGNAR OPCIONES AL MENU
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.menuMainLogin){
            Toast.makeText(this, "Función no desarrollada aún. Disculpe las molestias.", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menuMainAcercaDe){
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialogo_acercade);
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}