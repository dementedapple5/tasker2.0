package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class EditTaskController implements Initializable {
    
    Connector conn = new Connector();
    
    ObservableList<String> priorList = FXCollections.observableArrayList("Major","Medium","Minor");
    ObservableList<String> userList;
    
    private String username;
    private boolean isAdmin;
    
    @FXML
    private ChoiceBox<String> choice_prior, choice_user;
    
    @FXML
    private TextField title_tf, comment_tf, description_tf;
    
    @FXML
    private AnchorPane create_ap;
    
    @FXML
    private Label attendant_lbl;
    
    private String oldTitle,oldComent,oldDescription,oldDate;
    private int oldPrioriy;
    
    
    public ArrayList<String> getUsersName() throws IOException{
        ArrayList<String> usernames = new ArrayList<>();
        for (User user: conn.obtenerUsuarios()){
            usernames.add(user.getUsername());
        }
        return usernames;
    }

    public EditTaskController(String username, boolean isAdmin, String oldTitle, String oldComent, String oldDescription, int oldPrioriy, String oldDate) {
        this.username = username;
        this.isAdmin = isAdmin;
        this.oldTitle = oldTitle;
        this.oldComent = oldComent;
        this.oldDescription = oldDescription;
        this.oldPrioriy = oldPrioriy;
        this.oldDate = oldDate;
    }
    
    @FXML
    public void goBack() throws IOException{
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
            taskStage.show();
            
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        title_tf.setText(oldTitle);
        comment_tf.setText(oldComent);
        description_tf.setText(oldDescription);
        
        
        choice_prior.setItems(priorList);
        choice_prior.getSelectionModel().select(oldPrioriy);
        
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
        
       
          String prior = choice_prior.getValue();
        String user;
        if(isAdmin){
              user = choice_user.getValue();
        }else{
             user = username;
        }
       
        String title = title_tf.getText();
        String comment = comment_tf.getText();
        String desc = description_tf.getText();
        
        String priorInt;
        
        if (prior.equalsIgnoreCase("major")){
            priorInt = 1+"";
        }else if (prior.equalsIgnoreCase("medium")){
            priorInt = 2+"";
        }else {
            priorInt = 3+"";
        }
        
        if (checkTask()){
            if (!conn.updateTarea(user, title, comment, priorInt, desc, user, oldTitle, oldDate)){
            JOptionPane.showMessageDialog(null, "Tarea editada con exito");
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
