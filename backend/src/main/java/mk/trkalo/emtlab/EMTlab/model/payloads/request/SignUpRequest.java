package mk.trkalo.emtlab.EMTlab.model.payloads.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignUpRequest {

    public String name;

    public String email;

    public String password;

    public String matchPassword;
}