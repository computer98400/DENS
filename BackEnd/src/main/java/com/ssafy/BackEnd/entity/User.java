package com.ssafy.BackEnd.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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

//    @Column(name = "identity")
//    @Enumerated(EnumType.STRING)
    //@ElementCollection(fetch = FetchType.EAGER)
    //@Builder.Default
    @Enumerated(EnumType.STRING)
    private UserIdentity identity;

//    @ElementCollection(fetch = FetchType.EAGER)
//    @Builder.Default
//    private List<String> roles = new ArrayList<>();
    //private List<UserIdentity> identity = new ArrayList<>();

//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "salt_id")
//    private Salt salt;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id")
    //@JsonIgnore
    private Profile profile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    //@JsonIgnore
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
//            Collection<UserIdentity> userIdentities) {
//            List<GrantedAuthority> authorities = new ArrayList<>();
//
//            for(UserIdentity identity : userIdentities){
//                authorities.add(new SimpleGrantedAuthority(identity.name()));
//                identity.
//            }
//        return this.identity.stream()
//                .map(SimpleGrantedAuthority::new)
//                .collect(UserIdentity);
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