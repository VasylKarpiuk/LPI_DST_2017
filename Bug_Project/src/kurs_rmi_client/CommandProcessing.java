package kurs_rmi_client;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.rmi.RemoteException;

import rmi_server.Compute;
import rmi_server.Compute.SearchValue;

public class CommandProcessing {
    private static final int SIZE_ELEMENTS = 1_000_000;
    private static final int MAX_VALUE_FOR_ELEMENTS = 100_000;

    private final Compute remoteCompute;

    public CommandProcessing(Compute remoteCompute) {
        this.remoteCompute = remoteCompute;
    }

    public void exit() throws RemoteException {
        System.out.println("Exit from server");
        StartClient.working = false;
    }

    public void ping() throws RemoteException {
        System.out.println(remoteCompute.ping());
    }

    public void echo(String[] parameters) throws RemoteException {
        if (parameters.length != 2) {
            System.out.println("Bad argument");
            return;
        }
        System.out.println(remoteCompute.echo(parameters[1]));

    }

    public void search(String[] parameters) throws RemoteException, IOException {
        if (parameters.length != 2) {
            System.out.println("Bad argument");
            return;
        }

        int[] intMass = new int[SIZE_ELEMENTS];
        for (int i = 0; i < intMass.length; i++) {
            int value = (int) (Math.random() * MAX_VALUE_FOR_ELEMENTS);
            intMass[i] = value;
        }

        File file = new File("values.txt");
        try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(file))){
            for(int value : intMass){
                dos.writeBytes(value + " ");
            }
        }
        byte[] mas = Files.readAllBytes(file.toPath());

        int key = Integer.parseInt(parameters[1]);
        int[] result = remoteCompute.executeTask(new SearchValue(mas, key));

        if(result[0] == -1){
            System.out.println("They is not this element");
        }else {
            System.out.print("Successfully\nKey index : ");
            for(int i : result){
                System.out.print(i + " ");
            }
            System.out.println();
        }

    }
}
