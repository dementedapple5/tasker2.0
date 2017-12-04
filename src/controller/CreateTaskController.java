package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.Connector;
import model.User;

public class CreateTaskController implements Initializable {
    
    Connector conn = new Connector();
    
    ObservableList<String> priorList = FXCollections.observableArrayList("Major","Medium","Minor");
    ObservableList<String> userList;
    
    private String username;
    private boolean isAdmin;
    
    @FXML
    private ChoiceBox<String> choice_prior, choice_user;
    
    @FXML
    private Button back_btn, create_btn;
    
    @FXML
    private TextField title_tf, comment_tf, description_tf;
    
    @FXML
    private AnchorPane create_ap;
    
    @FXML
    private Label attendant_lbl;
    
    
    public ArrayList<String> getUsersName() throws IOException{
        ArrayList<String> usernames = new ArrayList<>();
        for (User user: conn.obtenerUsuarios()){
            usernames.add(user.getUsername());
        }
        return usernames;
    }
    
    public CreateTaskController(String username, boolean isAdmin){
        this.username = username;
        this.isAdmin = isAdmin;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        choice_prior.setItems(priorList);
        choice_prior.getSelectionModel().selectFirst();
        
        try {
            userList = FXCollections.observableArrayList(getUsersName());
        } catch (IOException ex) {
            Logger.getLogger(CreateTaskController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (isAdmin){
            choice_user.setItems(userList);
            choice_user.getSelectionModel().select(username);
        }else{
            attendant_lbl.setVisible(false);
            choice_user.setVisible(false);
        } 
    }
    
    @FXML
    public void sendData() throws IOException{
        String user;
        String prior = choice_prior.getValue();
        if(isAdmin){
            user = choice_user.getValue();
        }else{
            user = username;
        }
        
        String title = title_tf.getText();
        String comment = comment_tf.getText();
        String desc = description_tf.getText();
        
        int priorInt;
        
        if (prior.equalsIgnoreCase("major")){
            priorInt = 1;
        }else if (prior.equalsIgnoreCase("medium")){
            priorInt = 2;
        }else {
            priorInt = 3;
        }
        if (checkTask()){
            if (!conn.addTask(title, comment, desc, priorInt, user)){
                JOptionPane.showMessageDialog(null, "Tarea añadida con exito");
                
            }else{
                JOptionPane.showMessageDialog(null, "La tarea ya existe");
            }
        }else{
            JOptionPane.showMessageDialog(null, "Debes rellenar todos los campos");
        }
        
    }
    
    public boolean checkTask(){
        String title = title_tf.getText();
        String comment = comment_tf.getText();
        String desc = description_tf.getText();
        if (title.length()==0 || comment.length()==0 || desc.length()==0) return false;
        else return true;
    }
    
    public void goBack() throws IOException{
        Stage stageCreate = (Stage) create_ap.getScene().getWindow();
        stageCreate.close();
    }
    
    @FXML
    public void handdleActionButton(ActionEvent e) throws IOException{
        if (e.getSource()==back_btn){
            goBack();
        }else if (e.getSource()==create_btn){
            sendData();
        }
  
    }
}

/*
Stage stageRegister = (Stage) create_ap.getScene().getWindow();
                TaskerController controler = new TaskerController();
                controler.setUser(username);
                FXMLLoader task = new FXMLLoader(getClass().getResource("/view/FXMLTasker.fxml"));
                task.setController(controler);
                Pane pane = (Pane)task.load();
                Scene taskScene = new Scene(pane);
                Stage taskStage = new Stage();
                taskStage.setScene(taskScene);
                taskStage.setTitle("Tasks");
                stageRegister.close();
                taskStage.show();*/