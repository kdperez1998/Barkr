public class HumanProfile {
    String name, gender, location, phoneNumber, email, bio;
    int age;

    public static void main(String[] args) {

    }

    public void setname(String name) {
        this.name = name;
    }

    public void setgender(String gender) {
        this.gender = gender;
    }
    public void setlocation(String location) {
        this.location = location;
    }
    public void setphoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setemail(String email) {
        this.email = email;
    }
    public void setbio(String bio) {
        this.bio = bio;
    }
    public void setbreed(int age) {
        this.age = age;
    }
    public String getname() {
        return this.name;
    }

    public String getgender() {
       return this.gender;
    }
    public String getlocation() {
       return this.location;
    }
    public String getphoneNumber() {
        return this.phoneNumber;
    }
    public String getemail() {
       return this.email;
    }
    public String getbio() {
        return this.bio;
    }
    public int getage() {
       return this.age;
    }
}