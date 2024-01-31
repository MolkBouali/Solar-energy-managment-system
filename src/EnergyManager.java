import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EnergyManager {
    protected String location;
    protected List<Component> managed_collection; // list of components sharing the same location
    protected Map<String, EnergyManager> locationToManagerMap;

    public EnergyManager(String location) {
        this.location = location;
        this.managed_collection = new ArrayList<>();
        this.locationToManagerMap = new HashMap<>();
        locationToManagerMap.put(location, this); // we consider one EnergyManager per location
    }

    public String getLocation() {
        return location;
    }

    // make sure component's location is the same as the EnergyManager instance
    // loction and that this component doesn't already exist in the managed list
    public void addComponent(Component component) {
        if (component.getAssociatedClient() == null) {
            System.out.println(component + " does not have an owner ! Added successfully to the manager list !");
            managed_collection.add(component);
            component.setLocation(this.location);
        } else {
            String l = component.getAssociatedClient().getLocation();
            if ((l == this.location) && (!managed_collection.contains(component))) {
                managed_collection.add(component);
                System.out.println(component + " added successfully to the manager list !");
            } else {
                System.out.println(component + " is not under the control of " + this + " !");
            }
        }
    }

    public void removeComponent(Component component) { // no remove without add so component location is already checked
        if (managed_collection.contains(component)) { // checking if the component does exist in the managed list
            if (component.getAssociatedClient() == null) {
                component.setLocation(null);
            }
            managed_collection.remove(component);
            System.out.println(component + " removed successfully from the manager list !");
        } else {
            System.out.println(component + " Remove failed ! It is not found in the managed list ! ");
        }
    }

    public int calculateTotalCapacity() {
        int TotalBatteriesCapacity = 0;
        for (Component c : this.managed_collection) {
            if (c instanceof Battery) {
                TotalBatteriesCapacity += ((Battery) c).getCapacity();
            }
        }
        return TotalBatteriesCapacity;
    }

    // getting all the managed components and its number
    public String getAllComponents() {
        StringBuilder allComponents = new StringBuilder();
        for (Component c : this.managed_collection) {
            allComponents.append(c).append(" - ").append(c.getAssociatedClient()).append("\n");
        }
        return (allComponents.toString() + "The total number of components is " + managed_collection.size());
    }

    public Set<Client> getAllClients() { // all clients managed by an EnergyManager instance

        Set<Client> clientNotRepeated = new HashSet<>();
        for (Component c : this.managed_collection) {
            Client associatedClient = c.getAssociatedClient();
            if (associatedClient != null) {
                clientNotRepeated.add(associatedClient);
            }
        }
        return clientNotRepeated;

    }

    public List<Client> getClientsByStatus(String status) {
        List<Client> filteredClients = new ArrayList<>();
        if (getAllClients().isEmpty()) {
            System.out.println("There is no clients' components managed here ! ");
        } else {
            for (Client client : this.getAllClients()) {
                if (client.getStatus().equals(status)) {
                    filteredClients.add(client);
                }
            }
            System.out.println(
                    "The number of " + status + " clients in " + this.location + " is " + filteredClients.size()
                            + " :");
        }
        return filteredClients;
    }

}
