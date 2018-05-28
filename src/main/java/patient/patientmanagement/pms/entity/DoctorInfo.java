package patient.patientmanagement.pms.entity;

public class DoctorInfo {
    String doctorName;
    String idval;
    String image;
    String name;
    String education;
    String specialityName;
    String designation;
    String hospitalName;

    public DoctorInfo(String idval, String image, String doctorName, String education, String specialityName, String designation, String hospitalName) {
        this.idval = idval;
        this.image = image;
        this.doctorName = doctorName;
        this.education  = education;
        this.specialityName = specialityName;
        this.designation = designation;
        this.hospitalName = hospitalName;
    }

    public String getIdval() {
        return idval;
    }

    public void setIdval(String idval) {
        this.idval = idval;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getSpecialist() {
        return specialityName;
    }

    public void setSpecialist(String specialityName) {
        this.specialityName = specialityName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }
}
