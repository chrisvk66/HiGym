package com.example.adrian.mapa;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by alumno on 13/01/2017.
 */
    //esta clase la extendemos de la clase adapter de andorrid
public class MiAdapter extends ArrayAdapter<Ejercicio>{

        //clase interna
    static class ViewHolder{
        protected TextView tv_nombre;
        protected LinearLayout ll;
    }//viewHolder

        //atributos
    private Activity ctx = null;
    private ArrayList<Ejercicio> modelo;

    public MiAdapter(Activity ctx, ArrayList<Ejercicio> modelo) {
        super(ctx, R.layout.layout_row, modelo);

        this.ctx = ctx;
        this.modelo = modelo;
    }//constructor();

    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;//recicla la fila porque ya existia

        if(row==null){
            /*el list view aun no esta lleno, es decir, faltan filas por instanciar, en este caso tenemos que instanciar
            * la fila completa. Con instanciar nos referimos a inflar el layout de la fila*/
            //infla
            row = ctx.getLayoutInflater().inflate(R.layout.layout_row,null);

            //instanciamos el viewholder
            ViewHolder vh =  new ViewHolder();

            vh.tv_nombre = (TextView) row.findViewById(R.id.row_nombre);
            vh.ll = (LinearLayout) row.findViewById(R.id.row_layout);

            //importante los valores buscados se los asignamos a la vista nueva creada
            row.setTag(vh);
        }
        //recuperamos los valores del viewholder
        ViewHolder vh = (ViewHolder) row.getTag();
        //buscamos el libro en el ArrayList (mdoelo) por su posicion
        Ejercicio l = modelo.get(position);

            //metemos los datos del libro en los objetos
        vh.tv_nombre.setText(l.getNombre());
        vh.tv_nombre.setTextColor(Color.WHITE);
        vh.tv_nombre.setTextSize(23);
        return row;
    }



}//class
