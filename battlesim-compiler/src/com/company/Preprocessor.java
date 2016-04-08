package com.company;
import java.io.*;
import java.nio.file.Files;
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


    public Preprocessor(String path){
        this.inputPath = path;
        this.outputPath = path.substring(0, path.lastIndexOf('\\') + 1) + "tmp" + path.substring(path.lastIndexOf('\\') + 1);
    }

    public String MakeFile(){


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

                //handle include
                if(line.startsWith("#include")){
                    String includeFile = inputPath.substring(0, inputPath.lastIndexOf('\\') + 1) + line.substring(INCLUDE_STR_INDEX, line.lastIndexOf('"'));
                    Preprocessor includePP = new Preprocessor(includeFile);
                    List<String> includeLines = new ArrayList<>(includePP.ReadIncludeFile());
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

    public List<String> ReadIncludeFile(){
        List<String> lines = new ArrayList<String>();
        try(BufferedReader br = new BufferedReader(new FileReader(inputPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.startsWith("#include")){
                    String includefile = line.substring(INCLUDE_STR_INDEX, line.lastIndexOf('"') - 1);
                    Preprocessor includePP = new Preprocessor(includefile);
                    List<String> includeLines = new ArrayList<>(includePP.ReadIncludeFile());
                    for(int i = 0; i <= includeLines.size(); i++)
                        lines.add(includeLines.get(i));
                }
                else
                    lines.add(line);
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return lines;
    }

    public void RemoveOutFile(){
        boolean succes = (new File(outputPath)).delete();
        if(succes)
            System.out.println("Temporary files have been deleted");
        else
            System.out.println("Temporary files could not be deleted");
    }
}


