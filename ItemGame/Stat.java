public class Stat {

    // start with this for now - maybe becomes an abstract class in the future

    // type should be moved to a list of limited options
    private String type;
    private double value;

    public Stat() {
        this.type = "";
        this.value = 0.0;
    }

    // dummy constructor for initializing with dummy data
    public Stat(String type, double value) {
        this.type = type;
        this.value = value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getName() {
        return this.type;
    }
    public double getValue() {
        return this.value;
    }

}
