package patient.patientmanagement.pms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HealthTipsDetails extends AppCompatActivity {

    String idvalue;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("healthTips");
    TextView headingTxt,detailsTxt;
    private ProgressDialog progressDialog;
    private String heading,details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_tips_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setTitle("Helath Tips Details");

        showProcessDialog();
        headingTxt = (TextView) findViewById(R.id.heading);
        detailsTxt = (TextView) findViewById(R.id.details);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idvalue = extras.getString("idvalue");
            //Toast.makeText(this, ""+idvalue, Toast.LENGTH_SHORT).show();

            int id = Integer.parseInt(idvalue);
            myRef.orderByChild("id").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                        heading = String.valueOf(childDataSnapshot.child("heading").getValue());
                        details = String.valueOf(childDataSnapshot.child("details").getValue());

                        headingTxt.setText(heading);
                        detailsTxt.setText(details);

                        progressDialog.dismiss();
                        //Log.d("valuesspecialist",districtId+specialityId);
                    }
                    //Toast.makeText(context, ""+dataSnapshot, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,heading);
                intent.putExtra(Intent.EXTRA_TEXT,details);
                startActivity(Intent.createChooser(intent,"Share using"));
            }
        });
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
            Intent intent = new Intent(HealthTipsDetails.this,HealthTipsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }


        return super.onOptionsItemSelected(item);
    }
    private void showProcessDialog() {
        progressDialog = new ProgressDialog(HealthTipsDetails.this);
        //progressDialog.setTitle("Getting Informations");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();


    }

}
