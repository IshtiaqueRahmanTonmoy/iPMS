package patient.patientmanagement.pms;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikepenz.iconics.context.IconicsLayoutInflater2;


import patient.patientmanagement.pms.databinding.ActivitySignupBinding;
import patient.patientmanagement.pms.entity.Utils;
import patient.patientmanagement.pms.entity.Validation;
import patient.patientmanagement.pms.entity.patientInfo;

public class SignupActivity extends AppCompatActivity {
    ActivitySignupBinding binding;
    private EditText userEdt,emailEdt,phoneEdt,genderEdt,ageEdt,bloodgroupEdt,passwordEdt;
    private Button signupBtn;
    private String value;
    private FirebaseAuth auth;
    DatabaseReference databaseUsers;
    Long tsLong;
    private ProgressDialog progressDialog;
    private String photo,email,name,phone,gender,age,bloodgroup,password,created,uid,fromonlydistrict,fromdistandhos;

    ScrollView scrollview;
    String date,disease,doctorId,hospitalId,dates,time,serialNo,confirm;
    //long id = 0;
    String id;
    ImageButton back;

    String[] AlertDialogItems = new String[]{
            "Male",
            "Female"
    };

    String[] Bloodgroups = new String[]{
            "O+",
            "O-",
            "A+",
            "A-",
            "B+",
            "B-",
            "AB+",
            "AB-"
    };


    AlertDialog alertdialogbuilder,alertdialogbuilder1;
    String description,speciality,education,district,hospital,expertise,ids,namevalue,idvalues;

    ImageButton imgback;
    private String doctorlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), new IconicsLayoutInflater2(getDelegate()));
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        databaseUsers = FirebaseDatabase.getInstance().getReference("patientInfo");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            //idvalue = extras.getString("idvalue");

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

            fromonlydistrict = extras.getString("fromonlydistrict");
            fromdistandhos = extras.getString("fromonlydistrictandhosptial");
            doctorlist = extras.getString("doctorlist");

            Log.d("extra",id);
            Log.d("value",date+"\n"+disease+"\n"+doctorId+"\n"+hospitalId+"\n"+id+"\n"+null+"\n"+serialNo+"\n"+1+"\n"+time);

            //Toast.makeText(this, ""+idvalue, Toast.LENGTH_SHORT).show();
        }

        back = (ImageButton) findViewById(R.id.et_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
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
                intent.putExtra("fromonlydistrictandhosptial", fromdistandhos);
                intent.putExtra("doctorlist", doctorlist);
                startActivity(intent);

            }
        });

        userEdt = (EditText) findViewById(R.id.et_full_name);
        emailEdt = (EditText) findViewById(R.id.et_email);

        phoneEdt = (EditText) findViewById(R.id.et_phone);
        phoneEdt.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.isPhoneNumber(phoneEdt, false);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        genderEdt = (EditText) findViewById(R.id.et_gender);
        ageEdt = (EditText) findViewById(R.id.et_age);
        bloodgroupEdt = (EditText) findViewById(R.id.et_email_bloodgroup);
        passwordEdt = (EditText) findViewById(R.id.et_password);
        scrollview = (ScrollView) findViewById(R.id.scrollview);


        genderEdt.setFocusable(false);
        bloodgroupEdt.setFocusable(false);

        genderEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);

                builder.setTitle("Select Your Gender");

                builder.setSingleChoiceItems(AlertDialogItems, -1, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {
                        String genderValue = AlertDialogItems[item];
                        genderEdt.setText(genderValue);
                        alertdialogbuilder.dismiss();
                    }
                });
                alertdialogbuilder = builder.create();
                alertdialogbuilder.show();
            }
        });

        bloodgroupEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);

                builder.setTitle("Select Your BloodGroup");

                builder.setSingleChoiceItems(Bloodgroups, -1, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {
                        String bloodValue = Bloodgroups[item];
                        bloodgroupEdt.setText(bloodValue);
                        alertdialogbuilder1.dismiss();
                    }
                });
                alertdialogbuilder1 = builder.create();
                alertdialogbuilder1.show();
            }
        });

        signupBtn = (Button) findViewById(R.id.btn_signup);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userEdt.getText().toString().trim().equalsIgnoreCase("")) {
                    userEdt.setError("This field can not be blank");
                } else if (emailEdt.getText().length() < 0) {
                    emailEdt.setError("This field can not be blank");
                }else if (phoneEdt.getText().length() < 0) {
                    phoneEdt.setError("This field can not be blank");
                }else if (ageEdt.getText().length() < 0) {
                    ageEdt.setError("This field can not be blank");
                }
                else if (passwordEdt.getText().length() < 0) {
                    Utils.showToast(SignupActivity.this, "Please input your password");
                } else {

                    confirmSignin();
                    /*
                    if ( checkValidation () )
                        confirmSignin();
                    else
                        Toast.makeText(SignupActivity.this, "Form contains error", Toast.LENGTH_LONG).show();
                   */
                }
              }
            });

        //Toast.makeText(this, ""+value, Toast.LENGTH_SHORT).show();
    }

    private void hideKeyboard(View v) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    private boolean checkValidation() {
        boolean ret = true;

        if (!Validation.isEmailAddress(emailEdt, true)) ret = false;
        if (!Validation.isPhoneNumber(phoneEdt, false)) ret = false;

        return ret;
    }

    private void confirmSignin() {
        name = userEdt.getText().toString();
        email = emailEdt.getText().toString();
        phone = phoneEdt.getText().toString();
        gender = genderEdt.getText().toString();
        age = ageEdt.getText().toString();
        bloodgroup = bloodgroupEdt.getText().toString();
        tsLong = System.currentTimeMillis() / 1000;
        created = tsLong.toString();
        password = passwordEdt.getText().toString();

        showProcessDialog();

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("task",task.getResult().toString());
                if (!task.isSuccessful()) {
                    //progressDialog.dismiss();
                    Utils.showToast(SignupActivity.this, "Register failed!");
                } else {
                    Utils.showToast(SignupActivity.this, "Register successful!");
                    FirebaseUser currentFirebaseUser = auth.getInstance().getCurrentUser() ;
                    if (currentFirebaseUser != null) {
                        uid = currentFirebaseUser.getUid();

                        addValue(age,bloodgroup,created,"null",gender,"image",name,phone,uid);
                                  /*
                                   storageReference2nd.putFile(FilePathUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            photoUrl = taskSnapshot.getDownloadUrl().toString();
                                            addValue(uid,addresstext,createdtext,deviceidtext,emailtext,gendertext,nametext,phoneNumber,photoUrl,roletext);
                                        }
                                    });
                                   */


                        Log.d("userid",uid);
                        //Toast.makeText(SigninActivity.this, ""+userId, Toast.LENGTH_SHORT).show();
                        //String userEmail = currentFirebaseUser.getEmail();
                    }

                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                    //progressDialog.dismiss();
                    finish();
                }
            }
        });
    }

    private void showProcessDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Register");
        progressDialog.setMessage("Register a new account...");
        progressDialog.show();
    }

    private void addValue(String age, String bloodgroup, String created, String disease,String gender,String image, String name, String phone, String uid) {

        patientInfo patient = new patientInfo(age,bloodgroup,created,disease,gender,image,name,phone,uid);
        databaseUsers.child(uid).setValue(patient);
    }


    public void login(View view) {
        startActivity(new Intent(this, LoginActivity.class));
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
