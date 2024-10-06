package br.com.systec.fintrack.tenant.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tenant")
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "federal_id")
    private String federalId;
    @Column(name = "user_principal_id")
    private Long userPrincipalId;

    public Tenant() {
    }

    public Tenant(String name, String federalId) {
        this.name = name;
        this.federalId = federalId;
    }

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

    public Long getUserPrincipalId() {
        return userPrincipalId;
    }

    public void setUserPrincipalId(Long userPrincipalId) {
        this.userPrincipalId = userPrincipalId;
    }
}
