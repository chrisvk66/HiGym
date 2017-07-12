package com.example.adrian.mapa;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	//aatributos para bd
	private CrearBD cbd= null;
	private SQLiteDatabase db= null;

	private String id_dia= null;
	private String id_progreso= null;

	EditText NUsuario, Password, Edad,ConfPass;
	ImageButton save,volver;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
	
	
		NUsuario= (EditText)findViewById(R.id.UserR);
		Password =(EditText)findViewById(R.id.PassR);
		Edad=(EditText)findViewById(R.id.EdadR);

		ConfPass=(EditText)findViewById(R.id.ConfP);
		volver=(ImageButton)findViewById(R.id.Volver);
		save=(ImageButton)findViewById(R.id.Ok);


		//listener de volver a login
		volver.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {


				startActivity(new Intent(getApplicationContext(), login_Activity.class));

				finish();


			}
		});


		//listener de guardar registro
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				finish();
			String edUsuario = NUsuario.getText().toString();
			String edPassword = Password.getText().toString();
			String edEdad = Edad.getText().toString();
			String edconfpass = ConfPass.getText().toString();
				boolean userExiste= false;

			if(edPassword.equals(edconfpass) ){
				db= conexionBD();
				ContentValues nuevoUsuario = new ContentValues();

				Cursor cursorUsuarios = db.rawQuery("SELECT nombre_user from usuario", null);
				cursorUsuarios.moveToFirst();

				do{
					if(edUsuario.equals(cursorUsuarios.getString(0))){
						userExiste= true;
						cursorUsuarios.moveToLast();
					}
				}while (cursorUsuarios.moveToNext());

				if(userExiste==false) nuevoUsuario.put("nombre_user", edUsuario);
				else{
					Toast.makeText(getApplicationContext(), "Ya existe el usuario", Toast.LENGTH_LONG).show();
					startActivity(new Intent(getApplicationContext(), login_Activity.class));
				}

				nuevoUsuario.put("contrasenya_user", edPassword);

				if(edEdad!= null) nuevoUsuario.put("edad", edEdad);
				else nuevoUsuario.put("edad", 18);

				nuevoUsuario.put("numero_rutina_seleccionada", 0);
				//Insertamos el registro en la base de datos
				db.insert("usuario", null, nuevoUsuario);

				ContentValues nuevaRutinaUserNuevo = new ContentValues();
				String rutinaNuevoUser= edUsuario+" rutina";
				nuevaRutinaUserNuevo.put("nombre_rutina", rutinaNuevoUser);
				nuevaRutinaUserNuevo.put("nombre_user", edUsuario);
				//Insertamos el registro en la base de datos
				db.insert("rutina", null, nuevaRutinaUserNuevo);

				insertarLunes(rutinaNuevoUser);
				insertarMartes(rutinaNuevoUser);
				insertarMiercoles(rutinaNuevoUser);
				insertarJueves(rutinaNuevoUser);
				insertarViernes(rutinaNuevoUser);
				insertarSabado(rutinaNuevoUser);
				insertarDomingo(rutinaNuevoUser);

				Toast.makeText(getApplicationContext(), "Registrado", Toast.LENGTH_LONG).show();
				startActivity(new Intent(getApplicationContext(), login_Activity.class));


			}
			else{
				Toast.makeText(getApplicationContext(), "La contraseña no coincide", Toast.LENGTH_LONG).show();
				Password.setText("");
				ConfPass.setText("");
			}
			}
		});
	}
	public SQLiteDatabase conexionBD(){
		cbd= new CrearBD(this, "high_gym", null, 1);
		db= cbd.getWritableDatabase();

		return db;
	}

	public void insertarLunes(String rutina_nombre){
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
		nuevoDia.put("ejer1", "ejercicio");
		nuevoDia.put("ejer2", "ejercicio");
		nuevoDia.put("ejer3", "ejercicio");
		nuevoDia.put("nombre_rutina", rutina_nombre);
		nuevoDia.put("id_progreso", id_progreso);

		//Insertamos el registro en la base de datos
		db.insert("dia", null, nuevoDia);
		//cerramos la bd
		db.close();
	}//insertarLunes();


	public void insertarMartes(String rutina_nombre){
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
		nuevoDia.put("ejer1", "ejercicio");
		nuevoDia.put("ejer2", "ejercicio");
		nuevoDia.put("ejer3", "ejercicio");
		nuevoDia.put("nombre_rutina", rutina_nombre);
		nuevoDia.put("id_progreso", id_progreso);

		//Insertamos el registro en la base de datos
		db.insert("dia", null, nuevoDia);
		//cerramos la bd
		db.close();
		id_dia= null;
		id_progreso= null;
	}//insertarMartes();

	public void insertarMiercoles(String rutina_nombre){
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
		nuevoDia.put("ejer1", "ejercicio");
		nuevoDia.put("ejer2", "ejercicio");
		nuevoDia.put("ejer3", "ejercicio");
		nuevoDia.put("nombre_rutina", rutina_nombre);
		nuevoDia.put("id_progreso", id_progreso);

		//Insertamos el registro en la base de datos
		db.insert("dia", null, nuevoDia);
		//cerramos la bd
		db.close();
		id_dia= null;
		id_progreso= null;
	}//insertarMiercoles();

	public void insertarJueves(String rutina_nombre){
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
		nuevoDia.put("ejer1", "ejercicio");
		nuevoDia.put("ejer2", "ejercicio");
		nuevoDia.put("ejer3", "ejercicio");
		nuevoDia.put("nombre_rutina", rutina_nombre);
		nuevoDia.put("id_progreso", id_progreso);

		//Insertamos el registro en la base de datos
		db.insert("dia", null, nuevoDia);
		//cerramos la bd
		db.close();
		id_dia= null;
		id_progreso= null;
	}//insertarJueves();

	public void insertarViernes(String rutina_nombre){
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
		nuevoDia.put("ejer1", "ejercicio");
		nuevoDia.put("ejer2", "ejercicio");
		nuevoDia.put("ejer3", "ejercicio");
		nuevoDia.put("nombre_rutina", rutina_nombre);
		nuevoDia.put("id_progreso", id_progreso);

		//Insertamos el registro en la base de datos
		db.insert("dia", null, nuevoDia);
		//cerramos la bd
		db.close();
		id_dia= null;
		id_progreso= null;
	}//insertarViernes();

	public void insertarSabado(String rutina_nombre){
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
		nuevoDia.put("ejer1", "ejercicio");
		nuevoDia.put("ejer2", "ejercicio");
		nuevoDia.put("ejer3", "ejercicio");
		nuevoDia.put("nombre_rutina", rutina_nombre);
		nuevoDia.put("id_progreso", id_progreso);

		//Insertamos el registro en la base de datos
		db.insert("dia", null, nuevoDia);
		//cerramos la bd
		db.close();
		id_dia= null;
		id_progreso= null;
	}//insertarSabado();

	public void insertarDomingo(String rutina_nombre){
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
		nuevoDia.put("ejer1", "ejercicio");
		nuevoDia.put("ejer2", "ejercicio");
		nuevoDia.put("ejer3", "ejercicio");
		nuevoDia.put("nombre_rutina", rutina_nombre);
		nuevoDia.put("id_progreso", id_progreso);

		//Insertamos el registro en la base de datos
		db.insert("dia", null, nuevoDia);
		//cerramos la bd
		db.close();
		id_dia= null;
		id_progreso= null;
	}//insertarDomingo();

}
