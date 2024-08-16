package server.domain;

import client.domain.ClientController;
import client.ui.ClientGUI;
import server.repository.Repository;
import server.ui.ServerView;

import java.util.ArrayList;
import java.util.List;

public class ServerController {
    private boolean work;
    private ServerView serverView;
    private List<ClientController> clientControllerList;
    private Repository<String> reposotory;

    public ServerController(ServerView serverView, Repository<String> reposotory){
        this.serverView=serverView;
        this.reposotory=reposotory;
        clientControllerList = new ArrayList<>();
        serverView.setServerController(this);
    }

    public void start(){
        if (work){
            showOnWindow("Сервер уже был запущен");
        }else {
            work = true;
            showOnWindow("Сервер запущен!");
        }
    }

    public void stop(){
        if (!work){
            showOnWindow("Сервер уже был остановлен");
        }else {
            work = false;
            while (!clientControllerList.isEmpty()){

            }
        }
    }

    //метод откучения клиента от сервера
    public void disconnectUser(ClientController clientController){
        clientControllerList.remove(clientController);
        if (clientController != null){
            clientController.disconnectedFromServer();
            showOnWindow(clientController.getName() + " отключился от беседы");
        }
    }

    private void showOnWindow(String text){
        serverView.showMessage(text + "\n");
    }
}
