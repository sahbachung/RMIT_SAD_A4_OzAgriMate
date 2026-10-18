package com.ozagrimate.userservice;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User {

    private final String username;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String userType;
    private String password;
    private boolean userActiveSW;

    public User(String username) {
        this.username = username;
        this.userActiveSW = true;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
    public String getUserType() {
        return this.userType;
    }

    public void updatePassword(String password) {
        this.password = password;
    }
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return this.email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getFirstName() {
        return this.firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getLastName() {
        return this.lastName;
    }

    public String getName() {
        String name = "";
        if (this.lastName!=null) {
            name += this.lastName;
        }
        if (this.firstName!=null && this.lastName !=null) {
            name += ", ";
            name += this.firstName;
        }
        else if (this.lastName != null) {
            name += this.lastName;
        }
        return name;
    }

    public UserResponse generateUserResponseDTO () {
        return new UserResponse(this);
    }

    @Override
    public String toString() {
        return "User {" +
                "username: " + this.getUsername() +
                ", userType: " + this.getUserType() +
                ", userActiveSW: " + this.getUserActiveSW() +
                ", email: " + this.getEmail() +
                ", name: " + this.getName() +
                "}";
    }

    public boolean getUserActiveSW() {
        return userActiveSW;
    }

    public void setUserActiveSW(boolean userActiveSW) {
        this.userActiveSW = userActiveSW;
    }
}
