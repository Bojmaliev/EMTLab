package mk.trkalo.emtlab.EMTlab.model;


import mk.trkalo.emtlab.EMTlab.model.exceptions.*;
import mk.trkalo.emtlab.EMTlab.service.Methods;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.lang.Error;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotNull
    public String name;
    @NotNull
    @Column(unique=true)
    public String email;
    @NotNull
    public String password;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Role.class)
    public List<Role> roleList = new ArrayList<>();

    public boolean activated = false;

    public LocalDateTime registeredOn;

    public String activationCode;
    public User(){
        registeredOn= LocalDateTime.now();
        roleList.add(Role.ROLE_USER);
        activationCode = Methods.generateRandomString(50);
    }
    public static User createUser(String name, String email, String password)  {
        if(!Methods.isValidEmail(email)) throw new Error("Invalid email");

        User user = new User();
        user.password = password;
        user.email = email;
        user.name = name;
        return user;
    }

    public void activateUser(String activationCode) {
        if(activated)throw new Error("User already activated");
        if(activationCode.equals(this.activationCode)){
            activated = true;
            this.activationCode = null;
        }else throw new Error("Activation code not found");
    }


    public void setPasswordViaRequest(String password, String token) {
        if(activationCode == null || !activationCode.equals(token)) throw new Error("Invalid token!");
        this.activationCode = null;
        this.password=password;
    }
}
