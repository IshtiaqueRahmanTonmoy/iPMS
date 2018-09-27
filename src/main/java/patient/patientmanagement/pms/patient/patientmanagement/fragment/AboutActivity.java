package patient.patientmanagement.pms.patient.patientmanagement.fragment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import patient.patientmanagement.pms.BloodDonorActivity;
import patient.patientmanagement.pms.DashboardActivity;
import patient.patientmanagement.pms.R;
public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("i-PMS");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //Toast.makeText(this, ""+idvalrecong, Toast.LENGTH_SHORT).show();

        if(id == android.R.id.home){

            Intent intent = new Intent(AboutActivity.this,DashboardActivity.class);
            //intent.putExtra("hospital",hospital);
            //intent.putExtra("doctorlist",doctorlist);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }

}
