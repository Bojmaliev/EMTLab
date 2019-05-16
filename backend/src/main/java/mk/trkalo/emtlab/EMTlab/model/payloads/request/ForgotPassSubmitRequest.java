package mk.trkalo.emtlab.EMTlab.model.payloads.request;

public class ForgotPassSubmitRequest {
    public Long userId;
    public String token;
    public String password;
    public String matchPassword;
}
