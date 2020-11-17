package com.cieep.ejemplo05_listview.modelos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
/*
    HACEMOS ESTA IMPLEMENTACION PARA MOVER LA INFORMACION ENTRE VENTANAS, CONVIRTIENDO LOS
    DATOS EN STRING, PERO OJO, QUE EL ATRIBUTO DATE, es un objeto compuesto no parcelable
    por lo que hay que realizar algunos ajustes para convertirlo en parcelable
 */
public class Nota implements Parcelable {
    private String titulo;
    private String contenido;
    private Date fecha;

    // Creamos dos constructores, 1 con todos los atributos y otro vacío
    public Nota(String titulo, String contenido, Date fecha) {
        this.titulo = titulo;
        this.contenido = contenido;
        this.fecha = fecha;
    }

    public Nota() {
    }
 //********************************************************
    /** convertirmos los datos de la fecha en un tipo LONG
     *     fecha = new Date(in.readLong());
     */
    protected Nota(Parcel in) {
        titulo = in.readString();
        contenido = in.readString();
        fecha = new Date(in.readLong()); /*    Date ->>> Long*/
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(titulo);
        dest.writeString(contenido);
        dest.writeLong(fecha.getTime()); /*    Date ->>> Long*/
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Nota> CREATOR = new Creator<Nota>() {
        @Override
        public Nota createFromParcel(Parcel in) {
            return new Nota(in);
        }

        @Override
        public Nota[] newArray(int size) {
            return new Nota[size];
        }
    };

    // ---------------------- GETTERS Y SETTERS ------------------------------------------------------
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
