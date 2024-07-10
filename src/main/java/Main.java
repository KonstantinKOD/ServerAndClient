import client.ClientGUI ;
import server.ServerWindow ;

public class Main {
    public static void main(String[] args) {
        ServerWindow serverWindow = new ServerWindow();
        System.out.println("Запуск приложения 'ServerAndClients'");
        new ClientGUI(serverWindow);
        new ClientGUI(serverWindow);
    }
}