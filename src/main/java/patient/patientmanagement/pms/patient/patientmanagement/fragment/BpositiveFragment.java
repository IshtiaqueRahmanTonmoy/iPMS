package patient.patientmanagement.pms.patient.patientmanagement.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import patient.patientmanagement.pms.DoctorList;
import patient.patientmanagement.pms.R;
import patient.patientmanagement.pms.adapter.SpecialistAdapter;
import patient.patientmanagement.pms.entity.DividerItemDecoration;
import patient.patientmanagement.pms.entity.RecyclerItemClickListener;
import patient.patientmanagement.pms.entity.speciality;

/**
 * Created by suraj on 23/6/17.
 */

public class BpositiveFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_bpos, container, false);



        return view;
    }
}
