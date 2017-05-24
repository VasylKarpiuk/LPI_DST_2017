package rmi_server;

import java.io.IOException;

public class Main{
    public static void main(String[] args) {

        try{
            Server server = new Server();
            server.startServer();
            System.out.println("Server starting");

            System.in.read();
            server.close();

        }catch (IOException ex){
            ex.printStackTrace();
        }
        finally{
            System.out.println("Server closed");
        }
    }
}
