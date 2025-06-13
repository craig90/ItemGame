public class Stat {

    protected String type;
    protected double value;

    public Stat() {
        this.type = "";
        this.value = 0.0;
    }

    // dummy constructor for initializing with dummy data
    public Stat(String type, double value) {
        this.type = type;
        this.value = value;
    }

    // TODO: this might need to be changed or moved out of abstract class Stat
    public void setValue(double value) {
        this.value = value;
    }

    public String getStatType() {
        return this.type;
    }
    
    public double getValue() {
        return this.value;
    }

}
