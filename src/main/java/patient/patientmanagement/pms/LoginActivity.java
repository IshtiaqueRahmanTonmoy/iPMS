package patient.patientmanagement.pms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mikepenz.iconics.context.IconicsLayoutInflater2;

import patient.patientmanagement.pms.databinding.ActivityLoginBinding;
import patient.patientmanagement.pms.entity.Utils;
import patient.patientmanagement.pms.entity.appoinmentSchedule;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    private EditText emailEdt,passwordEdt;
    private Button loginBtn;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;
    private ProgressBar progressBar;
    private TextView signupText;
    DatabaseReference databaseUsers,databaseUsersAppoinment;
    String idvalue;

    String date,disease,doctorId,hospitalId,dates,time,serialNo,confirm;
    //long id = 0;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), new IconicsLayoutInflater2(getDelegate()));
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            idvalue = extras.getString("idvalue");

            date = extras.getString("format");
            disease = extras.getString("symptom");
            doctorId = extras.getString("id");
            hospitalId = extras.getString("hospitalId");
            dates = extras.getString("date");
            time = extras.getString("time");
            serialNo = extras.getString("serial");
            confirm = extras.getString("confirm");
            //id = Long.parseLong(extras.getString("appoinmentid"));
            id = extras.getString("appoinmentid");

            Log.d("extra",id);
            Log.d("value",date+"\n"+disease+"\n"+doctorId+"\n"+hospitalId+"\n"+id+"\n"+null+"\n"+serialNo+"\n"+1+"\n"+time);

            //Toast.makeText(this, ""+idvalue, Toast.LENGTH_SHORT).show();
        }

        emailEdt = (EditText) findViewById(R.id.et_phone);
        passwordEdt = (EditText) findViewById(R.id.et_password);
        loginBtn = (Button) findViewById(R.id.btn_login); 

        auth = FirebaseAuth.getInstance();
        databaseUsers = FirebaseDatabase.getInstance().getReference("patientInfo");
        databaseUsersAppoinment = FirebaseDatabase.getInstance().getReference("appoinmentSchedule");


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (emailEdt.getText().length()<0) {
                    Utils.showToast(LoginActivity.this, "Please input your email");
                } else if (passwordEdt.getText().length()<0) {
                    Utils.showToast(LoginActivity.this, "Please input your password");
                } else {

                    final String emailtext = emailEdt.getText().toString();
                    final String passwordtext = passwordEdt.getText().toString();
                    //requesting Firebase server
                    showProcessDialog();
                    auth.signInWithEmailAndPassword(emailtext, passwordtext)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        if (passwordtext.length() < 6) {
                                            passwordEdt.setError("password length must be minimum 6");
                                        } else {
                                            Toast.makeText(LoginActivity.this, "Authentication failed..", Toast.LENGTH_LONG).show();
                                            progressDialog.dismiss();
                                        }
                                    } else {

                                        FirebaseUser firebaseUser = auth.getCurrentUser();
                                        if (firebaseUser != null) {
                                            String patientId = firebaseUser.getUid();
                                            //Toast.makeText(LoginActivity.this, "successfull"+idvalue, Toast.LENGTH_SHORT).show();
                                            addvalue(date,disease,doctorId,hospitalId,id,patientId,serialNo,"1",time);
                                            progressDialog.dismiss();
                                        }
                                        //Intent intent = new Intent(LoginActivity.this, Doc.class);
                                        //progressDialog.dismiss();
                                        //intent.putExtra("email",emailtext);
                                        //startActivity(intent);
                                        //finish();
                                    }
                                }
                            });
                }
            }
        });

    }

    private void addvalue(String date, String disease, String doctorId, String hosId, String i, String patientId, String srlno, String sts, String time) {

        int hospitalId = Integer.parseInt(hosId);
        int serialNo = Integer.parseInt(srlno);
        int status = Integer.parseInt(sts);
        int id = Integer.parseInt(i);

        Log.d("value",date+"\n"+disease+"\n"+doctorId+"\n"+hospitalId+"\n"+id+"\n"+patientId+"\n"+serialNo+"\n"+status+"\n"+time);

        appoinmentSchedule user = new appoinmentSchedule(date,disease,doctorId,hospitalId,id,patientId,serialNo,status,time);
        //long node = id - 1;
        //String idnode = String.valueOf(node);
        int idnode = 2;
        databaseUsersAppoinment.child(String.valueOf(idnode)).setValue(user);
    }

    private void showProcessDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

    }

    public void signup(View view) {
        startActivity(new Intent(this, SignupActivity.class));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }
}
