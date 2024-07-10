package client;

import server.ServerView;
import server.ServerWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Класс описывающий работу графического интерфейса приложения.
 * Является абстракцией GUI
 */
public class ClientGUI extends JFrame implements ClientView {
    private static final int HEIGHT = 555;
    private static final int WIDTH = 507;

    // виджеты
    JTextArea log;
    JTextField tfIPAdress, tfPort, tfLogin, tfMessage;
    JPasswordField password;
    JButton btLogin, btSend;
    JPanel headerPanel;
    private ClientController clientController;


    /**
     * Контроллер, описывающий реакцию на различные события.
     * Когда что-то происходит, например нажата какая-то кнопка на экране, то обращаемся
     * к контроллеру и вызываем нужный метод
     */


    // создание окна клиента(Конструктор)
    public ClientGUI(ServerWindow serverWindow) { // передаем объек сервера
        setSize(WIDTH, HEIGHT);
        setResizable(true);
        setTitle("Chat client");
//        setLocation(server.getX() - 500, server.getY()); // изменение появление окна клиента относительно окна сервера
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        createPanel();
        setVisible(true);
        clientController = new ClientController(this, serverWindow.);
    }



            // сеттер
    public void setClient(ClientController clientController){
        this.clientController=clientController;
    }

    /**
     * Метод вывода текста на экран GUI. Вызывается из контроллера
     * @param message текст, который требуется отобразить на экране
     */
    @Override
    public void showMessage(String message) {
        log.append(message + "\n");
    }

    /**
     * Метод, описывающий отключение клиента от сервера со стороны сервера
     */
    @Override
    public void disconnectedFromServer() {
        hideHeaderPanel(true);
    }

    /**
     * Метод, описывающий отключение клиента от сервера со стороны клиента
     */
    public void disconnectFromServer() {
        clientController.disconnectFromServer();
    }

    /**
     * Метод изменения видимости верхней панели экрана, на которой виджеты для авторизации (например кнопка логин)
     * @param visible true, если надо сделать панель видимой
     */
    public void hideHeaderPanel(boolean visible){
        headerPanel.setVisible(visible);
    }

    /**
     * Метод, срабатывающий при нажатии кнопки авторизации
     */
    public void login(){
        if (clientController.connectToServer(tfLogin.getText())){
            headerPanel.setVisible(false);
        }
    }

    /**
     * Метод для отправки сообщения. Используется при нажатии на кнопку send
     */
    public void message() {
        clientController.message(tfMessage.getText());
        tfMessage.setText("");
    }

    /**
     * Метод добавления виджетов на экран
     */
    private void createPanel() {
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createLog());
        add(createFooter(), BorderLayout.SOUTH);
    }

    /**
     * Метод создания панели авторизации
     * @return возвращает созданную панель
     */
    private Component createHeaderPanel() {
        headerPanel = new JPanel(new GridLayout(2, 3)); //создание панели с 2-мя строками и 3-мя колонками
        // добавление произвольной инфы в виджеты
        tfIPAdress = new JTextField("127.0.0.1");
        tfPort = new JTextField("8189");
        tfLogin = new JTextField("Ivan Ivanovich");
        password = new JPasswordField("123");
        btLogin = new JButton("Login"); // кнопка Login(залогиниться) подключает к серверу
        btLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login(); // вызов метод подключения к серверу
            }
        });

        //добавление всех виджетов на панель JPanel
        headerPanel.add(tfIPAdress);
        headerPanel.add(tfPort);
        headerPanel.add(new JPanel());
        headerPanel.add(tfLogin);
        headerPanel.add(password);
        headerPanel.add(btLogin);

        return headerPanel; // возвращение верхней панели регистрации
    }

    /**
     * Метод создания центральной панели, на которой отображается история сообщений
     * @return возвращает созданную панель
     */
    private Component createLog() {
        log = new JTextArea(); // добавление окна переписки
        log.setEditable(false); // запрет для редактирования текста в окне переписки
        return new JScrollPane(log); // добавление возможности скролить в окне
    }

    /**
     * Метод создания панели отправки сообщений
     * @return возвращает созданную панель
     */
    // создание нижней панели(панели отправки сообщений(подвал(Footer)))
    private Component createFooter() {
        JPanel panel = new JPanel(new BorderLayout());
        tfMessage = new JTextField();
        tfMessage.addKeyListener(new KeyAdapter() { // это слушатель клавиатуры для текстого поля
            @Override                               // когда поле в фокусе, идет прослушка клавишь
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n') { // если клавиша соответствует преносу строки(\n(то есть Enter))
                    message();             // выполняется метод message(отправка сообщения)
                }
            }
        });
        btSend = new JButton("Send");
        // переопределение кнопки Send
        btSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                message();  // клик по кнопке Send вызывает метод message(отправка сообщения)
            }
        });
        // добавление виджетов на панель(поле ввода сообщений и кнопку Send)
        panel.add(tfMessage);
        panel.add(btSend, BorderLayout.EAST);
        return panel;
    }

    /**
     * Метод срабатывающий при важных событиях связанных с графическим окном (например окно в фокусе)
     * @param e  the window event
     */
    // преопределение нажатия крестика
    // при его нажатии отключаются все клиенты
    // этот метод есть изначально в JFrame
    @Override
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e); // оставляем изначальное событие(нажатие крестика-закрытие приложения)
        if (e.getID() == WindowEvent.WINDOW_CLOSING) { // если был нажат крестик
            this.disconnectedFromServer();                 // вызывается метод отключения клиентов от сервера
        }
    }
}
