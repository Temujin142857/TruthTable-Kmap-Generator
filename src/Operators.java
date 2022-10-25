import java.util.ArrayList;

/**
 * Class hold all the operations and their symbols.
 */
public class Operators {
    public final String[] Operations={"+","*","^","&","!","%"};//list of operators
    // +=Or, *=And, ^=Nor, &=Nand, !=Not, %=Xor


    public boolean Or(ArrayList<Boolean> values){
        for (Boolean value:values) {
            if (value){return true;}
        }
        return false;
    }

    public boolean And(ArrayList<Boolean> values){
        for (Boolean value:values) {
            if (!value){return false;}
        }
        return true;
    }

    public boolean Nor(ArrayList<Boolean> values){
        return !Or(values);
    }

    public boolean Nand(ArrayList<Boolean> values){
        return !And(values);
    }

    public boolean Not(Input variable){
        return !variable.getValue();
    }

    public boolean Not(boolean value){
        return !value;
    }

    public boolean Xor(ArrayList<Boolean> values){
        boolean result=false;
        for (Boolean value:values) {
            if (value&&result){return false;}
            if (value){result=true;}
        }
        return result;
    }



}
