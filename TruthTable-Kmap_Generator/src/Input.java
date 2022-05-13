/**
 * class represents a single variable
 */
public class Input {
    private boolean value = true;
    private String name;
    private boolean inverse=false;

    /**
     * Constructor that initialises this Input with a name.
     * @param name The letter used to represent the input.
     */
    public Input(String name){
        this.name=name;
    }

    /**
     * Getter for the value of this input.
     * @return
     */
    public boolean getValue(){
        return value;
    }

    /**
     * Getter for the name of this input.
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the binary equivalent of this inputs truth value.
     * @return An int which represents this inputs truth value.
     */
    public int getBit(){
        if(value){return 1;}
        return 0;
    }

    public void setInverse(boolean inverse) {
        this.inverse = inverse;
    }

    /**
     * Setter for this inputs value.
     * @param value
     */
    public void setValue(boolean value) {
        this.value = value;
    }
}
