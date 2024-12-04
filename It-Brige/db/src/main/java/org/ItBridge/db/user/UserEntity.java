package org.ItBridge.db.user;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.ItBridge.db.BaseEntity;
import org.ItBridge.db.auth.AuthorityEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name="user")
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends BaseEntity implements UserDetails {
    @Column(length = 50,nullable = false)
    private String name;

    @Column(length = 50,nullable = false)
    private String email;

    @Column(length = 100,nullable = false)
    private String password;

    @Column(length = 20,nullable = false)
    private String phone;

    private LocalDateTime date;
    private LocalDateTime created_at;

    private LocalDateTime updated_at;
    @ManyToOne
    @JoinColumn(name = "auth_id", nullable = false)
    private AuthorityEntity authority;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + authority.getAuthLevel()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
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
