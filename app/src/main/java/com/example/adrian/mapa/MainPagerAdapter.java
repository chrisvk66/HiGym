package com.example.adrian.mapa;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class MainPagerAdapter extends PagerAdapter {
    private RelativeLayout p4, p5, p6;
    private LinearLayout p1,p3;
    private FrameLayout p2;
    private final String[] title = {"", "", "", "", "", ""};
    private Context context;
    private Activity act;
    private String usuarioActivo= null;

    public MainPagerAdapter(Context context, Activity act, String user) {
        super();
        this.context = context;
        this.act = act;
        this.usuarioActivo= user;
    }//MainPagerAdapter();

    //coloca los nombres en sus respectivas posiciones en la toolbar
    public CharSequence getPageTitle(int position) {
        return title[position];
    }//getPageTitle();

    public Object instantiateItem(ViewGroup collection, int posicion) {
        View paginaVisible = null;

        switch (posicion) {
            case 0:
                //instanciamos el layout
                if (p1 == null)
                    p1 = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.pagina1, collection, false);

                //damos funciones a la pagina cargada
                classPagina1 classPage1 = new classPagina1();
                classPage1.todoPage1(p1, act, usuarioActivo);

                paginaVisible = p1;
                break;
            case 1:

                if (p2 == null)
                    p2 = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.pagina2, collection, false);
                paginaVisible = p2;
                classPagina2 classPage2 = new classPagina2();
                classPage2.todoPage2(p2, act);
                break;


            case 2:
                if (p3 == null)
                    p3 = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.pagina3, collection, false);
                paginaVisible = p3;
                classPagina3 classPage3 = new classPagina3();
                classPage3.todoPage3(p3);

                break;
            case 3:
                if ((p4 == null) || (p4 != null))
                    p4 = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.pagina4, collection, false);
                paginaVisible = p4;
                classPagina4 classPage4 = new classPagina4();
                classPage4.todoPage4(p4);
                break;
            case 4:
                if (p5 == null)
                    p5 = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.pagina5, collection, false);
                paginaVisible = p5;
                classPagina5 classPage5 = new classPagina5();
                classPage5.todoPage5(p5);
                break;

            case 5:
                if (p6 == null)
                    p6 = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.pagina6, collection, false);
                paginaVisible = p6;
                classPagina6 classPage6 = new classPagina6();
                classPage6.todoPage6(p6, act);
                break;
        }
        collection.addView(paginaVisible, 0);
        return paginaVisible;
    }//instantiateItem();

    @Override
    public int getCount() {
        return 6;
    }//getCount();

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }//isViewFromObject();

    @Override
    public void destroyItem(ViewGroup collection, int position, Object object) {
        collection.removeView((View) object);
    }//destroyItem();

    public int getItemPosition(Object object){
        return POSITION_NONE;
    }


    //clase creada que contiene todos los eventos y funciones de la pagina1
    static class classPagina1 {    //aatributos para bd
        private CrearBD cbd= null;
        private SQLiteDatabase db= null;

        public ListView lv;
        public ArrayList<Ejercicio> datos;
        public ArrayAdapter<Ejercicio> adapter;
        String dia_semana= null;
        String id_progreso= null;
        String nombre_rutina= null;

        public void todoPage1(LinearLayout p1, final Activity act, final String user) {
            p1InstanciarListView(p1, act, user);
            p1BotonRutinas(p1, act, user);
            b3añadir(p1, act, user);
            b4añadir(p1, act, user);
            b5añadir(p1, act, user);
            b6añadir(p1, act);
            barraStart(p1, act);
            diaSemana(p1);
        }

        public SQLiteDatabase conexionBD(final Activity act){
            cbd= new CrearBD(act, "high_gym", null, 1);
            db= cbd.getWritableDatabase();

            return db;
        }

        private String obtenerDiaSemana(){
            String[] dias={"domingo","lunes","martes", "miercoles","jueves","viernes","sabado"};
            Date hoy=new Date();
            int numeroDia=0;
            Calendar cal= Calendar.getInstance();
            cal.setTime(hoy);
            numeroDia=cal.get(Calendar.DAY_OF_WEEK);
            return dias[numeroDia - 1];
        }

        private String obtenerDiaSemanaMayus(){
            String[] dias={"DOMINGO","LUNES","MARTES", "MIERCOLES","JUEVES","VIERNES","SABADO"};
            Date hoy=new Date();
            int numeroDia=0;
            Calendar cal= Calendar.getInstance();
            cal.setTime(hoy);
            numeroDia=cal.get(Calendar.DAY_OF_WEEK);
            return dias[numeroDia - 1];
        }

        public void diaSemana(LinearLayout p1){
            TextView tv= (TextView) p1.findViewById(R.id.tv1_p1);
            String dia= obtenerDiaSemanaMayus();

            tv.setText(dia.toString());
        }

        public void p1InstanciarListView(LinearLayout p1, final Activity act, final String user) {
            dia_semana= obtenerDiaSemana();
            db= conexionBD(act);
            Cursor cursorRutinas = db.rawQuery("SELECT nombre_rutina from rutina where nombre_user='"+user+"'", null);
            Cursor cursorNumRutinaSelecionada= db.rawQuery("SELECT numero_rutina_seleccionada from usuario where nombre_user='"+user+"'", null);

            //movemos los cursores al principio.
            cursorRutinas.moveToFirst();
            cursorNumRutinaSelecionada.moveToFirst();

            for(int i=0; i<cursorNumRutinaSelecionada.getInt(0); i++){
                cursorRutinas.moveToNext();
            }

            nombre_rutina= cursorRutinas.getString(0);
            Cursor cursorDia = db.rawQuery("SELECT ejer1, ejer2, ejer3, id_progreso from dia where nombre_rutina='"+nombre_rutina+"'", null);
            cursorDia.moveToFirst();

            datos = new ArrayList<Ejercicio>();
            datos.add(new Ejercicio(cursorDia.getString(0)));
            datos.add(new Ejercicio(cursorDia.getString(1)));
            datos.add(new Ejercicio(cursorDia.getString(2)));
            id_progreso= cursorDia.getString(3);

            lv = (ListView) p1.findViewById(R.id.listView_page1);
            adapter = new MiAdapter(act, datos);
            lv.setAdapter(adapter);
            //aqui añadiriamos ejercicios extras
            adapter.notifyDataSetChanged();
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });


        }//instanciarlistview

        public void b6añadir(final LinearLayout p1, final Activity act) {
            final ImageButton b6 = (ImageButton) p1.findViewById(R.id.imageButton3);
            final ImageView iv = (ImageView) p1.findViewById((R.id.imageView));
            final ImageView iv2 = (ImageView) p1.findViewById(R.id.imageView2);
            final TextView tv = (TextView) p1.findViewById(R.id.textView2);

            db= conexionBD(act);
            Cursor cursorProgreso = db.rawQuery("SELECT boton_agua from progreso where id_progreso='"+id_progreso+"'", null);
            cursorProgreso.moveToFirst();

            tv.setText(Integer.toString(cursorProgreso.getInt(0)));

            if(cursorProgreso.getInt(0)!=0) tv.setVisibility(View.VISIBLE);

            b6.setOnClickListener(new View.OnClickListener() {

                int n = 0;

                public void onClick(View v) {
                    iv2.setVisibility(View.VISIBLE);
                    int valor = Integer.parseInt(String.valueOf(tv.getText()));
                    valor = valor + 1;
                    String valor2 = String.valueOf(valor);

                    ContentValues modificarProgreso = new ContentValues();
                    modificarProgreso.put("boton_agua", valor2);

                    db.update("progreso", modificarProgreso, "id_progreso='"+id_progreso+"'", null);

                    tv.setText(valor2);
                    tv.setVisibility(View.VISIBLE);
                    ProgressBar simpleProgressBar = (ProgressBar) p1.findViewById(R.id.progressBar6);
                    if (valor < 9) {
                        // Perform action on click
                        añadir5(p1, act);
                        if (simpleProgressBar.getProgress() > 96) {
                            simpleProgressBar.setVisibility(View.INVISIBLE);
                            iv.setVisibility(View.VISIBLE);

                        }
                    }
                }
            });
        }

        public void b5añadir(final LinearLayout p1, final Activity act, String user) {
            final Button b5 = (Button) p1.findViewById(R.id.button5);
            final ImageView iv = (ImageView) p1.findViewById((R.id.imageView));

            db= conexionBD(act);
            Cursor cursorProgreso = db.rawQuery("SELECT boton3 from progreso where id_progreso='"+id_progreso+"'", null);
            cursorProgreso.moveToFirst();

            if(cursorProgreso.getString(0).equals("si")) b5.setBackgroundColor(Color.GREEN);
            else b5.setBackgroundColor(Color.RED);

            b5.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ProgressBar simpleProgressBar = (ProgressBar) p1.findViewById(R.id.progressBar6);
                    db= conexionBD(act);

                    Cursor cursorProgreso = db.rawQuery("SELECT boton3 from progreso where id_progreso='"+id_progreso+"'", null);
                    cursorProgreso.moveToFirst();

                    if(cursorProgreso.getString(0).equals("no")){
                        ContentValues modificarProgreso = new ContentValues();
                        modificarProgreso.put("boton3", "si");

                        db.update("progreso", modificarProgreso, "id_progreso='"+id_progreso+"'", null);

                        b5.setBackgroundColor(Color.GREEN);
                        añadir20(p1, act);
                        if (simpleProgressBar.getProgress() > 96) {
                            simpleProgressBar.setVisibility(View.INVISIBLE);
                            iv.setVisibility(View.VISIBLE);

                        }
                    }else{
                        ContentValues modificarProgreso = new ContentValues();
                        modificarProgreso.put("boton3", "no");

                        db.update("progreso", modificarProgreso, "id_progreso='"+id_progreso+"'", null);

                        b5.setBackgroundColor(Color.RED);
                        quitar20(p1, act);
                    }
                }
            });
        }

        public void b4añadir(final LinearLayout p1, final Activity act, String user) {
            final Button b4 = (Button) p1.findViewById(R.id.button4);
            final ImageView iv = (ImageView) p1.findViewById((R.id.imageView));

            db= conexionBD(act);
            Cursor cursorProgreso = db.rawQuery("SELECT boton2 from progreso where id_progreso='"+id_progreso+"'", null);
            cursorProgreso.moveToFirst();

            if(cursorProgreso.getString(0).equals("si")) b4.setBackgroundColor(Color.GREEN);
            else b4.setBackgroundColor(Color.RED);

            b4.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ProgressBar simpleProgressBar = (ProgressBar) p1.findViewById(R.id.progressBar6);
                    db= conexionBD(act);

                    Cursor cursorProgreso = db.rawQuery("SELECT boton2 from progreso where id_progreso='"+id_progreso+"'", null);
                    cursorProgreso.moveToFirst();

                    if(cursorProgreso.getString(0).equals("no")){
                        ContentValues modificarProgreso = new ContentValues();
                        modificarProgreso.put("boton2", "si");

                        db.update("progreso", modificarProgreso, "id_progreso='"+id_progreso+"'", null);

                        b4.setBackgroundColor(Color.GREEN);
                        añadir20(p1, act);
                        if (simpleProgressBar.getProgress() > 96) {
                            simpleProgressBar.setVisibility(View.INVISIBLE);
                            iv.setVisibility(View.VISIBLE);

                        }
                    }else{
                        ContentValues modificarProgreso = new ContentValues();
                        modificarProgreso.put("boton2", "no");

                        db.update("progreso", modificarProgreso, "id_progreso='"+id_progreso+"'", null);

                        b4.setBackgroundColor(Color.RED);
                        quitar20(p1, act);
                    }
                }
            });
        }

        public void b3añadir(final LinearLayout p1, final Activity act, String user) {
            final Button b3 = (Button) p1.findViewById(R.id.button3);
            final ImageView iv = (ImageView) p1.findViewById((R.id.imageView));

            db= conexionBD(act);
            Cursor cursorProgreso = db.rawQuery("SELECT boton1 from progreso where id_progreso='"+id_progreso+"'", null);
            cursorProgreso.moveToFirst();

            if(cursorProgreso.getString(0).equals("si")) b3.setBackgroundColor(Color.GREEN);
            else b3.setBackgroundColor(Color.RED);

            b3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ProgressBar simpleProgressBar = (ProgressBar) p1.findViewById(R.id.progressBar6);
                    db= conexionBD(act);

                    Cursor cursorProgreso = db.rawQuery("SELECT boton1 from progreso where id_progreso='"+id_progreso+"'", null);
                    cursorProgreso.moveToFirst();

                    if(cursorProgreso.getString(0).equals("no")){
                        ContentValues modificarProgreso = new ContentValues();
                        modificarProgreso.put("boton1", "si");

                        db.update("progreso", modificarProgreso, "id_progreso='"+id_progreso+"'", null);

                        b3.setBackgroundColor(Color.GREEN);
                        añadir20(p1, act);
                        if (simpleProgressBar.getProgress() > 96) {
                            simpleProgressBar.setVisibility(View.INVISIBLE);
                            iv.setVisibility(View.VISIBLE);

                        }
                    }else{
                        ContentValues modificarProgreso = new ContentValues();
                        modificarProgreso.put("boton1", "no");

                        db.update("progreso", modificarProgreso, "id_progreso='"+id_progreso+"'", null);

                        b3.setBackgroundColor(Color.RED);
                        quitar20(p1, act);
                    }
                }
            });
        }

        void barraStart(LinearLayout p1, final Activity act) {
            db= conexionBD(act);

            Cursor cursorProgreso = db.rawQuery("SELECT boton_barra_progreso from progreso where id_progreso='"+id_progreso+"'", null);
            cursorProgreso.moveToFirst();

            ProgressBar simpleProgressBar = (ProgressBar) p1.findViewById(R.id.progressBar6);

            int progressValue = cursorProgreso.getInt(0);
            simpleProgressBar.setProgress(progressValue);
        }

        void añadir20(LinearLayout p1, final Activity act) {
            db= conexionBD(act);

            Cursor cursorProgreso = db.rawQuery("SELECT boton_barra_progreso from progreso where id_progreso='"+id_progreso+"'", null);
            cursorProgreso.moveToFirst();

            ProgressBar simpleProgressBar = (ProgressBar) p1.findViewById(R.id.progressBar6);

            int progressValue = cursorProgreso.getInt(0);

            ContentValues modificarProgreso = new ContentValues();
            modificarProgreso.put("boton_barra_progreso", progressValue+20);

            db.update("progreso", modificarProgreso, "id_progreso='"+id_progreso+"'", null);

            simpleProgressBar.setProgress(progressValue + 20);
        }

        void quitar20(LinearLayout p1, final Activity act) {
            db= conexionBD(act);

            Cursor cursorProgreso = db.rawQuery("SELECT boton_barra_progreso from progreso where id_progreso='"+id_progreso+"'", null);
            cursorProgreso.moveToFirst();

            ProgressBar simpleProgressBar = (ProgressBar) p1.findViewById(R.id.progressBar6);

            int progressValue = cursorProgreso.getInt(0);

            ContentValues modificarProgreso = new ContentValues();
            modificarProgreso.put("boton_barra_progreso", progressValue-20);

            db.update("progreso", modificarProgreso, "id_progreso='"+id_progreso+"'", null);

            simpleProgressBar.setProgress(progressValue - 20);
        }

        void añadir5(LinearLayout p1, final Activity act) {
            db= conexionBD(act);

            Cursor cursorProgreso = db.rawQuery("SELECT boton_barra_progreso from progreso where id_progreso='"+id_progreso+"'", null);
            cursorProgreso.moveToFirst();

            ProgressBar simpleProgressBar = (ProgressBar) p1.findViewById(R.id.progressBar6);

            int progressValue = cursorProgreso.getInt(0);

            ContentValues modificarProgreso = new ContentValues();
            modificarProgreso.put("boton_barra_progreso", progressValue+5);

            db.update("progreso", modificarProgreso, "id_progreso='"+id_progreso+"'", null);

            simpleProgressBar.setProgress(progressValue + 5);
        }

        public static void p1BotonRutinas(LinearLayout p1, final Activity act, final String user){
            ImageButton b1= (ImageButton) p1.findViewById(R.id.page1_button_rutinas);
            b1.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    // Perform action on click
                    Intent rutina= new Intent(act, Rutinas.class);
                    rutina.putExtra("usuario", user);
                    act.startActivityForResult(rutina, 1);
                }
            });
        }
    }//classPagina1


    static class classPagina2 {
        //atributos...


        //metodos...
        public void todoPage2(FrameLayout p1, Activity act) {

            mapa(p1, act);

        }

        public static void mapa(FrameLayout p1, final Activity act) {
            final ImageButton b9 = (ImageButton) p1.findViewById(R.id.imageButton2);
            b9.setOnClickListener(new View.OnClickListener() {


                public void onClick(View v) {

                    Intent map = new Intent(act, MapsActivity.class);
                    act.startActivityForResult(map, 1);


                }

            });
        }






    }//classPagina3


    class classPagina3 extends AppCompatActivity {




        public void todoPage3(LinearLayout p1) {
                musica(p1);
        }



         public void musica(LinearLayout p1){
             final ImageButton b9 = (ImageButton) p1.findViewById(R.id.imageButton7);
             b9.setOnClickListener(new View.OnClickListener() {

                 public void onClick(View v) {


                     Intent map = new Intent(act, Music_Activity.class);
                     act.startActivityForResult(map, 1);
                 }

             });
         }



    }//classPagina3



    static class classPagina4 extends AppCompatActivity  {
        //atributos...
         long timeCountInMilliSeconds = 1 * 60000;



         enum TimerStatus {
            STARTED,
            STOPPED
        }

        TimerStatus timerStatus = TimerStatus.STOPPED;
        ProgressBar progressBarCircle;
        EditText editTextMinute;
        TextView textViewTime;
        ImageView imageViewReset;
        ImageView imageViewStartStop;
        CountDownTimer countDownTimer;

        private void reset() {
            stopCountDownTimer();
            startCountDownTimer();
        }


        /**
         * method to start and stop count down timer
         */
        private void startStop() {
            if (timerStatus == TimerStatus.STOPPED) {

                // call to initialize the timer values
                setTimerValues();
                // call to initialize the progress bar values
                setProgressBarValues();
                // showing the reset icon
                imageViewReset.setVisibility(View.VISIBLE);
                // changing play icon to stop icon
                imageViewStartStop.setImageResource(R.drawable.icon_stop);
                // making edit text not editable
                editTextMinute.setEnabled(false);
                // changing the timer status to started
                timerStatus = TimerStatus.STARTED;
                // call to start the count down timer
                startCountDownTimer();

            } else {

                // hiding the reset icon
                imageViewReset.setVisibility(View.GONE);
                // changing stop icon to start icon
                imageViewStartStop.setImageResource(R.drawable.icon_start);
                // making edit text editable
                editTextMinute.setEnabled(true);
                // changing the timer status to stopped
                timerStatus = TimerStatus.STOPPED;
                stopCountDownTimer();

            }

        }

        /**
         * method to initialize the values for count down timer
         */
        private void setTimerValues() {
            int time = 0;
            if (!editTextMinute.getText().toString().isEmpty()) {
                // fetching value from edit text and type cast to integer
                time = Integer.parseInt(editTextMinute.getText().toString().trim());
            } else {
                // toast message to fill edit text
                //Toast.makeText(getApplicationContext(), getString(R.string.message_minutes), Toast.LENGTH_LONG).show();
            }
            // assigning values after converting to milliseconds
            timeCountInMilliSeconds = time * 60 * 1000;
        }



        /**
         * method to start count down timer
         */
        private void startCountDownTimer() {

            countDownTimer = new CountDownTimer(timeCountInMilliSeconds, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                    textViewTime.setText(hmsTimeFormatter(millisUntilFinished));

                    progressBarCircle.setProgress((int) (millisUntilFinished / 1000));

                }

                @Override
                public void onFinish() {

                    textViewTime.setText(hmsTimeFormatter(timeCountInMilliSeconds));
                    // call to initialize the progress bar values
                    setProgressBarValues();
                    // hiding the reset icon
                    imageViewReset.setVisibility(View.GONE);
                    // changing stop icon to start icon
                    imageViewStartStop.setImageResource(R.drawable.icon_start);
                    // making edit text editable
                    editTextMinute.setEnabled(true);
                    // changing the timer status to stopped
                    timerStatus = TimerStatus.STOPPED;


                }

            }.start();
            countDownTimer.start();
        }

        /**
         * method to stop count down timer
         */
        private void stopCountDownTimer() {
            countDownTimer.cancel();
        }

        /**
         * method to set circular progress bar values
         */
        private void setProgressBarValues() {

            progressBarCircle.setMax((int) timeCountInMilliSeconds / 1000);
            progressBarCircle.setProgress((int) timeCountInMilliSeconds / 1000);
        }


        /**
         * method to convert millisecond to time format
         *
         * @param milliSeconds
         * @return HH:mm:ss time formatted string
         */
        private String hmsTimeFormatter(long milliSeconds) {

            String hms = String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(milliSeconds),
                    TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                    TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));

            return hms;
        }

        //metodos...
        public void todoPage4(RelativeLayout p1) {
            //instanciar todos los metodos dentro de este para cargarlo arriba en una única linea

            progressBarCircle = (ProgressBar) p1.findViewById(R.id.progressBarCircle);
            editTextMinute = (EditText) p1.findViewById(R.id.editTextMinute);
            textViewTime = (TextView) p1.findViewById(R.id.textViewTime);
            imageViewReset(p1);
            imageViewStartStop(p1);

        }
        public void imageViewReset(RelativeLayout p1) {
            imageViewReset = (ImageView) p1.findViewById(R.id.imageViewReset);
            imageViewReset.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                    reset();


                }
            });
        }

        public void imageViewStartStop(RelativeLayout p1) {
            imageViewStartStop = (ImageView) p1.findViewById(R.id.imageViewStartStop);
            imageViewStartStop.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                    startStop();

                }
            });
        }

            /**
             * method to reset count down timer
             */





    }//classPagina4

    class classPagina5 extends AppCompatActivity{

        private WebView mWebView;
        //metodos...
        public void todoPage5(RelativeLayout p1){
            //instanciar todos los metodos dentro de este para cargarlo arriba en una única linea
            mWebView = (WebView)p1.findViewById(R.id.web);
            WebSettings webSettings = mWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            mWebView.loadUrl("http://gymvirtual.com/consejos-y-tips/");

        }

        public void onBackPressed() {
            if(mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                this.onBackPressed();
            }
        }


    }//classPagina5

    class classPagina6{
        //atributos...



        //metodos...
        public void todoPage6(RelativeLayout  p1,final Activity act) {
            //instanciar todos los metodos dentro de este para cargarlo arriba en una única linea

            final ImageButton b5 = (ImageButton) p1.findViewById(R.id.imageButton5);
            final ImageButton b6 = (ImageButton) p1.findViewById(R.id.salir);

            b6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int p = android.os.Process.myPid();
                    android.os.Process.killProcess(p);
                }
            });

            b5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(act, login_Activity.class);
                    act.startActivityForResult(i, 2);
                    act.finish();
                }
            });

        }



    }//classPagina6
}//class
