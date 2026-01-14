package com.rural.sports.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String role; // VILLAGER, ORGANIZER, ADMIN
    private String status; // PENDING, APPROVED, REJECTED
    
    @JsonProperty("realName")
    private String realName;
    
    @JsonProperty("villageName")
    private String villageName;
    
    @JsonProperty("phone")
    private String contactInfo;
    
    @JsonProperty("exercisePref")
    private String sportPreference;

    private String organizationCertificate;
    private String responsibleArea;

    @Override
    @com.fasterxml.jackson.annotation.JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    @Override
    @com.fasterxml.jackson.annotation.JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @com.fasterxml.jackson.annotation.JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @com.fasterxml.jackson.annotation.JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @com.fasterxml.jackson.annotation.JsonIgnore
    public boolean isEnabled() {
        return "APPROVED".equals(this.status);
    }
    
    @com.fasterxml.jackson.annotation.JsonIgnore
    public String getPassword() {
        return password;
    }
    
    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }
}
