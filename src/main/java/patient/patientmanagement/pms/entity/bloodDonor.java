package patient.patientmanagement.pms.entity;

public class bloodDonor {
    private String address,bloodgroup,gender,name,phone,uid,email,password;
    int districtId,thanaId,age;

    public bloodDonor(String address, int age, String bloodgroup, int districtId, String email, String gender, String name, String password, String phone, int thanaId, String uid){
        this.address = address;
        this.age = age;
        this.bloodgroup = bloodgroup;
        this.districtId = districtId;
        this.email = email;
        this.gender = gender;
        this.name = name;
        this.password = password;

        this.phone = phone;
        this.thanaId = thanaId;
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }




    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public int getThanaId() {
        return thanaId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setThanaId(int thanaId) {
        this.thanaId = thanaId;
    }
}
