public class DogProfile {
    String name, breed, gender, spayedNeutered, shotDate, bio;
    int age;

    public static void main(String[] args) {

    }

    public void setname(String name) {
        this.name = name;
    }

    public void setbreed(String breed) {
        this.breed = breed;
    }
    public void setgender(String gender) {
        this.gender = gender;
    }
    public void setspayedNeutered(String spayedNeutered) {
        this.spayedNeutered = spayedNeutered;
    }
    public void setshotDate(String shotDate) {
        this.shotDate = shotDate;
    }
    public void setbio(String bio) {
        this.bio = bio;
    }
    public void setage(int age) {
        this.age = age;
    }
    public String getname() {
        return this.name;
    }

    public String getbreed() {
        return this.breed;
    }
    public String getgender() {
        return this.gender;
    }
    public String getspayedNeutered() {
        return this.spayedNeutered;
    }
    public String getshotDate() {
        return this.shotDate;
    }
    public String getbio() {
        return this.bio;
    }
    public int getage() {
        return this.age;
    }
}