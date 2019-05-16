package mk.trkalo.emtlab.EMTlab.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import mk.trkalo.emtlab.EMTlab.model.Branch;
import mk.trkalo.emtlab.EMTlab.model.Role;
import mk.trkalo.emtlab.EMTlab.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {

    public Long id;

    public String name;

    private String email;

    @JsonIgnore
    private String password;

    public Branch branch;

    Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(User user) {
        id = user.id;
        email =  user.email;
        name = user.name;
        password = user.password;
        List<GrantedAuthority> auths = new ArrayList<>();
        branch=user.branch;
        auths.add(new SimpleGrantedAuthority(user.role.toString()));
        authorities = auths;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
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
