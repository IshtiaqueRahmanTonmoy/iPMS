package patient.patientmanagement.pms.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import patient.patientmanagement.pms.DoctorList;
import patient.patientmanagement.pms.HospitalSearchActivity;
import patient.patientmanagement.pms.R;
import patient.patientmanagement.pms.entity.CardAdapter;
import patient.patientmanagement.pms.entity.CardItem;
import patient.patientmanagement.pms.entity.Chamber;

public class ChamberAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<Chamber> mData;
    private TextView nameTxt,locationTxt,booknowTxt;
    private float mBaseElevation;

    public ChamberAdapter() {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();

    }

    public void addCardItem(Chamber item) {
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
                .inflate(R.layout.cardview_item, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.card_view1);

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

    private void bind(Chamber item, final View view) {

        //Log.d("itemslist", String.valueOf(items.size()));

        nameTxt = (TextView) view.findViewById(R.id.name);
        locationTxt = (TextView) view.findViewById(R.id.location);
        booknowTxt = (TextView) view.findViewById(R.id.bookappoinment);

        nameTxt.setText(item.getChamberName());
        locationTxt.setText(item.getChamberLocation());
        booknowTxt.setText(item.getBooknow());
    }
}