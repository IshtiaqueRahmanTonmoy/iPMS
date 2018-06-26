package patient.patientmanagement.pms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import patient.patientmanagement.pms.adapter.HealthNewsAdapter;
import patient.patientmanagement.pms.adapter.HealthTipsAdapter;
import patient.patientmanagement.pms.entity.DividerItemDecoration;
import patient.patientmanagement.pms.entity.HealthTips;
import patient.patientmanagement.pms.entity.RecyclerItemClickListener;

public class HealthTipsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRefHealthNews = database.getReference("healthNews");
    private ProgressDialog progressDialog;
    private List<HealthTips> healthTipsActivityList = new ArrayList<HealthTips>();
    private HealthTipsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_news);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        showProcessDialog();
        getValue();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        if(id == android.R.id.home){
            Intent intent = new Intent(HealthTipsActivity.this,DashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }


        return super.onOptionsItemSelected(item);
    }

    private void showProcessDialog() {
        progressDialog = new ProgressDialog(HealthTipsActivity.this);
        //progressDialog.setTitle("Getting Informations");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

    }

    private void getValue() {
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("healthTips");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("datasnpahsopt", String.valueOf(dataSnapshot.getChildrenCount()));


                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    long idss = ds.child("id").getValue(Long.class);
                    String id = String.valueOf(idss);
                    String details = ds.child("details").getValue(String.class);
                    //names.add(name);
                    String heading = ds.child("heading").getValue(String.class);
                    String upload = ds.child("date").getValue(String.class);
                    String image = ds.child("image").getValue(String.class);

                    healthTipsActivityList.add(new HealthTips(id,image,heading,details,upload));
                    mAdapter = new HealthTipsAdapter(healthTipsActivityList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(HealthTipsActivity.this);
                    recyclerView.addItemDecoration(
                            new DividerItemDecoration(HealthTipsActivity.this, R.drawable.divider));

                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);

                    progressDialog.dismiss();

                    /*
                    recyclerView.addOnItemTouchListener(
                            new RecyclerItemClickListener(HealthTipsActivity.this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                                @Override public void onItemClick(View view, int position) {

                                    TextView idtxtview = (TextView) view.findViewById(R.id.idvalue);

                                    String idvalue = idtxtview.getText().toString();

                                    Intent intent = new Intent(HealthTipsActivity.this,HealthTipsDetails.class);
                                    intent.putExtra("idvalue",idvalue);
                                    startActivity(intent);

                                    //Toast.makeText(getContext(), ""+district+""+hospitalName, Toast.LENGTH_SHORT).show();
                                }

                                @Override public void onLongItemClick(View view, int position) {
                                    // do whatever
                                }
                            })
                    );
                    */
                    //mCardAdapter.addCardItem(new Chamber(idvalue,name,number,"BOOK APPOINMENT NOW"));
                    //mCardAdapter.addCardItem(new Chamber("Syed Diagonstics & Consultation Center,Main Branch", "Ka 164/2(Ground Floor),Bottola,Khilagaon,Dhaka", "BOOK APPOINMENT NOW"));

                    //Log.d("TAG", name);
                    //Log.d("TAG", number);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}
