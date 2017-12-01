package controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import model.Connector;
import model.User;

public class CreateTaskController implements Initializable {
    
    Connector conn = new Connector();
    
    ObservableList<String> priorList = FXCollections.observableArrayList("Major","Medium","Minor");
    ObservableList<String> userList;
    
    
    @FXML
    private ChoiceBox<String> choice_prior, choice_user;
    
    @FXML
    private TextField title_tf, comment_tf, description_tf;
    
    @FXML
    private Button btn_create;
    
    
    public ArrayList<String> getUsersName() throws IOException{
        ArrayList<String> usernames = new ArrayList<>();
        for (User user: conn.obtenerUsuarios()){
            usernames.add(user.getUsername());
        }
        return usernames;
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
        
        choice_user.setItems(userList);
        choice_user.getSelectionModel().selectFirst();
    }
    
    @FXML
    public void sendData(){
        String prior = choice_prior.getValue();
        String user = choice_user.getValue();
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
    

    
    
}
