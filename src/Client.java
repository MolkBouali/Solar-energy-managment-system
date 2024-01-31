import java.util.ArrayList;
import java.util.List;

public class Client extends EnergyManager {
    static int nbrOfClients = 0;
    private int id;
    private String status; // "buyer" or "seller" or "member"
    private float capacityofEnergy; // kW
    private EnergyManager manager;
    public static List<Client> allClients = new ArrayList<>();
    private float amountofEnergyForTrade; // kW

    public Client(String location, String status) {
        super(location);
        this.id = nbrOfClients++;
        statusValidation(status);
        this.status = status;
        this.managed_collection = new ArrayList<>();
        this.manager = locationToManagerMap.get(location); // we consider one EnergyManager per location
        if (manager == null) {
            System.out.println("There is no manager found for your location !");
        }
        allClients.add(this);
    }

    // getters
    // getLocation() method is inherited

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public EnergyManager getManager() {
        return this.manager;
    }

    public float getTotalCapacity() {
        this.capacityofEnergy = calculateTotalCapacity();
        return this.capacityofEnergy;
    }

    public float getEnergyTrade() {
        return this.amountofEnergyForTrade;
    }

    public List<Component> getClientComponents() {
        return this.managed_collection;
    }

    // setters
    public void setLocation(String location) {
        this.location = location;
    }

    public void setStatus(String status) {
        if (status.equals("seller") && managed_collection.isEmpty()) {
            throw new IllegalArgumentException("Seller clients must have at least one component");
        } else
            this.status = status;
    }

    public void setEnergyTrade(float amountofEnergyForTrade) {
        this.amountofEnergyForTrade = amountofEnergyForTrade;
    }

    private void setTotalCapacity(float remainingCapacity) {
        this.capacityofEnergy = remainingCapacity;

    }

    // when client adds/removes a component its associated EnergyManager instance
    // adds/removes that component too from its managed list

    public void addComponentByClient(Component component) {
        managed_collection.add(component);
        component.setAssociatedClient(this);
        component.setLocation(this.location);
        System.out.println(component + " added to " + component.getAssociatedClient() + "'s components");
        this.getManager().addComponent(component);
    }

    public void removeComponentByClient(Component component) {
        managed_collection.remove(component);
        component.setAssociatedClient(null);
        component.setLocation(null);
        System.out.println(component + " removed from " + component.getAssociatedClient() + "'s components");
        this.getManager().removeComponent(component);
    }

    public void buyEnergy(int energyToBuy, Client client) { // if a buyer client wants solar energy from a seller client
        if (this.getStatus() != "buyer") {
            System.out.println(this + " should be a buyer to buy !");
        }
        if (client.getStatus() != "seller") {
            System.out.println(client + " is not a seller !");
        } else if (client.getEnergyTrade() < energyToBuy) {
            System.out.println("The seller has lesser than wanted !");
        } else {

            client.setTotalCapacity(client.getTotalCapacity() - energyToBuy); // remove the amount of energy of the
            // seller from its total capicity
            this.setTotalCapacity(getTotalCapacity() + energyToBuy); // add the amount of energy to buy to the total
                                                                     // capacity

            System.out.println("Successful transaction !");
        }
    }

    public void sellEnergy(int energyToSell, Client client) { // if a client sells solar energy to a buyer client
        if (client.getStatus() != "buyer") {
            System.out.println("You are not dealing with a buyer here !");
        } else if (client.getEnergyTrade() > energyToSell) {
            System.out.println("The buyer wants more than you have !");
        } else {
            client.setTotalCapacity(client.getTotalCapacity() + energyToSell); // add the amount of energy to sell to
                                                                               // the
                                                                               // buyer total capacity
            this.setTotalCapacity(getTotalCapacity() - energyToSell); // remove the amount of energy of the seller from
                                                                      // its total capicity

            System.out.println("Successful transaction !");
        }
    }

    public String getDetails() {
        StringBuilder detailsOfClient = new StringBuilder();
        detailsOfClient.append("Client ID: ").append(this.getId()).append("\n");
        detailsOfClient.append("Location: ").append(this.getLocation()).append("\n");
        detailsOfClient.append("Total capacity of batteries: ").append(this.getTotalCapacity()).append(" kWh\n");
        detailsOfClient.append("Status: ").append(this.getStatus()).append(" \n");
        // Get details of components
        List<Component> clientComponents = this.getClientComponents();
        if (!clientComponents.isEmpty()) {
            detailsOfClient.append("Number of components: ").append(clientComponents.size() + "\n");
            detailsOfClient.append("The components:\n");
            for (Component component : managed_collection) {
                detailsOfClient.append("- ").append(component.getDetails() + "\n");
            }
        } else {
            detailsOfClient.append("No Components !");
        }

        return detailsOfClient.toString();
    }

    private void statusValidation(String status) {
        if (!((status.equals("member")) || (status.equals("buyer")))) { // "seller" not included because there is no
                                                                        // owned components when a client instance is
                                                                        // created
            throw new IllegalArgumentException("There is no valid client status");
        }
    }

    // getting all clients sharing the same location
    public static List<Client> getClientsByLocation(String location) {
        List<Client> filteredClients = new ArrayList<>();
        for (Client client : allClients) {
            if (client.getLocation().equals(location)) {
                filteredClients.add(client);
            }
        }
        return filteredClients;
    }

}
