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
            int temp;          // вспомогательная переменная для обмена
            for (int i = 0; i < clientMass.length; i++) // просматриваем все элементы в цикле
            {
                if (clientMass[i] == key) // если находим элемент со значением key,
                {
                    if (i == 0) // если индекс равен нулю, возвращаем его,
                    {
                        list.add(i);
                        continue;// потому что смещаться ближе к началу массива невозможно
                    }
                    temp = clientMass[i];      // меняем местами найденный элемент с предыдущим
                    clientMass[i] = clientMass[i - 1];
                    clientMass[i - 1] = temp;
                    list.add(i);         // возвращаем найденный индекс элемента
                }
            }
            if (list.isEmpty()) {
                list.add(-1);
            }
            return getIntMass(list);  // если элемент не найден, возвращаем -1
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
