package br.com.systec.controle.financeiro.administrator.tenant.model;

import br.com.systec.controle.financeiro.user.model.User;
import jakarta.persistence.*;

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

    public Tenant(User user) {
        this.name = user.getName();
        this.federalId = user.getFederalId();
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
