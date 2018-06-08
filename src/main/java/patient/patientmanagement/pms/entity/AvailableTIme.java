package patient.patientmanagement.pms.entity;

import android.widget.Toast;

public class AvailableTIme {
    String date;
    String time;
    String serial;
    String confirm;
    String format;
    String symptoms;
    String id;
    String hospitalId;

    public AvailableTIme(String format, String symptoms, String id, String hospitalId, long aid, String aNull,String serial,String status,String time,String confirm) {

        this.format = format;
        this.symptoms = symptoms;
        this.id = id;
        this.hospitalId = hospitalId;
        this.aid = aid;

        this.date = date;

        this.serial = serial;
        this.status = status;
        this.time = time;
        this.confirm = confirm;

        //Toast.makeText(, "", Toast.LENGTH_SHORT).show();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String status;
    long aid;



    
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public long getAid() {
        return aid;
    }

    public void setAid(long aid) {
        this.aid = aid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }
}
