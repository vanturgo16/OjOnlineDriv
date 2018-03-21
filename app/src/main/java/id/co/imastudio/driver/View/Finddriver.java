package id.co.imastudio.driver.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.co.imastudio.driver.Helper.HeroHelper;
import id.co.imastudio.driver.Helper.SessionManager;
import id.co.imastudio.driver.InitRetrofit.InitLibrary;
import id.co.imastudio.driver.R;
import id.co.imastudio.driver.Response.ResponseCheckBooking;
import id.co.imastudio.driver.Response.ResponseDaftar;
import pl.bclogic.pulsator4droid.library.PulsatorLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Finddriver extends AppCompatActivity {

    @BindView(R.id.pulsator)
    PulsatorLayout pulsator;
    @BindView(R.id.buttoncancel)
    Button buttoncancel;
    int id ;
    SessionManager sesi ;
    Timer timer ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finddriver);
        ButterKnife.bind(this);

        timer = new Timer();

        pulsator.start();
        sesi = new SessionManager(this);

        id = getIntent().getIntExtra("idbooking",0);

        checkBooking();



        
    }

    private void  checkBooking() {
        String idbooking = String.valueOf(id);
        InitLibrary.getInstance().request_check(idbooking).enqueue(new Callback<ResponseCheckBooking>() {
            @Override
            public void onResponse(Call<ResponseCheckBooking> call, Response<ResponseCheckBooking> response) {


                if(response.isSuccessful()){
                    String result = response.body().getResult();
                    if(result.equals("true")){
                        String iddriver = response.body().getDriver();
                        Intent i = new Intent(Finddriver.this,PosisiDriver.class);
                        i.putExtra("driver",iddriver);

                        startActivity(i);

                        finish();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseCheckBooking> call, Throwable t) {

            }
        });
    }


    @OnClick(R.id.buttoncancel)
    public void onViewClicked() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Yakin mau cancel ini untuk masa depan dunia akhirat ?");
        builder.setPositiveButton("ya yakin ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //action cancel
                actionCancel();
            }
        });
        builder.setNegativeButton("bimbang", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            };
        });

        builder.show();


    }

    private void actionCancel() {

        String token = sesi.getToken();
        String device = HeroHelper.getDeviceUUID(this);
        String idbooking = String.valueOf(id);
        InitLibrary.getInstance().request_cancel(token,device,idbooking).enqueue(new Callback<ResponseDaftar>() {
            @Override
            public void onResponse(Call<ResponseDaftar> call, Response<ResponseDaftar> response) {

            }

            @Override
            public void onFailure(Call<ResponseDaftar> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        checkBooking();
                        HeroHelper.pesan(Finddriver.this,"refresh");
                    }
                });

            }
        },1000,3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }
}
