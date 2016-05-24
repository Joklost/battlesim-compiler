package com.company.SyntaxAnalysis;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Magnus on 08-04-2016.
 */
public class Preprocessor {

    public static int STDLIB_LINES = 0;
    private static int INCLUDE_STR_INDEX = 10; //#include("simProtectTheGeneral.inc")
    private PrintWriter writer;
    public String inputPath;
    public String outputPath;
    public String directory;


    public Preprocessor(String path){
        this.inputPath = path;
        if(inputPath.contains("/"))
            this.directory = path.substring(0, path.lastIndexOf('/') + 1);
        else
            this.directory = path.substring(0, path.lastIndexOf('\\') + 1);

        this.outputPath = directory + "tmp" + inputPath.substring(directory.length());
    }

    public String makeFile(){
        //make output file ready
        try {
            writer = new PrintWriter(outputPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Start reading input file
        try (BufferedReader br = new BufferedReader(new FileReader(inputPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                //include handling
                if(line.startsWith("#Include")){
                    String includeFile = directory + line.substring(INCLUDE_STR_INDEX, line.lastIndexOf('"'));
                    if(includeFile == inputPath){
                        throw new Error("Unable to include file " + includeFile + " in " + inputPath);
                    }
                    Preprocessor includePP = new Preprocessor(includeFile);
                    List<String> includeLines = new ArrayList<>(includePP.readIncludeFile());
                    if(line.contains(".inj")){ //Is an injection file
                        String fileName = line.substring(INCLUDE_STR_INDEX, line.lastIndexOf('"'));
                        writer.println("%``//START INJECTION OF FILE " + fileName);
                        writer.println("");
                        STDLIB_LINES += 2;
                        for(int i = 0; i < includeLines.size(); i++){
                            writer.println("%``" + includeLines.get(i));
                            writer.println(""); // empty line between injectionlines
                            STDLIB_LINES += 2;
                        }
                        writer.println("%``//END INJECTION OF FILE " + fileName);
                        writer.println("");
                        STDLIB_LINES += 2;
                    }else{
                        for(int i = 0; i < includeLines.size(); i++){
                            writer.println(includeLines.get(i));
                        }
                    }
                }
                else{
                    writer.println(line);
                    STDLIB_LINES++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.close();
        return outputPath;
    }

    public List<String> readIncludeFile(){
        List<String> lines = new ArrayList<String>();
        try(BufferedReader br = new BufferedReader(new FileReader(inputPath))) {
            String line;
            int lineNum = 1; // brug den her til noget pls
            while ((line = br.readLine()) != null) {
                if(line.startsWith("#Include")){
                    //System.out.println("Illegal include inside " + inputPath + " on line " + lineNum + ". Include the file inside your main file. Continuing with including the file.");
                    //Uncomment if you want include files to be able to include other files
                    String includeFile = directory + line.substring(INCLUDE_STR_INDEX, line.lastIndexOf('"'));
                    Preprocessor includePP = new Preprocessor(includeFile);
                    List<String> includeLines = new ArrayList<>(includePP.readIncludeFile());
                    if(line.contains(".inj")){ //Is an injection file
                        String fileName = line.substring(INCLUDE_STR_INDEX, line.lastIndexOf('"'));
                        lines.add("%``//START INJECTION OF FILE " + fileName);
                        lines.add("");
                        STDLIB_LINES += 2;
                        for(int i = 0; i < includeLines.size(); i++){
                            lines.add("%``" + includeLines.get(i));
                            lines.add(""); // empty line between injectionlines
                            STDLIB_LINES += 2;
                        }
                        lines.add("%``//END INJECTION OF FILE " + fileName);
                        lines.add("");
                        STDLIB_LINES += 2;
                    } else{
                        for(int i = 0; i < includeLines.size(); i++){
                            lines.add(includeLines.get(i));
                        }
                    }
                }
                else{
                    lines.add(line);
                    STDLIB_LINES++;
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return lines;
    }

    public void removeOutFile(){
        boolean succes = (new File(outputPath)).delete();
        if(!succes)
            //System.out.println("Temp files have been deleted");
            System.out.println("Temp files could not be deleted");
    }
}


