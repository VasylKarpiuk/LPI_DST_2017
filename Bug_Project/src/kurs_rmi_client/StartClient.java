package kurs_rmi_client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import rmi_server.Compute;

public class StartClient {

    private final GetCommand getCommand;
    private final Registry registry;
    private final Compute remoteCompute;

    public StartClient() throws Exception {
        getSecurityManager();
        registry = LocateRegistry.getRegistry(Compute.PORT);
        remoteCompute = (Compute) registry.lookup(Compute.SERVER_NAME);
        getCommand = new GetCommand(remoteCompute);
    }

    private void getSecurityManager() {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
    }

    public static boolean working = true;

    public void starting() {
        System.out.println("Welcome to the RMI server, Enter \"exit\" to closing client");

        try (Scanner scanner = new Scanner(System.in)) {
            while (working)
                getCommand.parameters(scanner.nextLine());
        } catch (Exception ex) {
            System.err.println("Connections problem ");
        }finally {
            System.out.println("Client closed");
        }
    }
}

