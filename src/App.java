import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {

        // Create battery and solar panel objects
        Battery battery1 = new Battery(10, 90);
        Battery battery2 = new Battery(8, 20);
        SolarPanel panel1 = new SolarPanel(5, 10, 0.85);
        SolarPanel panel2 = new SolarPanel(3, 5, 0.7);

        // create clients
        Client Sami = new Client("Tunis", "member");
        Client Hattab = new Client("Tunis", "buyer");
        Client Salah = new Client("Tunis", "buyer");
        Client Salwa = new Client("Tunis", "buyer");

        // display all clients and their total number
        System.out.println(
                "The total number of clients is " + Integer.toString(Client.nbrOfClients) + "\n" + Client.allClients);

        // client add panel1 and battery1 as components
        Sami.addComponentByClient(panel1);
        Sami.addComponentByClient(battery1);
        Hattab.addComponentByClient(panel2);
        Salwa.addComponentByClient(battery2);

        // display a battery details
        System.out.println(battery1 + "details: \n" + battery1.getDetails());
        // display a battery capacity
        System.out.println(battery1 + "capacity: " + battery1.getCapacity());

        // display a solar panel details
        System.out.println(panel1 + "details: \n" + panel1.getDetails());
        // display a solar power generated power
        System.out.println(panel2 + "generated power: " + panel2.generatePower());

        // Create an EnergyManager instance
        EnergyManager manager = new EnergyManager("Tunis");

        SolarPanel panel4 = new SolarPanel(10, 18, 0.95);
        // Add components to the manager
        manager.addComponent(panel4);
        manager.removeComponent(panel2);
        manager.addComponent(battery1);
        manager.addComponent(battery2);

        // display total capacity in the managed list of components of the manager
        System.out.println("The total capacity of all the batteries managed by the " + manager + " is : "
                + manager.calculateTotalCapacity());

        // Display managed components
        System.out.println("Components with associated clients managed in " + manager.getLocation() + " : ");
        System.out.println(manager.getAllComponents());

        // panel2 owner information
        System.out.println(panel2 + " owner is " + panel2.getAssociatedClient());

        // list of components of a client
        System.out.println("The list of components of " + Hattab + " is : \n" + Hattab.getClientComponents());

        // the client Sami details
        System.out.println("The client " + Sami + " : \n" + Sami.getDetails());

        // display solar energy system clients in Tunis
        System.out.println("All clients in Tunis : " + Client.getClientsByLocation("Tunis"));

        // display buyers in "manager" location (Tunis)
        System.out.println("All clients " + manager.getAllClients());

        System.out.println("Here are the clients wanting to buy their solar energy : \n" +
                manager.getClientsByStatus("buyer"));

        // Sami wants to be a seller
        Sami.setStatus("seller");

        // display sellers in "manager" location (Tunis)
        System.out.println(manager.getClientsByStatus("seller"));

        // client buy and sell energy
        Sami.setEnergyTrade(5);
        System.out.println(Sami + " 's total capacity of batteries " + Sami.getTotalCapacity()); // 10
        System.out.println(Salwa + " 's total capacity of batteries " + Salwa.getTotalCapacity()); // 8
        Sami.buyEnergy(2, Salah);
        Salwa.buyEnergy(10, Sami);
        Salwa.buyEnergy(3, Sami);
        System.out.println(Sami + " 's total capacity of batteries " + Sami.getTotalCapacity()); // 10 - 3 = 7
        System.out.println(Salwa + " 's total capacity of batteries " + Salwa.getTotalCapacity()); // 8+ 3 = 11

        // Battery1 charging
        System.out.println("The state of charge of " + battery1 + " is " + battery1.getStateOfBattery() + "% ");
        battery1.charge(5);
        battery1.charge(5);
        battery1.discharge(2);

        // Battery2 discharging
        System.out.println(
                "The state of charge of " + battery2 + " is " + battery2.getStateOfBattery() + "% ");
        battery2.discharge(10);
        battery2.discharge(2);

        // create sensors
        Sensor sensortemp1 = new Sensor("temperature", 1, 1, 28);
        Sensor sensorlight1 = new Sensor("light", 1, 1, 15);
        Sensor sensortemp2 = new Sensor("temperature", 1, 0, 26);
        Sensor sensorlight2 = new Sensor("light", 1, 0, 19);

        // associate the sensors to the solar panel
        List<Sensor> panel1sensors = new ArrayList<>();
        panel1sensors.add(sensorlight1);
        panel1sensors.add(sensorlight2);
        panel1sensors.add(sensortemp2);
        panel1sensors.add(sensortemp1);

        panel1.setSensors(panel1sensors);

        // the panel get oriented to a direction where there is more light and
        // temperature (based on sensors)
        // display the angle of orientation of panel1
        System.out.println(panel1.toString() + " should be oriented " + panel1.orientationAdjustment() + " degrees");

    }

}