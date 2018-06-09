package patient.patientmanagement.pms.entity;

public class appoinmentSchedule {
    String date,disease,doctorId,patientId,time;
    int hospitalId,serialNo,status,id;

    public appoinmentSchedule(String date, String disease, String doctorId, int hospitalId, int id, String patientId, int serialNo, int status, String time) {
         this.date = date;
         this.disease = disease;
         this.doctorId = doctorId;
         this.hospitalId = hospitalId;
         this.id = id;
         this.patientId = patientId;
         this.serialNo = serialNo;
         this.status = status;
         this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
