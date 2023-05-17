package serverControllers;

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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.ClientModel;
import ocsf.server.ConnectionToClient;
import serverHandler.EchoServer;

public class ServerController {

    public static ArrayList<ClientModel> clientArrayList = new ArrayList<ClientModel>();
	
    @FXML
    private Button connectButton;

    @FXML
    private TextArea console;

    @FXML
    private TextField dbNameField;

    @FXML
    private Button defaultButton;

    @FXML
    private Button disconnectButton;

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
     * Method to remove client from client table
     * @param client the client to remove
     */
    public void RemoveFromTable(ConnectionToClient client) {
        System.out.println(1);
        ClientModel clientModel = new ClientModel(client.getInetAddress().getHostName(), client.getInetAddress() ,client.isAlive());
        System.out.println(clientArrayList.indexOf(clientModel));
        clientArrayList.remove(clientArrayList.indexOf(clientModel));
        ObservableList<ClientModel> clientObserveList = FXCollections.observableArrayList(clientArrayList);
        connectionList.setItems(clientObserveList);
    }

    /**
     * Method to add client to the client table
     * @param client the client to add
     */
    public void loadTable(ClientModel client) {
        clientArrayList.add(client);
        ObservableList<ClientModel> clientObserveList = FXCollections.observableArrayList(clientArrayList);
        connectionList.setItems(clientObserveList);

    }

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

    @FXML
    void connectServer(ActionEvent event) {
    	EchoServer.main(null);
    	EchoServer.startServer(dbNameField.getText(), usernameField.getText(), passwordField.getText());
    }

    @FXML
    void disconnectServer(ActionEvent event) {
    	//EchoServer.close();
    	System.exit(1);
    }

    @FXML
    void insertDefaultValues(ActionEvent event) {
    	load();
    }

}
