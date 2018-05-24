package patient.patientmanagement.pms.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import patient.patientmanagement.pms.R;
import java.util.List;

import patient.patientmanagement.pms.entity.speciality;

public class SpecialistAdapter extends RecyclerView.Adapter<SpecialistAdapter.MyViewHolder> {

    private List<speciality> specialistList;
    Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);

        }
    }


    public SpecialistAdapter(List<speciality> specialistList) {
        this.specialistList = specialistList;
        Log.d("list", String.valueOf(specialistList.size()));
        //Toast.makeText(context, ""+specialistList.size(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.specialist_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        speciality specialist = specialistList.get(position);
        holder.title.setText(specialist.getSpecialityName());

    }

    @Override
    public int getItemCount() {
        return specialistList.size();
    }
}
