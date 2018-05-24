package patient.patientmanagement.pms.entity;

public class speciality {
    float specialityId;
    String specialityName;

    public speciality(String specialityName) {
        this.specialityName = specialityName;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }

    public float getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(float specialityId) {
        this.specialityId = specialityId;
    }
}
