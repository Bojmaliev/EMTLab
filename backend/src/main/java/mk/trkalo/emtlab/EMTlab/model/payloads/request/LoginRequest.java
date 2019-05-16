package mk.trkalo.emtlab.EMTlab.model.payloads.request;


import javax.validation.constraints.NotBlank;

public class LoginRequest {
    public String email;
    public String password;
}