package id.co.imastudio.driver.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.co.imastudio.driver.Fragment.RequestFragment;
import id.co.imastudio.driver.Helper.HeroHelper;
import id.co.imastudio.driver.Helper.SessionManager;
import id.co.imastudio.driver.InitRetrofit.InitLibrary;
import id.co.imastudio.driver.R;
import id.co.imastudio.driver.Response.ResponseDaftar;
import id.co.imastudio.driver.ResponseHistory.DataItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailRequestActivity extends AppCompatActivity {

    int posisi;
    DataItem data;
    @BindView(R.id.requestFrom)
    TextView requestFrom;
    @BindView(R.id.requestTo)
    TextView requestTo;
    @BindView(R.id.requestWaktu)
    TextView requestWaktu;
    @BindView(R.id.requestTarif)
    TextView requestTarif;
    @BindView(R.id.requestNama)
    TextView requestNama;
    @BindView(R.id.requestEmail)
    TextView requestEmail;
    @BindView(R.id.requestID)
    TextView requestID;
    @BindView(R.id.requestTakeBooking)
    Button requestTakeBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        posisi = getIntent().getIntExtra("index", 0);

        data = RequestFragment.data.get(posisi);

        requestFrom.setText("From : " +data.getBookingFrom());



        //https://pastebin.com/ztgA4Sv3
    }

    @OnClick(R.id.requestTakeBooking)
    public void onViewClicked() {

        String idbooking = data.getIdBooking();
        String token = new SessionManager(this).getToken();
        String iduser = new SessionManager(this).getIdUser();
        String device = HeroHelper.getDeviceUUID(this);

        InitLibrary.getInstance().takebooking(token,device,iduser,idbooking)
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
                                startActivity(new Intent(DetailRequestActivity.this,BookingActivity.class));
                            }
                            else {
                                HeroHelper.pesan(DetailRequestActivity.this,pesan);

                            }

                        }
                    }


                    @Override
                    public void onFailure(Call<ResponseDaftar> call, Throwable t) {
                        Log.d("error daftar : " , t.getMessage());

                    }
                });
    }
    }