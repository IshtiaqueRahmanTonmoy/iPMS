package patient.patientmanagement.pms.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import patient.patientmanagement.pms.patient.patientmanagement.fragment.ABnegativeFragment;
import patient.patientmanagement.pms.patient.patientmanagement.fragment.ABpositiveFragment;
import patient.patientmanagement.pms.patient.patientmanagement.fragment.AnegativeFragment;
import patient.patientmanagement.pms.patient.patientmanagement.fragment.ApositiveFragment;
import patient.patientmanagement.pms.patient.patientmanagement.fragment.BnegativeFragment;
import patient.patientmanagement.pms.patient.patientmanagement.fragment.BpositiveFragment;
import patient.patientmanagement.pms.patient.patientmanagement.fragment.OnegativeFragment;
import patient.patientmanagement.pms.patient.patientmanagement.fragment.OpositiveFragment;
import patient.patientmanagement.pms.patient.patientmanagement.fragment.Tab1Fragment;
import patient.patientmanagement.pms.patient.patientmanagement.fragment.Tab2Fragment;
import patient.patientmanagement.pms.patient.patientmanagement.fragment.Tab3Fragment;

/**
 * Created by suraj on 23/6/17.
 */

public class PagerBlood extends FragmentStatePagerAdapter {

    int tabCount;

    public PagerBlood(FragmentManager fm, int tabCount){
        super(fm);
        this.tabCount=tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0 :
                ApositiveFragment tab1Fragment=new ApositiveFragment();
                return tab1Fragment;
            case 1 :
                AnegativeFragment tab2Fragment=new AnegativeFragment();
                return tab2Fragment;
            case 2 :
                BpositiveFragment tab3Fragment=new BpositiveFragment();
                return tab3Fragment;
            case 3 :
                BnegativeFragment tab4Fragment=new BnegativeFragment();
                return tab4Fragment;
            case 4 :
                ABpositiveFragment tab5Fragment=new ABpositiveFragment();
                return tab5Fragment;
            case 5 :
                ABnegativeFragment tab6Fragment=new ABnegativeFragment();
                return tab6Fragment;
            case 6 :
                OpositiveFragment tab7Fragment=new OpositiveFragment();
                return tab7Fragment;
            case 7 :
                OnegativeFragment tab8Fragment=new OnegativeFragment();
                return tab8Fragment;


            default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
