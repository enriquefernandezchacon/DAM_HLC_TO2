package com.example.hlc02_enriquefernadez;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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
                Toast.makeText(view.getContext(), "Hotel Barcelo", Toast.LENGTH_SHORT).show();
                Intent activityBarcelo = new Intent (view.getContext(), HotelActivity.class);
                startActivity(activityBarcelo);
            }
        });

        cardGranHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Hotel Barcelo", Toast.LENGTH_SHORT).show();
            }
        });

        cardHotelElba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Hotel Barcelo", Toast.LENGTH_SHORT).show();
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