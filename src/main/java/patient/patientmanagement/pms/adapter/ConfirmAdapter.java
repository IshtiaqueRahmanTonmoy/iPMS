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
    String format,symptom,id,hospitalId,date,time,serial,confirm;
    long appoinmentid = 0;

    public ConfirmAdapter(Context context) {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
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
        slnoTextView.setText(item.getSerial());

        format = item.getFormat();
        symptom = item.getSymptoms();
        id = item.getId();
        hospitalId = item.getHospitalId();
        date = item.getDate();
        time = item.getTime();
        serial = item.getSerial();
        confirm = item.getConfirm();
        appoinmentid = item.getAid();

        Log.d("val",format+time);

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
                intent.putExtra("appoinmentid",appoinmentid);

                v.getContext().startActivity(intent);

            }
        });
    }

}