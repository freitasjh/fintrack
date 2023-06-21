package br.com.systec.controle.financeiro.user.v1.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class UserInputDTO implements Serializable {

    private Long id;
    private String name;
    private String federalId;
    private String email;
    private String phone;
    private String cellphone;
    private Date dateOfBirth;
    private String gender;
    private String profilePicture;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFederalId() {
        return federalId;
    }

    public void setFederalId(String federalId) {
        this.federalId = federalId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserInputDTO that = (UserInputDTO) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "UserInputDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", federalId='" + federalId + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", cellphone='" + cellphone + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender='" + gender + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                '}';
    }
}
