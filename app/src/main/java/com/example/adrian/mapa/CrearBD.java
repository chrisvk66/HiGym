package com.example.adrian.mapa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by beca_ux2 on 12/06/2017.
 */

public class CrearBD extends SQLiteOpenHelper{

        //sentencias sql
    String tablaUsuario= "CREATE TABLE usuario(" +
                        "nombre_user VARCHAR(25) NOT NULL," +
                        "contrasenya_user VARCHAR(25) NOT NULL," +
                        "edad INT(3) NOT NULL," +
                        "numero_rutina_seleccionada INT(3)," +
                        "PRIMARY KEY (nombre_user)"+
                    ")";

    String insertTablaUsuario= "INSERT INTO usuario (nombre_user, contrasenya_user, edad, numero_rutina_seleccionada) " +
                        "VALUES ('prueba1', '1234', 20, 0);";
    String insertTablaUsuario2= "INSERT INTO usuario (nombre_user, contrasenya_user, edad, numero_rutina_seleccionada) " +
                        "VALUES ('prueba2', '1234', 25, 0);";

    String tablaRutina= "CREATE TABLE rutina(" +
                        "nombre_rutina VARCHAR(25) NOT NULL," +
                        "nombre_user VARCHAR(25) NOT NULL," +
                        "PRIMARY KEY (nombre_rutina)," +
                        "FOREIGN KEY (nombre_user) REFERENCES usuario(nombre_user) ON UPDATE CASCADE ON DELETE CASCADE" +
                    ")";

    String insertTablaRutina= "INSERT INTO rutina (nombre_rutina, nombre_user) VALUES ('rutina1', 'prueba1');";
    String insertTablaRutina2= "INSERT INTO rutina (nombre_rutina, nombre_user) VALUES ('rutina2', 'prueba1');";
    String insertTablaRutina3= "INSERT INTO rutina (nombre_rutina, nombre_user) VALUES ('rutina3', 'prueba1');";
    String insertTablaRutina4= "INSERT INTO rutina (nombre_rutina, nombre_user) VALUES ('rutina4', 'prueba2');";
    String insertTablaRutina5= "INSERT INTO rutina (nombre_rutina, nombre_user) VALUES ('rutina5', 'prueba2');";
    String insertTablaRutina6= "INSERT INTO rutina (nombre_rutina, nombre_user) VALUES ('rutina6', 'prueba1');";
    String insertTablaRutina7= "INSERT INTO rutina (nombre_rutina, nombre_user) VALUES ('rutina7', 'prueba1');";
    String insertTablaRutina8= "INSERT INTO rutina (nombre_rutina, nombre_user) VALUES ('rutina8', 'prueba1');";
    String insertTablaRutina9= "INSERT INTO rutina (nombre_rutina, nombre_user) VALUES ('rutina9', 'prueba1');";
    String insertTablaRutina10= "INSERT INTO rutina (nombre_rutina, nombre_user) VALUES ('rutina10', 'prueba1');";


    String tablaProgreso= "CREATE TABLE progreso(" +
                        "id_progreso VARCHAR(5) NOT NULL," +
                        "boton1 VARCHAR(10) NOT NULL," +
                        "boton2 VARCHAR(10) NOT NULL," +
                        "boton3 VARCHAR(10) NOT NULL," +
                        "boton_agua int(3) NOT NULL," +
                        "boton_barra_progreso int(3) NOT NULL," +
                        "PRIMARY KEY (id_progreso)" +
                    ")";

    String insertTablaProgreso= "INSERT INTO progreso (id_progreso, boton1, boton2, boton3, boton_agua, boton_barra_progreso) " +
                            "VALUES ('001', 'no', 'no', 'no', 0, 0);";


    String tablaDia= "CREATE TABLE dia(" +
                        "id_dia VARCHAR(5) NOT NULL," +
                        "dia_semana VARCHAR(25) NOT NULL," +
                        "ejer1 VARCHAR(25) NOT NULL," +
                        "ejer2 VARCHAR(25) NOT NULL," +
                        "ejer3 VARCHAR(25) NOT NULL," +
                        "nombre_rutina VARCHAR(5) NOT NULL," +
                        "id_progreso VARCHAR(5) NOT NULL," +
                        "PRIMARY KEY (id_dia)," +
                        "FOREIGN KEY (nombre_rutina) REFERENCES rutina(nombre_rutina) ON UPDATE CASCADE ON DELETE CASCADE," +
                        "FOREIGN KEY (id_progreso) REFERENCES progreso(id_progreso) ON UPDATE CASCADE ON DELETE CASCADE" +
                    ")";

    String insertTablaDia_rutina1_l= "INSERT INTO dia (id_dia, dia_semana, ejer1, ejer2, ejer3, nombre_rutina, id_progreso) " +
                            "VALUES ('001', 'lunes', 'ej1l', 'ej2l', 'ej3l', 'rutina1', '001');";

    String insertTablaDia_rutina1_m=  "INSERT INTO dia (id_dia, dia_semana, ejer1, ejer2, ejer3, nombre_rutina, id_progreso) " +
                            "VALUES ('002', 'martes', 'ej1m', 'ej2m', 'ej3m', 'rutina1', '001');";

    String insertTablaDia_rutina1_x=  "INSERT INTO dia (id_dia, dia_semana, ejer1, ejer2, ejer3, nombre_rutina, id_progreso) " +
                            "VALUES ('003', 'miercoles', 'ej1x', 'ej2x', 'ej3x', 'rutina1', '001');";

    String insertTablaDia_rutina1_j=  "INSERT INTO dia (id_dia, dia_semana, ejer1, ejer2, ejer3, nombre_rutina, id_progreso) " +
                            "VALUES ('004', 'jueves', 'ej1j', 'ej2j', 'ej3j', 'rutina1', '001');";

    String insertTablaDia_rutina1_v= "INSERT INTO dia (id_dia, dia_semana, ejer1, ejer2, ejer3, nombre_rutina, id_progreso) " +
                            "VALUES ('005', 'viernes', 'ej1v', 'ej2v', 'ej3v', 'rutina1', '001');";

    String insertTablaDia_rutina1_s= "INSERT INTO dia (id_dia, dia_semana, ejer1, ejer2, ejer3, nombre_rutina, id_progreso) " +
                            "VALUES ('006', 'sabado', 'ej1s', 'ej2s', 'ej3s', 'rutina1', '001');";

    String insertTablaDia_rutina1_d= "INSERT INTO dia (id_dia, dia_semana, ejer1, ejer2, ejer3, nombre_rutina, id_progreso) " +
                            "VALUES ('007', 'domingo', 'ej1d', 'ej2d', 'ej3d', 'rutina1', '001');";

    String insertTablaDia_rutina2_l= "INSERT INTO dia (id_dia, dia_semana, ejer1, ejer2, ejer3, nombre_rutina, id_progreso) "+
            "VALUES ('008', 'lunes', 'ej1lr2', 'ej2lr2', 'ej3lr2', 'rutina2', '001');";
    String insertTablaDia_rutina2_m= "INSERT INTO dia (id_dia, dia_semana, ejer1, ejer2, ejer3, nombre_rutina, id_progreso) "+
            "VALUES ('009', 'martes', 'ej1mr2', 'ej2mr2', 'ej3mr2', 'rutina2', '001');";
    String insertTablaDia_rutina2_x= "INSERT INTO dia (id_dia, dia_semana, ejer1, ejer2, ejer3, nombre_rutina, id_progreso) "+
            "VALUES ('010', 'miercoles', 'ej1xr2', 'ej2xr2', 'ej3xr2', 'rutina2', '001');";
    String insertTablaDia_rutina2_j= "INSERT INTO dia (id_dia, dia_semana, ejer1, ejer2, ejer3, nombre_rutina, id_progreso) "+
            "VALUES ('011', 'jueves', 'ej1jr2', 'ej2jr2', 'ej3jr2', 'rutina2', '001');";
    String insertTablaDia_rutina2_v= "INSERT INTO dia (id_dia, dia_semana, ejer1, ejer2, ejer3, nombre_rutina, id_progreso) "+
            "VALUES ('012', 'viernes', 'ej1vr2', 'ej2vr2', 'ej3vr2', 'rutina2', '001');";
    String insertTablaDia_rutina2_s= "INSERT INTO dia (id_dia, dia_semana, ejer1, ejer2, ejer3, nombre_rutina, id_progreso) "+
            "VALUES ('013', 'sabado', 'ej1sr2', 'ej2sr2', 'ej3sr2', 'rutina2', '001');";
    String insertTablaDia_rutina2_d= "INSERT INTO dia (id_dia, dia_semana, ejer1, ejer2, ejer3, nombre_rutina, id_progreso) "+
            "VALUES ('014', 'domingo', 'ej1dr2', 'ej2dr2', 'ej3dr2', 'rutina2', '001');";

    String insertTablaDia_rutina3_l= "INSERT INTO dia (id_dia, dia_semana, ejer1, ejer2, ejer3, nombre_rutina, id_progreso) " +
            "VALUES ('015', 'lunes', 'ej1l', 'ej2l', 'ej3l', 'rutina3', '001');";
    String insertTablaDia_rutina3_m=  "INSERT INTO dia (id_dia, dia_semana, ejer1, ejer2, ejer3, nombre_rutina, id_progreso) " +
            "VALUES ('016', 'martes', 'ej1m', 'ej2m', 'ej3m', 'rutina3', '001');";
    String insertTablaDia_rutina3_x=  "INSERT INTO dia (id_dia, dia_semana, ejer1, ejer2, ejer3, nombre_rutina, id_progreso) " +
            "VALUES ('017', 'miercoles', 'ej1x', 'ej2x', 'ej3x', 'rutina3', '001');";
    String insertTablaDia_rutina3_j=  "INSERT INTO dia (id_dia, dia_semana, ejer1, ejer2, ejer3, nombre_rutina, id_progreso) " +
            "VALUES ('018', 'jueves', 'ej1j', 'ej2j', 'ej3j', 'rutina3', '001');";
    String insertTablaDia_rutina3_v= "INSERT INTO dia (id_dia, dia_semana, ejer1, ejer2, ejer3, nombre_rutina, id_progreso) " +
            "VALUES ('019', 'viernes', 'ej1v', 'ej2v', 'ej3v', 'rutina3', '001');";
    String insertTablaDia_rutina3_s= "INSERT INTO dia (id_dia, dia_semana, ejer1, ejer2, ejer3, nombre_rutina, id_progreso) " +
            "VALUES ('020', 'sabado', 'ej1s', 'ej2s', 'ej3s', 'rutina3', '001');";
    String insertTablaDia_rutina3_d= "INSERT INTO dia (id_dia, dia_semana, ejer1, ejer2, ejer3, nombre_rutina, id_progreso) " +
            "VALUES ('021', 'domingo', 'ej1d', 'ej2d', 'ej3d', 'rutina3', '001');";

    String insertTablaDia_rutina4_l= "INSERT INTO dia (id_dia, dia_semana, ejer1, ejer2, ejer3, nombre_rutina, id_progreso) "+
            "VALUES ('022', 'lunes', 'ej1lr2', 'ej2lr2', 'ej3lr2', 'rutina4', '001');";
    String insertTablaDia_rutina4_m= "INSERT INTO dia (id_dia, dia_semana, ejer1, ejer2, ejer3, nombre_rutina, id_progreso) "+
            "VALUES ('023', 'martes', 'ej1mr2', 'ej2mr2', 'ej3mr2', 'rutina4', '001');";
    String insertTablaDia_rutina4_x= "INSERT INTO dia (id_dia, dia_semana, ejer1, ejer2, ejer3, nombre_rutina, id_progreso) "+
            "VALUES ('024', 'miercoles', 'ej1xr2', 'ej2xr2', 'ej3xr2', 'rutina4', '001');";
    String insertTablaDia_rutina4_j= "INSERT INTO dia (id_dia, dia_semana, ejer1, ejer2, ejer3, nombre_rutina, id_progreso) "+
            "VALUES ('025', 'jueves', 'ej1jr2', 'ej2jr2', 'ej3jr2', 'rutina4', '001');";
    String insertTablaDia_rutina4_v= "INSERT INTO dia (id_dia, dia_semana, ejer1, ejer2, ejer3, nombre_rutina, id_progreso) "+
            "VALUES ('026', 'viernes', 'ej1vr2', 'ej2vr2', 'ej3vr2', 'rutina4', '001');";
    String insertTablaDia_rutina4_s= "INSERT INTO dia (id_dia, dia_semana, ejer1, ejer2, ejer3, nombre_rutina, id_progreso) "+
            "VALUES ('027', 'sabado', 'ej1sr2', 'ej2sr2', 'ej3sr2', 'rutina4', '001');";
    String insertTablaDia_rutina4_d= "INSERT INTO dia (id_dia, dia_semana, ejer1, ejer2, ejer3, nombre_rutina, id_progreso) "+
            "VALUES ('028', 'domingo', 'ej1dr2', 'ej2dr2', 'ej3dr2', 'rutina4', '001');";

    String insertTablaDia_rutina5_l= "INSERT INTO dia (id_dia, dia_semana, ejer1, ejer2, ejer3, nombre_rutina, id_progreso) " +
            "VALUES ('029', 'lunes', 'ej1l', 'ej2l', 'ej3l', 'rutina5', '001');";
    String insertTablaDia_rutina5_m=  "INSERT INTO dia (id_dia, dia_semana, ejer1, ejer2, ejer3, nombre_rutina, id_progreso) " +
            "VALUES ('030', 'martes', 'ej1m', 'ej2m', 'ej3m', 'rutina5', '001');";
    String insertTablaDia_rutina5_x=  "INSERT INTO dia (id_dia, dia_semana, ejer1, ejer2, ejer3, nombre_rutina, id_progreso) " +
            "VALUES ('031', 'miercoles', 'ej1x', 'ej2x', 'ej3x', 'rutina5', '001');";
    String insertTablaDia_rutina5_j=  "INSERT INTO dia (id_dia, dia_semana, ejer1, ejer2, ejer3, nombre_rutina, id_progreso) " +
            "VALUES ('032', 'jueves', 'ej1j', 'ej2j', 'ej3j', 'rutina5', '001');";
    String insertTablaDia_rutina5_v= "INSERT INTO dia (id_dia, dia_semana, ejer1, ejer2, ejer3, nombre_rutina, id_progreso) " +
            "VALUES ('033', 'viernes', 'ej1v', 'ej2v', 'ej3v', 'rutina5', '001');";
    String insertTablaDia_rutina5_s= "INSERT INTO dia (id_dia, dia_semana, ejer1, ejer2, ejer3, nombre_rutina, id_progreso) " +
            "VALUES ('034', 'sabado', 'ej1s', 'ej2s', 'ej3s', 'rutina5', '001');";
    String insertTablaDia_rutina5_d= "INSERT INTO dia (id_dia, dia_semana, ejer1, ejer2, ejer3, nombre_rutina, id_progreso) " +
            "VALUES ('035', 'domingo', 'ej1d', 'ej2d', 'ej3d', 'rutina5', '001');";



    public CrearBD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            //cuando no exista la bd entrara aqui (solo se ejecutara la primera vez que se abra al instalarse)

        db.execSQL(tablaUsuario);
        db.execSQL(insertTablaUsuario);
        db.execSQL(insertTablaUsuario2);

        db.execSQL(tablaRutina);
        db.execSQL(insertTablaRutina);
        db.execSQL(insertTablaRutina2);
        db.execSQL(insertTablaRutina3);
        db.execSQL(insertTablaRutina4);
        db.execSQL(insertTablaRutina5);


        db.execSQL(tablaProgreso);
        db.execSQL(insertTablaProgreso);

        db.execSQL(tablaDia);
        db.execSQL(insertTablaDia_rutina1_l);
        db.execSQL(insertTablaDia_rutina1_m);
        db.execSQL(insertTablaDia_rutina1_x);
        db.execSQL(insertTablaDia_rutina1_j);
        db.execSQL(insertTablaDia_rutina1_v);
        db.execSQL(insertTablaDia_rutina1_s);
        db.execSQL(insertTablaDia_rutina1_d);
        db.execSQL(insertTablaDia_rutina2_l);
        db.execSQL(insertTablaDia_rutina2_m);
        db.execSQL(insertTablaDia_rutina2_x);
        db.execSQL(insertTablaDia_rutina2_j);
        db.execSQL(insertTablaDia_rutina2_v);
        db.execSQL(insertTablaDia_rutina2_s);
        db.execSQL(insertTablaDia_rutina2_d);
        db.execSQL(insertTablaDia_rutina3_l);
        db.execSQL(insertTablaDia_rutina3_m);
        db.execSQL(insertTablaDia_rutina3_x);
        db.execSQL(insertTablaDia_rutina3_j);
        db.execSQL(insertTablaDia_rutina3_v);
        db.execSQL(insertTablaDia_rutina3_s);
        db.execSQL(insertTablaDia_rutina3_d);
        db.execSQL(insertTablaDia_rutina4_l);
        db.execSQL(insertTablaDia_rutina4_m);
        db.execSQL(insertTablaDia_rutina4_x);
        db.execSQL(insertTablaDia_rutina4_j);
        db.execSQL(insertTablaDia_rutina4_v);
        db.execSQL(insertTablaDia_rutina4_s);
        db.execSQL(insertTablaDia_rutina4_d);
        db.execSQL(insertTablaDia_rutina5_l);
        db.execSQL(insertTablaDia_rutina5_m);
        db.execSQL(insertTablaDia_rutina5_x);
        db.execSQL(insertTablaDia_rutina5_j);
        db.execSQL(insertTablaDia_rutina5_v);
        db.execSQL(insertTablaDia_rutina5_s);
        db.execSQL(insertTablaDia_rutina5_d);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //si por ejemplo hay un cambio de version, los cammbios que se produzcan
        //en la bd se realizan aqui.

    }
}
