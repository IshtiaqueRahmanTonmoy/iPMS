package patient.patientmanagement.pms.entity;

public class Chamber {
    String idvalue;
    String chamberName;
    String chamberLocation;
    String booknow;
    String name,district,hospital,expertise,description,education,speciality;

    public Chamber(String idvalue, String chamberName, String chamberLocation, String booknow){
        this.idvalue = idvalue;
        this.chamberName = chamberName;
        this.chamberLocation = chamberLocation;
        this.booknow = booknow;

        //this.district = district;
        //this.hospital = hospital;
        //this.booknow = expertise;

    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Chamber(String idvalue, String chamberName, String chamberLocation, String booknow, String district, String hospital, String expertise, String description, String education, String speciality){
        this.idvalue = idvalue;
        this.chamberName = chamberName;
        this.chamberLocation = chamberLocation;
        this.booknow = booknow;

        this.name = name;

        this.district = district;
        this.hospital = hospital;
        this.expertise = expertise;

        this.description = description;
        this.education = education;

        this.speciality = speciality;
    }
    public String getIdvalue() {
        return idvalue;
    }

    public void setIdvalue(String idvalue) {
        this.idvalue = idvalue;
    }

    public String getBooknow() {
        return booknow;
    }

    public void setBooknow(String booknow) {
        this.booknow = booknow;
    }

    public String getChamberName() {
        return chamberName;
    }

    public void setChamberName(String chamberName) {
        this.chamberName = chamberName;
    }

    public String getChamberLocation() {
        return chamberLocation;
    }

    public void setChamberLocation(String chamberLocation) {
        this.chamberLocation = chamberLocation;
    }
}
