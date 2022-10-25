import java.util.ArrayList;

public class Table_Reader {
    //logic:
    //input in the form of a string of 1's and 0's, which represents the output column of the truth table
    //the rest can be assumed
    //names default to letter's of the alphabet
    //set up equation
    Input[] inputs;
    final String[] names={"a","b","c","d","e","f","g","h"};

    /**
     * takes the raw input of 1's and 0's, turns it into a series of lines each holding the inputs that are true, and the
     * overall true/false value
     * Adding together these lines gives us the unsimplified equation
     * @param data hi
     */
    public Family take_input(String data){
        Family mother = new Family();
        int numInputs= (int) Math.ceil(Math.log(data.length())/Math.log(2));
        make_inputs(numInputs);
        int i=0;
        for (char c:data.toCharArray()) {
            if(c=='1') {
                mother.addFamily(make_line(i,numInputs));
            }
            i++;
        }
        mother.setOperator("+");
        return mother;
    }



    private void make_inputs(int numInputs){
        inputs=new Input[numInputs];
        for (int i = 0; i < numInputs; i++) {
            inputs[i]=new Input(names[i]);
        }
    }
    
    private Family make_line(int num, int numInputs){
        ArrayList<Input> actives=new ArrayList<>();
        ArrayList<Family> inactives=new ArrayList<>();
        for (int i = 0; i < numInputs; i++) {
            System.out.println("i= "+i);
            System.out.println("if= "+(Math.floor(num/(Math.pow(2,i)))%2));
             if(Math.floor(num/(Math.pow(2,i)))%2==1){actives.add(inputs[numInputs-1-i]);
                 //3/2^0 %2=1
                 //3/2^1 %2=1
                 System.out.println("yes "+inputs[i].getName());}
             else{inactives.add(new Family(new Input[]{inputs[numInputs-1-i]},"!"));
                 System.out.println("no "+inputs[i].getName());}
             //each variable flips on a power of 2, if it's flipped an even amount of times it is true

        }
        Family equation_of_line=new Family(actives.toArray(Input[]::new),"*");
        for (Family family:inactives) {
            equation_of_line.addFamily(family);

        }

        return equation_of_line;
    }

    public Input[] getInputs(){
        return inputs;
    }

}
