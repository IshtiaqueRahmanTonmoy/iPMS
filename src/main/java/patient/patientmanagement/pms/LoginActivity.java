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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikepenz.iconics.context.IconicsLayoutInflater2;

import java.util.ArrayList;
import java.util.List;

import patient.patientmanagement.pms.databinding.ActivityLoginBinding;
import patient.patientmanagement.pms.entity.Utils;
import patient.patientmanagement.pms.entity.appoinmentSchedule;
import patient.patientmanagement.pms.patient.patientmanagement.fragment.AppoinmentSuccess;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    private EditText emailEdt,passwordEdt;
    private Button loginBtn;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;
    private ProgressBar progressBar;
    private TextView signupText;
    DatabaseReference databaseUsers,databaseUsersAppoinment,databaseBlood;
    String idvalue;
    long nodecount;

    String date,disease,doctorId,hospitalId,dates,time,serialNo,confirm;
    String description,speciality,education,district,hospital,expertise,ids,namevalue,idvalues,fromonlydistrict,fromonlydistrictandhos,doctorlist;
    //long id = 0;
    String id;
    ImageView back;
    ImageButton imageBtn;
    int District,DistrictAndHos,DistrictHosSpeciality;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("patientInfo");
    DatabaseReference myRefBlood = database.getReference("bloodDonor");
    private List<String> itemsEmail,itemsPhone,itemsPassword;
    TextView toptext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), new IconicsLayoutInflater2(getDelegate()));
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        itemsEmail = new ArrayList<String>();
        itemsPhone = new ArrayList<String>();
        itemsPassword = new ArrayList<String>();

        getblood();
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

            description = extras.getString("description");
            speciality = extras.getString("speciality");
            education = extras.getString("education");
            district = extras.getString("district");
            hospital = extras.getString("hospital");
            expertise = extras.getString("expertise");
            idvalues = extras.getString("idvalues");
            namevalue = extras.getString("name");

            District = extras.getInt("District");
            DistrictAndHos = extras.getInt("DistrictAndHos");
            DistrictHosSpeciality = extras.getInt("DistrictHosSpeciality");

            fromonlydistrict = extras.getString("fromonlydistrict");
            fromonlydistrictandhos = extras.getString("fromonlydistrictandhosptial");
            doctorlist = extras.getString("doctorlist");
            nodecount = extras.getLong("nodecount");
            nodecount = nodecount + 1;

            Log.d("extra", String.valueOf(nodecount));
            Log.d("value",date+"\n"+disease+"\n"+doctorId+"\n"+hospitalId+"\n"+id+"\n"+null+"\n"+serialNo+"\n"+1+"\n"+time);

            //Toast.makeText(this, ""+idvalue, Toast.LENGTH_SHORT).show();
        }

        imageBtn = (ImageButton) findViewById(R.id.imageback);
        emailEdt = (EditText) findViewById(R.id.et_email);
        passwordEdt = (EditText) findViewById(R.id.et_password);
        loginBtn = (Button) findViewById(R.id.btn_login);

        toptext = (TextView) findViewById(R.id.text1);

        emailEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 toptext.setText("*Note* By loging your appoinment to doctor will be given.");
            }
        });

        passwordEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toptext.setText("*Note* By loggedin your appoinment to doctor will be given.");
            }
        });
        auth = FirebaseAuth.getInstance();
        databaseUsers = FirebaseDatabase.getInstance().getReference("patientInfo");
        databaseUsersAppoinment = FirebaseDatabase.getInstance().getReference("appoinmentSchedule");
        databaseBlood = FirebaseDatabase.getInstance().getReference("bloodDonor");

        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,AppoinmentBooking.class);

                intent.putExtra("description",description);
                intent.putExtra("speciality",speciality);
                intent.putExtra("education",education);
                intent.putExtra("district",district);
                intent.putExtra("hospital",hospital);
                intent.putExtra("expertise",expertise);
                intent.putExtra("idvalue",idvalues);
                intent.putExtra("name",namevalue);
                intent.putExtra("fromonlydistrict",fromonlydistrict);
                intent.putExtra("fromonlydistrictandhosptial",fromonlydistrictandhos);
                intent.putExtra("doctorlist", doctorlist);

                intent.putExtra("District",District);
                intent.putExtra("DistrictAndHos",DistrictAndHos);
                intent.putExtra("DistrictHosSpeciality",DistrictHosSpeciality);
                intent.putExtra("nodecount",nodecount);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (emailEdt.getText().length()<0) {
                    Utils.showToast(LoginActivity.this, "Please input your email");
                } else if (passwordEdt.getText().length()<0) {
                    Utils.showToast(LoginActivity.this, "Please input your password");
                } else {

                    final String emailphonetext = emailEdt.getText().toString();
                    final String passwordtext = passwordEdt.getText().toString();
                    //requesting Firebase server
                    showProcessDialog();
                    if(emailEdt.getText().length() == 0 && passwordEdt.getText().length()==0){
                       emailEdt.setError("E-mail cannot be blank");
                       passwordEdt.setError("Password cannot be blank");
                       progressDialog.dismiss();
                    }else{
                        signin(emailphonetext,passwordtext);
                    }
                }
            }
        });

    }

    private void getblood() {
        myRefBlood.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String emails = String.valueOf(ds.child("email").getValue());
                    String phones = String.valueOf(ds.child("phone").getValue());
                    String codes = String.valueOf(ds.child("password").getValue());

                    itemsEmail.add(emails);
                    itemsPhone.add(phones);
                    itemsPassword.add(codes);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void signin(final String emailphonetext,final String passwordtext) {
        auth.signInWithEmailAndPassword(emailphonetext, passwordtext)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        final FirebaseUser firebaseUser1 = auth.getCurrentUser();
                        //Toast.makeText(LoginActivity.this, ""+firebaseUser1.getUid(), Toast.LENGTH_SHORT).show();
                        //Log.d("userid",uid);
                        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                                    String email = String.valueOf(ds.child("email").getValue());
                                    String phone = String.valueOf(ds.child("phone").getValue());
                                    String code = String.valueOf(ds.child("password").getValue());

                                    itemsEmail.add(email);
                                    itemsPhone.add(phone);
                                    itemsPassword.add(code);

                                }
                                if(itemsEmail.contains(emailphonetext) && itemsPassword.contains(passwordtext) || itemsPhone.contains(emailphonetext) && itemsPassword.contains(passwordtext)){
                                    Toast.makeText(LoginActivity.this, "Successfully login", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    String patientId = firebaseUser1.getUid();
                                    //Toast.makeText(LoginActivity.this, "successfull"+idvalue, Toast.LENGTH_SHORT).show();
                                    addvalue(date,disease,doctorId,hospitalId,id,patientId,serialNo,"1",time);
                                    progressDialog.dismiss();
                                }else{
                                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                });
    }

    private void addvalue(String date, String disease, String doctorId, String hosId, String i, String patientId, String srlno, String sts, String time) {

        //Toast.makeText(LoginActivity.this, ""+hosId, Toast.LENGTH_SHORT).show();
        int hospitalId = Integer.parseInt(hosId);
        int serialNo = Integer.parseInt(srlno);
        int status = Integer.parseInt(sts);
        int id = Integer.parseInt(i);

        Log.d("value", date + "\n" + disease + "\n" + doctorId + "\n" + hospitalId + "\n" + id + "\n" + patientId + "\n" + serialNo + "\n" + status + "\n" + time);

        appoinmentSchedule user = new appoinmentSchedule(date, disease, doctorId, hospitalId, id, patientId, serialNo, status, time);
        long node = id - 1;
        String idnode = String.valueOf(node);
        //int idnode = 2;
        databaseUsersAppoinment.child(String.valueOf(nodecount)).setValue(user);

        Intent intent = new Intent(LoginActivity.this, AppoinmentSuccess.class);
        intent.putExtra("format",date);
        intent.putExtra("symptom",disease);
        intent.putExtra("id",doctorId);
        intent.putExtra("hospitalId",hospitalId);
        intent.putExtra("date",dates);
        intent.putExtra("time",time);
        intent.putExtra("serial",serialNo);
        intent.putExtra("confirm",confirm);
        intent.putExtra("appoinmentid",String.valueOf(id));

        Log.d("valueofappoinment", String.valueOf(id));
        Log.d("serial", String.valueOf(serialNo));

        intent.putExtra("description",description);
        intent.putExtra("speciality",speciality);
        intent.putExtra("education",education);
        intent.putExtra("district",district);
        intent.putExtra("hospital",hospital);
        intent.putExtra("expertise",expertise);
        intent.putExtra("idvalues",idvalues);
        intent.putExtra("name",namevalue);
        intent.putExtra("fromonlydistrict",fromonlydistrict);
        intent.putExtra("fromonlydistrictandhosptial", fromonlydistrictandhos);
        intent.putExtra("doctorlist", doctorlist);

        intent.putExtra("District",District);
        intent.putExtra("DistrictAndHos",DistrictAndHos);
        intent.putExtra("DistrictHosSpeciality",DistrictHosSpeciality);
        //startActivity(intent);
        startActivity(intent);
        finish();
    }

    private void showProcessDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

    }

    public void signup(View view) {
        Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
        intent.putExtra("format",date);
        intent.putExtra("symptom",disease);
        intent.putExtra("id",doctorId);
        intent.putExtra("hospitalId",hospitalId);
        intent.putExtra("date",dates);
        intent.putExtra("time",time);
        intent.putExtra("serial",serialNo);
        intent.putExtra("confirm",confirm);
        intent.putExtra("appoinmentid",String.valueOf(id));

        intent.putExtra("description",description);
        intent.putExtra("speciality",speciality);
        intent.putExtra("education",education);
        intent.putExtra("district",district);
        intent.putExtra("hospital",hospital);
        intent.putExtra("expertise",expertise);
        intent.putExtra("idvalues",idvalues);
        intent.putExtra("name",namevalue);
        intent.putExtra("fromonlydistrict",fromonlydistrict);
        intent.putExtra("fromonlydistrictandhosptial", fromonlydistrictandhos);
        intent.putExtra("doctorlist", doctorlist);

        intent.putExtra("District",District);
        intent.putExtra("DistrictAndHos",DistrictAndHos);
        intent.putExtra("DistrictHosSpeciality",DistrictHosSpeciality);
        intent.putExtra("nodecount",nodecount);

        startActivity(intent);

        //startActivity(new Intent(this, SignupActivity.class));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(LoginActivity.this,AppoinmentBooking.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
        }
        return true;
    }
}
