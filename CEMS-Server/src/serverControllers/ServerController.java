package serverControllers;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import serverHandler.EchoServer;

public class ServerController {

	
    @FXML
    private Button connectButton;

    @FXML
    private TableView<?> connectionList;

    @FXML
    private TableColumn<?, ?> connectionStatusColumn;

    @FXML
    private TextArea console;

    @FXML
    private TextField dbNameField;

    @FXML
    private Button defaultButton;

    @FXML
    private Button disconnectButton;

    @FXML
    private TableColumn<?, ?> hostNameColumn;

    @FXML
    private TableColumn<?, ?> ipColumn;

    @FXML
    private TextField ipField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField usernameField;

    public void load() {
    	passwordField.setText("02587595mM!");
    	usernameField.setText("root");
    	dbNameField.setText("projecton");
    	try {
			ipField.setText("" + InetAddress.getLocalHost());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
    	/*
    	Thread t = new Thread ( new Runnable() {
			
			@Override
			public void run() {
				Scanner s = new Scanner((Readable) System.out);
				while (true)
					if (s.hasNext())
						console.setText(s.next());
			}
		});
    	t.start();*/
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
