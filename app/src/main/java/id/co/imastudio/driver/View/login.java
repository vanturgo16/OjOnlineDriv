package id.co.imastudio.driver.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.co.imastudio.driver.Helper.HeroHelper;
import id.co.imastudio.driver.Helper.SessionManager;
import id.co.imastudio.driver.InitRetrofit.InitLibrary;
import id.co.imastudio.driver.R;
import id.co.imastudio.driver.Response.Data;
import id.co.imastudio.driver.Response.ResponseLogin;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {

    @BindView(R.id.loginemail)
    EditText loginemail;
    @BindView(R.id.loginpassword)
    EditText loginpassword;
    @BindView(R.id.signin)
    Button signin;
    @BindView(R.id.textlink)
    TextView textlink;
    //deklrasi variable untuk class sesi
    SessionManager sesi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.signin, R.id.textlink})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.signin:

                actionLogin();
                break;
            case R.id.textlink:
                startActivity(new Intent(this,Daftar.class));
                break;
        }
    }

    private void actionLogin() {
      sesi = new SessionManager(this);

        //requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},12);

            String uid = HeroHelper.getDeviceUUID(login.this);

            InitLibrary.getInstance().ini_login(loginemail.getText().toString(),loginpassword.getText().toString(),uid).enqueue(new Callback<ResponseLogin>() {
                @Override
                public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {

                    String result = response.body().getResult();
                    if(result.equals("true")){

                        String token = response.body().getToken();
                        Data data = response.body().getData();
                        startActivity(new Intent(login.this,BookingActivity.class));
                        finish();


                        sesi.setIduser(data.getIdUser());
                        sesi.setNama(data.getUserNama());
                        sesi.setPhone(data.getUserHp());
                        sesi.cerateLoginSession(token);



                    }
                }

                @Override
                public void onFailure(Call<ResponseLogin> call, Throwable t) {

                }
            });





    }


//    @OnClick(R.id.textlink)
//    public void onViewClicked() {
//        startActivity(new Intent(this,Daftar.class));
//    }
}
