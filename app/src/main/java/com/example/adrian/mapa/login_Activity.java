package com.example.adrian.mapa;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class login_Activity extends Activity {
	//aatributos para bd
	private CrearBD cbd= null;
	private SQLiteDatabase db= null;

	boolean userExiste= false;
	boolean contrasenyaCorrecta= false;
	EditText user, pass;
	Button login, not_reg;
	//DatabaseHandler db;
	//Cursor cursor;

		@Override
	protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.login);

			user = (EditText) findViewById(R.id.User);
			pass = (EditText) findViewById(R.id.Pass);
			login = (Button) findViewById(R.id.login);
			not_reg = (Button) findViewById(R.id.register);


			not_reg.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {


					startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                    finish();
				}
			});


			login.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					db= conexionBD();

					Cursor cursorUsuarios = db.rawQuery("SELECT nombre_user from usuario", null);
					cursorUsuarios.moveToFirst();

					do{
						if(user.getText().toString().equals(cursorUsuarios.getString(0))){
							userExiste= true;
							cursorUsuarios.moveToLast();
						}
					}while (cursorUsuarios.moveToNext());

					if(userExiste== true){
						Cursor cursorPassword = db.rawQuery("SELECT contrasenya_user from usuario", null);
						cursorPassword.moveToFirst();

						do{
							if(pass.getText().toString().equals(cursorPassword.getString(0))){
								contrasenyaCorrecta= true;
								cursorPassword.moveToLast();
							}
						}while (cursorPassword.moveToNext());

						if(contrasenyaCorrecta== true){
							Intent lanzarMain= new Intent(getApplicationContext(), MainActivity.class);
							lanzarMain.putExtra("usuario", user.getText().toString());
							startActivity(lanzarMain);
							finish();
						}else Toast.makeText(getApplicationContext(), "Contraseña incorrecta", Toast.LENGTH_LONG).show();

					}else Toast.makeText(getApplicationContext(), "No existe el usuario", Toast.LENGTH_LONG).show();
				}
			});

		}

	public SQLiteDatabase conexionBD(){
		cbd= new CrearBD(this, "high_gym", null, 1);
		db= cbd.getWritableDatabase();

		return db;
	}
}
		

