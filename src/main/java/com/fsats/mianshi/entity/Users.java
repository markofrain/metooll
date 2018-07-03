package com.fsats.mianshi.entity;

import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Pattern;
import java.util.Date;

@Component("users")
public class Users {

    public Users() {
    }

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private Integer id;

    @NotBlank(message = "{username.not.blank}")
    @Length(min = 6, max = 20, message = "{username.not.length}")
    private String username;
    @NotBlank(message = "{password.not.blank}")
    @Length(min = 6, max = 20, message = "{password.not.length}")
    private String password;
    @Email(message = "{email.not.format}")
    private String email;

    private Date dateOfBirth;

    private String realName;
    @Pattern(regexp = "^1[3|4|5|7|8][0-9]{9}$", message = "{phone.not.pattern}")
    private String phone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", realName='" + realName + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
