package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.ENTER;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.Connector;

public class RegisterController implements Initializable {
    
    @FXML
    private TextField name_tf, username_tf;
    
    @FXML
    private PasswordField pass_pf, rpass_pf;
    
    @FXML 
    private AnchorPane ap_register;
    
    @FXML
    private Button send_btn, back_arrow;
    
    
    
    @FXML
    private void handleButtonAction(ActionEvent e) throws IOException {
        if (e.getSource()==send_btn){
            registerUser();
        }else if (e.getSource()==back_arrow){
            goBack(e);
        } 
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        send_btn.setOnKeyPressed(
                event -> {
                    if (event.getCode()==ENTER) try {
                        registerUser();
                    } catch (IOException ex) {
                        Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        );
        
    }
    
    public void goBack(ActionEvent e) throws IOException{
        Stage stageRegister = (Stage) ap_register.getScene().getWindow();
        
        FXMLLoader login = new FXMLLoader(getClass().getResource("/view/FXMLLogin.fxml"));
        Pane pane = (Pane)login.load();
        Scene loginScene = new Scene(pane);
        Stage loginStage = new Stage();
        loginStage.setScene(loginScene);
        loginStage.setTitle("Login");
        stageRegister.close();
        loginStage.show();
    }
    
    public void registerUser() throws IOException{
        Connector conn = new Connector();
        String name = name_tf.getText();
        String username = username_tf.getText();
        String password = pass_pf.getText();
        String rPassword = rpass_pf.getText();
        
        
        if (rPassword.equals(password)) {
            if (username.length()<3 || password.length()<3 || name.length()<3) {
                JOptionPane.showMessageDialog(null,"Debes rellenar todos los campos con almenos 3 caracteres");
            }else {
                if (conn.registerUser(name, username, password)) {
                        JOptionPane.showMessageDialog(null,"Usuario creado correctamente");
                }else {
                        JOptionPane.showMessageDialog(null,"El usuario ya existe");
                }	
            }		
        }else {
            JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden");
        }
    }
    
}
