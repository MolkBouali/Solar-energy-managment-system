public class Sensor {

    private String type; // two types : light sensor and temperature sensor
    private float x; // sensor Cartesian coordinate along the x-axis
    private float y; // sensor Cartesian coordinate along the y-axis
    private float measurement;

    // constructor

    public Sensor(String type, float x, float y, float measurement) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.measurement = measurement;
    }

    // getters
    public String getType() {
        return this.type;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getMeasurement() {
        return this.measurement;
    }

    public float getOrientationAngle() {
        return (float) Math.atan2(this.x, this.y);
    }

    // setters
    public void setType(String type) {
        this.type = type;
    }

    public void setX(float X) {
        this.x = X;
    }

    public void setY(float Y) {
        this.y = Y;
    }

    public void setMeasurement(float value) {
        this.measurement = value;
    }

    public String toString() {
        return "(" + this.x +
                " , " + this.y +
                ")";
    }

}

