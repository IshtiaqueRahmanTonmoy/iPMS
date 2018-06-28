package patient.patientmanagement.pms.adapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import patient.patientmanagement.pms.patient.patientmanagement.fragment.Tab1Fragment;
import patient.patientmanagement.pms.patient.patientmanagement.fragment.Tab2Fragment;
import patient.patientmanagement.pms.patient.patientmanagement.fragment.Tab3Fragment;

/**
 * Created by suraj on 23/6/17.
 */

public class Pager extends FragmentStatePagerAdapter {

    int tabCount;
    String hospitalname;

    public Pager(FragmentManager fm, int tabCount, String hospitalname) {
        super(fm);
        this.tabCount = tabCount;
        this.hospitalname = hospitalname;
        //Log.d("hospitalName",hospitalname);
    }


    @Override
    public Fragment getItem(int position) {
        switch(position){
                case 0 :
                    Tab1Fragment tab1Fragment=new Tab1Fragment();
                    return tab1Fragment;

                case 1 :
                    Bundle bundle = new Bundle();
                    bundle.putString("hospital", hospitalname);
// set MyFragment Arguments
                    Tab3Fragment tab3Fragment=new Tab3Fragment();
                    tab3Fragment.setArguments(bundle);

                    return tab3Fragment;
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
