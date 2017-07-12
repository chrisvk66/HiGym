package com.example.adrian.mapa;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity{
    private ViewPager vp1;
    private TabLayout tl1;
    private String usuarioActivo= null;
    MainPagerAdapter mpa= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuarioActivo= getIntent().getExtras().getString("usuario");
        Log.d("usuarios activo", usuarioActivo);

        mpa= new MainPagerAdapter(getApplicationContext(), this, usuarioActivo);

        vp1= (ViewPager) findViewById(R.id.pager);
        vp1.setAdapter(mpa);

        tl1= (TabLayout) findViewById(R.id.tabs);
        tl1.setupWithViewPager(vp1);

    }//onCreate();

    @Override
    public void onResume() {
        super.onResume();
        mpa.notifyDataSetChanged();

        tl1.getTabAt(0).setIcon(R.drawable.casa);
        tl1.getTabAt(1).setIcon(R.drawable.mapa);
        tl1.getTabAt(2).setIcon(R.drawable.pesa);
        tl1.getTabAt(3).setIcon(R.drawable.cronometro);
        tl1.getTabAt(4).setIcon(R.drawable.internet);
        tl1.getTabAt(5).setIcon(R.drawable.logo);
    }

}//class





