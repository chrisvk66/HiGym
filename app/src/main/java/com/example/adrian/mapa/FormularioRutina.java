package com.example.adrian.mapa;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class FormularioRutina extends AppCompatActivity implements View.OnClickListener {
    //atributos para bd
    private CrearBD cbd= null;
    private SQLiteDatabase db= null;
    private String usuario= null;
    private String id_dia= null;
    private String id_progreso= null;

    private EditText rutina_nombre= null;
    private EditText rutina_et_ejer1= null;
    private EditText rutina_et_ejer2= null;
    private EditText rutina_et_ejer3= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_rutina);

        findViewById(R.id.bton_form).setOnClickListener(this);
        usuario= getIntent().getExtras().getString("usuario");
    }

    public SQLiteDatabase conexionBD(){
        cbd= new CrearBD(this, "high_gym", null, 1);
        db= cbd.getWritableDatabase();

        return db;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bton_form:
                insertarRutina();

                break;
        }//switch
    }//onClick();


    public void insertarRutina(){
        //creamos la conexion a la bd
        db= conexionBD();

        //creamos un cursor para almacenar los nombres de todas las rutinas existentes
        Cursor cursorRutinas = db.rawQuery("SELECT nombre_rutina from rutina", null);
        cursorRutinas.moveToFirst();

        //leemos el nombre de la rutina
        rutina_nombre= (EditText) findViewById(R.id.formulario_nombre_rutina);

        //comprobamos si existe o no el nombre de rutina
        boolean existeRutina= false;
        do {
            if(cursorRutinas.getString(0).equals(rutina_nombre.getText().toString())){
                existeRutina= true;
            }
        }while(cursorRutinas.moveToNext());

        //si existe, enviamos un mensaje y no hacemos nada
        if(existeRutina){
            Toast.makeText(this, "Ya existe la rutina. Cambie el nombre.", Toast.LENGTH_SHORT).show();
        }else{
            //si no existe el usuario...
            //Creamos el registro a insertar como objeto ContentValues
            ContentValues nuevaRutina = new ContentValues();
            nuevaRutina.put("nombre_rutina", rutina_nombre.getText().toString());
            nuevaRutina.put("nombre_user", usuario);

            //Insertamos el registro en la base de datos
            db.insert("rutina", null, nuevaRutina);

            insertarLunes();
            insertarMartes();
            insertarMiercoles();
            insertarJueves();
            insertarViernes();
            insertarSabado();
            insertarDomingo();

            Toast.makeText(this, "Rutina creada.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }//insertarRutina();

    public void insertarLunes(){
        //creamos la conexion a la bd
        db= conexionBD();

        //insertamos en la tabla progreso
        Cursor cursorProgreso = db.rawQuery("SELECT id_progreso from progreso", null);

        //buscamos un numero que no exista ya en la bd como id
        boolean existeProgreso= false;
        for(int i= 0; i<10000; i++){
            cursorProgreso.moveToFirst();
            existeProgreso= false;

            do{
                String dato= cursorProgreso.getString(0);
                int num= Integer.parseInt(dato);
                if(num== i){
                    existeProgreso= true;
                    cursorProgreso.moveToLast();
                }
            }while(cursorProgreso.moveToNext());

            if(!existeProgreso){
                id_progreso= Integer.toString(i);
                i= 10001;
            }
        }

        //insertamos en la tabla progreso
        ContentValues nuevoProgreso = new ContentValues();

        nuevoProgreso.put("id_progreso", id_progreso);
        nuevoProgreso.put("boton1", "no");
        nuevoProgreso.put("boton2", "no");
        nuevoProgreso.put("boton3", "no");
        nuevoProgreso.put("boton_agua", 0);
        nuevoProgreso.put("boton_barra_progreso", 0);

        //Insertamos el registro en la base de datos
        db.insert("progreso", null, nuevoProgreso);

        //ahora vamos con la tabla dia...
        //cogemos los valores
        rutina_et_ejer1= (EditText) findViewById(R.id.formulario_lun_ejer1);
        rutina_et_ejer2= (EditText) findViewById(R.id.formulario_lun_ejer2);
        rutina_et_ejer3= (EditText) findViewById(R.id.formulario_lun_ejer3);

        //cogemos el id de todos los datos de la tabla dia
        ContentValues nuevoDia = new ContentValues();
        Cursor cursorDia = db.rawQuery("SELECT id_dia from dia", null);

        boolean existeDia= false;
        for(int i= 0; i<10000; i++){
            cursorProgreso.moveToFirst();
            existeDia= false;

            do{
                if(Integer.parseInt(cursorProgreso.getString(0))== i){
                    existeDia= true;
                    cursorProgreso.moveToLast();
                }
            }while(cursorProgreso.moveToNext());

            if(!existeDia){
                id_dia= Integer.toString(i);
                i= 10001;
            }
        }

        //agregamos el resto de datos
        nuevoDia.put("id_dia", id_dia);
        nuevoDia.put("dia_semana", "lunes");
        nuevoDia.put("ejer1", rutina_et_ejer1.getText().toString());
        nuevoDia.put("ejer2", rutina_et_ejer2.getText().toString());
        nuevoDia.put("ejer3", rutina_et_ejer3.getText().toString());
        nuevoDia.put("nombre_rutina", rutina_nombre.getText().toString());
        nuevoDia.put("id_progreso", id_progreso);

        //Insertamos el registro en la base de datos
        db.insert("dia", null, nuevoDia);
        //cerramos la bd
        db.close();
    }//insertarLunes();


    public void insertarMartes(){
        //creamos la conexion a la bd
        db= conexionBD();

        //insertamos en la tabla progreso
        Cursor cursorProgreso = db.rawQuery("SELECT id_progreso from progreso", null);
        //buscamos un numero que no exista ya en la bd como id
        boolean existeProgreso= false;
        for(int i= 0; i<10000; i++){
            cursorProgreso.moveToFirst();
            existeProgreso= false;

            do{
                if(Integer.parseInt(cursorProgreso.getString(0))== i){
                    existeProgreso= true;
                    cursorProgreso.moveToLast();
                }
            }while(cursorProgreso.moveToNext());

            if(!existeProgreso){
                id_progreso= Integer.toString(i);
                i= 10001;
            }
        }

        //insertamos en la tabla progreso
        ContentValues nuevoProgreso = new ContentValues();

        nuevoProgreso.put("id_progreso", id_progreso);
        nuevoProgreso.put("boton1", "no");
        nuevoProgreso.put("boton2", "no");
        nuevoProgreso.put("boton3", "no");
        nuevoProgreso.put("boton_agua", 0);
        nuevoProgreso.put("boton_barra_progreso", 0);

        //Insertamos el registro en la base de datos
        db.insert("progreso", null, nuevoProgreso);

        //ahora vamos con la tabla dia...
        //cogemos los valores
        rutina_et_ejer1= (EditText) findViewById(R.id.formulario_mar_ejer1);
        rutina_et_ejer2= (EditText) findViewById(R.id.formulario_mar_ejer2);
        rutina_et_ejer3= (EditText) findViewById(R.id.formulario_mar_ejer3);

        //cogemos el id de todos los datos de la tabla dia
        ContentValues nuevoDia = new ContentValues();
        Cursor cursorDia = db.rawQuery("SELECT id_dia from dia", null);

        boolean existeDia= false;
        for(int i= 0; i<10000; i++){
            cursorProgreso.moveToFirst();
            existeDia= false;

            do{
                if(Integer.parseInt(cursorProgreso.getString(0))== i){
                    existeDia= true;
                    cursorProgreso.moveToLast();
                }
            }while(cursorProgreso.moveToNext());

            if(!existeDia){
                id_dia= Integer.toString(i);
                i= 10001;
            }
        }

        //agregamos el resto de datos
        nuevoDia.put("id_dia", id_dia);
        nuevoDia.put("dia_semana", "martes");
        nuevoDia.put("ejer1", rutina_et_ejer1.getText().toString());
        nuevoDia.put("ejer2", rutina_et_ejer2.getText().toString());
        nuevoDia.put("ejer3", rutina_et_ejer3.getText().toString());
        nuevoDia.put("nombre_rutina", rutina_nombre.getText().toString());
        nuevoDia.put("id_progreso", id_progreso);

        //Insertamos el registro en la base de datos
        db.insert("dia", null, nuevoDia);
        //cerramos la bd
        db.close();
        id_dia= null;
        id_progreso= null;
    }//insertarMartes();

    public void insertarMiercoles(){
        //creamos la conexion a la bd
        db= conexionBD();

        //insertamos en la tabla progreso
        Cursor cursorProgreso = db.rawQuery("SELECT id_progreso from progreso", null);
        //buscamos un numero que no exista ya en la bd como id
        boolean existeProgreso= false;
        for(int i= 0; i<10000; i++){
            cursorProgreso.moveToFirst();
            existeProgreso= false;

            do{
                if(Integer.parseInt(cursorProgreso.getString(0))== i){
                    existeProgreso= true;
                    cursorProgreso.moveToLast();
                }
            }while(cursorProgreso.moveToNext());

            if(!existeProgreso){
                id_progreso= Integer.toString(i);
                i= 10001;
            }
        }
        //Log.d("Insertamos miercoles...", cursorProgreso.getString(0));

        //insertamos en la tabla progreso
        ContentValues nuevoProgreso = new ContentValues();

        nuevoProgreso.put("id_progreso", id_progreso);
        nuevoProgreso.put("boton1", "no");
        nuevoProgreso.put("boton2", "no");
        nuevoProgreso.put("boton3", "no");
        nuevoProgreso.put("boton_agua", 0);
        nuevoProgreso.put("boton_barra_progreso", 0);

        //Insertamos el registro en la base de datos
        db.insert("progreso", null, nuevoProgreso);

        //ahora vamos con la tabla dia...
        //cogemos los valores
        rutina_et_ejer1= (EditText) findViewById(R.id.formulario_mie_ejer1);
        rutina_et_ejer2= (EditText) findViewById(R.id.formulario_mie_ejer2);
        rutina_et_ejer3= (EditText) findViewById(R.id.formulario_mie_ejer3);

        //cogemos el id de todos los datos de la tabla dia
        ContentValues nuevoDia = new ContentValues();
        Cursor cursorDia = db.rawQuery("SELECT id_dia from dia", null);

        boolean existeDia= false;
        for(int i= 0; i<10000; i++){
            cursorProgreso.moveToFirst();
            existeDia= false;

            do{
                if(Integer.parseInt(cursorProgreso.getString(0))== i){
                    existeDia= true;
                    cursorProgreso.moveToLast();
                }
            }while(cursorProgreso.moveToNext());

            if(!existeDia){
                id_dia= Integer.toString(i);
                i= 10001;
            }
        }

        //agregamos el resto de datos
        nuevoDia.put("id_dia", id_dia);
        nuevoDia.put("dia_semana", "miercoles");
        nuevoDia.put("ejer1", rutina_et_ejer1.getText().toString());
        nuevoDia.put("ejer2", rutina_et_ejer2.getText().toString());
        nuevoDia.put("ejer3", rutina_et_ejer3.getText().toString());
        nuevoDia.put("nombre_rutina", rutina_nombre.getText().toString());
        nuevoDia.put("id_progreso", id_progreso);

        //Insertamos el registro en la base de datos
        db.insert("dia", null, nuevoDia);
        //cerramos la bd
        db.close();
        id_dia= null;
        id_progreso= null;
    }//insertarMiercoles();

    public void insertarJueves(){
        //creamos la conexion a la bd
        db= conexionBD();

        //insertamos en la tabla progreso
        Cursor cursorProgreso = db.rawQuery("SELECT id_progreso from progreso", null);
        //buscamos un numero que no exista ya en la bd como id
        boolean existeProgreso= false;
        for(int i= 0; i<10000; i++){
            cursorProgreso.moveToFirst();
            existeProgreso= false;

            do{
                if(Integer.parseInt(cursorProgreso.getString(0))== i){
                    existeProgreso= true;
                    cursorProgreso.moveToLast();
                }
            }while(cursorProgreso.moveToNext());

            if(!existeProgreso){
                id_progreso= Integer.toString(i);
                i= 10001;
            }
        }
        //Log.d("Insertamos jueves...", cursorProgreso.getString(0));

        //insertamos en la tabla progreso
        ContentValues nuevoProgreso = new ContentValues();

        nuevoProgreso.put("id_progreso", id_progreso);
        nuevoProgreso.put("boton1", "no");
        nuevoProgreso.put("boton2", "no");
        nuevoProgreso.put("boton3", "no");
        nuevoProgreso.put("boton_agua", 0);
        nuevoProgreso.put("boton_barra_progreso", 0);

        //Insertamos el registro en la base de datos
        db.insert("progreso", null, nuevoProgreso);

        //ahora vamos con la tabla dia...
        //cogemos los valores
        rutina_et_ejer1= (EditText) findViewById(R.id.formulario_jue_ejer1);
        rutina_et_ejer2= (EditText) findViewById(R.id.formulario_jue_ejer2);
        rutina_et_ejer3= (EditText) findViewById(R.id.formulario_jue_ejer3);

        //cogemos el id de todos los datos de la tabla dia
        ContentValues nuevoDia = new ContentValues();
        Cursor cursorDia = db.rawQuery("SELECT id_dia from dia", null);

        boolean existeDia= false;
        for(int i= 0; i<10000; i++){
            cursorProgreso.moveToFirst();
            existeDia= false;

            do{
                if(Integer.parseInt(cursorProgreso.getString(0))== i){
                    existeDia= true;
                    cursorProgreso.moveToLast();
                }
            }while(cursorProgreso.moveToNext());

            if(!existeDia){
                id_dia= Integer.toString(i);
                i= 10001;
            }
        }

        //agregamos el resto de datos
        nuevoDia.put("id_dia", id_dia);
        nuevoDia.put("dia_semana", "jueves");
        nuevoDia.put("ejer1", rutina_et_ejer1.getText().toString());
        nuevoDia.put("ejer2", rutina_et_ejer2.getText().toString());
        nuevoDia.put("ejer3", rutina_et_ejer3.getText().toString());
        nuevoDia.put("nombre_rutina", rutina_nombre.getText().toString());
        nuevoDia.put("id_progreso", id_progreso);

        //Insertamos el registro en la base de datos
        db.insert("dia", null, nuevoDia);
        //cerramos la bd
        db.close();
        id_dia= null;
        id_progreso= null;
    }//insertarJueves();

    public void insertarViernes(){
        //creamos la conexion a la bd
        db= conexionBD();

        //insertamos en la tabla progreso
        Cursor cursorProgreso = db.rawQuery("SELECT id_progreso from progreso", null);
        //buscamos un numero que no exista ya en la bd como id
        boolean existeProgreso= false;
        for(int i= 0; i<10000; i++){
            cursorProgreso.moveToFirst();
            existeProgreso= false;

            do{
                if(Integer.parseInt(cursorProgreso.getString(0))== i){
                    existeProgreso= true;
                    cursorProgreso.moveToLast();
                }
            }while(cursorProgreso.moveToNext());

            if(!existeProgreso){
                id_progreso= Integer.toString(i);
                i= 10001;
            }
        }
        //Log.d("Insertamos viernes...", cursorProgreso.getString(0));

        //insertamos en la tabla progreso
        ContentValues nuevoProgreso = new ContentValues();

        nuevoProgreso.put("id_progreso", id_progreso);
        nuevoProgreso.put("boton1", "no");
        nuevoProgreso.put("boton2", "no");
        nuevoProgreso.put("boton3", "no");
        nuevoProgreso.put("boton_agua", 0);
        nuevoProgreso.put("boton_barra_progreso", 0);

        //Insertamos el registro en la base de datos
        db.insert("progreso", null, nuevoProgreso);

        //ahora vamos con la tabla dia...
        //cogemos los valores
        rutina_et_ejer1= (EditText) findViewById(R.id.formulario_vie_ejer1);
        rutina_et_ejer2= (EditText) findViewById(R.id.formulario_vie_ejer2);
        rutina_et_ejer3= (EditText) findViewById(R.id.formulario_vie_ejer3);

        //cogemos el id de todos los datos de la tabla dia
        ContentValues nuevoDia = new ContentValues();
        Cursor cursorDia = db.rawQuery("SELECT id_dia from dia", null);

        boolean existeDia= false;
        for(int i= 0; i<10000; i++){
            cursorProgreso.moveToFirst();
            existeDia= false;

            do{
                if(Integer.parseInt(cursorProgreso.getString(0))== i){
                    existeDia= true;
                    cursorProgreso.moveToLast();
                }
            }while(cursorProgreso.moveToNext());

            if(!existeDia){
                id_dia= Integer.toString(i);
                i= 10001;
            }
        }

        //agregamos el resto de datos
        nuevoDia.put("id_dia", id_dia);
        nuevoDia.put("dia_semana", "viernes");
        nuevoDia.put("ejer1", rutina_et_ejer1.getText().toString());
        nuevoDia.put("ejer2", rutina_et_ejer2.getText().toString());
        nuevoDia.put("ejer3", rutina_et_ejer3.getText().toString());
        nuevoDia.put("nombre_rutina", rutina_nombre.getText().toString());
        nuevoDia.put("id_progreso", id_progreso);

        //Insertamos el registro en la base de datos
        db.insert("dia", null, nuevoDia);
        //cerramos la bd
        db.close();
        id_dia= null;
        id_progreso= null;
    }//insertarViernes();

    public void insertarSabado(){
        //creamos la conexion a la bd
        db= conexionBD();

        //insertamos en la tabla progreso
        Cursor cursorProgreso = db.rawQuery("SELECT id_progreso from progreso", null);
        //buscamos un numero que no exista ya en la bd como id
        boolean existeProgreso= false;
        for(int i= 0; i<10000; i++){
            cursorProgreso.moveToFirst();
            existeProgreso= false;

            do{
                if(Integer.parseInt(cursorProgreso.getString(0))== i){
                    existeProgreso= true;
                    cursorProgreso.moveToLast();
                }
            }while(cursorProgreso.moveToNext());

            if(!existeProgreso){
                id_progreso= Integer.toString(i);
                i= 10001;
            }
        }
        //Log.d("Insertamos sabado...", cursorProgreso.getString(0));

        //insertamos en la tabla progreso
        ContentValues nuevoProgreso = new ContentValues();

        nuevoProgreso.put("id_progreso", id_progreso);
        nuevoProgreso.put("boton1", "no");
        nuevoProgreso.put("boton2", "no");
        nuevoProgreso.put("boton3", "no");
        nuevoProgreso.put("boton_agua", 0);
        nuevoProgreso.put("boton_barra_progreso", 0);

        //Insertamos el registro en la base de datos
        db.insert("progreso", null, nuevoProgreso);

        //ahora vamos con la tabla dia...
        //cogemos los valores
        rutina_et_ejer1= (EditText) findViewById(R.id.formulario_sab_ejer1);
        rutina_et_ejer2= (EditText) findViewById(R.id.formulario_sab_ejer2);
        rutina_et_ejer3= (EditText) findViewById(R.id.formulario_sab_ejer3);

        //cogemos el id de todos los datos de la tabla dia
        ContentValues nuevoDia = new ContentValues();
        Cursor cursorDia = db.rawQuery("SELECT id_dia from dia", null);

        boolean existeDia= false;
        for(int i= 0; i<10000; i++){
            cursorProgreso.moveToFirst();
            existeDia= false;

            do{
                if(Integer.parseInt(cursorProgreso.getString(0))== i){
                    existeDia= true;
                    cursorProgreso.moveToLast();
                }
            }while(cursorProgreso.moveToNext());

            if(!existeDia){
                id_dia= Integer.toString(i);
                i= 10001;
            }
        }

        //agregamos el resto de datos
        nuevoDia.put("id_dia", id_dia);
        nuevoDia.put("dia_semana", "sabado");
        nuevoDia.put("ejer1", rutina_et_ejer1.getText().toString());
        nuevoDia.put("ejer2", rutina_et_ejer2.getText().toString());
        nuevoDia.put("ejer3", rutina_et_ejer3.getText().toString());
        nuevoDia.put("nombre_rutina", rutina_nombre.getText().toString());
        nuevoDia.put("id_progreso", id_progreso);

        //Insertamos el registro en la base de datos
        db.insert("dia", null, nuevoDia);
        //cerramos la bd
        db.close();
        id_dia= null;
        id_progreso= null;
    }//insertarSabado();

    public void insertarDomingo(){
        //creamos la conexion a la bd
        db= conexionBD();

        //insertamos en la tabla progreso
        Cursor cursorProgreso = db.rawQuery("SELECT id_progreso from progreso", null);
        //buscamos un numero que no exista ya en la bd como id
        boolean existeProgreso= false;
        for(int i= 0; i<10000; i++){
            cursorProgreso.moveToFirst();
            existeProgreso= false;

            do{
                if(Integer.parseInt(cursorProgreso.getString(0))== i){
                    existeProgreso= true;
                    cursorProgreso.moveToLast();
                }
            }while(cursorProgreso.moveToNext());

            if(!existeProgreso){
                id_progreso= Integer.toString(i);
                i= 10001;
            }
        }
        //Log.d("Insertamos domingo...", cursorProgreso.getString(0));

        //insertamos en la tabla progreso
        ContentValues nuevoProgreso = new ContentValues();

        nuevoProgreso.put("id_progreso", id_progreso);
        nuevoProgreso.put("boton1", "no");
        nuevoProgreso.put("boton2", "no");
        nuevoProgreso.put("boton3", "no");
        nuevoProgreso.put("boton_agua", 0);
        nuevoProgreso.put("boton_barra_progreso", 0);

        //Insertamos el registro en la base de datos
        db.insert("progreso", null, nuevoProgreso);

        //ahora vamos con la tabla dia...
        //cogemos los valores
        rutina_et_ejer1= (EditText) findViewById(R.id.formulario_dom_ejer1);
        rutina_et_ejer2= (EditText) findViewById(R.id.formulario_dom_ejer2);
        rutina_et_ejer3= (EditText) findViewById(R.id.formulario_dom_ejer3);

        //cogemos el id de todos los datos de la tabla dia
        ContentValues nuevoDia = new ContentValues();
        Cursor cursorDia = db.rawQuery("SELECT id_dia from dia", null);

        boolean existeDia= false;
        for(int i= 0; i<10000; i++){
            cursorProgreso.moveToFirst();
            existeDia= false;

            do{
                if(Integer.parseInt(cursorProgreso.getString(0))== i){
                    existeDia= true;
                    cursorProgreso.moveToLast();
                }
            }while(cursorProgreso.moveToNext());

            if(!existeDia){
                id_dia= Integer.toString(i);
                i= 10001;
            }
        }

        //agregamos el resto de datos
        nuevoDia.put("id_dia", id_dia);
        nuevoDia.put("dia_semana", "domingo");
        nuevoDia.put("ejer1", rutina_et_ejer1.getText().toString());
        nuevoDia.put("ejer2", rutina_et_ejer2.getText().toString());
        nuevoDia.put("ejer3", rutina_et_ejer3.getText().toString());
        nuevoDia.put("nombre_rutina", rutina_nombre.getText().toString());
        nuevoDia.put("id_progreso", id_progreso);

        //Insertamos el registro en la base de datos
        db.insert("dia", null, nuevoDia);
        //cerramos la bd
        db.close();
        id_dia= null;
        id_progreso= null;
    }//insertarDomingo();

    public void leerBD(){
        db= null;
        db= conexionBD();

        Cursor cursorRutina = db.rawQuery("SELECT nombre_rutina from rutina", null);
        cursorRutina.moveToFirst();

        do{
            Log.d("cursor rutina", cursorRutina.getString(0));
        }while(cursorRutina.moveToNext());

        Cursor cursorProgreso = db.rawQuery("SELECT id_progreso from progreso", null);
        cursorProgreso.moveToFirst();

        do{
            Log.d("cursor progreso", cursorProgreso.getString(0));
        }while(cursorProgreso.moveToNext());

        Cursor cursorDia = db.rawQuery("SELECT id_dia from dia", null);
        cursorDia.moveToFirst();

        do{
            Log.d("cursor dia", cursorDia.getString(0));
        }while(cursorDia.moveToNext());

        db.close();
    }//leerBD();
}//class