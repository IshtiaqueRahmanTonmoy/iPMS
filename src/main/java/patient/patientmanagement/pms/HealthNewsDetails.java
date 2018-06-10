package patient.patientmanagement.pms;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HealthNewsDetails extends AppCompatActivity {

    String idvalue;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("healthNews");
    TextView headingTxt,detailsTxt;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_news_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

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
                        String heading = String.valueOf(childDataSnapshot.child("heading").getValue());
                        String details = String.valueOf(childDataSnapshot.child("details").getValue());

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
    }

    private void showProcessDialog() {
        progressDialog = new ProgressDialog(HealthNewsDetails.this);
        //progressDialog.setTitle("Getting Informations");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();


    }

}
