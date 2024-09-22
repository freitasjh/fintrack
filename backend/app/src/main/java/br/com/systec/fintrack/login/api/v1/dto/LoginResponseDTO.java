package br.com.systec.fintrack.login.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponseDTO {

    private String token;
    private String type;
    private String profile;
    private Long userId;
    private Long tenantId;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(String token, String type) {
        this.token = token;
        this.type = type;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }
}
