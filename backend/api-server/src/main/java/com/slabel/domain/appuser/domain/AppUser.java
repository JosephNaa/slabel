package com.slabel.domain.appuser.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "AppUser")
public class AppUser {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Column(unique = true)
    @NotNull
    private String username;
    @NotBlank
    private String email;
    @NotNull
    private String password;

    @Column(name = "AppUserRole")
    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole = AppUserRole.ROLE_USER;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createAt;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updateAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SaltId")
    private Salt salt;

    public AppUser(@NotBlank String name,
                   @NotBlank String username,
                   @NotBlank String email,
                   @NotBlank String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appUserRole.name());
//        return Collections.singletonList(authority);
//    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", appUserRole=" + appUserRole +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                '}';
    }
}
