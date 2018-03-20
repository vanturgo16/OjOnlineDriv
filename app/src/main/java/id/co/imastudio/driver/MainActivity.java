package id.co.imastudio.driver;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.co.imastudio.driver.Helper.DirectionMapsV2;
import id.co.imastudio.driver.Helper.GPSTracker;
import id.co.imastudio.driver.Helper.HeroHelper;
import id.co.imastudio.driver.Helper.SessionManager;
import id.co.imastudio.driver.InitRetrofit.InitLibrary;
import id.co.imastudio.driver.Response.ResponseInsert;
import id.co.imastudio.driver.ResponseRoute.ResponseDirection;
import id.co.imastudio.driver.View.Finddriver;
import id.co.imastudio.driver.View.BookingActivity;
import id.co.imastudio.driver.View.login;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    //@BindView(R.id.map)
    //android.widget.fragment map;
    @BindView(R.id.lokasiawal)
    TextView lokasiawal;
    @BindView(R.id.lokasitujuan)
    TextView lokasitujuan;
    @BindView(R.id.requestorder)
    Button requestorder;
    @BindView(R.id.txtharga)
    TextView txtharga;
    private GoogleMap mMap;
    String jarakkm;
    Double harga;
    String namalokasiawal ;


    //deklrasi variable koordinat global
    Double latawal, lonawal, latakhir, lonakkhir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //get class gps tracker
        GPSTracker gps = new GPSTracker(this);

        //check gps aktif apa enggak
        if(gps.canGetLocation()) {
            //get koordinat kalau gps aktif
            latawal = gps.getLatitude();
            lonawal = gps.getLongitude();

            //masukin method marker kmren


            //ubah koordinat jadi nama lokasi
            Geocoder geo = new Geocoder(this, Locale.getDefault());
            try {
                List<Address> addresses = geo.getFromLocation(latawal,lonawal,1);

                //get name
                String name = addresses.get(0).getAddressLine(1);
                String kec = addresses.get(0).getCountryName();

                lokasiawal.setText(name +"," + kec);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        //kalau seandai tidak aktif
        else {
            gps.showSettingGps();
        }



        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-6.1925297, 106.8001397);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        // menampilkan control zoom in zoom out
        mMap.getUiSettings().setZoomControlsEnabled(true);
        // menampilkan compas
        mMap.getUiSettings().setCompassEnabled(true);
        // mengatur jenis peta
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //padding maps
        mMap.setPadding(40, 150, 50, 120);

        //auto zoom
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16));

        //button current location
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }


            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.history:

                startActivity(new Intent(MainActivity.this, BookingActivity.class));


                break;

            case R.id.Profil:
                //untuk tombol profile nanti bakal muncul informasi dari user yang login
                //yang bakal muncul nanti nama user,email dan hp nya
                //

                final SessionManager sesi = new SessionManager(MainActivity.this);

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Profil User");
                alert.setMessage("Nama :" + sesi.getNama() + "\n" +
                        "Email :" + sesi.getEmail() + "\n" +
                        "Handphone :" + sesi.getPhone()
                );
                alert.setPositiveButton("Sign Out", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //event saat button sign out di klik


                        sesi.logout();
                        Intent intent = new Intent(MainActivity.this, login.class);
                        startActivity(intent);
                        finish();

                    }
                });
                //untuk manampilkan dialog
                alert.show();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.lokasiawal, R.id.lokasitujuan, R.id.requestorder})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //
            case R.id.lokasiawal:
                // aksi disini dijalanka
                auto(1);

                break;
            case R.id.lokasitujuan:

                auto(2);

                // disini dijalankan
                break;

            case R.id.requestorder:

                actionInsert();


                break;
        }
    }

    private void actionInsert() {

        String iduser = new SessionManager(this).getIdUser();
        String token = new SessionManager(this).getToken();
        String awal = lokasiawal.getText().toString();
        String tujuan = lokasitujuan.getText().toString();
        String ltawal = latawal.toString();
        String lngawal = lonawal.toString();
        String ltakhir = latakhir.toString();
        String lngakhir = lonakkhir.toString();
        String jarak = "sialakan bkin global di atas";
        String tarif = "silakan bkin golbal d i atas hehe semngat untuk masa depan lebih cerah";
        String device = HeroHelper.getDeviceUUID(this);


        InitLibrary.getInstance().ini_insert(iduser,ltawal,lngawal,awal,ltakhir,lngakhir,tujuan,
                tarif,jarak,token,device).enqueue(new Callback<ResponseInsert>() {
            @Override
            public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {

                if(response.isSuccessful()){


                    String result = response.body().getResult();
                    String pesan = response.body().getMsg();
                    if(result.equals("true")){
                        int idbooking = response.body().getIdBooking();
                        Intent intent = new Intent(MainActivity.this, Finddriver.class);
                        intent.putExtra("idbooking",idbooking);
                        startActivity(intent);
                    }
                    else{
                        HeroHelper.pesan(MainActivity.this,pesan);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseInsert> call, Throwable t) {

            }
        });
    }

    private void auto(int i) {
        try {

            AutocompleteFilter.Builder filter = new AutocompleteFilter.Builder();
            filter.setCountry("ina");


            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).setFilter(filter.build())

                            .build(this);
            startActivityForResult(intent, i);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }

    }


    // A place has been received; use requestCode to track the request.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                latawal = place.getLatLng().latitude;
                lonawal = place.getLatLng().longitude;

                //make marker berdasarkan koordinat
                LatLng posisi1 = new LatLng(latawal, lonawal);

                mMap.clear();

                if(lokasitujuan.getText().toString().length() != 0) {

                    mMap.clear();

                    mMap.addMarker(new MarkerOptions().position(new LatLng(latakhir,lonakkhir)).title(namalokasiawal));

                }


                mMap.addMarker(new MarkerOptions().position(posisi1).title(place.getAddress().toString()));

                //auto zoom
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posisi1, 15));
                //

                namalokasiawal = place.getAddress().toString();
                lokasiawal.setText(namalokasiawal);


                //get koordinat

                //  Log.i(TAG, "Place: " + place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                //   Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
        else {

                Place place = PlaceAutocomplete.getPlace(this, data);
                latakhir = place.getLatLng().latitude;
                lonakkhir = place.getLatLng().longitude;

                //make marker berdasarkan koordinat
                LatLng posisi1 = new LatLng(latawal, lonawal);

                if(lokasiawal.getText().toString().length() != 0) {

                    mMap.clear();

                    mMap.addMarker(new MarkerOptions().position(new LatLng(latawal,lonawal)).title(namalokasiawal));

                }




                mMap.addMarker(new MarkerOptions().position(posisi1).title(place.getAddress().toString()));

                //auto zoom
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posisi1, 15));
                //
                lokasiawal.setText(place.getAddress().toString());



                actionRoute();


                //get koordinat

                //  Log.i(TAG, "Place: " + place.getName());
            }

        }

    private void actionRoute() {
        //get koordinat
        String origin = latawal.toString() + "," + lonawal.toString();
        String desti = latakhir.toString() + "," + lonakkhir.toString();

        LatLngBounds.Builder bound  = LatLngBounds.builder();
        bound.include(new LatLng(latawal,lonawal));
        bound.include(new LatLng(latakhir,lonakkhir));
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bound.build(),16));



        InitLibrary.getInstance2().request_route(origin,desti)
                .enqueue(new Callback<ResponseDirection>() {
                    @Override
                    public void onResponse(Call<ResponseDirection> call, Response<ResponseDirection> response) {

                        String points  =response.body().getRoutes().get(0).getOverviewPolyline().getPoints();
                       DirectionMapsV2 direction = new DirectionMapsV2(MainActivity.this);
                               direction.gambarRoute(mMap,points);
                    }

                    @Override
                    public void onFailure(Call<ResponseDirection> call, Throwable t) {

                    }
                });
    }

//
//    @Override
//    public boolean onCreatePanelMenu(int featureId, Menu menu) {
//        getMenuInflater().inflate(R.menu.profil_menu,menu);
//
//
//        return super.onCreatePanelMenu(featureId, menu);
//    }
    }
