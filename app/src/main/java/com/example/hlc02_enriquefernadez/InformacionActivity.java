package com.example.hlc02_enriquefernadez;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
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

    //ESTABLEZCO LOS ELEMENTOS DEL FORMULARIO COMO ATRIBUTOS DE LA CLASE
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);
        //ESTABLEZCO LA ORIENTACION DE LA PANTALLA SIEMPRE VERTICAL
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //OBTENGO EL INTENT
        Intent intent = getIntent();
        //ASIGNAMOS EL NOMBRE DEL HOTEL AL TITULO
        TextView titulo = findViewById(R.id.tvTitulo);
        titulo.setText(intent.getStringExtra("TITULO_HOTEL"));
        //METODO PARA INICIALIZAR LOS ELEMENTOS Y SUS PROPIEDADES
        gestionarElementos();
    }

    private void gestionarElementos(){
        //ASIGNAMOS LOS ELEMENTOS A LOS ATRIBUTOS
        scrollView = findViewById(R.id.scrollView);
        etNombre = findViewById(R.id.editTextNombre);
        etApellidos = findViewById(R.id.editTextApellidos);
        etEmail = findViewById(R.id.editTextEmail);
        etConsulta = findViewById(R.id.etConsulta);
        checkSi = findViewById(R.id.checkBoxSi);
        checkNo = findViewById(R.id.checkBoxNo);
        //ASIGNAMOS AL CAMPO FECHA LA FECHA DE HOY COMO PREDETERMINADA
        Calendar c = Calendar.getInstance();
        etFecha = findViewById(R.id.editTextFecha);
        //CON FOCUS "FALSE" EVITAMOS QUE SE PUEDA ESCRIBIR EN EL CAMPO FECHA, SOLO PERMITE SELECCIONAR DLE DATE PICKER
        etFecha.setFocusable(false);
        //ESTABLEZCO LA FECHA DE HOY EN EL EDIT TEXT DE FECHA
        etFecha.setText(new SimpleDateFormat("dd-MM-yyyy").format(c.getTime()));

        npDias = findViewById(R.id.numberPickerDias);
        //LOS DIAS DE RESERVA SERAN ENTRE 1 Y 15
        npDias.setMinValue(1);
        npDias.setMaxValue(15);
        //CON ESTA OPCIÓN LA RUEDA DE SELECCIÓN NO DA LA VUELTA
        npDias.setWrapSelectorWheel(false);
        npDias.setOrientation(LinearLayout.HORIZONTAL);
        //EVITA QUE PUEDAS ESCRIBIR, SOLO SE USA LA RUEDA
        npDias.setFocusable(false);

        botonEnviar = findViewById(R.id.buttonEnviar);

        crearEventos();
    }

    //METODO PARA GESTIONAR LOS EVENTOS
    private void crearEventos(){
        //EL METODO DEL DATEPICKER LO HE SACADO DE INTERNET
        etFecha.setOnClickListener(view -> {
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
                    (view1, year1, monthOfYear, dayOfMonth) -> {
                        // on below line we are setting date to our text view.
                        etFecha.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year1);

                    },
                    // on below line we are passing year,
                    // month and day for selected date in our date picker.
                    year, month, day);
            // at last we are calling show to
            // display our date picker dialog.
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            datePickerDialog.show();
        });

        //EVITO QUE SE ABRA EL TECLADO EN LA PANTALLA AL SELECCIONAR EL NUMBERPICKER
        npDias.setOnClickListener(view -> ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS));

        //EVENTO DEL BOTON ENVIAR
        botonEnviar.setOnClickListener(view -> {
            //VARIABLE FLAG
            boolean centinela = true;
            //ELIMINO LOS ERRORES DE LOS CHECKBOX, PUES HE VISTO QUE NO SE HACE AUTOMATICAMENTE
            //COOMO A MI ME INTERESA
            checkSi.setError(null);
            checkNo.setError(null);
            //COMPRUEBO QUE EL NOMBRE NO ESTE VACIO
            if (etNombre.getText().toString().trim().isEmpty()){
                centinela = false;
                etNombre.setError("El nombre no puede estar vacío");
            }
            //COMPRUEBO QUE LOS APELLIDOS NO ESTEN VACIOS
            if (etApellidos.getText().toString().trim().isEmpty()){
                centinela = false;
                etApellidos.setError("Los apellidos no pueden estar vacíos");
            }
            //COMPRUEBO QUE EL EMAIL NO ESTE VACIO
            if (etEmail.getText().toString().trim().isEmpty()){
                centinela = false;
                etEmail.setError("El email no puede estar vacío");
            }
            //COMPRUEBO QUE EL EMAIL PRESENTE UN FORMATO CORRECTO
            String regex = "\\w+[.]?\\w+[@]\\w+[.]\\w{2,5}";
            if (!etEmail.getText().toString().matches(regex)){
                centinela = false;
                etEmail.setError("El email no es válido");
            }
            //COMPROBACION DE LA FECHA
            //CALENDAR DE APOYO
            Calendar calendarHoy = Calendar.getInstance();
            try {
                //OBTENCION DE LA FECHA DEL CAMPO DEL FORMULARIO
                Date fechaInput = new SimpleDateFormat("dd-MM-yyyy").parse(etFecha.getText().toString());
                //CREO UN CALENDAR DE APOYO
                Calendar inputCalendar = Calendar.getInstance();
                //ESTABLEZCO EL TIEMPO DE LOS DOS CALENDAR A 0, PARA PODER HACER CORRECTAMENTE LA
                //COMPROBACION SOLO CON LA FECHA, SIN LA HORA
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
                //COMPROBACION DE LA FECHA
                if (inputCalendar.before(calendarHoy)){
                    centinela = false;
                    etFecha.setError("La fecha no puede ser anterior al día de hoy");
                    Toast.makeText(InformacionActivity.this, "La fecha no puede ser anterior a hoy", Toast.LENGTH_SHORT).show();
                }
            } catch (ParseException e) {
                centinela = false;
                etFecha.setError("El formato de la fecha no es correcto");
            }
            //COMPRUEBO QUE LA CONSULTA EXISTA
            if (etConsulta.getText().toString().trim().isEmpty()){
                centinela = false;
                etConsulta.setError("La consulta no puede estar vacía");
            }
            //COMPRUEBO QUE UNO DE LOS CHECKBOX ESTA SELECCIONADO
            if (!checkSi.isChecked() && !checkNo.isChecked()){
                centinela = false;
                checkSi.setError("");
                checkNo.setError("");
            }

            //COMPROBACION DEL FLAG
            //SI ES CORRECTO, MUESTRO UN TOAST Y PONGO LOS CAMPOS EN BLANCO
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
                //SI ES INCOPRRECTO MUESTRO UN TOAST DE AVISO
            } else {
                Toast.makeText(InformacionActivity.this, "Hay errores en los datos, reviselo por favor", Toast.LENGTH_SHORT).show();
            }
            //TRAS TERMINAR HAGO SCROLL HASTA LA PARTE SUPERIOR DE LA PANTALLA
            scrollView.fullScroll(ScrollView.FOCUS_UP);
        });

        //ESTOS DOS EVENTOS HACEN QUE SI UNO ESTA CLICKADO Y SE HACE CLICK EN EL OTRO, ESTE SE
        //DESMARQUE DE FORMA AUTOMÁTICA. SOLO PODREMOS SELECCIONAR UNO
        checkSi.setOnClickListener(view -> {
            if (checkSi.isChecked()){
                checkNo.setChecked(false);
            }
        });

        checkNo.setOnClickListener(view -> {
            if (checkNo.isChecked()){
                checkSi.setChecked(false);
            }
        });
    }
}