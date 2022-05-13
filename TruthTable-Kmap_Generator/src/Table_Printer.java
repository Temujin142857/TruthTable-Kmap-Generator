import java.util.ArrayList;
import java.io.File;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.FileWriter;

/**
 * Prints the truth table onto a .txt files.
 */
public class Table_Printer {
    private final String filename="Truth_Table.txt";
    private File file;
    private FileWriter writer;

    /**
     * Writes the header of the truth table.
     * @param inputs An arraylist of all the inputs used in the expression.
     * @param output Name of the output.
     */
    public void write_header(ArrayList<Input> inputs,String output) {
        String header="";
        for (int i=0;i<inputs.size();i++) {
            header+=inputs.get(i).getName();
            header+=" | ";
        }
        header+=output;

        try {
            writer = new FileWriter(filename);
            writer.write(header);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints one line of the truth table.
     * @param inputs An arraylist of all the inputs used in the expression.
     * @param output Name of the output.
     */
    public void write_line(ArrayList<Input> inputs,boolean output) {
        String line= "\n";
        for (int i=0;i< inputs.size();i++) {
            line+=inputs.get(i).getBit();
            line+=" | ";
        }
        if(output){line+=1;}
        else{line+=0;}
        try{
        writer = new FileWriter(filename,true);
        writer.append(line);
        writer.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Builds a new file if one has not already been mate.
     */
    public void build_file(){
        try {
            file = new File(filename);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
