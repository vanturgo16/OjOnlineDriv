package id.co.imastudio.driver.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import id.co.imastudio.driver.R;
import id.co.imastudio.driver.ResponseHistory.DataItem;
import id.co.imastudio.driver.View.DetailActiveActivity;
import id.co.imastudio.driver.View.DetailCompleteActivity;
import id.co.imastudio.driver.View.DetailRequestActivity;
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<DataItem>  mDataset;

    int status ;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tanggal,awal,tujuan,harga;
        public ViewHolder(View v) {
            super(v);
            tanggal = v.findViewById(R.id.texttgl);
            awal = v.findViewById(R.id.txtawal);
            tujuan = v.findViewById(R.id.txtakhir);
            harga = v.findViewById(R.id.txtharga);
            //mTextView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<DataItem> myDataset, int status) {
        mDataset = myDataset;
        this.status = status;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_recyclerview, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.tanggal.setText(mDataset.get(position).getBookingTanggal());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(status == 1){
                    Intent i = new Intent(holder.itemView.getContext(), DetailRequestActivity.class);
                    i.putExtra("index",position);
                    holder.itemView.getContext().startActivity(i);
                }
                else if(status == 2){
                    Intent i = new Intent(holder.itemView.getContext(), DetailActiveActivity.class);
                    i.putExtra("index",position);
                    holder.itemView.getContext().startActivity(i);
                }
                else{
                    Intent i = new Intent(holder.itemView.getContext(), DetailCompleteActivity.class);
                    i.putExtra("index",position);
                    holder.itemView.getContext().startActivity(i);
                }


            }
        });


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

