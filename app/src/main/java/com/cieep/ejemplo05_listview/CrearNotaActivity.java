package com.cieep.ejemplo05_listview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cieep.ejemplo05_listview.modelos.Nota;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CrearNotaActivity extends AppCompatActivity {

    private TextView txtTitulo, txtContenido, txtFecha;
    private Button btnGuardar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_nota);
    // enlazamos nuestra variables con las id del layout, correspondiendo con nuestros elementos
        txtTitulo = findViewById(R.id.txtTituloNotaAdd);
        txtContenido = findViewById(R.id.txtContenidoNotaAdd);
        txtFecha = findViewById(R.id.txtFechaNotaAdd);
        btnGuardar = findViewById(R.id.btnCrearNotaAdd);
// habiendo interactuacion cuando hacemos click en el bton guardar
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // condicion para obligar al usuario que introduzca todos los datos
                if (!txtTitulo.getText().toString().isEmpty() &&
                    !txtContenido.getText().toString().isEmpty() &&
                    !txtFecha.getText().toString().isEmpty()) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Nota nota = new Nota(txtTitulo.getText().toString(),
                                             txtContenido.getText().toString(),
                                             simpleDateFormat.parse(txtFecha.getText().toString()));

                        Intent intent = new Intent(); // intent transportador
                        Bundle bundle = new Bundle(); // el que se lleve el parcelable
                        bundle.putParcelable("NOTA", nota);
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        finish();

                    } catch (ParseException e) {
                        Toast.makeText(CrearNotaActivity.this, "El formato de la fecha es incorrecto", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(CrearNotaActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}