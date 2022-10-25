import java.util.ArrayList;

public class Bracket_Reader {
    private ArrayList<Input> variables;
    private ArrayList<Family> families=new ArrayList<>();
    private Operators reference=new Operators();
    private Family mother_family;

    /**
     * Constructor that initialises this class with the inputs that appear in the expression.
     * @param variables
     */
    public Bracket_Reader(ArrayList<Input> variables){
        this.variables=variables;
    }

    /**
     * Turns the expression into a single family that can be solved using the families internal method.
     * @param expression The expression given by the user, enclosed in brackets.
     * @return  The final family that represents the entire expression.
     */
    public Family read(String[] expression){
        for (String s:expression) {
            System.out.print(s);
        }
        System.out.println("");
        int opening_bracket=0;
        for (int i = 0; i < expression.length; i++) {
            if (expression[i].equals("(")){
                System.out.println("hi");
                opening_bracket=i;
            }
            else if(expression[i].equals(")")){
                expression=checkEnd(expression,opening_bracket,i);
                return read(expression);
            }
        }
        System.out.println("hi2");
        mother_family=families.get(families.size()-1);
        return mother_family;
    }

    /**
     * Helper method for read, replaces an expression inside of brackets with a family.
     * @param expression The expression given by the user enclosed in brackets.
     * @param opening_bracket The location of the relevant "(" in array expression.
     * @param closing_bracket The location of the relevant ")" in array expression.
     * @return A string array identical to expression, except the part enclosed in the relevant brackets
     * has been replaced with a family.
     */
    private String[] checkEnd(String[] expression, int opening_bracket, int closing_bracket){
        String[] replacement=new String[2];
        families.add(new Family());
        replacement[0]="$";//$ is symbol for family
        replacement[1]=String.valueOf(families.size()-1);
        for (int j=opening_bracket;j<closing_bracket;j++){//loops between the ( and the )
            for (String symbol: reference.Operations) {//loops through all the operator symbols and saves the correct one
                if (expression[j].equals(symbol)){
                    families.get(families.size()-1).setOperator(symbol);
                }
            }
            if (Character.isLetter(expression[j].charAt(0))){//finds the variables
                for (Input variable:variables) {//loops through all the variables we have named
                    if (variable.getName().equals(String.valueOf(expression[j]))){//finds the variable with the correct name
                        families.get(families.size()-1).addInput(variable);
                    }
                }
            }
            if (expression[j].equals("$")){//finds the family
                families.get(families.size()-1).addFamily(families.get(Integer.valueOf(expression[j+1])));
            }
        }
        return removeFamily(expression,replacement,opening_bracket,closing_bracket);//replaces the family in the char[] with the correct symbol
    }

    /**
     * Helper method for checkEnd.
     * Removes the expression found between start and end, then replaces it with the family name replacement.
     * @param expression The expression given by the user enclosed in brackets.
     * @param replacement String array holding the name of the family to be added to expression.
     * @param start Location of the start of the expression to be replaced.
     * @param end Location of the end of the expression to be replaced.
     * @return A string array identical to expression but with the relevant portion replaced with the replacement string array.
     */
    private String[] removeFamily(String[] expression,String[] replacement,int start, int end){
        ArrayList<String> result=new ArrayList<>();//initialises the array to return
        for (int i = 0; i < expression.length; i++) {//loops through the original array
            if (i<start){result.add(expression[i]);}//adds all the characters before the part to replace
            else if(i==start){result.add(replacement[0]);}//adds the $
            else if(i==start+1){result.add(replacement[1]);}//adds the family number
            else if(i>end){result.add(expression[i]);}//adds all the characters after the family being replaced
        }


        return result.toArray(String[]::new);//returns a String[] version
    }

    /**
     * Getter for families.
     * @return
     */
    public ArrayList<Family> getFamilies() {
        return families;
    }

    /**
     * Getter for variables.
     * @return
     */
    public ArrayList<Input> getVariables() {
        return variables;
    }
}
