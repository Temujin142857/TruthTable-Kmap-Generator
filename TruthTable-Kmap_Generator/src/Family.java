import java.util.ArrayList;

/**
 * Class represents the contents of a set of brackets.
 */

public class Family extends Operators {
    private ArrayList<Input> Inputs=new ArrayList<>();
    private ArrayList<Family> Families=new ArrayList<>();
    private ArrayList<Boolean> values;
    private String operator;
    private boolean value;

    /**
     * Forces the family to update its values, does the same to all the families referenced by this family.
     * Forces the family to solve itself, then returns its value.
     * @return the truth value of this value.
     */
    public boolean getValue() {
        values=setInternal_values();
        solve();
        return value;
    }

    /**
     * Helper method to getValue.
     * Updates the values of the families and inputs reference by this family.
     * Will force all families referenced to update their values.
     * @return A boolean arraylist with all the values in this family.
     */
    private ArrayList<Boolean> setInternal_values(){
        ArrayList<Boolean> values=new ArrayList<>();
        for (Family family:Families) {
            values.add(family.getValue());
        }
        for (Input input:Inputs) {
            values.add(input.getValue());
        }
        return values;
    }

    /**
     * Helper method to getValue.
     * Finds the appropriate truth value of this family.
     */
    private void solve() {
        System.out.println(operator);
        switch (operator){
            case "+":
                this.value= Or(values);
                break;

            case "*":
                this.value=And(values);
                break;

            case "^":
                this.value=Nor(values);
                break;

            case "&":
                this.value=Nand(values);
                break;

            case "!":
                this.value=Not(values.get(0));
                break;

            case "%":
                this.value=Xor(values);
        }
    }

    /**
     * Adds and input to the family.
     * @param member An input that is referenced in the brackets.
     */
    public void addInput(Input member) {
        Inputs.add(member);
    }

    /**
     * Adds another family to the family.
     * @param family A family that is referenced in the brackets.
     */
    public void addFamily(Family family) {
        Families.add(family);
    }

    /**
     * Sets the operator to perform.
     * @param operator The operator referenced in the brackets.
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * Getter for the operator.
     * @return
     */
    public String getOperator(){
        return operator;
    }

    /**
     * Getter for the families referenced by this family.
     * @return
     */
    public ArrayList<Family> getFamilies() {
        return Families;
    }

    /**
     * Getter for the inputs referenced by this family.
     * @return
     */
    public ArrayList<Input> getInputs() {
        return Inputs;
    }
}
