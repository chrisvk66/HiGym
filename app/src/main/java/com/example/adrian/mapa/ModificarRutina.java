package com.example.adrian.mapa;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by beca_ux2 on 19/06/2017.
 */

public class ModificarRutina extends AppCompatActivity implements View.OnClickListener{
    //atributos para bd
    private CrearBD cbd= null;
    private SQLiteDatabase db= null;

    private String usuario= null;
    private String rutina_nombre22 = null;


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

        usuario= getIntent().getExtras().getString("usuario");;
        rutina_nombre22 = getIntent().getExtras().getString("rutina");;

        rutina_nombre = (EditText) findViewById(R.id.formulario_nombre_rutina);
        rutina_nombre.setText(rutina_nombre22);

        recogerDatos();

        //findViewById(R.id.bton_form).setOnClickListener(this);

        recogerDatos();
    }

    public SQLiteDatabase conexionBD(){
        cbd= new CrearBD(this, "high_gym", null, 1);
        db= cbd.getWritableDatabase();

        return db;
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bton_form:
                //recogerDatos();
                insertarRutina();
                break;
        }//switch
    }//onClick();

    public void recogerDatos(){
        recogerDatosRutinaLunes();
        recogerDatosRutinaMartes();
        recogerDatosRutinaMiercoles();
        recogerDatosRutinaJueves();
        recogerDatosRutinaViernes();
        recogerDatosRutinaSabado();
        recogerDatosRutinaDomingo();
    }

    public void recogerDatosRutinaLunes(){
        db= conexionBD();

        Cursor cursorDia = db.rawQuery("SELECT id_dia, ejer1, ejer2, ejer3 from dia where nombre_rutina='"+ rutina_nombre22 +"'" +
                " and dia_semana='lunes'", null);
        cursorDia.moveToFirst();

        rutina_et_ejer1 = (EditText) findViewById(R.id.formulario_lun_ejer1);
        rutina_et_ejer2 = (EditText) findViewById(R.id.formulario_lun_ejer2);
        rutina_et_ejer3 = (EditText) findViewById(R.id.formulario_lun_ejer3);

        rutina_et_ejer1.setText(cursorDia.getString(1).toString());
        rutina_et_ejer2.setText(cursorDia.getString(2).toString());
        rutina_et_ejer3.setText(cursorDia.getString(3).toString());
    }//recogerDatosRutinaLunes();

    public void recogerDatosRutinaMartes(){
        db= conexionBD();

        Cursor cursorDia = db.rawQuery("SELECT id_dia, ejer1, ejer2, ejer3 from dia where nombre_rutina='"+ rutina_nombre22 +"'" +
                " and dia_semana='martes'", null);
        cursorDia.moveToFirst();

        rutina_et_ejer1 = (EditText) findViewById(R.id.formulario_mar_ejer1);
        rutina_et_ejer2 = (EditText) findViewById(R.id.formulario_mar_ejer2);
        rutina_et_ejer3 = (EditText) findViewById(R.id.formulario_mar_ejer3);

        rutina_et_ejer1.setText(cursorDia.getString(1).toString());
        rutina_et_ejer2.setText(cursorDia.getString(2).toString());
        rutina_et_ejer3.setText(cursorDia.getString(3).toString());
    }//recogerDatosRutinaMartes();

    public void recogerDatosRutinaMiercoles(){
        db= conexionBD();

        Cursor cursorDia = db.rawQuery("SELECT id_dia, ejer1, ejer2, ejer3 from dia where nombre_rutina='"+ rutina_nombre22 +"'" +
                " and dia_semana='miercoles'", null);
        cursorDia.moveToFirst();

        rutina_et_ejer1 = (EditText) findViewById(R.id.formulario_mie_ejer1);
        rutina_et_ejer2 = (EditText) findViewById(R.id.formulario_mie_ejer2);
        rutina_et_ejer3 = (EditText) findViewById(R.id.formulario_mie_ejer3);

        rutina_et_ejer1.setText(cursorDia.getString(1).toString());
        rutina_et_ejer2.setText(cursorDia.getString(2).toString());
        rutina_et_ejer3.setText(cursorDia.getString(3).toString());
    }//recogerDatosRutinaMiercoles();

    public void recogerDatosRutinaJueves(){
        db= conexionBD();

        Cursor cursorDia = db.rawQuery("SELECT id_dia, ejer1, ejer2, ejer3 from dia where nombre_rutina='"+ rutina_nombre22 +"'" +
                " and dia_semana='jueves'", null);
        cursorDia.moveToFirst();

        rutina_et_ejer1 = (EditText) findViewById(R.id.formulario_jue_ejer1);
        rutina_et_ejer2 = (EditText) findViewById(R.id.formulario_jue_ejer2);
        rutina_et_ejer3 = (EditText) findViewById(R.id.formulario_jue_ejer3);

        rutina_et_ejer1.setText(cursorDia.getString(1).toString());
        rutina_et_ejer2.setText(cursorDia.getString(2).toString());
        rutina_et_ejer3.setText(cursorDia.getString(3).toString());
    }//recogerDatosRutinaJueves();

    public void recogerDatosRutinaViernes(){
        db= conexionBD();

        Cursor cursorDia = db.rawQuery("SELECT id_dia, ejer1, ejer2, ejer3 from dia where nombre_rutina='"+ rutina_nombre22 +"'" +
                " and dia_semana='viernes'", null);
        cursorDia.moveToFirst();

        rutina_et_ejer1 = (EditText) findViewById(R.id.formulario_vie_ejer1);
        rutina_et_ejer2 = (EditText) findViewById(R.id.formulario_vie_ejer2);
        rutina_et_ejer3 = (EditText) findViewById(R.id.formulario_vie_ejer3);

        rutina_et_ejer1.setText(cursorDia.getString(1).toString());
        rutina_et_ejer2.setText(cursorDia.getString(2).toString());
        rutina_et_ejer3.setText(cursorDia.getString(3).toString());
    }//recogerDatosRutinaViernes();

    public void recogerDatosRutinaSabado(){
        db= conexionBD();

        Cursor cursorDia = db.rawQuery("SELECT id_dia, ejer1, ejer2, ejer3 from dia where nombre_rutina='"+ rutina_nombre22 +"'" +
                " and dia_semana='sabado'", null);
        cursorDia.moveToFirst();

        rutina_et_ejer1 = (EditText) findViewById(R.id.formulario_sab_ejer1);
        rutina_et_ejer2 = (EditText) findViewById(R.id.formulario_sab_ejer2);
        rutina_et_ejer3 = (EditText) findViewById(R.id.formulario_sab_ejer3);

        rutina_et_ejer1.setText(cursorDia.getString(1).toString());
        rutina_et_ejer2.setText(cursorDia.getString(2).toString());
        rutina_et_ejer3.setText(cursorDia.getString(3).toString());
    }//recogerDatosRutinaSabado();

    public void recogerDatosRutinaDomingo(){
        db= conexionBD();

        Cursor cursorDia = db.rawQuery("SELECT id_dia, ejer1, ejer2, ejer3 from dia where nombre_rutina='"+ rutina_nombre22 +"'" +
                " and dia_semana='domingo'", null);
        cursorDia.moveToFirst();

        rutina_et_ejer1 = (EditText) findViewById(R.id.formulario_dom_ejer1);
        rutina_et_ejer2 = (EditText) findViewById(R.id.formulario_dom_ejer2);
        rutina_et_ejer3 = (EditText) findViewById(R.id.formulario_dom_ejer3);

        rutina_et_ejer1.setText(cursorDia.getString(1).toString());
        rutina_et_ejer2.setText(cursorDia.getString(2).toString());
        rutina_et_ejer3.setText(cursorDia.getString(3).toString());
    }//recogerDatosRutinaDomingo();

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
                if(cursorRutinas.getString(0).equals(rutina_nombre22));
                else existeRutina= true;
            }
        }while(cursorRutinas.moveToNext());

        //si existe, enviamos un mensaje y no hacemos nada
        if(existeRutina){
            Toast.makeText(this, "Ya existe la rutina. Cambie el nombre.", Toast.LENGTH_SHORT).show();
        }else{
            //si no existe el usuario...
            //Creamos el registro a insertar como objeto ContentValues
            ContentValues modificarRutina = new ContentValues();
            modificarRutina.put("nombre_rutina", rutina_nombre.getText().toString());

            db.update("dia", modificarRutina, "nombre_rutina='"+rutina_nombre22+"'", null);
            //Actualizamos el registro en la base de datos
            db.update("rutina", modificarRutina, "nombre_rutina='"+rutina_nombre22+"'", null);

            insertarLunes();
            insertarMartes();
            insertarMiercoles();
            insertarJueves();
            insertarViernes();
            insertarSabado();
            insertarDomingo();

            Toast.makeText(this, "Rutina modificada.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }//insertarRutina();

    public void insertarLunes(){
        //creamos la conexion a la bd
        db= conexionBD();

        //cogemos los valores
        rutina_et_ejer1= (EditText) findViewById(R.id.formulario_lun_ejer1);
        rutina_et_ejer2= (EditText) findViewById(R.id.formulario_lun_ejer2);
        rutina_et_ejer3= (EditText) findViewById(R.id.formulario_lun_ejer3);

            //actualizamos los campos de la tabla dia
        ContentValues modificarDia = new ContentValues();
        modificarDia.put("ejer1", rutina_et_ejer1.getText().toString());
        modificarDia.put("ejer2", rutina_et_ejer2.getText().toString());
        modificarDia.put("ejer3", rutina_et_ejer3.getText().toString());

        db.update("dia", modificarDia, "nombre_rutina='"+rutina_nombre22+"' and dia_semana='lunes'", null);
        //cerramos la bd
        db.close();
    }//insertarLunes();


    public void insertarMartes(){
        //creamos la conexion a la bd
        db= conexionBD();

        //cogemos los valores
        rutina_et_ejer1= (EditText) findViewById(R.id.formulario_mar_ejer1);
        rutina_et_ejer2= (EditText) findViewById(R.id.formulario_mar_ejer2);
        rutina_et_ejer3= (EditText) findViewById(R.id.formulario_mar_ejer3);

        //actualizamos los campos de la tabla dia
        ContentValues modificarDia = new ContentValues();
        modificarDia.put("ejer1", rutina_et_ejer1.getText().toString());
        modificarDia.put("ejer2", rutina_et_ejer2.getText().toString());
        modificarDia.put("ejer3", rutina_et_ejer3.getText().toString());

        db.update("dia", modificarDia, "nombre_rutina='"+rutina_nombre22+"' and dia_semana='martes'", null);
        //cerramos la bd
        db.close();
    }//insertarMartes();

    public void insertarMiercoles(){
        //creamos la conexion a la bd
        db= conexionBD();

        //cogemos los valores
        rutina_et_ejer1= (EditText) findViewById(R.id.formulario_mie_ejer1);
        rutina_et_ejer2= (EditText) findViewById(R.id.formulario_mie_ejer2);
        rutina_et_ejer3= (EditText) findViewById(R.id.formulario_mie_ejer3);

        //actualizamos los campos de la tabla dia
        ContentValues modificarDia = new ContentValues();
        modificarDia.put("ejer1", rutina_et_ejer1.getText().toString());
        modificarDia.put("ejer2", rutina_et_ejer2.getText().toString());
        modificarDia.put("ejer3", rutina_et_ejer3.getText().toString());

        db.update("dia", modificarDia, "nombre_rutina='"+rutina_nombre22+"' and dia_semana='miercoles'", null);
        //cerramos la bd
        db.close();
    }//insertarMiercoles();

    public void insertarJueves(){
        //creamos la conexion a la bd
        db= conexionBD();

        //cogemos los valores
        rutina_et_ejer1= (EditText) findViewById(R.id.formulario_jue_ejer1);
        rutina_et_ejer2= (EditText) findViewById(R.id.formulario_jue_ejer2);
        rutina_et_ejer3= (EditText) findViewById(R.id.formulario_jue_ejer3);

        //actualizamos los campos de la tabla dia
        ContentValues modificarDia = new ContentValues();
        modificarDia.put("ejer1", rutina_et_ejer1.getText().toString());
        modificarDia.put("ejer2", rutina_et_ejer2.getText().toString());
        modificarDia.put("ejer3", rutina_et_ejer3.getText().toString());

        db.update("dia", modificarDia, "nombre_rutina='"+rutina_nombre22+"' and dia_semana='jueves'", null);
        //cerramos la bd
        db.close();
    }//insertarJueves();

    public void insertarViernes(){
        //creamos la conexion a la bd
        db= conexionBD();

        //cogemos los valores
        rutina_et_ejer1= (EditText) findViewById(R.id.formulario_vie_ejer1);
        rutina_et_ejer2= (EditText) findViewById(R.id.formulario_vie_ejer2);
        rutina_et_ejer3= (EditText) findViewById(R.id.formulario_vie_ejer3);

        //actualizamos los campos de la tabla dia
        ContentValues modificarDia = new ContentValues();
        modificarDia.put("ejer1", rutina_et_ejer1.getText().toString());
        modificarDia.put("ejer2", rutina_et_ejer2.getText().toString());
        modificarDia.put("ejer3", rutina_et_ejer3.getText().toString());

        db.update("dia", modificarDia, "nombre_rutina='"+rutina_nombre22+"' and dia_semana='viernes'", null);
        //cerramos la bd
        db.close();
    }//insertarViernes();

    public void insertarSabado(){
        //creamos la conexion a la bd
        db= conexionBD();

        //cogemos los valores
        rutina_et_ejer1= (EditText) findViewById(R.id.formulario_sab_ejer1);
        rutina_et_ejer2= (EditText) findViewById(R.id.formulario_sab_ejer2);
        rutina_et_ejer3= (EditText) findViewById(R.id.formulario_sab_ejer3);

        //actualizamos los campos de la tabla dia
        ContentValues modificarDia = new ContentValues();
        modificarDia.put("ejer1", rutina_et_ejer1.getText().toString());
        modificarDia.put("ejer2", rutina_et_ejer2.getText().toString());
        modificarDia.put("ejer3", rutina_et_ejer3.getText().toString());

        db.update("dia", modificarDia, "nombre_rutina='"+rutina_nombre22+"' and dia_semana='sabado'", null);
        //cerramos la bd
        db.close();
    }//insertarSabado();

    public void insertarDomingo(){
        //creamos la conexion a la bd
        db= conexionBD();

        //cogemos los valores
        rutina_et_ejer1= (EditText) findViewById(R.id.formulario_dom_ejer1);
        rutina_et_ejer2= (EditText) findViewById(R.id.formulario_dom_ejer2);
        rutina_et_ejer3= (EditText) findViewById(R.id.formulario_dom_ejer3);

        //actualizamos los campos de la tabla dia
        ContentValues modificarDia = new ContentValues();
        modificarDia.put("ejer1", rutina_et_ejer1.getText().toString());
        modificarDia.put("ejer2", rutina_et_ejer2.getText().toString());
        modificarDia.put("ejer3", rutina_et_ejer3.getText().toString());

        db.update("dia", modificarDia, "nombre_rutina='"+rutina_nombre22+"' and dia_semana='domingo'", null);
        //cerramos la bd
        db.close();
    }//insertarDomingo();
}//class
