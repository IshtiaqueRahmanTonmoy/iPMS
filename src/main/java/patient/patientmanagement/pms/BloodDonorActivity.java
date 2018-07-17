package patient.patientmanagement.pms;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import patient.patientmanagement.pms.entity.Utils;
import patient.patientmanagement.pms.entity.Validation;
import patient.patientmanagement.pms.entity.bloodDonor;

public class BloodDonorActivity extends AppCompatActivity {

    private EditText addressEdt,emailEdt,ageEdt,bloodgroupEdt,districtEdt,thanaEdt,genderEdt,nameEdt,phoneEdt,passwordEdt,confirmpassEdt;
    private Button submitBtn;
    private FirebaseAuth auth;
    private DatabaseReference databaseUsers;
    private String uid,address,email,age,bloodgroup,district,thana,gender,name,phone,password,confirm;
    private ProgressDialog progressDialog;
    private List<String> itemsDistrict,itemsThana;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    AlertDialog alertDialog1,alertDialog2;
    String districtid,thanaid;
    int districtId,thanaId,agevalue;
    DatabaseReference myRef = database.getReference("district");
    DatabaseReference myRefThana = database.getReference("thana");

    AlertDialog alertdialogbuilder,alertdialogbuilder1;

    String[] AlertDialogItems = new String[]{
            "Male",
            "Female"
    };

    boolean[] Selectedtruefalse = new boolean[]{
            false,
            false
    };

    int value;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String VALUE = "valueKey";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    String emailval;
    int valueid;



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

    boolean[] Selectedtruefalseblood = new boolean[]{
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false
    };
    List<String> ItemsIntoList,ItemsIntoList1,DisList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_donor);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        valueid = sharedpreferences.getInt(VALUE, 0);


        itemsDistrict = new ArrayList<>();
        itemsThana = new ArrayList<>();

        auth = FirebaseAuth.getInstance();
        databaseUsers = FirebaseDatabase.getInstance().getReference("bloodDonor");

        addressEdt = (EditText) findViewById(R.id.addressEdt);
        emailEdt = (EditText) findViewById(R.id.emailEdt);
        ageEdt = (EditText) findViewById(R.id.ageEdt);
        bloodgroupEdt = (EditText) findViewById(R.id.bloodgroupEdt);
        districtEdt = (EditText) findViewById(R.id.districtEdt);
        thanaEdt = (EditText) findViewById(R.id.thanaEdt);
        genderEdt = (EditText) findViewById(R.id.genderEdt);
        nameEdt = (EditText) findViewById(R.id.nameEdt);
        phoneEdt = (EditText) findViewById(R.id.phoneEdt);

        passwordEdt = (EditText) findViewById(R.id.passwordEdt);
        confirmpassEdt = (EditText) findViewById(R.id.confirmpasswordEdt);

        genderEdt.setFocusable(false);
        bloodgroupEdt.setFocusable(false);

        genderEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BloodDonorActivity.this);

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
                AlertDialog.Builder builder = new AlertDialog.Builder(BloodDonorActivity.this);

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



        districtEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDistrict();
            }
        });

        submitBtn = (Button) findViewById(R.id.saveBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameEdt.getText().toString().trim().equalsIgnoreCase("")) {
                    nameEdt.setError("This field can not be blank");
                } else if (emailEdt.getText().toString().trim().equalsIgnoreCase("")) {
                    emailEdt.setError("This field can not be blank");
                }else if (addressEdt.getText().toString().trim().equalsIgnoreCase("")) {
                    addressEdt.setError("This field can not be blank");
                }else if (ageEdt.getText().toString().trim().equalsIgnoreCase("")) {
                    ageEdt.setError("This field can not be blank");
                }else if (phoneEdt.getText().toString().trim().equalsIgnoreCase("")) {
                    phoneEdt.setError("This field can not be blank");
                }
                  else if (passwordEdt.getText().toString().trim().equalsIgnoreCase("")) {
                    passwordEdt.setError("This field can not be blank");

                }
                else if (confirmpassEdt.getText().toString().trim().equalsIgnoreCase("")) {
                    confirmpassEdt.setError("This field can not be blank");

                }else {
                    confirmSignin(valueid);
                    /*
                    if ( checkValidation () )
                        confirmSignin(valueid);
                    else
                        Toast.makeText(BloodDonorActivity.this, "Form contains error", Toast.LENGTH_LONG).show();
                    */
                    }
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

        //Toast.makeText(this, ""+idvalrecong, Toast.LENGTH_SHORT).show();

        if(id == android.R.id.home){

            Intent intent = new Intent(BloodDonorActivity.this,DashboardActivity.class);
            intent.putExtra("district",district);
            //intent.putExtra("hospital",hospital);
            //intent.putExtra("doctorlist",doctorlist);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }

    private void getDistrict() {
        itemsDistrict.clear();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot zoneSnapshot: dataSnapshot.getChildren()) {
                    Log.i("specialityName", zoneSnapshot.child("districtName").getValue(String.class));
                    String districtName = zoneSnapshot.child("districtName").getValue(String.class);
                    itemsDistrict.add(districtName);
                }

                final String namesdistrict[]=itemsDistrict.toArray(new String[itemsDistrict.size()]);
                final AlertDialog.Builder builder = new AlertDialog.Builder(BloodDonorActivity.this);

                builder.setTitle("Select Your Choice");

                builder.setSingleChoiceItems(namesdistrict, -1, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {

                        String districtname = namesdistrict[item];
                        districtEdt.setText(districtname);
                        getThana(districtEdt.getText().toString());
                        /*
                        Toast.makeText(
                                BloodDonorActivity.this,
                                "You Clicked : " + namesdistrict[item],
                                Toast.LENGTH_SHORT
                        ).show();
                        */
                        itemsDistrict.clear();
                        alertDialog1.dismiss();

                    }
                });
                alertDialog1 = builder.create();
                alertDialog1.show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Log.w(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

    private void getThana(final String title) {
        itemsThana.clear();
        thanaEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.orderByChild("districtName").equalTo(String.valueOf(title)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                            districtid = String.valueOf(childDataSnapshot.child("districtId").getValue());
                            value(districtid);
                        }
                        //Toast.makeText(context, ""+dataSnapshot, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
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
                    Log.d("vals", thanaName);
                    itemsThana.add(thanaName);
                    //value(id);
                }

                final String namesthana[]=itemsThana.toArray(new String[itemsThana.size()]);
                AlertDialog.Builder builder = new AlertDialog.Builder(BloodDonorActivity.this);

                builder.setTitle("Select Your Choice");

                builder.setSingleChoiceItems(namesthana, -1, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {

                        String thananame = namesthana[item];
                        thanaEdt.setText(thananame);
                        getThanaid(thananame);

                        /*
                        Toast.makeText(
                                BloodDonorActivity.this,
                                "You Clicked : " + namesdistrict[item],
                                Toast.LENGTH_SHORT
                        ).show();
                        */
                        itemsThana.clear();
                        alertDialog2.dismiss();
                    }
                });
                alertDialog2 = builder.create();
                alertDialog2.show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getThanaid(String thananame) {

        myRefThana.orderByChild("thanaName").equalTo(String.valueOf(thananame)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    thanaid = String.valueOf(childDataSnapshot.child("thanaId").getValue());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void confirmSignin(int value) {

        value = value + 1;

        emailval = "noemail"+value+"@gmail.com";
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        editor.putInt(VALUE,value);
        editor.apply();

        //Toast.makeText(this, ""+thanaid, Toast.LENGTH_SHORT).show();
        address = addressEdt.getText().toString();
        email = emailEdt.getText().toString();
        age = ageEdt.getText().toString();
        bloodgroup = bloodgroupEdt.getText().toString();
        district = districtEdt.getText().toString();
        thana = thanaEdt.getText().toString();
        gender = genderEdt.getText().toString();
        name = nameEdt.getText().toString();
        phone = phoneEdt.getText().toString();
        password = passwordEdt.getText().toString();

        agevalue = Integer.parseInt(age);
        districtId = Integer.parseInt(districtid);
        thanaId = Integer.parseInt(thanaid);

        if(emailEdt.getText().length() == 0){
            email = emailval;
        }
        else {
            email = emailEdt.getText().toString();
        }
        showProcessDialog();

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(BloodDonorActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("task",task.getResult().toString());
                if (!task.isSuccessful()) {
                    //progressDialog.dismiss();
                    Utils.showToast(BloodDonorActivity.this, "Register failed!");
                } else {
                    Utils.showToast(BloodDonorActivity.this, "Register successful!");
                    FirebaseUser currentFirebaseUser = auth.getInstance().getCurrentUser() ;
                    if (currentFirebaseUser != null) {
                        uid = currentFirebaseUser.getUid();

                        addValue(address,agevalue,bloodgroup,districtId,gender,name,phone,thanaId,uid);
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

                    startActivity(new Intent(BloodDonorActivity.this, DashboardActivity.class));
                    //progressDialog.dismiss();
                    finish();
                }
            }
        });
    }

    private void addValue(String address, int age, String bloodgroup, int districtId, String gender, String name, String phone, int thanaId, String uid) {
       bloodDonor blooddonor = new bloodDonor(address,age,bloodgroup,districtId,gender,name,phone,thanaId,uid);
        databaseUsers.child(uid).setValue(blooddonor);
    }

    private void showProcessDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Register");
        progressDialog.setMessage("Register a new blood donor...");
        progressDialog.show();
    }

    private boolean checkValidation() {
        boolean ret = true;

        if (!Validation.isEmailAddress(emailEdt, false)) ret = false;
        if (!Validation.isPhoneNumber(phoneEdt, false)) ret = false;

        return ret;
    }
}

