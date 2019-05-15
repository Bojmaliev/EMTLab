package mk.trkalo.emtlab.EMTlab.model.payloads.response;

public class ApiResponse {
    public Boolean error;
    public String message;

    public ApiResponse(String message) {
        this.error=false;
        this.message=message;
    }
}