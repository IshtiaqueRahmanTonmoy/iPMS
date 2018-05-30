package patient.patientmanagement.pms.patient.patientmanagement.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import patient.patientmanagement.pms.R;

/**
 * Created by suraj on 23/6/17.
 */

public class OpositiveFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_opos, container, false);



        return view;
    }
}
