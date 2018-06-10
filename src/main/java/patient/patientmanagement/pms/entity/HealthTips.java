package patient.patientmanagement.pms.entity;

public class HealthTips {
    String id,image,heading,details,date;

    public HealthTips(String id, String image, String heading, String details, String date){
        this.id = id;

        this.image = image;
        this.heading = heading;
        this.details = details;
        this.date = date;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
