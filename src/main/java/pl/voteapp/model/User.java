package pl.voteapp.model;

import com.google.firebase.auth.UserRecord;

import javax.persistence.*;

@Entity
@Table(name="USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean active;
    private String city;
    private String email;
    private String personalIdNumber;
    private String phoneNumber;
    private String mobileNumber;
    private String name;
    private String surname;
    private String street;
    private String userType;

    public User() {
    }

    public User(UserRecord userRecord) {
        this.email = userRecord.getEmail();
        this.name = userRecord.getDisplayName();
        this.mobileNumber = userRecord.getPhoneNumber();
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getPersonalIdNumber() {
        return personalIdNumber;
    }

    public void setPersonalIdNumber(String personalIdNumber) {
        this.personalIdNumber = personalIdNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        String string = (name + " " + email + " " + userType);
        return string;
    }
}
