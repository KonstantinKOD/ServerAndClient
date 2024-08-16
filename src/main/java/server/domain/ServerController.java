package server.domain;

import client.ClientGUI;
import server.repository.Reposotory;
import server.ui.ServerView;

import java.util.ArrayList;
import java.util.List;

public class Server {
    private boolean work;
    private ServerView serverView;
    private List<ClientGUI> clientGUIList;
    private Reposotory<String> reposotory;

    public Server(ServerView serverView, Reposotory<String> reposotory){
        this.serverView=serverView;
        this.reposotory=reposotory;
        clientGUIList = new ArrayList<>();
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
            for (ClientGUI clientGUI: clientGUIList){
                dis
            }
        }
    }
}
