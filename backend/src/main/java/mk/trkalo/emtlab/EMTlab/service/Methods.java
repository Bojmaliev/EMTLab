package mk.trkalo.emtlab.EMTlab.service;

import java.util.Random;

public class Methods {

    public static String generateRandomString(int length){
        Random r = new Random();
        String mozni = "1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
        char [] chars = new char[length];
        for(int i=0; i<chars.length; i++) chars[i] = mozni.charAt(r.nextInt(length));
        return new String(chars);
    }
    public static boolean isPasswordStrong(String strong){
        return strong.matches("(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$");
    }
    public static boolean isValidEmail(String email){
        return email.matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$");
    }
}
