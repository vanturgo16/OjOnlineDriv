package id.co.imastudio.driver.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.co.imastudio.driver.Fragment.AktifFragment;
import id.co.imastudio.driver.Fragment.CompleteFragment;
import id.co.imastudio.driver.Fragment.RequestFragment;
import id.co.imastudio.driver.Helper.HeroHelper;
import id.co.imastudio.driver.Helper.PositionHelper;
import id.co.imastudio.driver.Helper.SessionManager;
import id.co.imastudio.driver.InitRetrofit.InitLibrary;
import id.co.imastudio.driver.R;
import id.co.imastudio.driver.Response.ResponseDaftar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingActivity extends AppCompatActivity {

    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.pager)
    ViewPager pager;


    SessionManager sesi ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);


        Intent i  = new Intent(this, PositionHelper.class);
        startService(i);


        sesi = new SessionManager(this);

        if(sesi.getGcm().isEmpty()){


            //request token
            String token = FirebaseInstanceId.getInstance().getToken();
            sesi.setGcm(token);


            //save to server
            insertToken(token);
        }





        //membuat nama tabnya
        tablayout.addTab(tablayout.newTab().setText("Request"));
        tablayout.addTab(tablayout.newTab().setText("Aktif"));
        tablayout.addTab(tablayout.newTab().setText("Complete"));

        //dari masing tab layout itu ada classnya
        PagerAdapter adapter = new CustomPager(getSupportFragmentManager());

        pager.setAdapter(adapter);

        //kalau pager di geser tab nya juga ikut ganti
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));



        //tabnya diklik fragment juga ganti
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }

    private void insertToken(String token) {

        //TODO 4 : get konfigurasi retrofit
        InitLibrary.getInstance().token(token,sesi.getIdUser())
                .enqueue(new Callback<ResponseDaftar>() {
                    @Override
                    public void onResponse(Call<ResponseDaftar> call, Response<ResponseDaftar> response) {

                        //TODO 5 : get response server
                        Log.d("reesponse daftar" , response.message());

                        String pesan = response.body().getMsg();

                        //TODO 6 : check response server
                        if(response.isSuccessful()){
                            //TODO 7 : get result rsponse
                            String result = response.body().getResult();
                            if(result.equals("true")){

                                //TODO 8 : pindah halaman ke login
                               // startActivity(new Intent(BookingActivity.this,login.class));
                            }
                            else {
                                HeroHelper.pesan(BookingActivity.this,pesan);

                            }

                        }
                    }


                    @Override
                    public void onFailure(Call<ResponseDaftar> call, Throwable t) {
                        Log.d("error daftar : " , t.getMessage());

                    }
                });
    }

    private class CustomPager extends FragmentStatePagerAdapter {
        public CustomPager(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0){

                return new RequestFragment();

            }
            else if(position == 1){

                return new AktifFragment();

            }
            else{

                return new CompleteFragment();
            }


        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
