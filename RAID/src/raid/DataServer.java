/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raid;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Luis Martinez
 */
public class DataServer {
    File directory;
    
    public DataServer() {
        directory = new File("./dataServer");
        directory.mkdir();
    }
    
    public void createFile(String fileName, String fileContent) {
        try {
            File neoFile = new File(fileName);
            BufferedWriter bw = new BufferedWriter(new FileWriter(neoFile));
            bw.write(fileContent);
            bw.close();
        } catch (Exception ex) {
            System.err.println("ERROR: Creacion de archivo - DataServer");
        }
    }
    
    public void createFolder(String folderName) {
        try {
            File neoFolder = new File(folderName);
            neoFolder.mkdir();
        } catch (Exception ex) {
            System.err.println("ERROR: Creacion de carpeta - DataServer");
        }
    }
    
    public String readFile(String fileName) {
        String fileContent = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
            String line = "";
            while ((line = br.readLine()) != null) 
                fileContent += line + "\n";
        } catch (Exception ex) {
            System.err.println("ERROR: Lectura de archivo - DataServer");
        }
        return fileContent;
    }
    
    public static void main(String[] args) {
        try {
            DataServer dataServer = new DataServer();
            dataServer.start();
        } catch (Exception ex) {
            System.out.println("ERROR: Ejecucion de main - DataServer");
        }
    }
    
    public void start() {
        String host = "192.168.43.54";
        int port = 1099;
        try {
            Registry registry = LocateRegistry.getRegistry(host, port);
            ServerNode stub = (ServerNode) registry.lookup("DataServer");
            stub.registerDataServer(this);
        } catch (Exception ex) {
            System.out.println("ERROR: Start - DataServer");
        }
    }
}
