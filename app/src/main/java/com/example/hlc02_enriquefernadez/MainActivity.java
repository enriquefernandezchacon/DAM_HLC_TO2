package com.example.hlc02_enriquefernadez;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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