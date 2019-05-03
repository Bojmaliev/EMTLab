package mk.trkalo.emtlab.EMTlab.model;


import mk.trkalo.emtlab.EMTlab.model.exceptions.InvalidEmailException;
import mk.trkalo.emtlab.EMTlab.model.exceptions.ShortNameException;
import mk.trkalo.emtlab.EMTlab.model.exceptions.UserActivationCodeNotFound;
import mk.trkalo.emtlab.EMTlab.model.exceptions.WeakPasswordException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    public String firstLastName;
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
        roleList.add(Role.USER);
        activationCode = randomStringGenerator(50);
    }
    public static User createUser(String firstLastName, String email, String password) throws InvalidEmailException, WeakPasswordException, ShortNameException {
        if(!isValidEmail(email)) throw new InvalidEmailException();
        if(!isPasswordStrong(password)) throw new WeakPasswordException();
        if(firstLastName.length() < 3) throw new ShortNameException();
        User user = new User();
        user.password = password;
        user.email = email;
        user.firstLastName = firstLastName;
        return user;
    }
    public static boolean isPasswordStrong(String strong){
        return strong.matches("(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$");
    }
    public static boolean isValidEmail(String email){
        return email.matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$");
    }
    public static String randomStringGenerator(int length){
        Random r = new Random();
        String mozni = "1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
        char [] chars = new char[length];
        for(int i=0; i<chars.length; i++) chars[i] = mozni.charAt(r.nextInt(length));
        return new String(chars);
    }
    public void activateUser(String activationCode) throws UserActivationCodeNotFound {
        if(this.activationCode.equals(activationCode)){
            activated = true;
            this.activationCode = null;
        }else throw new UserActivationCodeNotFound();
    }


}
