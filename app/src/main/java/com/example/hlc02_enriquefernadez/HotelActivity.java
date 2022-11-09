package com.example.hlc02_enriquefernadez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HotelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        asignarEventosClick();
    }

    private void asignarEventosClick(){

    }

    @Override
    public void onBackPressed() {
        Intent volverAMain = new Intent(this, MainActivity.class);
        startActivity(volverAMain);
    }
}