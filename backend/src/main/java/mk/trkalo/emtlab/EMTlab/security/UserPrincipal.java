package mk.trkalo.emtlab.EMTlab.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    public String email;

    @JsonIgnore
    private String password;

    Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(User byEmail) {
        id = byEmail.id;
        email =  byEmail.email;
        name = byEmail.name;
        password = byEmail.password;
        List<GrantedAuthority> auths = new ArrayList<>();

        for(Role role : byEmail.roleList){
            auths.add(new SimpleGrantedAuthority(role.toString()));

        }
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
