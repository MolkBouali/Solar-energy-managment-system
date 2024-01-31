import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SolarPanel extends Component {

    private int power; // wattage in kW
    private int size; // Size of solar panel (in square metres)
    private double efficiency;
    private List<Sensor> sensors;

    public SolarPanel(int power, int size, double efficiency) {
        this.power = power;
        this.size = size;
        this.efficiency = efficiency;
        this.sensors = new ArrayList<>();
    }

    @Override
    public String getDetails() {
        int actualOutput = generatePower();
        StringBuilder panelDetails = new StringBuilder();
        panelDetails.append("SOLAR PANEL DETAILS:\n");
        panelDetails.append("Power capacity (STC): ").append(this.getPower() * 1000).append(" W\n");
        panelDetails.append("Size: ").append(this.getSize()).append(" m*m\n");
        panelDetails.append("Actual wattage: ").append(actualOutput).append(" kW\n");
        panelDetails.append("Efficiency: ").append(this.efficiency).append(" \n");
        panelDetails.append("Owner: ").append(this.getAssociatedClient());

        return panelDetails.toString();
    }

    public int generatePower() {
        int simulatedPower = 0;
        // Get the current time of day
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        // Assume higher power generation during daytime (6 AM to 6 PM)
        if (currentHour >= 6 && currentHour < 18) {
            // Simulate power generation based on sunlight levels and other factors
            //// Solar panel output per day = Size x 1,000 x Efficiency (percentage as a
            // decimal) x Number of sun hours in the area each day
            simulatedPower = (int) (this.getSize() * 1000 * this.getEfficiency() * 12);
        }
        return simulatedPower;
    }

    // getters
    public int getPower() {
        return this.power;
    }

    private int getSize() {
        return this.size;
    }

    public double getEfficiency() {
        return this.efficiency;
    }

    // setters
    public void setSize(int size) {
        this.size = size;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public float orientationAdjustment() {
        float angleAdjustment = 0.0f;
        List<List<Float>> sumofMeasurements = new ArrayList<>(); // each element contains two values: the sum of both
                                                                 // measurements of the same coordinates and the angle
                                                                 // adjstment
        Boolean notFound = true;

        for (int j = 0; j < (sensors.size() / 2); j++) {
            int i = j + 1;
            while (i < sensors.size() | notFound) { // we consider that both types of sensors are associated with each
                                                    // (x, y) available coordinate
                if (!(sensors.get(j).getType().equals(sensors.get(i).getType()))
                        & (sensors.get(j).toString().equals(sensors.get(i).toString()))) { // if not the same type but
                                                                                           // the same coordinates
                    notFound = false;

                    sumofMeasurements.add(List.of((sensors.get(j).getMeasurement() + sensors.get(i).getMeasurement()),
                            sensors.get(j).getOrientationAngle()));

                }
                i++;
            }
        }
        float maxOfSum = sumofMeasurements.get(0).get(0); // the maximum sum value of both sensors determines in which
                                                          // direction we should rotate the solar panel based on the
                                                          // orientation angle of (x,y)

        for (int j = 1; j < sumofMeasurements.size(); j++) {
            if (sumofMeasurements.get(j).get(0) > maxOfSum) {
                maxOfSum = sumofMeasurements.get(j).get(0);
                angleAdjustment = sumofMeasurements.get(j).get(1);
            }
        }
        return (float) Math.toDegrees(angleAdjustment);

    }

}
