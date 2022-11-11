package com.example.hlc02_enriquefernadez;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class InformacionActivity extends AppCompatActivity {

    private EditText etNombre;
    private EditText etApellidos;
    private EditText etEmail;
    private EditText etFecha;
    private EditText etConsulta;
    private NumberPicker npDias;
    private Button botonEnviar;
    private ScrollView scrollView;
    private CheckBox checkSi;
    private CheckBox checkNo;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        intent = getIntent();
        TextView titulo = findViewById(R.id.tvTitulo);
        titulo.setText(intent.getStringExtra("TITULO_HOTEL"));

        gestionarElementos();
    }

    private void gestionarElementos(){
        scrollView = findViewById(R.id.scrollView);
        etNombre = findViewById(R.id.editTextNombre);
        etApellidos = findViewById(R.id.editTextApellidos);
        etEmail = findViewById(R.id.editTextEmail);
        etConsulta = findViewById(R.id.textArea);
        checkSi = findViewById(R.id.checkBoxSi);
        checkNo = findViewById(R.id.checkBoxNo);

        Calendar c = Calendar.getInstance();
        etFecha = findViewById(R.id.editTextFecha);
        etFecha.setFocusable(false);
        etFecha.setText(new SimpleDateFormat("dd-MM-yyyy").format(c.getTime()));

        npDias = findViewById(R.id.numberPickerDias);
        npDias.setMinValue(1);
        npDias.setMaxValue(15);
        npDias.setWrapSelectorWheel(false);
        npDias.setOrientation(LinearLayout.HORIZONTAL);
        npDias.setFocusable(false);

        botonEnviar = findViewById(R.id.buttonEnviar);

        crearEventos();
    }

    private void crearEventos(){
        etFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        InformacionActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                etFecha.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                datePickerDialog.show();
            }
        });

        npDias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

        botonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean centinela = true;
                checkSi.setError(null);
                checkNo.setError(null);

                if (etNombre.getText().toString().trim().isEmpty()){
                    centinela = false;
                    etNombre.setError("El nombre no puede estar vacío");
                }

                if (etApellidos.getText().toString().trim().isEmpty()){
                    centinela = false;
                    etApellidos.setError("Los apellidos no pueden estar vacíos");
                }

                if (etEmail.getText().toString().trim().isEmpty()){
                    centinela = false;
                    etEmail.setError("El email no puede estar vacío");
                }

                String regex = "\\w+[.]?\\w+[@]\\w+[.]\\w{2,5}";
                if (!etEmail.getText().toString().matches(regex)){
                    centinela = false;
                    etEmail.setError("El email no es válido");
                }

                Calendar calendarHoy = Calendar.getInstance();
                try {
                    Date fechaInput = new SimpleDateFormat("dd-MM-yyyy").parse(etFecha.getText().toString());
                    Calendar inputCalendar = Calendar.getInstance();
                    inputCalendar.setTime(fechaInput);
                    inputCalendar.set(Calendar.HOUR_OF_DAY, 0);
                    inputCalendar.set(Calendar.MINUTE, 0);
                    inputCalendar.set(Calendar.SECOND, 0);
                    inputCalendar.set(Calendar.MILLISECOND, 0);
                    inputCalendar.setTimeZone(TimeZone.getTimeZone("UTC"));
                    calendarHoy.set(Calendar.HOUR_OF_DAY, 0);
                    calendarHoy.set(Calendar.MINUTE, 0);
                    calendarHoy.set(Calendar.SECOND, 0);
                    calendarHoy.set(Calendar.MILLISECOND, 0);
                    calendarHoy.setTimeZone(TimeZone.getTimeZone("UTC"));
                    if (inputCalendar.before(calendarHoy)){
                        centinela = false;
                        etFecha.setError("La fecha no puede ser anterior al día de hoy");
                        Toast.makeText(InformacionActivity.this, "La fecha no puede ser anterior a hoy", Toast.LENGTH_SHORT).show();
                    }
                } catch (ParseException e) {
                    centinela = false;
                    etFecha.setError("El formato de la fecha no es correcto");
                }

                if (etConsulta.getText().toString().trim().isEmpty()){
                    centinela = false;
                    etConsulta.setError("La consulta no puede estar vacía");
                }

                if (!checkSi.isChecked() && !checkNo.isChecked()){
                    centinela = false;
                    checkSi.setError("");
                    checkNo.setError("");
                }

                if (centinela){
                    String texto = "Estimado cliente Don/Doña " + etNombre.getText().toString() + " " + etApellidos.getText().toString() + " le informamos de la correcta recepción de su consulta.";
                    texto += " Nos pondremos en contacto con usted a la mayor brevedad posible.";
                    Toast.makeText(InformacionActivity.this, texto, Toast.LENGTH_LONG).show();
                    etNombre.setText(null);
                    etApellidos.setText(null);
                    etConsulta.setText(null);
                    etEmail.setText(null);
                    checkSi.setChecked(false);
                    checkNo.setChecked(false);
                    Calendar c = Calendar.getInstance();
                    etFecha.setText(new SimpleDateFormat("dd-MM-yyyy").format(c.getTime()));
                    npDias.setValue(1);
                } else {
                    Toast.makeText(InformacionActivity.this, "Hay errores en los datos, reviselo por favor", Toast.LENGTH_SHORT).show();
                }
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });

        checkSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSi.isChecked()){
                    checkNo.setChecked(false);
                }
            }
        });

        checkNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkNo.isChecked()){
                    checkSi.setChecked(false);
                }
            }
        });
    }


}