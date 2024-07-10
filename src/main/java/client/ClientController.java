package client;
/**
КОД С СЕМИНАРА 2(ВТОРОГО), ПЕРЕНЕСТИ В JDK_SEM2_HW
 */


import server.ServerView;

/**
 * класс содержащий логику работы клиента
 *
 * @clientView абстракция графического интерфейса
 * @server объект для связи с сервером
 */

public class ClientController {
    private boolean connected;
    private String name; // лучше использовать Класс User(нужно его создать)
    private ClientView clientView;
    private ServerView serverView;

    public ClientController(ClientView clientView, ServerView serverView){
        this.clientView = clientView;
        this.serverView = serverView;
    }

    public boolean connectToServer(String name) {
        this.name = name;
        if (serverView.connectUser(this)) {
            showOnWindow("Вы успешно подключились!\n");
            connected = true;
            String log = serverView.getHistory();
            if (log != null) {
                showOnWindow(log);
            }
            return true;
        } else {
            showOnWindow("Подключение не удалось");
            return false;
        }
    }

    public void disconnectFromServer() {
        if (connected) {
            connected = false;
            clientView.disconnectedFromServer();
            serverController.disconnectUser(this);
            showOnWindow("Вы были отключены от сервера!");
        }
    }

    public void answerFromServer(String text) {
        showOnWindow(text);
    }

    public void message(String text) {
        if (connected) {
            if (!text.isEmpty()) {
                serverController.message(name + ": " + text);
            }
        } else {
            showOnWindow("Нет подключения к серверу");
        }
    }

    private void showOnWindow(String text) {
        clientView.showMessage(text + "\n");
    }
}
