package patient.patientmanagement.pms.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import patient.patientmanagement.pms.AppoinmentBooking;
import patient.patientmanagement.pms.LoginActivity;
import patient.patientmanagement.pms.R;
import patient.patientmanagement.pms.SignupActivity;
import patient.patientmanagement.pms.SplashActivity;
import patient.patientmanagement.pms.VerifyNumber;
import patient.patientmanagement.pms.entity.CardAdapter;
import patient.patientmanagement.pms.entity.Chamber;

public class ChamberAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<Chamber> mData;
    private TextView nameTxt,locationTxt,booknowTxt;
    private float mBaseElevation;
    Context context;
    private String id;
    private String name,description,speciality,district,hospital,education,expertise;
    public ChamberAdapter(Context context) {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();

    }

    public void addCardItem(Chamber item) {
        mViews.add(null);
        mData.add(item);

        id = mData.get(0).getIdvalue();
        district = mData.get(0).getDistrict();
        hospital = mData.get(0).getHospital();
        expertise = mData.get(0).getExpertise();

        //name = mData.get(0).getName();
        description = mData.get(0).getDescription();
        speciality = mData.get(0).getSpeciality();
        education = mData.get(0).getEducation();
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
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.cardview_item, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.card_view1);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               TextView nametxt = (TextView) v.findViewById(R.id.name);
               name = nametxt.getText().toString();
                //Toast.makeText(v.getContext(), ""+mData.get(position).getChamberLocation(), Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(v.getContext(),AppoinmentBooking.class);
                intent.putExtra("idvalueforrecongnize","1");
                intent.putExtra("idvalueforrecongnized","3");
                intent.putExtra("idvalueforrecongnizedoctor","4");
               intent.putExtra("idvalue",id);
               intent.putExtra("district", district);
               intent.putExtra("hospital", hospital);
               intent.putExtra("expertise", expertise);
               intent.putExtra("name",name);
                intent.putExtra("education",education);
               intent.putExtra("description",description);
               intent.putExtra("speciality",speciality);
               v.getContext().startActivity(intent);

            }
        });
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(Chamber item, final View view) {

        //Log.d("itemslist", String.valueOf(items.size()));

        nameTxt = (TextView) view.findViewById(R.id.name);
        locationTxt = (TextView) view.findViewById(R.id.location);
        booknowTxt = (TextView) view.findViewById(R.id.bookappoinment);

        //Toast.makeText(view.getContext(), ""+item.getIdvalue(), Toast.LENGTH_SHORT).show();
        nameTxt.setText(item.getChamberName());
        locationTxt.setText(item.getChamberLocation());
        booknowTxt.setText(item.getBooknow());
    }
}