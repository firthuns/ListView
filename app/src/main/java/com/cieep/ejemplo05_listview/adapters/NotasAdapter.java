package com.cieep.ejemplo05_listview.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cieep.ejemplo05_listview.MainActivity;
import com.cieep.ejemplo05_listview.R;
import com.cieep.ejemplo05_listview.modelos.Nota;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class NotasAdapter extends ArrayAdapter<Nota> {

    /** Para poder ser Adapter tiene que heredar dela clase ArrayAdapter<tipo de elemento que va a
     * gestionar>
     *
     * @param context -> Sirve para saber que actividad está montado el listado
     * @param resource -> Es la plantilla para ir mostrando cada nota
     * @param objects -> Es el conjunto de elementos que voy a mostrar
     */

    private Context context;
    private int resource;
    private ArrayList<Nota> objects;

    /*
        Ojo! que cuando generamos nuesto constructor, el tercer argumento, nos crea un objetct de tipo list
        que lo modificamos a Arraylist<Nota> > para asi evitar tener que hacer un cast en la linea 43
     */
    public NotasAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Nota> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        }

    /** Antes con un for se hacía lo mismo que este método, ahora se automatiza to.do
     * Ahora se instancia a este metodo tantas veces como elementos tenga el arraylist que le paso
     *
     * @param position > es el elemento del array en el que estoy y nos devuelve  un View
     * @param convertView
     * @param parent
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Un fallo al crear este metodo es que no se suprime la linea 58, llamada al padre
        // return super.getView(position,convertView,parent);
        View filaNota = LayoutInflater.from(context).inflate(resource, null);
        Nota nota = objects.get(position);
        TextView txtTitulo = filaNota.findViewById(R.id.txtTituloFilaNota);
        TextView txtFecha = filaNota.findViewById(R.id.txtFechaFilaNota);
        txtTitulo.setText(nota.getTitulo());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        txtFecha.setText(simpleDateFormat.format(nota.getFecha()));

        return filaNota;
    }
}
