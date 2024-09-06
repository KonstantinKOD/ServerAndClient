package server.ui;

import server.domain.ServerController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerWindow extends JFrame implements ServerView {
    // задаю размеры окна
    private static final int HEIGHT = 555;
    private static final int WIDTH = 507;
    private static final int POSX = 700;
    private static final int POSY = 300;

    //создаю кнопки для JPanel
    private JButton btStart, btStop;
    private JTextArea log;

    private ServerController serverController;

    //конструктор окна сервера
    public ServerWindow() {

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(POSX, POSY); // Позиция на экране
//        setLocationRelativeTo(null); //не привязывает обьект к чему либо, вызывает обьект по середине экрана
        setSize(WIDTH, HEIGHT);
        setTitle("Chat server");
        setResizable(true);
        createPanel();

        setVisible(true);
    }

    @Override
    public void setServerController(ServerController serverController) {
        this.serverController=serverController;
    }

    public ServerController getConnection(){
        return serverController;
    }

    // метод создания визуала
    public void createPanel() {
        log = new JTextArea(); // создание текстового поля
        add(log); // добавляю текстовое поле в окно приложения
        add(createButtons(), BorderLayout.SOUTH); // добавляю панель с кнопками в окно
    }

    // метод создания панели с кнопками
    private Component createButtons() {
        JPanel panel = new JPanel(new GridLayout(1, 2)); // создание панели с строками и колонками
        btStart = new JButton("Server start");
        btStop = new JButton("Server stop");

        // добавление функционала кнопке старт
        btStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverController.start();
            }
        });

        // добавление функционала кнопке стоп
        btStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverController.stop();
            }
        });

        panel.add(btStart);
        panel.add(btStop);
        return panel;
    }

    @Override
    public void showMessage(String msg){
        log.append(msg);
    }




}
