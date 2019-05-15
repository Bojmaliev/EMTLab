package mk.trkalo.emtlab.EMTlab.security;

import mk.trkalo.emtlab.EMTlab.model.Role;
import mk.trkalo.emtlab.EMTlab.model.User;
import mk.trkalo.emtlab.EMTlab.repository.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User not found with username or email : " + email));
        if(!user.activated) throw new Error("User not activated");
        return  new UserPrincipal(user);
    }
    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return new UserPrincipal(user);
    }
}
