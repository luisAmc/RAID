/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raid;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Luis Martinez
 */
public class ServerNodeImpl implements ServerNode{
    Map filesInSystem = new HashMap();
    DataServer dataServer;
    
    public ServerNodeImpl() throws RemoteException {
        super();
    }
    
    public void saveFile(String fileName, String fileContent) throws RemoteException {
        try {
            filesInSystem.put(fileName.split("/")[fileName.split("/").length - 1], fileName);
            dataServer.createFile(fileName, fileContent);
        } catch (Exception ex) {
        }
    }
    
    public void createFolder(String folderName) throws RemoteException {
        try {
            filesInSystem.put(folderName.split("/")[folderName.split("/").length - 1], folderName);
            dataServer.createFolder(folderName);
        } catch (Exception ex) {
            
        }
    }
    
    public String readFile(String fileName) throws RemoteException {
        return dataServer.readFile(fileName);
    }
    
    public void sendFile(File file) throws RemoteException {
        
    }
    
    public void registerDataServer(DataServer server) throws RemoteException {
        dataServer = server;
    }
}
