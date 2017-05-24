package rmi_server;

import java.io.*;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

public class Server implements Compute {

    private Registry registry;
    private Compute stub;

    public void startServer() {
        getSecurityManager();
        start();
    }

    private void getSecurityManager() {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
    }

    private void start() {
        try {
            registry = LocateRegistry.createRegistry(PORT);
            stub = (Compute) UnicastRemoteObject.exportObject(this, PORT);
            registry.rebind(Compute.SERVER_NAME, stub);
        } catch (Exception ex) {
            System.err.println("ComputeEngine exception: ");
        }
    }

    public void close(){
        try {
            if (registry != null) {
                registry.unbind(SERVER_NAME);
                registry = null;
            }

            if (stub != null) {
                UnicastRemoteObject.unexportObject(this, true);
                stub = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String ping() throws RemoteException {
        System.out.println(" Sending ping response");
        return "PONG";
    }

    @Override
    public String echo(String text) throws RemoteException {
        System.out.println(" Sending echo response");
        return "ECHO: " + text;
    }

    @Override
    public <T> T executeTask(Task<T> t) throws IOException, RemoteException {
        System.out.println(" Start search");
        return t.execute();
    }
}
