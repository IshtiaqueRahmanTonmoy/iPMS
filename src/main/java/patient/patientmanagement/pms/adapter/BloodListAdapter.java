package patient.patientmanagement.pms.adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import patient.patientmanagement.pms.R;
import patient.patientmanagement.pms.entity.Blood;
import patient.patientmanagement.pms.entity.DoctorInfo;
import patient.patientmanagement.pms.entity.Hospital;

public class BloodListAdapter extends RecyclerView.Adapter<BloodListAdapter.MyViewHolder> {
    private List<Blood> bloodList;
    Context context;
    private ArrayList<Blood> arraylist;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView id, name, address, phone, callnow;

        public MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.image);
            name = (TextView) view.findViewById(R.id.name);
            address = (TextView) view.findViewById(R.id.address);
            phone = (TextView) view.findViewById(R.id.phone);
            callnow = (TextView) view.findViewById(R.id.callnow);
        }
    }


    public BloodListAdapter(Context context, List<Blood> bloodList) {
        this.bloodList = bloodList;
        Log.d("list", String.valueOf(bloodList.size()));

        this.context = context;
        PhoneCallListener phoneListener = new PhoneCallListener();
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        this.arraylist = new ArrayList<Blood>();
        this.arraylist.addAll(bloodList);
        //Toast.makeText(context, ""+specialistList.size(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public BloodListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bloodlist_row, parent, false);

        return new BloodListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BloodListAdapter.MyViewHolder holder, int position) {
        final Blood blood = bloodList.get(position);
        //holder.image.setImageDrawable(R.drawable.ic_user);

        //holder.id.setText();
        holder.name.setText(blood.getDonorName());
        holder.address.setText(blood.getLocation());
        holder.phone.setText(blood.getPhoneNumber());

        holder.callnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = blood.getPhoneNumber().toString();
                //Toast.makeText(context, "" + phone, Toast.LENGTH_SHORT).show();
                String phoneNumber = String.format("tel: %s", phone);
                // Create the intent.
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                // Set the data for the intent as the phone number.
                dialIntent.setData(Uri.parse(phoneNumber));
                // If package resolves to an app, send intent.
                if (dialIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(dialIntent);
                } else {
                    Log.e("intent", "Can't resolve app for ACTION_DIAL Intent.");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return bloodList.size();
    }

    private class PhoneCallListener extends PhoneStateListener {

        private boolean isPhoneCalling = false;

        String LOG_TAG = "LOGGING 123";

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if (TelephonyManager.CALL_STATE_RINGING == state) {
                // phone ringing
                Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
            }

            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                // active
                Log.i(LOG_TAG, "OFFHOOK");

                isPhoneCalling = true;
            }

            if (TelephonyManager.CALL_STATE_IDLE == state) {
                // run when class initial and phone call ended,
                // need detect flag from CALL_STATE_OFFHOOK
                Log.i(LOG_TAG, "IDLE");

                if (isPhoneCalling) {

                    Log.i(LOG_TAG, "restart app");

                    // restart app
                    Intent i = context.getApplicationContext().getPackageManager()
                            .getLaunchIntentForPackage(
                                    context.getApplicationContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i);

                    isPhoneCalling = false;
                }

            }
        }
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        bloodList.clear();
        if (charText.length() == 0) {
            bloodList.addAll(arraylist);
        } else {
            for (Blood wp : arraylist) {
                if (wp.getLocation().toLowerCase(Locale.getDefault()).contains(charText) || wp.getDonorName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    bloodList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
