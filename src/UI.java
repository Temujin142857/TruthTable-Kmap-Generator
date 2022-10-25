import java.util.ArrayList;
import java.util.Scanner;

/**
 * Truth_Table_Generator --- program to solve a boolean expression, and create a txt with its truth table
 * @author Tomio Nagano, temujin142857@gmail.com
 */
public class UI {
    private String variable;
    private ArrayList<Input> inputs=new ArrayList<>();
    private ArrayList<Family> families=new ArrayList<>();
    private Family mother_family;
    private Table_Printer printer = new Table_Printer();
    private String output;


    /**
     * Controller method for the program.
     */
    public void start() {
        printer.build_file();
        String expression=take_question();
        select_table_equation(expression);
    }

    private void given_table(String input){
        Table_Reader reader=new Table_Reader();
        mother_family= reader.take_input(input);
        take_output();
        for (Input variable: reader.getInputs()) {
            inputs.add(variable);
        }
        printer.write_header(inputs,output);
        ArrayList<Integer> answers=For_all_values(input.length());
        if(take_kmap()){
            Karnough_Mapper kmap=new Karnough_Mapper(answers,inputs);
            KMap_Printer Kprinter=new KMap_Printer(kmap.getAnswers_In_Location(),inputs);
            Kprinter.write();
        }
    }

    private void given_equation(String temp_expression){
        String[] expression=temp_expression.split("");
        expression=addBrackets(expression);
        take_inputs(expression);
        Bracket_Reader handleBrackets=new Bracket_Reader(inputs);
        mother_family=handleBrackets.read(expression);
        families=handleBrackets.getFamilies();
        take_output();
        printer.write_header(inputs,output);
        ArrayList<Integer> answers=For_all_values((int) Math.pow(inputs.size(),2));
        if(take_kmap()){
            Karnough_Mapper kmap=new Karnough_Mapper(answers,inputs);
            KMap_Printer Kprinter=new KMap_Printer(kmap.getAnswers_In_Location(),inputs);
            Kprinter.write();
        }
    }

    private void select_table_equation(String input){
        if(input.charAt(0)=='1'||input.charAt(0)=='0'){
            given_table(input);
        }
        else given_equation(input);
    }

    /**
     * Asks the user for the question to be solved, and takes their input.
     * @return A String Array holding the entire line entered by the user.
     */
    private String take_question(){
        Scanner user = new Scanner(System.in);
        System.out.println("Format for equations: Write using only 1 kind of operation per bracket, ! operations does not need to be in a bracket");
        System.out.println("Operators list: +=Or, *=And, ^=Nor, &=Nand, !=Not, %=Xor");
        System.out.println("Format for truth tables: Write as a series of 1's and 0's representing the output column, no spaces");
        System.out.println("What's the problem my man?");
        String expression = user.nextLine();
        return expression;
    }

    /**
     * Surrounds the question given by the user with brackets.
     * @param expression A string array holding the expression entered by the user.
     * @return A string array holding the expression entered by the user but enclosed with brackets.
     */
    private String[] addBrackets(String[] expression){
        ArrayList<String> withBrackets=new ArrayList<>();
        withBrackets.add("(");
        boolean flag=false;
        for (String s:expression){
            if(s.equals("!")){withBrackets.add("(");}
            withBrackets.add(s);
            if(flag){withBrackets.add(")");}
            if(flag){flag=false;}
            if(s.equals("!")){flag=true;}
        }
        withBrackets.add(")");
        return withBrackets.toArray(String[]::new);
    }

    /**
     * Asks the user what order they want the inputs in, then creates the appropriate input objects, can accept null from user.
     * If user input is null uses order that inputs appear in the expression.
     * @param expression Asks the user for the question to be solved, and takes their input.
     */
    private void take_inputs(String[] expression){
        Scanner user = new Scanner(System.in);
        System.out.println("What order do you want the inputs?");
        String[] given = user.nextLine().split("");
        if(given.equals(null)||given[0].equals("")){
        find_inputs(expression);
        }
        else{
            for (String s:given) {
                inputs.add(new Input(s));
            }
        }
    }

    /**
     * Asks the user if they would like a K-Map
     * @return user's answer as a boolean
     */
    private boolean take_kmap(){
        Scanner user = new Scanner(System.in);
        System.out.println("K-map? y/n");
        String given = user.nextLine();
        if(given.equals("y")||given.equals("Y")){return true;}
        return false;
    }


    /**
     * Helper method to tale_inputs, if user provided null this method finds them in the expression
     * and makes the appropriate objects.
     * @param expression Asks the user for the question to be solved, and takes their input.
     */
    private void find_inputs(String[] expression){
        boolean added;
        for (int i = 0; i < expression.length; i++) {
            if(Character.isLetter(expression[i].charAt(0))){
                added=false;
                if (inputs.size()>0) {//makes sure this isn't the first input
                    for (int j = 0; j < inputs.size(); j++) {//goes through all the stored inputs
                        if (inputs.get(j).getName().equals(expression[i])) {//makes sure this input wasn't already added
                            added=true;
                        }
                    }
                    if(!added){
                        inputs.add(new Input(String.valueOf(expression[i])));
                    }
                }
                else {
                    inputs.add(new Input(String.valueOf(expression[i])));
                }

            }
           /* if(expression[i].equals("!")){
                added=false;
                if (inputs.size()>0) {//makes sure this isnt the first input
                    for (int j = 0; j < inputs.size(); j++) {//goes through all the stored inputs
                        if (inputs.get(j).getName().equals(expression[i+1])) {//makes sure this input wasn't already added
                            added=true;
                        }
                    }
                    if(!added){
                        inputs.add(new Input(String.valueOf(expression[i+1])));
                        inputs.get(inputs.size()-1).setInverse(true);
                    }
                }
                else {
                    inputs.add(new Input(String.valueOf(expression[i+1])));
                    inputs.get(inputs.size()-1).setInverse(true);
                }
            }*/
        }
    }

    /**
     * Asks the user what the output is called, can accept null from the user.
     * If the user gives null, uses "output".
     */
    private void take_output(){
        Scanner user = new Scanner(System.in);
        System.out.println("What do you want the output to be called?");
        output=user.nextLine();
        if (output.equals(null)||output.equals("")){
            output="output";
        }
    }

    /**
     * Cycles through every combination of values.
     * Finds the output for each unique combination of values, stores it, and sends it to the printer class
     * @return A boolean arraylist containing all the possible outputs
     */
    private ArrayList<Integer> For_all_values(int entries){
        ArrayList<Integer> answers=new ArrayList<>();
        int numRows=(int) Math.pow(2,inputs.size());

        for (int i = 1; i < numRows+1; i++) {
            for (Input input:inputs) {
                System.out.println("Input "+ input.getName() +" value: "+input.getValue());
            }
            if(i>entries){answers.add(2);}
            else if(mother_family.getValue()){answers.add(1);}
            else {answers.add(0);}
            System.out.println("Result: "+answers.get(answers.size()-1));
            System.out.println("");
            printer.write_line(inputs,answers.get(answers.size()-1));

            for (int j = 0; j < inputs.size(); j++) {
                if (i%Math.pow(2,j)==0){
                    inputs.get(inputs.size()-1-j).setValue(!inputs.get(inputs.size()-1-j).getValue());
                }
                //flips the value of a boolean at the appropriate interval
            }
        }
        return answers;
    }
}
