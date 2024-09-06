package server.domain;

import client.domain.ClientController;
import server.repository.Repository;
import server.ui.ServerView;

import java.util.ArrayList;
import java.util.List;

public class ServerController {
    private boolean work;
    private ServerView serverView; // Поле связывающее класс с интерфейсом
    private List<ClientController> clientControllerList;
    private Repository<String> repository; //Связь через интерфейс с передачей дженерика с типом данных String

    public ServerController(ServerView serverView, Repository<String> reposotory) {
        this.serverView = serverView;
        this.repository = reposotory;
        clientControllerList = new ArrayList<>();
        serverView.setServerController(this);
    }

    public void start() {
        if (work) {
            showOnWindow("Сервер уже был запущен");
        } else {
            work = true;
            showOnWindow("Сервер запущен!");
        }
    }

    public void stop() {
        if (!work) {
            showOnWindow("Сервер уже был остановлен");
        } else {
            work = false;
            while (!clientControllerList.isEmpty()) {
                disconnectUser(clientControllerList.get(clientControllerList.size() - 1));
            }
            showOnWindow("Сервер остановлен!");
        }
    }

    //метод откучения клиента от сервера
    public void disconnectUser(ClientController clientController) {
        clientControllerList.remove(clientController);
        if (clientController != null) {
            clientController.disconnectedFromServer();
            showOnWindow(clientController.getName() + " отключился от беседы");
        }
    }

    public boolean connectUser(ClientController clientController) { // передаем пользователя
        if (!work) {
            return false;
        }
        clientControllerList.add(clientController);// если true, добавляем пользователя в список(List)
        showOnWindow(clientController.getName()+" подключился к беседе");
        return true;
    }

    public void message(String text) {
        if (!work) {
            return;
        }
        showOnWindow(text); // метод добавляет сообщение в центральное окно
        answerAll(text); // метод перебора всех пользователей и передача им сообщения
        saveInHistory(text); // метод сохраняет историю переписки
    }

    public String getHistory(){
        return repository.load();
    }

    private void answerAll(String text) {
        for (ClientController clientController : clientControllerList) {
            clientController.answerFromServer(text);
        }
    }

    private void showOnWindow(String text) {
        serverView.showMessage(text + "\n");
    }

    private void saveInHistory(String text) {
        repository.save(text);
    }
}
