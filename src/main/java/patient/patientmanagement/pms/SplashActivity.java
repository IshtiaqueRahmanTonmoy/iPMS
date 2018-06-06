package patient.patientmanagement.pms;

import android.app.ActionBar;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import patient.patientmanagement.pms.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {
    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
    }


   public void  login(View view)
    {
        startActivity(new Intent(this,LoginActivity.class));
    }


    public void  getStarted(View view)
    {
        startActivity(new Intent(this,SignupActivity.class));
    }

}
