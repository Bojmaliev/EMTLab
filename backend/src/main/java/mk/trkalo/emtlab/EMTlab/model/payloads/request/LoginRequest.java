package mk.trkalo.emtlab.EMTlab.model.payloads.request;


import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    public String email;
    @NotBlank
    public String password;
}