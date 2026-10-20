package com.ozagrimate.userservice;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserServiceBusinessLogic {

    private Map<String, User> users = new ConcurrentHashMap<>();

    public UserServiceBusinessLogic () {
        this.createSystemUser();
    }

    private void createSystemUser() {
        User sysUser = new User("sysuser");
        sysUser.setUserType("system");
        sysUser.setFirstName("System");
        sysUser.setLastName("User");
        sysUser.setUserActiveSW(true);
        sysUser.setEmail("admin@ozagrimate.com.au");
        this.users.put(sysUser.getUsername(), sysUser);
    }

    public User getUser(String username) throws UsernameNotFoundException{
        User user = this.users.get(username);
        if (user==null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

    public User registerUser (UserRequest userRequest) throws UsernameExistsException {
        if (this.users.get(userRequest.getUsername()) != null) {
            throw new UsernameExistsException(userRequest.getUsername());
        }
        User user = new User(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.updatePassword(userRequest.getPassword());
        user.setUserType(userRequest.getUserType());
        this.users.put(user.getUsername(), user);
        return user;
    }

    public User updateUser (String username, UserRequest userRequest) throws UsernameNotFoundException {
        User user = this.users.get(username);
        if (user==null) {
            throw new UsernameNotFoundException(username);
        }
        user.setEmail(userRequest.getEmail());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setUserType(userRequest.getUserType());
        user.updatePassword(userRequest.getPassword());
        return user;
    }

    /**
     * Because we need to keep a Username for referential integrity of transactional data (in a real database),
     * We should only purge the users details while retaining the username Key so it is not reused.
     * Also retain the userType so it can be determined what access the user had to the system previously
     * @param username
     */
    public void purgeUser (String username) throws UsernameNotFoundException {
        User user = this.users.get(username);
        if (user==null){
            throw new UsernameNotFoundException(username);
        }
        user.setUserActiveSW(false);
        user.setEmail("");
        user.setPhoneNumber("");
        user.setFirstName("");
        user.setLastName("");
        user.updatePassword("");
    }


}
