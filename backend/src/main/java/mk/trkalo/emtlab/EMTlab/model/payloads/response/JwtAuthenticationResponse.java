package mk.trkalo.emtlab.EMTlab.model.payloads.response;


public class JwtAuthenticationResponse {
    public Boolean error=false;
    public String message = "Login successful!";
    public String accessToken;
    public String tokenType = "Bearer";

    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
