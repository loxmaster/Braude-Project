package serverControllers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.ClientModel;
import ocsf.server.ConnectionToClient;
import serverHandler.EchoServer;

public class ServerController {

    public ArrayList<ClientModel> clientArrayList = new ArrayList<ClientModel>();

    @FXML
    private Button BtnInfo;

    @FXML
    private TextField dbNameField;

    @FXML
    private TextField ipField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    private TableView<ClientModel> connectionList;

    @FXML
    private TableColumn<ClientModel, Boolean> connectionStatusColumn;

    @FXML
    private TableColumn<ClientModel, String> hostNameColumn;

    @FXML
    private TableColumn<ClientModel, InetAddress> ipColumn;

    /**
     * Method for loading the parameters of the clientListTable
     * columns and start text on screen.
     *
     */
    public void load() {

        connectionStatusColumn.setCellValueFactory(new PropertyValueFactory<>("IsConnected"));
        hostNameColumn.setCellValueFactory(new PropertyValueFactory<>("Host"));
        ipColumn.setCellValueFactory(new PropertyValueFactory<>("Ip"));

        passwordField.setText("02587595mM!");
        usernameField.setText("root");
        dbNameField.setText("projecton");
        try {
            ipField.setText("" + InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        
    }

    /**
     * Method to remove client from client table
     * 
     * @param ConnectionToClient
     */
    public void RemoveFromTable(ConnectionToClient client) {
        for (int i = 0; i < clientArrayList.size(); i++)
            if (clientArrayList.get(i).getIp().equals(client.getInetAddress()))
                clientArrayList.remove(clientArrayList.get(i));
        ObservableList<ClientModel> clientObserveList = FXCollections.observableArrayList(clientArrayList);
        connectionList.getItems().clear();
        connectionList.setItems(clientObserveList);
    }

    /**
     * Method to add client to the client table
     * 
     * @param ClientModel the client to add
     */
    public void loadToTable(ClientModel client) {
        clientArrayList.add(client);
        ObservableList<ClientModel> clientObserveList = FXCollections.observableArrayList(clientArrayList);
        connectionList.setItems(clientObserveList);
    }

    @FXML
    void connectServer(ActionEvent event) {
        EchoServer.main(null);
        EchoServer.startServer(dbNameField.getText(), usernameField.getText(), passwordField.getText());
    }

    @FXML
    void disconnectServer(ActionEvent event) {
        try {
            EchoServer.echoServer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    @FXML
    void insertDefaultValues(ActionEvent event) {
        load();
    }
}
