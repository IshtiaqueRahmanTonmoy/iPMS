package patient.patientmanagement.pms.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import patient.patientmanagement.pms.R;
import patient.patientmanagement.pms.entity.Blood;
import patient.patientmanagement.pms.entity.Hospital;

public class BloodListAdapter extends RecyclerView.Adapter<BloodListAdapter.MyViewHolder>{
    private List<Blood> bloodList;
    Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView id,name,address,phone;

        public MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.image);
            name = (TextView) view.findViewById(R.id.name);
            address = (TextView) view.findViewById(R.id.address);
            phone = (TextView) view.findViewById(R.id.phone);

        }
    }


    public BloodListAdapter(List<Blood> bloodList) {
        this.bloodList = bloodList;
        Log.d("list", String.valueOf(bloodList.size()));
        //Toast.makeText(context, ""+specialistList.size(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public BloodListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bloodlist_row, parent, false);

        return new BloodListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BloodListAdapter.MyViewHolder holder, int position) {
        Blood blood = bloodList.get(position);
        //holder.image.setImageDrawable(R.drawable.ic_user);

        //holder.id.setText();
        holder.name.setText(blood.getDonorName());
        holder.address.setText(blood.getLocation());
        holder.phone.setText(blood.getPhoneNumber());

    }

    @Override
    public int getItemCount() {
        return bloodList.size();
    }
}
