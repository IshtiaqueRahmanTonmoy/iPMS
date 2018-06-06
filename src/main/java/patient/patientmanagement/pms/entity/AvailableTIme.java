package patient.patientmanagement.pms.entity;

public class AvailableTIme {
    String date,time,serial,confirm;

    public AvailableTIme(String date, String time, String serial, String confirm) {
        this.date = date;
        this.time = time;
        this.serial = serial;
        this.confirm = confirm;
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
