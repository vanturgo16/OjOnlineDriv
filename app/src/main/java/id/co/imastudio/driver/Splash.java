package id.co.imastudio.driver;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import id.co.imastudio.driver.Helper.SessionManager;
import id.co.imastudio.driver.View.BookingActivity;
import id.co.imastudio.driver.View.login;

public class Splash extends AppCompatActivity {

    SessionManager sesi ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sesi = new SessionManager(Splash.this);

        //sebelum pindah ke halaman ke login atau ke mainactivity kita delay

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sesi.isLogin()){
                    //arahkan mainactivity
                    Intent i = new Intent(Splash.this,BookingActivity.class);
                    startActivity(i);
                    finish();



                }
                else{
                    //arahkan ke login class

                    Intent i = new Intent(Splash.this,login.class);
                    startActivity(i);
                    finish();
                }
            }
            //waktu delaynya 4000 ms = 4 s
        },4000);


    }
}
