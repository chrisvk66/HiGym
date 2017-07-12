package com.example.adrian.mapa;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class Rutinas extends AppCompatActivity implements View.OnClickListener {
        //aatributos para bd
    private CrearBD cbd= null;
    private SQLiteDatabase db= null;

        //atributos spinner
    private Spinner spinner = null;
    private ArrayAdapter<String> adapterSpinner= null;
    private String selectedSpinner = null;
    private int positionSelectedSpinner= 0;
    private String usuarioActivo= null;

        //atributos tabla
    private GridView gridViewTabla = null;
    private ArrayAdapter<String> adapterTabla= null;
    private ArrayList<String> arrayRutinas = new ArrayList<String>();
    private ArrayList<String> datosTablaConsulta2Todo = new ArrayList<String>();
    private ArrayList<String> datosTablaConsulta2Dia = new ArrayList<String>();
    private ArrayList<String> datosTablaConsulta2Ej1 = new ArrayList<String>();
    private ArrayList<String> datosTablaConsulta2Ej2 = new ArrayList<String>();
    private ArrayList<String> datosTablaConsulta2Ej3 = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutinas);

        usuarioActivo= getIntent().getExtras().getString("usuario");
        Log.d("usuarios activo en rutinas", usuarioActivo);


            //buscamos el boton y le asignamos el evento onclick
        findViewById(R.id.rutinas_bton_borrar).setOnClickListener(this);
        findViewById(R.id.rutinas_bton_crear).setOnClickListener(this);
        findViewById(R.id.rutinas_bton_modificar).setOnClickListener(this);

            //añadimos todos los dias de la semana al arraylist
        datosTablaConsulta2Dia.add("L"); datosTablaConsulta2Dia.add("M");
        datosTablaConsulta2Dia.add("X"); datosTablaConsulta2Dia.add("J");
        datosTablaConsulta2Dia.add("V"); datosTablaConsulta2Dia.add("S");
        datosTablaConsulta2Dia.add("D");

        cogerRutinasYRutinaSeleccionada();
    }//onCreate();

    public SQLiteDatabase conexionBD(){
        cbd= new CrearBD(this, "high_gym", null, 1);
        db= cbd.getWritableDatabase();

        return db;
    }

    public void cogerRutinasYRutinaSeleccionada(){
        arrayRutinas= new ArrayList<String>();
            //seleccionamos la bd y realizamos sentencias
        db= conexionBD();
            //si hay bd...
        if(db!= null){
                //buscamos los nombres de todas las rutinas y en número de la rutina seleccionada.
            Cursor cursorRutinas = db.rawQuery("SELECT nombre_rutina from rutina where nombre_user='"+usuarioActivo+"'", null);
            Cursor cursorNumRutinaSelecionada= db.rawQuery("SELECT numero_rutina_seleccionada from usuario where nombre_user='"+usuarioActivo+"'", null);

            //movemos los cursores al principio.
            cursorRutinas.moveToFirst();
            cursorNumRutinaSelecionada.moveToFirst();

                //si hay datos en cursorNumRutinaSelecionada, se lo asignamos a una variable.
            if (cursorNumRutinaSelecionada.getCount()>0){
                positionSelectedSpinner= cursorNumRutinaSelecionada.getInt(0);
            }
                //rellenamos el array con el primer dato de cada consulta (nombre de rutinas).
            do {
                arrayRutinas.add(cursorRutinas.getString(0));
            }while(cursorRutinas.moveToNext());
                //cerramos la bd
            db.close();
        }//if
            //lanzamos el metodo spinner.
        crearSpinner(arrayRutinas);
    }//cogerRutinasYRutinaSeleccionada();

    public void crearSpinner(ArrayList<String> arrayRutinas){
            //buscamos el spinner
        spinner= (Spinner) findViewById(R.id.spinner);
            //creamos el adaptador del spinner y le ponemos un layout por defecto
        adapterSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayRutinas);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterSpinner.notifyDataSetChanged();
            //asignamos el adapterSpinner al spinner
        spinner.setAdapter(adapterSpinner);
            //cambiamos el spinner seleccionado por el que estaba abierto al cerrar. Esta almacenado en la bd.
        spinner.setSelection(positionSelectedSpinner);
            //le asignamos un evento para cuando seleccionemos un item.
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                            //coge el nombre del item seleccionado.
                        selectedSpinner = parent.getItemAtPosition(position).toString();
                            //coge la posicion del spinner seleccionado.
                        positionSelectedSpinner= spinner.getSelectedItemPosition();
                            //realizamos la consulta de la rutina correspondiente.
                        cogerDatosItemSeleccionado(selectedSpinner);
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        //para cuando no haya ningun item seleccionado
                    }
                });//setOnItemSelectedListener();
    }//crearSpinner();

    public void cogerDatosItemSeleccionado(String selectedSpinner){
            //seleccionamos la bd y realizamos sentencias
        db= conexionBD();

            //si hay bd...
        if(db!= null){
                //buscamos los datos necesarios para la tabla.
            Cursor cursorDatosTabla = db.rawQuery("SELECT dia_semana, ejer1, ejer2, ejer3 from dia where nombre_rutina='"+selectedSpinner+"'", null);

                //colocamos los valores a modificar en el update.
            ContentValues actualizarValores = new ContentValues();
            actualizarValores.put("numero_rutina_seleccionada",positionSelectedSpinner);

                //actualizamos en la tabla usuario el numero de rutina seleccionada.
            db.update("usuario", actualizarValores, "nombre_user='"+usuarioActivo+"'", null);

                //limpiamos todos los registros si existen.
            if(datosTablaConsulta2Todo !=null){
                datosTablaConsulta2Ej1.clear();
                datosTablaConsulta2Ej2.clear();
                datosTablaConsulta2Ej3.clear();
                datosTablaConsulta2Todo.clear();
            }//if

                //movemos al cursor al principio y lo vamos recorriendo y guardando los datos en diferentes arrays.
            cursorDatosTabla.moveToFirst();
            do {
                datosTablaConsulta2Ej1.add(cursorDatosTabla.getString(1));
                datosTablaConsulta2Ej2.add(cursorDatosTabla.getString(2));
                datosTablaConsulta2Ej3.add(cursorDatosTabla.getString(3));
            }while(cursorDatosTabla.moveToNext());
                //cerramos la bd
            db.close();
        }//if
            //metemos todos los datos de los arrays por orden dentro de un nuevo arraylist, que se enviara a un metodo.
        datosTablaConsulta2Todo.addAll(datosTablaConsulta2Dia);
        datosTablaConsulta2Todo.addAll(datosTablaConsulta2Ej1);
        datosTablaConsulta2Todo.addAll(datosTablaConsulta2Ej2);
        datosTablaConsulta2Todo.addAll(datosTablaConsulta2Ej3);

        tabla(datosTablaConsulta2Todo);
    }//recogerDatosItemSeleccionado();

    public void tabla(ArrayList<String> datos){
            //buscamos el gridview y le asignamos un adaptador que contiene un layout por defecto.
        gridViewTabla = (GridView)findViewById(R.id.rutinas_grid_view);
        adapterTabla = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datos);
        gridViewTabla.setAdapter(adapterTabla);
    }//tabla();


    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.rutinas_bton_borrar:
                //procederemos a borrar la rutina seleccionada en dicho momento en el spinner.
                //seleccionamos la bd y realizamos sentencias
                db= conexionBD();
                //si hay bd...
                if(db!=null){
                    //creamos un cursor con el nombre de las rutinas existentes y movemos el cursor al principio.
                    Cursor cursorRutinas = db.rawQuery("SELECT nombre_rutina from rutina", null);
                    cursorRutinas.moveToFirst();

                    //si hay más de 1 rutina borramos, en caso contrario no nos permitira.
                    if(cursorRutinas.getCount()>1){
                        //creamos cuadro de dialogo. Depende de si pulsamos si o no, hara una cosa u otra
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("¿Desea borrar la rutina seleccionada?")
                                .setTitle("Advertencia")
                                .setCancelable(false)
                                .setNegativeButton("Cancelar",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                cancelBorradoRutina();
                                                dialog.cancel();
                                            }
                                        })
                                .setPositiveButton("Borrar",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                borrarRutina();
                                            }
                                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                        //reiniciarActivity(this);
                    }else{
                        Toast.makeText(this, "No es posible borrar la rutina. Debe tener al menos 2 para poder borrar.", Toast.LENGTH_SHORT).show();
                    }
                }//case
                break;
            case R.id.rutinas_bton_crear:
                Intent formAnyadir= new Intent(this, FormularioRutina.class);
                formAnyadir.putExtra("usuario", usuarioActivo);

                startActivityForResult(formAnyadir, 1);

                break;
            case R.id.rutinas_bton_modificar:
                Intent formModificar= new Intent(this, ModificarRutina.class);
                formModificar.putExtra("usuario", usuarioActivo);
                formModificar.putExtra("rutina", selectedSpinner);

                startActivityForResult(formModificar, 2);

        }//switch
    }//onClick();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1 || requestCode ==2){
            cogerRutinasYRutinaSeleccionada();
        }
    }

    public void cancelBorradoRutina(){
        Toast.makeText(this, "Ha seleccionado no borrar la rutina.", Toast.LENGTH_SHORT).show();
    }//cancelBorradoRutina();

    public void borrarRutina(){
        Cursor cursorRutinas = db.rawQuery("SELECT nombre_rutina from rutina where nombre_user='"+usuarioActivo+"'", null);

        if(cursorRutinas.getCount()>1){
            //borramos la rutina seleccionada.
            db.delete("rutina", "nombre_rutina='"+selectedSpinner+"'", null);
            Toast.makeText(this, "Rutina borrada con exito", Toast.LENGTH_SHORT).show();

            //actualizamos el numero de la rutina seleccionada a la primera.
            ContentValues valores = new ContentValues();
            valores.put("numero_rutina_seleccionada", 0);
            //actualizamos
            db.update("usuario", valores, "nombre_user='"+usuarioActivo+"'", null);
            //volvemos a ejecutar este metodo, que recargará el spinner para actualizar los datos
            cogerRutinasYRutinaSeleccionada();
        }else Toast.makeText(this, "No se puede borrar. Debe tener al menos 1 rutina creada", Toast.LENGTH_SHORT).show();

    }//borrarRutina();
}//class Rutinas;