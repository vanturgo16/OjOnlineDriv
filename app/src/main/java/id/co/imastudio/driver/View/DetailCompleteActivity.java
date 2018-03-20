package id.co.imastudio.driver.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.co.imastudio.driver.Fragment.AktifFragment;
import id.co.imastudio.driver.Fragment.CompleteFragment;
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

public class DetailCompleteActivity extends AppCompatActivity {

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

        data = CompleteFragment.data.get(posisi);

        requestFrom.setText("From : " +data.getBookingFrom());

        requestTakeBooking.setVisibility(View.GONE);




        //https://pastebin.com/ztgA4Sv3
    }

    @OnClick(R.id.requestTakeBooking)
    public void onViewClicked() {

    }
}