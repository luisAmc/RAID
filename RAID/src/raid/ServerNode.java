/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raid;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Luis Martinez
 */
public interface ServerNode extends Remote{
    public void saveFile(String fileName, String fileContent) throws RemoteException;
    public void createFolder(String folderName) throws RemoteException;
    public String readFile(String fileName) throws RemoteException;
    public void sendFile(File file) throws RemoteException;
    public void registerDataServer(DataServer server) throws RemoteException;
}
