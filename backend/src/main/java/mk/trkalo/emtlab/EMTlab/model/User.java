package mk.trkalo.emtlab.EMTlab.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import mk.trkalo.emtlab.EMTlab.service.Methods;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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
    @JsonIgnore
    public String password;

    @Enumerated(EnumType.STRING)
    public Role role = Role.ROLE_USER;

    @ManyToOne
    public Branch branch;

    public Boolean activated = false;

    public LocalDateTime registeredOn;

    public String activationCode;
    public User(){
        registeredOn= LocalDateTime.now();
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

    public void setPassword(String validatedPassword) {
        this.password=validatedPassword;
    }
}
