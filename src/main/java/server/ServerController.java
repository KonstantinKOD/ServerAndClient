package server;

import client.ClientView;

public class ServerController {
    private ServerView serverView;
    private ClientView clientView;

    public ServerController(ServerView serverView, ClientView clientView) {
        this.serverView = serverView;
        this.clientView = clientView;
    }

}
