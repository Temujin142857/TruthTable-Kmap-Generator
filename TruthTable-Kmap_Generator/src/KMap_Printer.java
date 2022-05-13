import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Class that prints the K-map to a txt file
 * Only works for up to 4 inputs
 */

public class KMap_Printer {
    private final String filename="K_Map.txt";
    private File file;
    private Writer writer;
    private ArrayList<Boolean> answers;
    private ArrayList<Input> inputs;
    private int lines=2;
    private int columns=2;

    public KMap_Printer(ArrayList<Boolean> answers, ArrayList<Input> inputs){
        this.answers=answers;
        this.inputs=inputs;
        if(inputs.size()>4){
            throw new IllegalArgumentException("Cannot print a K-map with more than 4 inputs.");
        }
        else if(inputs.size()==4){
            lines=4;
        }
        else if (inputs.size()==3||inputs.size()==4){
            columns=4;
        }
    }

     /**
     * controller method to write the .txt file
     */
    public void write(){
        build_file();
        write_header();
        write_body();
        write_divider();
    }

    /**
     * Method that writes the body of the table
     */
    private void write_body(){
        for (int i = 0; i < lines; i++) {
            write_divider();
            write_line(i);
        }

    }

    /**
     * writes the horizontal line
     */
    private void write_divider(){
        String line="\n";
        if(inputs.size()==4){line+="--";}
        for (int i = 0; i < columns; i++) {
            line+="--|--";
            if(i==columns-1){line+="--|";}
        }
        try {
            writer = new FileWriter(filename,true);
            writer.append(line);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * writes one line of the body
     * @param i The line number
     */
    private void write_line(int i){
        String line="\n";
        if(inputs.size()==2){
            if(i==0){line+="!"+inputs.get(1).getName();}
            else{line+=" "+inputs.get(1).getName();}
        }
        else if(inputs.size()==3){
            if(i==0){line+="!"+inputs.get(2).getName();}
            else{line+=" "+inputs.get(2).getName();}
        }
        else if(inputs.size()==4){
            switch (i) {
                case 0:
                    line+="!"+inputs.get(2).getName()+"!"+inputs.get(3).getName();
                    break;
                case 1:
                    line+="!"+inputs.get(2).getName()+" "+inputs.get(3).getName();
                    break;
                case 2:
                    line+=" "+inputs.get(2).getName()+" "+inputs.get(3).getName();
                    break;
                case 3:
                    line+=" "+inputs.get(2).getName()+"!"+inputs.get(3).getName();
                    break;
            }
        }

        for (int j = i * columns; j < (i * columns) + columns; j++) {
            line += "| ";
            if (answers.get(j)) {
                line += " 1 ";
            } else {
                line += " 0 ";
            }
        }

        line+="|";

        try {
            writer = new FileWriter(filename,true);
            writer.append(line);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * builds the file if it doesn't already exist
     */
    private void build_file(){
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

    public void write_header() {
        String header="";
        if(inputs.size()==4){header+="  ";}
        for (int i=0;i<columns;i++) {
            if(i==0){header+="  |";}
            if(columns==2){header+=" ";}
            if(i<columns/2)header+="!"+inputs.get(0).getName();
            else{header+=" "+inputs.get(0).getName();}
            if((i==0||i==columns-1)&&columns>2){header+="!"+inputs.get(1).getName();}
            else if(columns>2){header+=" "+inputs.get(1).getName();}
            if(columns==2){header+=" ";}
            header+="|";
        }

        try {
            writer = new FileWriter(filename);
            writer.write(header);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
