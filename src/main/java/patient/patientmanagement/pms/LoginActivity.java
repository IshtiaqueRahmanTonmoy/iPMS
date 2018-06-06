package patient.patientmanagement.pms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
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

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    private EditText emailEdt,passwordEdt;
    private Button loginBtn;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;
    private ProgressBar progressBar;
    private TextView signupText;
    DatabaseReference databaseUsers;
    String idvalue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), new IconicsLayoutInflater2(getDelegate()));
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            idvalue = extras.getString("idvalue");
            //Toast.makeText(this, ""+idvalue, Toast.LENGTH_SHORT).show();
        }

        emailEdt = (EditText) findViewById(R.id.et_phone);
        passwordEdt = (EditText) findViewById(R.id.et_password);
        loginBtn = (Button) findViewById(R.id.btn_login); 

        auth = FirebaseAuth.getInstance();
        databaseUsers = FirebaseDatabase.getInstance().getReference("patientInfo");


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
                                            String userId = firebaseUser.getUid();
                                            Toast.makeText(LoginActivity.this, "successfull"+idvalue, Toast.LENGTH_SHORT).show();
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
