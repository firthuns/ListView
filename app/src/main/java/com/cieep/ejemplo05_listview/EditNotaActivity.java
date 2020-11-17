package com.cieep.ejemplo05_listview;

import android.content.Intent;
import android.os.Bundle;

import com.cieep.ejemplo05_listview.modelos.Nota;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EditNotaActivity extends AppCompatActivity {

    // PASO 1: Definir Vistas en Variables
    private EditText txtTitulo, txtContenido, txtFecha;

    private Nota nota;
    private int posicion;
    private SimpleDateFormat simpleDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_nota);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    // paso 2: enlazar codigo con el layout, y siempre hacer esto detras
        // de la linea 32:  setContentView(R.layout.activity_edit_nota);
        txtTitulo = findViewById(R.id.txtTituloNotaEdit);
        txtContenido = findViewById(R.id.txtContenidoNotaEdit);
        txtFecha = findViewById(R.id.txtFechaEdit);

        // Me traigo la nota y compruebo que no venga null por el intent
        if (getIntent().getExtras() != null){
            nota =  getIntent().getExtras().getParcelable("NOTA");
            posicion = getIntent().getExtras().getInt("POS");

            if (nota != null){
                txtTitulo.setText(nota.getTitulo());
                txtContenido.setText(nota.getContenido());
                simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                txtFecha.setText(simpleDateFormat.format(nota.getFecha()));
            }
            else {
                // en este caso entrara en el caso de que la nota proveniente de la otra ventana
                //haya desaparecido por cualquier motivo, nosotro creemos una nota nueva
                nota = new Nota();
                txtTitulo.setHint("Titulo");

            }
        }
// este evento controla que para guardar los cambios este to.do rellenado
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               if (!txtTitulo.getText().toString().isEmpty() &&
                    !txtContenido.getText().toString().isEmpty() &&
                    !txtFecha.getText().toString().isEmpty()){
                   nota.setTitulo(txtTitulo.getText().toString());
                   nota.setContenido(txtContenido.getText().toString());
                   try {
                       nota.setFecha(simpleDateFormat.parse(txtFecha.getText().toString()));
                       Intent intent = new Intent();
                       Bundle bundle = new Bundle();
                       bundle.putParcelable("NOTA", nota);
                       bundle.putInt("POS", posicion);
                       intent.putExtras(bundle);
                       setResult(RESULT_OK, intent);
                       finish();
                   } catch (ParseException e) {
                       Toast.makeText(EditNotaActivity.this, "La fecha no es correcta", Toast.LENGTH_SHORT).show();
                   }
               }
            } // fin onclick
        }); // fin setOnClickListener
    }
}