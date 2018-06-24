package patient.patientmanagement.pms.adapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import patient.patientmanagement.pms.patient.patientmanagement.fragment.Tab1Fragment;
import patient.patientmanagement.pms.patient.patientmanagement.fragment.Tab2Fragment;
import patient.patientmanagement.pms.patient.patientmanagement.fragment.Tab3Fragment;

/**
 * Created by suraj on 23/6/17.
 */

public class Pager extends FragmentStatePagerAdapter {

    int tabCount;
    String hospitalname;

    public Pager(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }


    @Override
    public Fragment getItem(int position) {
        switch(position){
                case 0 :
                Tab1Fragment tab1Fragment=new Tab1Fragment();
                return tab1Fragment;

                case 1 :
                Tab3Fragment tab3Fragment=new Tab3Fragment();
                return tab3Fragment;
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
