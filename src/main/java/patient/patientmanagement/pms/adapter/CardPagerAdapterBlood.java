package patient.patientmanagement.pms.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import patient.patientmanagement.pms.BloodActivity;
import patient.patientmanagement.pms.BloodDonorActivity;
import patient.patientmanagement.pms.R;
import patient.patientmanagement.pms.entity.CardAdapter;
import patient.patientmanagement.pms.entity.CardItem;
import patient.patientmanagement.pms.patient.patientmanagement.fragment.ApositiveFragment;
import patient.patientmanagement.pms.patient.patientmanagement.fragment.BnegativeFragment;

public class CardPagerAdapterBlood extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<CardItem> mData;
    private float mBaseElevation;
    private String bloodname;
    private List<String> thanaItem,districtItem;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("district");
    DatabaseReference myRefThana = database.getReference("thana");
    Context context = null;
    EditText districtEditView;
    Button btnSearch;
    AutoCompleteTextView thanaEditView;
    ArrayAdapter<String> adapterThana;
    Button btnBlood1;
    Button btnBlood2;
    Button btnBlood3;
    Button btnBlood4;
    Button btnBlood5;
    Button btnBlood6;
    Button btnBlood7;
    Button btnBlood8;

    public CardPagerAdapterBlood(Context context) {

        this.context = context;
        getValue("Dhaka");
        districtItem = new ArrayList<>();
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
        thanaItem = new ArrayList<>();
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

        districtEditView = (EditText) view.findViewById(R.id.et_hospital);
        thanaEditView = (AutoCompleteTextView) view.findViewById(R.id.et_thana);
        btnSearch = (Button) view.findViewById(R.id.btn_search);

        districtEditView.setText("Dhaka");

        btnBlood1 = (Button) view.findViewById(R.id.bloodButton1);
        btnBlood2 = (Button) view.findViewById(R.id.bloodButton2);
        btnBlood3 = (Button) view.findViewById(R.id.bloodButton3);
        btnBlood4 = (Button) view.findViewById(R.id.bloodButton4);
        btnBlood5 = (Button) view.findViewById(R.id.bloodButton5);
        btnBlood6 = (Button) view.findViewById(R.id.bloodButton6);
        btnBlood7 = (Button) view.findViewById(R.id.bloodButton7);
        btnBlood8 = (Button) view.findViewById(R.id.bloodButton8);



        final int color = ((ColorDrawable)btnBlood1.getBackground()).getColor();

        btnBlood1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(v.getContext(), ""+btnBlood1.getText().toString(), Toast.LENGTH_SHORT).show();
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

                //Toast.makeText(v.getContext(), ""+btnBlood2.getText().toString(), Toast.LENGTH_SHORT).show();
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

                //Toast.makeText(v.getContext(), ""+btnBlood3.getText().toString(), Toast.LENGTH_SHORT).show();
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

                //Toast.makeText(v.getContext(), ""+btnBlood4.getText().toString(), Toast.LENGTH_SHORT).show();
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

                //Toast.makeText(v.getContext(), ""+btnBlood5.getText().toString(), Toast.LENGTH_SHORT).show();
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

                //Toast.makeText(v.getContext(), ""+btnBlood6.getText().toString(), Toast.LENGTH_SHORT).show();
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

                //Toast.makeText(v.getContext(), ""+btnBlood7.getText().toString(), Toast.LENGTH_SHORT).show();
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

                //Toast.makeText(v.getContext(), ""+btnBlood8.getText().toString(), Toast.LENGTH_SHORT).show();
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

                //districtEditView.setText("Dhaka");

                districtEditView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final PopupMenu popup = new PopupMenu(v.getContext(), districtEditView);

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot zoneSnapshot: dataSnapshot.getChildren()) {
                            Log.i("specialityName", zoneSnapshot.child("districtName").getValue(String.class));
                            String districtName = zoneSnapshot.child("districtName").getValue(String.class);
                            districtItem.add(districtName);
                        }

                        for(int i=0; i<districtItem.size(); i++){
                            popup.getMenu().add(Menu.NONE, i, i, districtItem.get(i));

                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                                @Override
                                public boolean onMenuItemClick(MenuItem item) {

                                    districtEditView.setText(item.getTitle());
                                    String districtName = item.getTitle().toString();

                                    //Toast.makeText(v.getContext(),districtName, Toast.LENGTH_SHORT).show();


                                    getValue(districtName);

                       /*
                       Toast.makeText(v.getContext(),
                               item.getTitle(), Toast.LENGTH_SHORT).show();
                         */
                                    return true;
                                }
                            });
                            popup.show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //Log.w(TAG, "onCancelled", databaseError.toException());
                    }
                });


            }
        });
    }

    private void getValue(String districtName) {
        myRef.orderByChild("districtName").equalTo(districtName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    String id = String.valueOf(childDataSnapshot.child("districtId").getValue());
                    //Log.d("val",id);
                    value(id);
                }
                //Toast.makeText(context, ""+dataSnapshot, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void value(String id) {

        int idsvalue = Integer.parseInt(id);
        myRefThana.orderByChild("districtId").equalTo(idsvalue).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    String thanaName = String.valueOf(childDataSnapshot.child("thanaName").getValue());
                    Log.d("vals",thanaName);
                    thanaItem.add(thanaName);
                    //value(id);
                }

                adapterThana = new ArrayAdapter<String>
                        (context,R.layout.hint_completion_text,R.id.tvHintCompletion, thanaItem);

                thanaEditView.setThreshold(1);
                thanaEditView.setAdapter(adapterThana);

                btnSearch.setOnClickListener(new View.OnClickListener() {

                    int btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8;

                    @Override
                    public void onClick(View v) {

                        String district = districtEditView.getText().toString();
                        String thana = thanaEditView.getText().toString();

                        //Toast.makeText(v.getContext(), ""+thana, Toast.LENGTH_SHORT).show();


                        btn1 = ((ColorDrawable)btnBlood1.getBackground()).getColor();
                        btn2 = ((ColorDrawable)btnBlood2.getBackground()).getColor();
                        btn3 = ((ColorDrawable)btnBlood3.getBackground()).getColor();
                        btn4 = ((ColorDrawable)btnBlood4.getBackground()).getColor();
                        btn5 = ((ColorDrawable)btnBlood5.getBackground()).getColor();
                        btn6 = ((ColorDrawable)btnBlood6.getBackground()).getColor();
                        btn7 = ((ColorDrawable)btnBlood7.getBackground()).getColor();
                        btn8 = ((ColorDrawable)btnBlood8.getBackground()).getColor();

                        if(btn1 == -13388315){
                            //PagerBlood pg = new PagerBlood();
                            Intent intent = new Intent(v.getContext(), BloodActivity.class);
                            intent.putExtra("district",district);
                            intent.putExtra("thana",thana);
                            intent.putExtra("bloodgroup","A+");
                            v.getContext().startActivity(intent);

                            //Toast.makeText(v.getContext(), "A+", Toast.LENGTH_SHORT).show();
                        }
                        if(btn2 == -13388315){
                            //Toast.makeText(v.getContext(), "A-", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(v.getContext(), BloodActivity.class);
                            intent.putExtra("district",district);
                            intent.putExtra("thana",thana);
                            intent.putExtra("bloodgroup","A-");
                            v.getContext().startActivity(intent);

                        }
                        if(btn3 == -13388315){
                            Intent intent = new Intent(v.getContext(), BloodActivity.class);
                            intent.putExtra("district",district);
                            intent.putExtra("thana",thana);
                            intent.putExtra("bloodgroup","B+");
                            v.getContext().startActivity(intent);

                            //Toast.makeText(v.getContext(), "B+", Toast.LENGTH_SHORT).show();
                        }
                        if(btn4 == -13388315){
                            Intent intent = new Intent(v.getContext(), BloodActivity.class);
                            intent.putExtra("district",district);
                            intent.putExtra("thana",thana);
                            intent.putExtra("bloodgroup","B-");
                            v.getContext().startActivity(intent);

                            //Toast.makeText(v.getContext(), "B-", Toast.LENGTH_SHORT).show();
                        }
                        if(btn5 == -13388315){
                            Intent intent = new Intent(v.getContext(), BloodActivity.class);
                            intent.putExtra("district",district);
                            intent.putExtra("thana",thana);
                            intent.putExtra("bloodgroup","AB+");
                            v.getContext().startActivity(intent);

                            //Toast.makeText(v.getContext(), "AB+", Toast.LENGTH_SHORT).show();
                        }
                        if(btn6 == -13388315){
                            Intent intent = new Intent(v.getContext(), BloodActivity.class);
                            intent.putExtra("district",district);
                            intent.putExtra("thana",thana);
                            intent.putExtra("bloodgroup","AB-");
                            v.getContext().startActivity(intent);

                            //Toast.makeText(v.getContext(), "AB-", Toast.LENGTH_SHORT).show();
                        }
                        if(btn7 == -13388315){
                            Intent intent = new Intent(v.getContext(), BloodActivity.class);
                            intent.putExtra("district",district);
                            intent.putExtra("thana",thana);
                            intent.putExtra("bloodgroup","O+");
                            v.getContext().startActivity(intent);

                            //Toast.makeText(v.getContext(), "O+", Toast.LENGTH_SHORT).show();
                        }
                        if(btn8 == -13388315){
                            Intent intent = new Intent(v.getContext(), BloodActivity.class);
                            intent.putExtra("district",district);
                            intent.putExtra("thana",thana);
                            intent.putExtra("bloodgroup","O-");
                            v.getContext().startActivity(intent);

                            //Toast.makeText(v.getContext(), "O-", Toast.LENGTH_SHORT).show();
                        }
                        //Toast.makeText(v.getContext(), ""+color, Toast.LENGTH_SHORT).show();
                    }
                });
                //Toast.makeText(context, ""+dataSnapshot, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
