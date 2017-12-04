
package view;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Tasker extends Application{
    
    
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLLogin.fxml"));
        
        Scene scene = new Scene(root);
        stage.setResizable(false);
        
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Login");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
