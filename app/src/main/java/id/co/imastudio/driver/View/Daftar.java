package id.co.imastudio.driver.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.co.imastudio.driver.Helper.HeroHelper;
import id.co.imastudio.driver.InitRetrofit.InitLibrary;
import id.co.imastudio.driver.R;
import id.co.imastudio.driver.Response.ResponseDaftar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Daftar extends AppCompatActivity {

    @BindView(R.id.daftarusername)
    EditText daftarusername;
    @BindView(R.id.daftaremail)
    EditText daftaremail;
    @BindView(R.id.daftarhp)
    EditText daftarhp;
    @BindView(R.id.daftarpassword)
    EditText daftarpassword;
    @BindView(R.id.daftarconfirmasipass)
    EditText daftarconfirmasipass;
    @BindView(R.id.btnsignup)
    Button btnsignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.btnsignup)
    public void onViewClicked() {
        //TODO 1 : check inputan user tidak boleh kosong
        if(HeroHelper.isEmpty(daftarusername) || HeroHelper.isEmpty(daftaremail) || HeroHelper.isEmpty(daftarhp )
                || HeroHelper.isEmpty(daftarpassword) || HeroHelper.isEmpty(daftarconfirmasipass)){


            HeroHelper.pesan(this,"harus isi semuanya");
        }
        //TODO 2 : insert inputan user
        else {

            //TODO 3 : get inputan user
            String name = daftarusername.getText().toString();
            String email = daftaremail.getText().toString();
            String phone = daftarpassword.getText().toString();
            String password = daftarpassword.getText().toString();


            //TODO 4 : get konfigurasi retrofit
            InitLibrary.getInstance().request_daftar(name,password,phone,email)
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
                                    startActivity(new Intent(Daftar.this,login.class));
                                }
                                else {
                                    HeroHelper.pesan(Daftar.this,pesan);

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
}
