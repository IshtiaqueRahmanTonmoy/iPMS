package patient.patientmanagement.pms.patient.patientmanagement.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import patient.patientmanagement.pms.R;
/**
 * Created by suraj on 23/6/17.
 */

public class Tab2Fragment extends Fragment {

    String district,hospital,speciality;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_tab2, container, false);

        //String getArgument = getArguments().getString("data");
        //text.setText(getArgument);//set string over textview

        //Toast.makeText(getContext(), ""+getArgument, Toast.LENGTH_SHORT).show();
        return view;
    }
}
