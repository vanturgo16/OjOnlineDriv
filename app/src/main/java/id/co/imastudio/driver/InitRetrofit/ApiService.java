package id.co.imastudio.driver.InitRetrofit;

import id.co.imastudio.driver.Response.ResponseCheckBooking;
import id.co.imastudio.driver.Response.ResponseDaftar;
import id.co.imastudio.driver.Response.ResponseInsert;
import id.co.imastudio.driver.Response.ResponseLogin;
import id.co.imastudio.driver.ResponseHistory.Response;
import id.co.imastudio.driver.ResponseRoute.ResponseDirection;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService{

//TODO 3 : tambahin ini
    @FormUrlEncoded
    //TODO 2 : get function di php
    //ini funtion di server
    @POST("daftar/1")
    //TODO 1 : get response
    Call<ResponseDaftar> request_daftar(
            //TODO 4 : tambahin paramter
            @Field("nama") String name ,
            @Field("password") String password,
            @Field("phone") String phone,
            @Field("email") String email
    );


    //TODO 2 : get function di php
    //ini funtion di server
    @POST("insert_posisi")
    //TODO 1 : get response
    Call<ResponseDaftar> request_posisi(
            //TODO 4 : tambahin paramter
            @Field("f_idUser") String idUse ,
            @Field("f_lat") String lat,
            @Field("f_lng") String lang

    );
            //add parameter

    @FormUrlEncoded
    @POST("cekbooking")
    Call<ResponseCheckBooking> request_check(@Field("idbooking") String idbooking);



            @FormUrlEncoded
            @POST("get_request_booking")
            Call<Response> history(
                                   @Field("f_token") String token,
                                   @Field("f_device") String device,
           @Field("f_idUser") String iddriver);

            @FormUrlEncoded
            @POST("get_handle_booking")
            Call<Response> handle(
                                   @Field("f_token") String token,
                                   @Field("f_device") String device,
                                   @Field("f_idUser") String iduser);

            @FormUrlEncoded
            @POST("get_complete_booking")
            Call<Response> complete(
                                   @Field("f_token") String token,
                                   @Field("f_device") String device,
                                   @Field("f_idUser") String iduser);

            @FormUrlEncoded
            @POST("registerGcm")
            Call<ResponseDaftar> token(
                                   @Field("f_gcm") String token,
                                   @Field("f_idUser") String iduser);

            @FormUrlEncoded
            @POST("take_booking")
            Call<ResponseDaftar> takebooking(
                                   @Field("f_token") String token,
                                   @Field("f_device") String device,
                                   @Field("id") String id,
                                   @Field("f_idUser") String iduser);



            @FormUrlEncoded
            @POST("complete_booking")
            Call<ResponseDaftar> completebooking(
                                   @Field("f_token") String token,
                                   @Field("f_device") String device,
                                   @Field("id") String id,
                                   @Field("f_idUser") String iduser);



    @FormUrlEncoded
    @POST("cancel_booking")
    Call<ResponseDaftar> request_cancel(@Field("f_token") String token,
                                        @Field("f_device") String device,
                                        @Field("id") String idbookng);



    @FormUrlEncoded
    @POST("insert_booking")
    Call<ResponseInsert> ini_insert(
            @Field("f_idUser") String iduser ,
            @Field("f_latAwal") String latAwal,
            @Field("f_lngAwal") String lattujuan ,
            @Field("f_awal") String awal ,
            @Field("f_latAkhir") String latakhir,
            @Field("f_lngAkhir") String lngakhir,
            @Field("f_akhir") String akhir ,
            @Field("f_alamat")String tarif,
            @Field("f_jarak") String jarak,

            @Field("f_token") String token ,
            @Field("f_device") String device
    );

    @FormUrlEncoded
    @POST("login_driver")
    Call<ResponseLogin> ini_login(  @Field("f_email") String phone,
                                        @Field("f_password") String email,
                                    @Field("device") String device);


    @GET("json")
    Call<ResponseDirection> request_route(@Query("origin") String origin,
                                          @Query("destination") String desitnation);
}
