package mk.trkalo.emtlab.EMTlab.model.payloads.response;

import mk.trkalo.emtlab.EMTlab.model.User;

import java.util.List;

public class SearchClientsResponse {
    public Integer numPages;
    public List<User> clients;
}
