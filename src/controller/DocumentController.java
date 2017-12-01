package controller;

import model.Connector;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author samuel
 */
public class DocumentController implements Initializable {

    @FXML
    private Button btn_login;
    @FXML
    private TextField et_username;
    @FXML
    private PasswordField et_password;
    @FXML
    private Hyperlink link_signup;
    @FXML
    private AnchorPane ap_login;

    Stage prevStage;

    public void setPrevStage(Stage stage) {
        this.prevStage = stage;
    }

    
    public void login(ActionEvent e) throws IOException{
        Stage stageRegister = (Stage) ap_login.getScene().getWindow();
        Connector conn = new Connector();
        
        if (conn.checkUser(et_username.getText(), et_password.getText())){
            
            TaskerController controler = new TaskerController();
            controler.setUser(et_username.getText());
            FXMLLoader task = new FXMLLoader(getClass().getResource("/view/FXMLTasker.fxml"));
            
            task.setController(controler);
            Pane pane = (Pane)task.load();
            
            Scene taskScene = new Scene(pane);
            Stage taskStage = new Stage();
            taskStage.setScene(taskScene);
            taskStage.setTitle("Tasks");
            stageRegister.close();
            taskStage.show();
        } else {
            JOptionPane.showMessageDialog(null, "Usuario o Contraseña incorrecta");
        }  
    }
    
    public void register(ActionEvent e) throws IOException{
        Stage stageRegister = (Stage) ap_login.getScene().getWindow();
        
        FXMLLoader createTask = new FXMLLoader(getClass().getResource("/view/FXMLRegister.fxml"));
        Pane pane = (Pane)createTask.load();
        Scene createTaskScene = new Scene(pane);
        Stage createTaskStage = new Stage();
        createTaskStage.setScene(createTaskScene);
        createTaskStage.setTitle("Register");
        stageRegister.close();
        createTaskStage.show();
    }
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        if (btn_login == event.getSource()) {
            login(event);
        } else if (link_signup == event.getSource()) {
            register(event);
        }
    }

    public void validation() {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                et_username.requestFocus();
            }
        });

    }

}
