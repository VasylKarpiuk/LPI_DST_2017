package kurs_rmi_client;

import java.io.IOException;
import java.rmi.RemoteException;
import rmi_server.Compute;

public class GetCommand {
    private final CommandProcessing commandProcessing;
    
    public GetCommand(Compute remoteCompute) {
        commandProcessing = new CommandProcessing(remoteCompute);
    }

    private final Parser parser = new Parser();
    public void parameters(String inLine) throws RemoteException, IOException {
        
        String[] parameters = parser.getParameters(inLine);

        switch (parameters[0]) {
            case "ping":
                commandProcessing.ping();
                break;

            case "echo":
                commandProcessing.echo(parameters);
                break;

            case "search":
                commandProcessing.search(parameters);
                break;

            case "exit":
                commandProcessing.exit();
                break;
                
            default:
                System.out.println("They is not this command");
                break;
        }
    }
}
