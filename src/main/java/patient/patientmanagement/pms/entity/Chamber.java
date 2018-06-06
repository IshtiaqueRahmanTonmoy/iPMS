package patient.patientmanagement.pms.entity;

public class Chamber {
    String idvalue;
    String chamberName;
    String chamberLocation;
    String booknow;

    public Chamber(String idvalue, String chamberName, String chamberLocation, String booknow){
        this.idvalue = idvalue;
        this.chamberName = chamberName;
        this.chamberLocation = chamberLocation;
        this.booknow = booknow;

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
