package it.uniroma3.icr.instagramConfig;

import org.jinstagram.entity.users.basicinfo.UserInfo;
import org.jinstagram.entity.users.basicinfo.UserInfoData;

import java.io.Serializable;

public class UserInstagram implements Serializable {

    private String bio;
    private String name;
    private String username;
    private String surname;
    private String fullName;
    private Long id;
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public UserInstagram(UserInfo userInfo) {
        super();
        UserInfoData data = userInfo.getData();
        bio = data.getBio();
        name = data.getFirstName();
        username = data.getUsername();
        surname = data.getLastName();
        fullName = data.getFullName();
        id = Long.valueOf(data.getId());
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
