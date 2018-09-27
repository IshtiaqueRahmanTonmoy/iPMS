package patient.patientmanagement.pms.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import patient.patientmanagement.pms.LoginActivity;
import patient.patientmanagement.pms.R;
import patient.patientmanagement.pms.entity.AvailableTIme;
import patient.patientmanagement.pms.entity.CardAdapter;

public class ConfirmAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<AvailableTIme> mData;
    private float mBaseElevation;
    Context context;
    long nodecount;
    String format,symptom,id,hospitalId,date,time,serial,confirm;
    String description,speciality,education,district,hospital,expertise,ids,namevalue,fromonlydistrict,fromdistrictandhos,doctorlist;
    long appoinmentid = 0;
    int District,DistrictAndHos,DistrictHosSpeciality;
    public ConfirmAdapter(Context context, String description, String speciality, String education, String district, String hospital, String expertise, String ids, String namevalue, String fromonlydistrict, String fromdistrictandhos, String doctorlist, int District, int DistrictAndHos, int DistrictHosSpeciality) {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();

        this.description = description;
        this.speciality = speciality;
        this.education = education;
        this.district = district;
        this.hospital = hospital;
        this.expertise = expertise;
        this.ids = ids;
        this.namevalue = namevalue;
        this.fromonlydistrict = fromonlydistrict;
        this.fromdistrictandhos = fromdistrictandhos;
        this.doctorlist = doctorlist;

        this.District = District;
        this.DistrictAndHos = DistrictAndHos;
        this.DistrictHosSpeciality = DistrictHosSpeciality;

        //Toast.makeText(context, "Districtappbooking"+District, Toast.LENGTH_SHORT).show();
        //Toast.makeText(context, "Hospitalappbooking"+DistrictAndHos, Toast.LENGTH_SHORT).show();
        //Toast.makeText(context, "Specialityappbooking"+DistrictHosSpeciality, Toast.LENGTH_SHORT).show();

    }

    public void addCardItem(AvailableTIme item) {
        mViews.add(null);
        mData.add(item);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.available_time, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(AvailableTIme item, View view) {


        Date dNow = new Date();
        SimpleDateFormat ft1 = new SimpleDateFormat("dd-MMM-yyyy");
        //System.out.println("Date 2: " + ft1.format(dNow));

        TextView dateTextView = (TextView) view.findViewById(R.id.date);
        TextView timeTextView = (TextView) view.findViewById(R.id.time);
        TextView slnoTextView = (TextView) view.findViewById(R.id.slno);
        Button confirmBtn = (Button) view.findViewById(R.id.button);
        //dateTextView.setText(item.getFormat());
        dateTextView.setText(ft1.format(dNow));
        timeTextView.setText(item.getTime());
        slnoTextView.setText("Approximate Serial No-" + item.getSerial());

        format = item.getFormat();
        symptom = item.getSymptoms();
        id = item.getId();
        hospitalId = item.getHospitalId();
        date = item.getDate();
        time = item.getTime();
        serial = item.getSerial();
        confirm = item.getConfirm();
        appoinmentid = item.getAid();
        nodecount = item.getNodecount();

        Log.d("val", String.valueOf(appoinmentid));

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),LoginActivity.class);
                intent.putExtra("format",format);
                intent.putExtra("symptom",symptom);
                intent.putExtra("id",id);
                intent.putExtra("hospitalId",hospitalId);
                intent.putExtra("date",date);
                intent.putExtra("time",time);
                intent.putExtra("serial",serial);
                intent.putExtra("confirm",confirm);
                intent.putExtra("fromonlydistrict", fromonlydistrict);
                intent.putExtra("fromonlydistrictandhosptial", fromdistrictandhos);
                intent.putExtra("doctorlist", "3");
                intent.putExtra("appoinmentid",String.valueOf(appoinmentid));

                intent.putExtra("description",description);
                intent.putExtra("speciality",speciality);
                intent.putExtra("education",education);
                intent.putExtra("district",district);
                intent.putExtra("hospital",hospital);
                intent.putExtra("expertise",expertise);
                intent.putExtra("idvalues",ids);
                intent.putExtra("name",namevalue);
                intent.putExtra("fromonlydistrict","1");

                intent.putExtra("District",District);
                intent.putExtra("DistrictAndHos",DistrictAndHos);
                intent.putExtra("DistrictHosSpeciality",DistrictHosSpeciality);
                intent.putExtra("nodecount",nodecount);

                v.getContext().startActivity(intent);

            }
        });
    }

}