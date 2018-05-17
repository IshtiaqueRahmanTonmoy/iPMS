package patient.patientmanagement.pms;

import android.view.View;

/**
 * Created by TONMOYPC on 12/19/2017.
 */

public interface ClickListener{
    public void onClick(View view, int position);
    public void onLongClick(View view, int position);
}