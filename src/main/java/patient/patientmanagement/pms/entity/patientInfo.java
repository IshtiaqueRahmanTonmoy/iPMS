package patient.patientmanagement.pms.entity;

public class patientInfo {
    String image;
    String created;
    String name;
    String phone;
    String gender;
    String age;
    String disease;
    String bloodgroup;
    String uid;
    String email;
    String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public patientInfo(String age, String bloodgroup,String password, String created, String disease, String email, String gender, String image, String name, String phone, String uid){

        this.age = age;
        this.bloodgroup = bloodgroup;
        this.password = password;
        this.created = created;
        this.disease = disease;
        this.email = email;
        this.gender = gender;
        this.image = image;
        this.name = name;
        this.phone = phone;
        this.uid = uid;

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
