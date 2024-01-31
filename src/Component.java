
public abstract class Component {

    private Client associatedClient; // we consider every component has an owner (client)
    private String location;

    public abstract String getDetails();

    public Client getAssociatedClient() {
        return this.associatedClient;
    }

    public void setAssociatedClient(Client client) {
        this.associatedClient = client;
    }

    public String getLocation() {
        return this.location;
    }

    protected void setLocation(String location) {
        this.location = location;
    }

}

