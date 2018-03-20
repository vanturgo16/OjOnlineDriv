package id.co.imastudio.driver.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.co.imastudio.driver.R;

public class PosisiDriver extends AppCompatActivity implements OnMapReadyCallback {

    @BindView(R.id.lokasiawal)
    TextView lokasiawal;
    @BindView(R.id.lokasitujuan)
    TextView lokasitujuan;
    @BindView(R.id.txtnamadriver)
    TextView txtnamadriver;
    @BindView(R.id.txthpdriver)
    TextView txthpdriver;
    String id ;
    GoogleMap mMap ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posisi_driver);
        ButterKnife.bind(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.





    }


    //method untuk setting maps
    //sample: setting zoom,type maps ,etc
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap ;







    }



}
