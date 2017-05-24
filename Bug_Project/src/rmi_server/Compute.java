package rmi_server;

import java.io.*;
import java.util.*;
import java.nio.charset.StandardCharsets;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.Instant;

public interface Compute extends Remote {

    public static final int PORT = 14888;
    public static final String SERVER_NAME = "rmi_server";

    public <T> T executeTask(Task<T> t) throws IOException, RemoteException;

    public String echo(String text) throws RemoteException;

    public String ping() throws RemoteException;

    public class SearchValue implements Task<int[]>, Serializable {

        private static final long serialVersionUID = 227L;

        private final byte[] clientMass;
        private final int key;

        public SearchValue(byte[] clientMass, int key) throws IOException {
            this.clientMass = clientMass;
            this.key = key;
        }

        @Override
        public int[] execute() throws IOException {
            String[] stringMass = new String(clientMass, StandardCharsets.UTF_8).trim().split(" ");
            int[] intMass = new int[stringMass.length];
            for(int i = 0; i < intMass.length; i++){
                intMass[i] = Integer.parseInt(stringMass[i]);
            }

            int timeStart = (int) Instant.now().getEpochSecond();
            int[] indexes = search(intMass);
            int timeEnd = (int) (Instant.now().getEpochSecond() - timeStart);

            System.out.println("Search " + timeEnd + " seconds");
            return indexes;
        }

        private int[] search(int[] clientMass){

            ArrayList<Integer> list = new ArrayList<>();
            int temp;          // допоміжна змінна для обміну
            for (int i = 0; i < clientMass.length; i++) // перебираємо всі елементи в циклі
            {
                if (clientMass[i] == key)
                {
                    if (i == 0) // якщо індекс = 0, записуємо його і продовжуємо далі
                    {
                        list.add(i);
                        continue;
                    }
                    temp = clientMass[i];      // міняємо місцями знайдений елемент з попереднім
                    clientMass[i] = clientMass[i - 1];
                    clientMass[i - 1] = temp;
                    list.add(i);         // записуємо знайдений індекс
                }
            }
            if (list.isEmpty()) {   // якщо елемент не знайдено, записуємо -1
                list.add(-1);
            }
            return getIntMass(list);
        }

        private int[] getIntMass(ArrayList<Integer> list){
            int[] mas = new int[list.size()];
            for(int i = 0; i < list.size(); i++){
                mas[i] = list.get(i);
            }
            return mas;
        }
    }
}
