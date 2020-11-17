package com.cieep.ejemplo05_listview;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.cieep.ejemplo05_listview.adapters.NotasAdapter;
import com.cieep.ejemplo05_listview.modelos.Nota;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private final int EDIT_NOTA = 2;
    private final int CREAR_NOTA = 1;

    // 1. Conjuntos de Datos
         private ArrayList<Nota> listaNotas;
    // 2. Plantilla de los elementos
        private int plantillaFilas;
    // 3. Adapter para Listview:
        private ListView listView;
        private NotasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    // PRIMERO CREAOS METODO QUE NOS INICIALICE LOS DATOS

        FloatingActionButton fab = inicializaDatos();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CrearNotaActivity.class);
                // la siguiente actividad se crea con espera de obtener resultado, por ello vamos a
                // crear una función startActivityForResult()
                startActivityForResult(intent, CREAR_NOTA);
            }
        });
    }

    private FloatingActionButton inicializaDatos() {
        listaNotas = new ArrayList<>();
        plantillaFilas = R.layout.fila_nota;
        listView = findViewById(R.id.contenedorMain);

        adapter = new NotasAdapter(this, plantillaFilas, listaNotas);
        listView.setAdapter(adapter);
                inicializaNotas();
// setOnItemClickListener , con esto lo que conseguimos en quedarnos con la linea a la cual
        // le hacemos un click para a continuacion modificar sus valores
        /**
         * int i -> es la posicion en la que ha hecho click
         * long l ->id que se ha generado por la fila al completo
         * adapterView -> es el arraylist que ha montado el adapter
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                CrearNota(adapterView, i);

            }

        });

        return findViewById(R.id.fab);
    } // FIN METODO inicializaDatos
//private AlertDialog CrearAlerta(final AdapterView<?> adapterView, final int i){
//    final AlertDialog.Builder constructor = new AlertDialog.Builder(this);
//    View alertaNota = LayoutInflater.from(this).inflate(R.layout.activity_crear_nota, null );
//    final EditText txtTitulo = alertaNota.findViewById(R.id.txtTituloNotaAdd);
//    final EditText txtContenido = alertaNota.findViewById(R.id.txtContenidoNotaAdd);
//
//
//    constructor.setTitle("Agregar nota");
//    constructor.setView(alertaNota);
//    constructor.setNegativeButton("Cancelar" , null);
//    constructor.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
//        @Override
//        public void onClick(DialogInterface dialog, int which) {
//
//
//        }
//    });
//
//    return constructor.create();
//    }

    private void CrearNota(AdapterView<?> adapterView, int i) {
        Nota nota = listaNotas.get(i);
        nota = (Nota) adapterView.getItemAtPosition(i);

        Intent intent = new Intent(MainActivity.this, EditNotaActivity.class);
        Bundle bundle = new Bundle();

        bundle.putParcelable("NOTA", nota);
        bundle.putInt("POS", i);
        intent.putExtras(bundle);
        startActivityForResult(intent, EDIT_NOTA);
    }


    /*       Metodo que nos crea 10 notas en nuestra pantalla principal */

    private void inicializaNotas() {
        for (int i = 0; i < 12; i++) {
            Nota nota = new Nota("Titulo "+i, "Contenido", new Date());
            listaNotas.add(nota);
        }
        // Cuando todas la notas esten dadas de altas, me indique q se ha actualizado
        // le metemos un avisador al adapter, para que nos redibuje
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CREAR_NOTA && resultCode == RESULT_OK){
            if (data != null){ //primera comprobacion que la data no sea nulo
                if (data.getExtras() != null){ //2º comprobacion que el bundle no me sea nulo
                    Nota nota = data.getExtras().getParcelable("NOTA");
                    if (nota != null){ // 3º comprobacion que este la nota
                        listaNotas.add(nota);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }//fin 1º if

        // en la siguiente condicion, gestionamos la informacion devuelta de editnotaactivity
        if (requestCode == EDIT_NOTA && resultCode == RESULT_OK) {
            if (data != null){
                if (data.getExtras() != null){
                    Nota nota = data.getExtras().getParcelable("NOTA");
                    int posicion = data.getExtras().getInt("POS");
                    if (nota != null){
                        listaNotas.set(posicion, nota);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }// fin 1 if
    } // fin onActivityResult()
}