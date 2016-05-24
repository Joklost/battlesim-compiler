package com.company;

import org.apache.commons.io.FileUtils;
import org.apache.tools.ant.taskdefs.Javadoc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.company.CompileJava.deleteDir;

/**
 * Created by Magnus on 25-04-2016.
 */
public class SimulationFileReader {
    public static Map<String, List<String>> getDST() throws IOException {
        //System.out.println("Downloading the newest simulation files...");
        String serverIp = "http://188.166.148.164/";

        // File names to download from server
        List<String> fileNames = new ArrayList<>();
        fileNames.add("BasicFrame.java");
        fileNames.add("Bullet.java");
        fileNames.add("CollisionDetector.java");
        fileNames.add("DSTFunctions.java");
        fileNames.add("FireBulletEvent.java");
        fileNames.add("FireBulletListener.java");
        fileNames.add("Map.java");
        fileNames.add("MoveStep.java");
        fileNames.add("SimObj.java");
        fileNames.add("Step.java");
        fileNames.add("Vector.java");
        fileNames.add("WaitStep.java");
        fileNames.add("Simulation.java");

        File currentDirFile = new File("");
        File simDir = new File(currentDirFile.getAbsolutePath() + File.separator + "SimulationFiles");
        if (simDir.exists() && simDir.isDirectory()) {
            deleteDir(simDir);
        }

        if (!simDir.mkdir()) {
            throw new IOException("Unable to create SimulationFiles directory. Do you have the proper rights?");
        }

        for (String fileName : fileNames) {
            URL server = new URL(serverIp + fileName);
            File f = new File(simDir.getAbsolutePath() + File.separator + fileName);
            FileUtils.copyURLToFile(server, f);
        }

//        FileUtils.copyURLToFile(server, fileNames.get(0));

        Map res = new HashMap<String, List<String>>();
        //File dir = new File(currentDirFile.getAbsolutePath() + File.separator + "SimulationFiles");
        File[] directoryListing = simDir.listFiles();
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
                    throw e;
                }
            }
        } else{
            throw new IOException("Unable to read filelist from SimulationFiles directory. Do you have the proper rights?");
        }

        if (simDir.exists() && simDir.isDirectory()) {
            deleteDir(simDir);
        }
        return res;
    }
}
