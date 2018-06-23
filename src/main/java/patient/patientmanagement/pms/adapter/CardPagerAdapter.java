package patient.patientmanagement.pms.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.QuickContactBadge;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import patient.patientmanagement.pms.DoctorList;
import patient.patientmanagement.pms.HospitalActivity;
import patient.patientmanagement.pms.HospitalSearchActivity;
import patient.patientmanagement.pms.R;
import patient.patientmanagement.pms.entity.CardAdapter;
import patient.patientmanagement.pms.entity.CardItem;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<CardItem> mData;
    private List<String> items;
    private List<String> itemsSpeciality,districtItem;
    private float mBaseElevation;
    private Context context;
    private PopupMenu popmenu;
    AlertDialog.Builder alertdialogbuilder;

    Button searchButton;
    EditText districtEditView;
    AutoCompleteTextView hospitalEditView,expertiseEditView;
    ArrayAdapter<String> adapter,adapterSpeciality;

    FirebaseDatabase database = FirebaseDatabase.getInstance();


    DatabaseReference myRef = database.getReference("district");
    DatabaseReference myRefHospital = database.getReference("hospitalInfo");
    DatabaseReference myRefExpertise = database.getReference("speciality");

    public CardPagerAdapter() {
        districtItem = new ArrayList<>();
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
        items = new ArrayList<String>();
        itemsSpeciality = new ArrayList<String>();
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
                .inflate(R.layout.adapter, container, false);
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

    private void bind(CardItem item, final View view) {

        //Log.d("itemslist", String.valueOf(items.size()));

        searchButton = (Button) view.findViewById(R.id.searchBtn);
        districtEditView = (EditText) view.findViewById(R.id.et_district);
        hospitalEditView = (AutoCompleteTextView) view.findViewById(R.id.et_hospital);
        expertiseEditView = (AutoCompleteTextView) view.findViewById(R.id.et_speciality);


        districtEditView.setText("Dhaka");
        myRefHospital.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot zoneSnapshot: dataSnapshot.getChildren()) {
                    Log.i("hospitalName", zoneSnapshot.child("hospitalName").getValue(String.class));
                    String hospitalName = zoneSnapshot.child("hospitalName").getValue(String.class);
                    items.add(hospitalName);
                    }
                adapter = new ArrayAdapter<String>
                        (view.getContext(),R.layout.hint_completion_text,R.id.tvHintCompletion, items);

                hospitalEditView.setThreshold(1);
                hospitalEditView.setAdapter(adapter);
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Log.w(TAG, "onCancelled", databaseError.toException());
            }
        });



        myRefExpertise.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot zoneSnapshot: dataSnapshot.getChildren()) {
                    Log.i("specialityName", zoneSnapshot.child("specialityName").getValue(String.class));
                    String specialityName = zoneSnapshot.child("specialityName").getValue(String.class);
                    itemsSpeciality.add(specialityName);
                }
                adapterSpeciality = new ArrayAdapter<String>
                        (view.getContext(),R.layout.hint_completion_text,R.id.tvHintCompletion, itemsSpeciality);

                expertiseEditView.setThreshold(1);
                expertiseEditView.setAdapter(adapterSpeciality);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Log.w(TAG, "onCancelled", databaseError.toException());
            }
        });

        //districtEditView.setText("Dhaka");

        districtEditView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick ( final View v){
                   final PopupMenu popup = new PopupMenu(v.getContext(), districtEditView);

                   myRef.addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(DataSnapshot dataSnapshot) {
                           for (DataSnapshot zoneSnapshot : dataSnapshot.getChildren()) {
                               Log.i("specialityName", zoneSnapshot.child("districtName").getValue(String.class));
                               String districtName = zoneSnapshot.child("districtName").getValue(String.class);
                               districtItem.add(districtName);
                           }

                           for (int i = 0; i < districtItem.size(); i++) {
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

          searchButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                  String district = districtEditView.getText().toString();
                  String hospital = hospitalEditView.getText().toString();
                  String expertise = expertiseEditView.getText().toString();

                  if(!district.isEmpty() && !hospital.isEmpty() && !expertise.isEmpty()) {
                      Intent intent = new Intent(v.getContext(), DoctorList.class);
                      intent.putExtra("district", district);
                      intent.putExtra("hospital", hospital);
                      intent.putExtra("expertise", expertise);
                      v.getContext().startActivity(intent);
                  }

                  else if(!district.isEmpty() && hospital.isEmpty() && !expertise.isEmpty()){
                      Intent intent = new Intent(v.getContext(), DoctorList.class);
                      intent.putExtra("district", district);
                      intent.putExtra("hospital", "null");
                      intent.putExtra("expertise", expertise);
                      v.getContext().startActivity(intent);
                  }

                  else if(!district.isEmpty() && hospital.isEmpty() && expertise.isEmpty()){
                      Intent intent = new Intent(v.getContext(), HospitalActivity.class);
                      intent.putExtra("district", district);
                      v.getContext().startActivity(intent);
                  }

                  else{

                      Intent intent = new Intent(v.getContext(), HospitalSearchActivity.class);
                      intent.putExtra("district", district);
                      intent.putExtra("hospital", hospital);
                      intent.putExtra("expertise", expertise);
                      v.getContext().startActivity(intent);
                      /*
                      Intent intent = new Intent(v.getContext(), DoctorList.class);
                      intent.putExtra("district", district);
                      intent.putExtra("hospital", hospital);
                      intent.putExtra("expertise", expertise);
                      v.getContext().startActivity(intent);
                      */
                  }
              }
          });
       }


    private void getValue(CharSequence title) {
        myRef.orderByChild("districtName").equalTo(String.valueOf(title)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                       String id = String.valueOf(childDataSnapshot.child("districtId").getValue());
                       //value(id);
                }
                //Toast.makeText(context, ""+dataSnapshot, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    /*
    private void value(String id) {
        myRefHospital.orderByChild("districtId").equalTo(String.valueOf(id)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    String hospitalName = String.valueOf(childDataSnapshot.child("hospitalName").getValue());
                    Log.d("hospitalName",hospitalName);
                    items.add(hospitalName);
                    //value(id);
                }

                adapter = new ArrayAdapter<String>
                        (context,R.layout.hint_completion_text,R.id.tvHintCompletion, items);

                hospitalEditView.setThreshold(1);
                hospitalEditView.setAdapter(adapter);
                //Toast.makeText(context, ""+dataSnapshot, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    */
}