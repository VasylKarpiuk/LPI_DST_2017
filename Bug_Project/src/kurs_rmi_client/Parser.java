package kurs_rmi_client;

public class Parser {

    public String[] getParameters(String line) {
        String[] parsMas = line.split(" ", 2);
        return (parsMas[0].equals("echo")) ? parsMas : line.split(" ");
    }
}
