package com.ozagrimate.userservice;

/**
 * We use a separate DTO for UserResponse to stanitise the User.password attribute from being returned via the user-service.
 * Because this is generated downstream from updates to a user, all fields in the UserResponse object should be immutable.
 * Attributes are final and only getters are provided
 */
public class UserResponse {

    private final String username;
    private final String email;
    private final String phoneNumber;
    private final String firstName;
    private final String lastName;
    private final String userType;

    /**
     *
     * @param user The User object to generate the UserResponseDTO from
     */
    public UserResponse(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.userType = user.getUserType();
    }


    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserType() {
        return userType;
    }
}
