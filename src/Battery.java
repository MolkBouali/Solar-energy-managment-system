public class Battery extends Component {
    private int capacity; // kW
    private float stateOfBattery; // State of charge in percentage
    private int maxCapacity; // the maximum capacity the battery can be charged with in kW

    // constructor
    public Battery(int capacity, float stateOfBattery) {
        // Ensure the capacity is non-negative
        if (capacity >= 0) {
            this.capacity = capacity;
        } else {
            throw new IllegalArgumentException("Capacity must be non-negative.");
        }
        // Ensure the stateOfBattery is within the valid range [0, 100]
        if (stateOfBattery >= 0 && stateOfBattery <= 100) {
            this.stateOfBattery = stateOfBattery;
        } else
            throw new IllegalArgumentException("The state of battery is not within the valid range [0, 100] ! ");
        this.maxCapacity = (int) (this.capacity / this.stateOfBattery * 100);
    }

    @Override
    public String getDetails() {

        StringBuilder batteryDetails = new StringBuilder();
        batteryDetails.append("Battery Details:\n");
        batteryDetails.append("Capacity: ").append(this.getCapacity()).append(" kW\n");
        batteryDetails.append("State of Battery: ").append(this.getStateOfBattery()).append("%\n");
        batteryDetails.append("Max Capacity: ").append(this.getMaxCapacity()).append(" kW\n");
        batteryDetails.append("Owner: ").append(this.getAssociatedClient());

        return batteryDetails.toString();
    }

    // getters
    public int getCapacity() {
        return this.capacity;
    }

    public float getStateOfBattery() {
        return stateOfBattery;
    }

    public int getMaxCapacity() {
        return this.maxCapacity;
    }

    // setters
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void setStateOfBattery(float stateOfBattery) {
        // Ensure that stateOfBattery is within the valid range [0, 100]
        if (stateOfBattery >= 0 && stateOfBattery <= 100) {
            this.stateOfBattery = stateOfBattery;
        } else
            throw new IllegalArgumentException("The state of battery is not within the valid range [0, 100] ! ");
    }

    // check if the battery can provide energy
    public boolean canProvideEnergy(int amount) {
        return this.getCapacity() >= amount;
    }

    // Simulate battery charging
    public void charge(int powerToCharge) {
        // Check if the battery is already fully charged or insufficient capacity
        if (this.getStateOfBattery() >= 100.0) {
            System.out.println(this + " is already fully charged !");
        } else {
            this.capacity += powerToCharge;
            // Update state of battery after charging
            this.stateOfBattery = this.capacity / this.maxCapacity * 100;
            // Print modified state of battery after charging
            int remainingCapacity = capacity - maxCapacity;
            if (remainingCapacity > 0) {
                System.out.println("100% charged battery ! " + remainingCapacity + "kW not charged power.");
            } else
                System.out.println("State of battery after charging: " + this.getStateOfBattery() + "%");
        }

    }

    // Simulate battery discharging
    public void discharge(int powerToDischarge) {
        if (!this.canProvideEnergy(powerToDischarge)) {
            System.out.println("Insufficient energy to discharge !");
        } else {
            this.capacity -= powerToDischarge;
            // Update state of battery after discharging
            this.stateOfBattery = this.capacity / this.maxCapacity * 100;
            // Print modified capacity after time of discharging and its state of battery
            System.out.println(
                    this + " after discharging: \n" + this.capacity + "kW of capacity  \n"
                            + this.stateOfBattery + "% state of battery ");
        }
    }

}

