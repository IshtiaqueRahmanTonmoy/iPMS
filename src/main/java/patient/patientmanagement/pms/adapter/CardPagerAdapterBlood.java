package patient.patientmanagement.pms.adapter;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;

import java.util.ArrayList;
import java.util.List;

import patient.patientmanagement.pms.R;
import patient.patientmanagement.pms.entity.CardAdapter;
import patient.patientmanagement.pms.entity.CardItem;

public class CardPagerAdapterBlood extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<CardItem> mData;
    private float mBaseElevation;

    public CardPagerAdapterBlood() {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    public void addCardItem(CardItem item) {
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
                .inflate(R.layout.adapterblood, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardViewBlood);

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

    private void bind(CardItem item, View view) {

        final Button btnBlood1 = (Button) view.findViewById(R.id.bloodButton1);
        final Button btnBlood2 = (Button) view.findViewById(R.id.bloodButton2);
        final Button btnBlood3 = (Button) view.findViewById(R.id.bloodButton3);
        final Button btnBlood4 = (Button) view.findViewById(R.id.bloodButton4);
        final Button btnBlood5 = (Button) view.findViewById(R.id.bloodButton5);
        final Button btnBlood6 = (Button) view.findViewById(R.id.bloodButton6);
        final Button btnBlood7 = (Button) view.findViewById(R.id.bloodButton7);
        final Button btnBlood8 = (Button) view.findViewById(R.id.bloodButton8);

        final int color = ((ColorDrawable)btnBlood1.getBackground()).getColor();

        btnBlood1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnBlood1.setBackgroundColor(v.getResources().getColor(R.color.wallet_holo_blue_light));
                btnBlood1.setTextColor(v.getResources().getColor(R.color.white));

                btnBlood2.setBackgroundColor(color);
                btnBlood2.setTextColor(Color.parseColor("#447a8f"));
                btnBlood3.setBackgroundColor(color);
                btnBlood3.setTextColor(Color.parseColor("#447a8f"));
                btnBlood4.setBackgroundColor(color);
                btnBlood4.setTextColor(Color.parseColor("#447a8f"));
                btnBlood5.setBackgroundColor(color);
                btnBlood5.setTextColor(Color.parseColor("#447a8f"));
                btnBlood6.setBackgroundColor(color);
                btnBlood6.setTextColor(Color.parseColor("#447a8f"));
                btnBlood7.setBackgroundColor(color);
                btnBlood7.setTextColor(Color.parseColor("#447a8f"));
                btnBlood8.setBackgroundColor(color);
                btnBlood8.setTextColor(Color.parseColor("#447a8f"));

            }
        });

        btnBlood2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnBlood2.setBackgroundColor(v.getResources().getColor(R.color.wallet_holo_blue_light));
                btnBlood2.setTextColor(v.getResources().getColor(R.color.white));

                btnBlood1.setBackgroundColor(color);
                btnBlood1.setTextColor(Color.parseColor("#447a8f"));
                btnBlood3.setBackgroundColor(color);
                btnBlood3.setTextColor(Color.parseColor("#447a8f"));
                btnBlood4.setBackgroundColor(color);
                btnBlood4.setTextColor(Color.parseColor("#447a8f"));
                btnBlood5.setBackgroundColor(color);
                btnBlood5.setTextColor(Color.parseColor("#447a8f"));
                btnBlood6.setBackgroundColor(color);
                btnBlood6.setTextColor(Color.parseColor("#447a8f"));
                btnBlood7.setBackgroundColor(color);
                btnBlood7.setTextColor(Color.parseColor("#447a8f"));
                btnBlood8.setBackgroundColor(color);
                btnBlood8.setTextColor(Color.parseColor("#447a8f")); }
        });

        btnBlood3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnBlood3.setBackgroundColor(v.getResources().getColor(R.color.wallet_holo_blue_light));
                btnBlood3.setTextColor(v.getResources().getColor(R.color.white));

                btnBlood1.setBackgroundColor(color);
                btnBlood1.setTextColor(Color.parseColor("#447a8f"));
                btnBlood2.setBackgroundColor(color);
                btnBlood2.setTextColor(Color.parseColor("#447a8f"));
                btnBlood4.setBackgroundColor(color);
                btnBlood4.setTextColor(Color.parseColor("#447a8f"));
                btnBlood5.setBackgroundColor(color);
                btnBlood5.setTextColor(Color.parseColor("#447a8f"));
                btnBlood6.setBackgroundColor(color);
                btnBlood6.setTextColor(Color.parseColor("#447a8f"));
                btnBlood7.setBackgroundColor(color);
                btnBlood7.setTextColor(Color.parseColor("#447a8f"));
                btnBlood8.setBackgroundColor(color);
                btnBlood8.setTextColor(Color.parseColor("#447a8f")); }
        });

        btnBlood4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnBlood4.setBackgroundColor(v.getResources().getColor(R.color.wallet_holo_blue_light));
                btnBlood4.setTextColor(v.getResources().getColor(R.color.white));

                btnBlood1.setBackgroundColor(color);
                btnBlood1.setTextColor(Color.parseColor("#447a8f"));
                btnBlood3.setBackgroundColor(color);
                btnBlood3.setTextColor(Color.parseColor("#447a8f"));
                btnBlood2.setBackgroundColor(color);
                btnBlood2.setTextColor(Color.parseColor("#447a8f"));
                btnBlood5.setBackgroundColor(color);
                btnBlood5.setTextColor(Color.parseColor("#447a8f"));
                btnBlood6.setBackgroundColor(color);
                btnBlood6.setTextColor(Color.parseColor("#447a8f"));
                btnBlood7.setBackgroundColor(color);
                btnBlood7.setTextColor(Color.parseColor("#447a8f"));
                btnBlood8.setBackgroundColor(color);
                btnBlood8.setTextColor(Color.parseColor("#447a8f")); }
        });

        btnBlood5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnBlood5.setBackgroundColor(v.getResources().getColor(R.color.wallet_holo_blue_light));
                btnBlood5.setTextColor(v.getResources().getColor(R.color.white));

                btnBlood1.setBackgroundColor(color);
                btnBlood1.setTextColor(Color.parseColor("#447a8f"));
                btnBlood3.setBackgroundColor(color);
                btnBlood3.setTextColor(Color.parseColor("#447a8f"));
                btnBlood2.setBackgroundColor(color);
                btnBlood2.setTextColor(Color.parseColor("#447a8f"));
                btnBlood4.setBackgroundColor(color);
                btnBlood4.setTextColor(Color.parseColor("#447a8f"));
                btnBlood6.setBackgroundColor(color);
                btnBlood6.setTextColor(Color.parseColor("#447a8f"));
                btnBlood7.setBackgroundColor(color);
                btnBlood7.setTextColor(Color.parseColor("#447a8f"));
                btnBlood8.setBackgroundColor(color);
                btnBlood8.setTextColor(Color.parseColor("#447a8f")); }
        });

        btnBlood6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnBlood6.setBackgroundColor(v.getResources().getColor(R.color.wallet_holo_blue_light));
                btnBlood6.setTextColor(v.getResources().getColor(R.color.white));

                btnBlood1.setBackgroundColor(color);
                btnBlood1.setTextColor(Color.parseColor("#447a8f"));
                btnBlood3.setBackgroundColor(color);
                btnBlood3.setTextColor(Color.parseColor("#447a8f"));
                btnBlood2.setBackgroundColor(color);
                btnBlood2.setTextColor(Color.parseColor("#447a8f"));
                btnBlood5.setBackgroundColor(color);
                btnBlood5.setTextColor(Color.parseColor("#447a8f"));
                btnBlood4.setBackgroundColor(color);
                btnBlood4.setTextColor(Color.parseColor("#447a8f"));
                btnBlood7.setBackgroundColor(color);
                btnBlood7.setTextColor(Color.parseColor("#447a8f"));
                btnBlood8.setBackgroundColor(color);
                btnBlood8.setTextColor(Color.parseColor("#447a8f")); }
        });

        btnBlood7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnBlood7.setBackgroundColor(v.getResources().getColor(R.color.wallet_holo_blue_light));
                btnBlood7.setTextColor(v.getResources().getColor(R.color.white));

                btnBlood1.setBackgroundColor(color);
                btnBlood1.setTextColor(Color.parseColor("#447a8f"));
                btnBlood3.setBackgroundColor(color);
                btnBlood3.setTextColor(Color.parseColor("#447a8f"));
                btnBlood2.setBackgroundColor(color);
                btnBlood2.setTextColor(Color.parseColor("#447a8f"));
                btnBlood5.setBackgroundColor(color);
                btnBlood5.setTextColor(Color.parseColor("#447a8f"));
                btnBlood6.setBackgroundColor(color);
                btnBlood6.setTextColor(Color.parseColor("#447a8f"));
                btnBlood4.setBackgroundColor(color);
                btnBlood4.setTextColor(Color.parseColor("#447a8f"));
                btnBlood8.setBackgroundColor(color);
                btnBlood8.setTextColor(Color.parseColor("#447a8f")); }
        });

        btnBlood8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnBlood8.setBackgroundColor(v.getResources().getColor(R.color.wallet_holo_blue_light));
                btnBlood8.setTextColor(v.getResources().getColor(R.color.white));

                btnBlood1.setBackgroundColor(color);
                btnBlood1.setTextColor(Color.parseColor("#447a8f"));
                btnBlood3.setBackgroundColor(color);
                btnBlood3.setTextColor(Color.parseColor("#447a8f"));
                btnBlood2.setBackgroundColor(color);
                btnBlood2.setTextColor(Color.parseColor("#447a8f"));
                btnBlood5.setBackgroundColor(color);
                btnBlood5.setTextColor(Color.parseColor("#447a8f"));
                btnBlood6.setBackgroundColor(color);
                btnBlood6.setTextColor(Color.parseColor("#447a8f"));
                btnBlood7.setBackgroundColor(color);
                btnBlood7.setTextColor(Color.parseColor("#447a8f"));
                btnBlood4.setBackgroundColor(color);
                btnBlood4.setTextColor(Color.parseColor("#447a8f")); }
        });

        final EditText districtEditView = (EditText) view.findViewById(R.id.et_hospital);
        AutoCompleteTextView expertiseEditView = (AutoCompleteTextView) view.findViewById(R.id.et_thana);

        districtEditView.setText("Dhaka");

        districtEditView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final PopupMenu popup = new PopupMenu(v.getContext(), districtEditView);
                popup.getMenuInflater().inflate(R.menu.pop_up_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        districtEditView.setText(item.getTitle());
                        //getValue(item.getTitle());

                       /*
                       Toast.makeText(v.getContext(),
                               item.getTitle(), Toast.LENGTH_SHORT).show();
                         */
                        return true;
                    }
                });
                popup.show();
            }
        });

    }

}
