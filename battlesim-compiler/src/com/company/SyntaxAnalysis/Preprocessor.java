package com.company.SyntaxAnalysis;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Magnus on 08-04-2016.
 */
public class Preprocessor {

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
                    for(int i = 0; i < includeLines.size(); i++)
                        writer.println(includeLines.get(i));
                }
                else
                    writer.println(line);
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
            int lineNum = 1;
            while ((line = br.readLine()) != null) {
                if(line.startsWith("#Include")){
                    //System.out.println("Illegal include inside " + inputPath + " on line " + lineNum + ". Include the file inside your main file. Continuing with including the file.");
                    //Uncomment if you want include files to be able to include other files
                    String includeFile = directory + line.substring(INCLUDE_STR_INDEX, line.lastIndexOf('"'));
                    Preprocessor includePP = new Preprocessor(includeFile);
                    List<String> includeLines = new ArrayList<>(includePP.readIncludeFile());
                    for(int i = 0; i < includeLines.size(); i++)
                        lines.add(includeLines.get(i));
                    lineNum++;
                }
                else{
                    lines.add(line);
                    lineNum++;
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


