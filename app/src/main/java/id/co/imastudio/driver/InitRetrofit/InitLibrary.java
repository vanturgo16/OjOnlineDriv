package id.co.imastudio.driver.InitRetrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class InitLibrary {


    //inizialisasi sebuah library untuk accesss data dari server
    //library yang digunakan adalah retrofit dari square

    public static Retrofit setInit() {

        return new Retrofit.Builder().baseUrl("http://latihan.bengkuluprov.go.id/ojeg_server/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }public static Retrofit setInit2() {

        return new Retrofit.Builder().baseUrl("https://maps.googleapis.com/maps/api/directions/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public static ApiService getInstance(){

        return setInit().create(ApiService.class);


    }public static ApiService getInstance2(){

        return setInit2().create(ApiService.class);


    }




}


