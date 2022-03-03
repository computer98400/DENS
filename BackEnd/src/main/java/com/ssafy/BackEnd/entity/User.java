package com.ssafy.BackEnd.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@NoArgsConstructor
@Entity
@Table(name = "user")
@Getter @Setter
public class User implements UserDetails {

    @Id
    @NotBlank
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    private LocalDateTime createDate;

    @Enumerated(EnumType.STRING)
    private UserIdentity identity;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<TeamMember> team_member = new ArrayList<>( );

    @Builder
    public User(String email, String name, String password, LocalDateTime createDate, UserIdentity identity) {
        System.out.println("cons hi");
        this.email = email;
        this.name = name;
        this.password = password;
        this.createDate = createDate;
        this.identity = identity;
        System.out.println("cons hi2");
}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        List<GrantedAuthority> authorities = new ArrayList<>();
        UserIdentity userIdentity = getIdentity();
        System.out.println("auth hi");
        if(userIdentity == UserIdentity.ROLE_UNAUTH){
            authorities.add(new SimpleGrantedAuthority("ROLE_UNAUTH"));
        } else if (userIdentity == UserIdentity.ROLE_USER) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        } else if (userIdentity == UserIdentity.ROLE_MANAGER) {
            authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
        } else if (userIdentity == UserIdentity.ROLE_ADMIN) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}