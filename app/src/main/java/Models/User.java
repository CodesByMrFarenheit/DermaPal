package Models;

public class User {

    String userEmail;
    String userName;
    String userGender;
    String userAge;
    String userPhone;

    public User() {
        //for firebase
    }

    public User(String userEmail, String userName, String userGender, String userAge, String userPhone) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.userGender = userGender;
        this.userAge = userAge;
        this.userPhone = userPhone;
    }


    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }


    @Override
    public String toString() {
        return "User{" +
                "userEmail='" + userEmail + '\'' +
                ", userName='" + userName + '\'' +
                ", userGender='" + userGender + '\'' +
                ", userAge='" + userAge + '\'' +
                ", userPhone='" + userPhone + '\'' +
                '}';
    }
}
