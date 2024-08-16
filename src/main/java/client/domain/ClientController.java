package client.domain;
/**
КОД С СЕМИНАРА 2(ВТОРОГО), ПЕРЕНЕСТИ В JDK_SEM2_HW
 */


import client.ui.ClientView;
import server.domain.ServerController;

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
    private ServerController serverController;

    //сеттер(связка клиента с интерфейсом(сеттер нужно передать в интерфейс))
    public ClientController(ClientView clientView, ServerController serverController) {
        this.clientView = clientView;
        this.serverController = serverController;
        clientView.setClientController(this);
    }

    /**
     * Метод попытки подключения к серверу. Вызывается из GUI
     * @param name имя пользователя, которым будем подписывать исходящие сообщения
     * @return ответ от сервера. true, если прошли авторизацию
     */
    public boolean connectToServer(String name) {
        this.name = name;
        if (server.connectUser(this)) {
            showOnWindow("Вы успешно подключились!\n");
            connected = true;
            String log = server.getHistory();
            if (log != null) {
                showOnWindow(log);
            }
            return true;
        } else {
            showOnWindow("Подключение не удалось");
            return false;
        }
    }

    /**
     * Метод отключения от сервера инициализированное сервером
     */
    public void disconnectedFromServer() {
        if (connected) {
            connected = false;
            clientView.disconnectedFromServer();
            showOnWindow("Вы были отключены от сервера!");
        }
    }

    /**
     * Метод отключения от сервера инициализированное клиентом (например закрыто GUI)
     */
    public void disconnectFromServer() {
        serverController.disconnectUser(this);
    }

    /**
     * Метод для передачи сообщения на сервер
     * @param text текст передаваемого сообщения
     */
    public void message(String text) {
        if (connected) {
            if (!text.isEmpty()) {
                serverController.message(name + ": " + text);
            }
        } else {
            showOnWindow("Нет подключения к серверу");
        }
    }

    /**
     * Геттер
     * @return возвращает имя клиента
     */
    public String getName(){
        return name;
    }

    /**
     * Метод вывода сообщения на GUI
     * @param text текст, который требуется вывести на экран
     */
    private void showOnWindow(String text) {
        clientView.showMessage(text + "\n");
    }
}
