import java.util.ArrayList;

public class Karnough_Mapper {
    private ArrayList<Boolean> Answers_In_Location=new ArrayList<>();
    ArrayList<Input> inputs;

    public Karnough_Mapper(ArrayList<Boolean> answers,ArrayList<Input> inputs){
        this.inputs=inputs;
        if(inputs.size()==2){
            for (int i = 0; i < answers.size(); i++) {
                Answers_In_Location.add(answers.get(i));
            }
        }
        else if(inputs.size()==3||inputs.size()==4) {
            for (int i = 0; i < answers.size(); i++) {
                System.out.println(i);
                if (i % 4 == 0 || i % 4 == 1) {
                    Answers_In_Location.add(answers.get(i));
                }
                else if (i % 4 == 2) {
                    Answers_In_Location.add(answers.get(i+1));
                }
                else if (i % 4 == 3) {
                    Answers_In_Location.add(answers.get(i-1));
                }
            }
        }
        else{
            System.out.println("Too many inputs for a K-Map.");
            throw new IllegalArgumentException();
        }
    }

    public ArrayList<Boolean> getAnswers_In_Location() {
        return Answers_In_Location;
    }
}
