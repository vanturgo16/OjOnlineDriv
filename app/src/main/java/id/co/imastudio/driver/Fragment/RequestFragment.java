package id.co.imastudio.driver.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import id.co.imastudio.driver.Helper.HeroHelper;
import id.co.imastudio.driver.Helper.SessionManager;
import id.co.imastudio.driver.InitRetrofit.InitLibrary;
import id.co.imastudio.driver.R;
import id.co.imastudio.driver.ResponseHistory.DataItem;
import id.co.imastudio.driver.ResponseHistory.Response;
import id.co.imastudio.driver.adapter.MyAdapter;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestFragment extends Fragment {


    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    Unbinder unbinder;
    SessionManager sesi ;
    MyAdapter adapter ;
   public static List<DataItem> data;


    public RequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_proses, container, false);
        unbinder = ButterKnife.bind(this, view);
        sesi = new SessionManager(getActivity());


        data = new ArrayList<>();

        gethistory();


        return view;
    }

    private void gethistory() {
        String iduser = sesi.getIdUser();
        String token = sesi.getToken();
        String device = HeroHelper.getDeviceUUID(getActivity());
        InitLibrary.getInstance().history(token,device,iduser)
        .enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Log.d("response : " ,response.message());

                if(response.isSuccessful()){
                    String result = response.body().getResult();
                    String pesan  = response.body().getMsg();
                    if(result.equals("true")){
                        data.clear();

                        data = response.body().getData();
                         adapter = new MyAdapter(data,1);

                        adapter.notifyDataSetChanged();
                        recyclerview.setAdapter(adapter);

                        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
                    }
                    else{
                        HeroHelper.pesan(getActivity(),pesan);
                    }

                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
