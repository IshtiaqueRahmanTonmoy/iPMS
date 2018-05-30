package patient.patientmanagement.pms.entity;

public class Blood {
    String donorName,location,phoneNumber;

    public Blood(String donorName, String location, String phoneNumber){
        this.donorName = donorName;
        this.location = location;
        this.phoneNumber = phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDonorName() {

        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }
}
