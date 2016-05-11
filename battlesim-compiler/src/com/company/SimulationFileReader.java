package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Magnus on 25-04-2016.
 */
public class SimulationFileReader {
    public static Map<String, List<String>> getDST() throws IOException {
        Map res = new HashMap<String, List<String>>();
        File currentDirFile = new File("");
        File dir = new File(currentDirFile.getAbsolutePath() + File.separator + "SimulationFiles");
        File[] directoryListing = dir.listFiles();
        if(directoryListing != null){
            for(File file : directoryListing){
                try(BufferedReader br = new BufferedReader(new FileReader(file))){
                    List<String> content = new ArrayList<>();
                    String line;
                    while((line = br.readLine()) != null){
                        content.add(line + "\n");
                    }
                    String name = file.getName();
                    int extPos = name.lastIndexOf('.');
                    name = name.substring(0, extPos);
                    res.put(name, content);
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        } else{
            //Give an error
        }
        return res;
    }
}
