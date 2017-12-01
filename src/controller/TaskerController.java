
package controller;

import model.TaskCellFactory;
import model.Connector;
import model.Task;
import model.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TaskerController implements Initializable {

    private String username;
    
    @FXML
    private ListView list_todo, list_done;
    
    @FXML
    private Menu menu_cambiarUser;
    
    @FXML
    private MenuItem edt_tarea, edt_completar, edt_ocultar;

    private Boolean privileges;
    
    @FXML
    private AnchorPane ap_tasker;
    
    @FXML
    private Label label_usuario, label_viendo;
    
    @FXML
    private Button btn_add;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        label_usuario.setText("Usuario : "+username);
        label_viendo.setText("Viendo : "+username);

        compruebaPrivilegios();    

        try {
            rellenarMenu();
        } catch (IOException ex) {
            Logger.getLogger(TaskerController.class.getName()).log(Level.SEVERE, null, ex);
        }

        list_todo.setCellFactory(new TaskCellFactory());
        list_done.setCellFactory(new TaskCellFactory());

        try {
            getTasks(username);
        } catch (IOException ex) {
            Logger.getLogger(TaskerController.class.getName()).log(Level.SEVERE, null, ex);
        }

        edt_tarea.setOnAction(editarTarea());
        edt_completar.setOnAction(completarTarea());
        edt_ocultar.setOnAction(prueba());

    }
    
    @FXML
    public void addTask(){
        Stage taskerStage = (Stage) ap_tasker.getScene().getWindow();
        CreateTaskController ctc = new CreateTaskController(username,getPrivileges());
        FXMLLoader createTask = new FXMLLoader(getClass().getResource("/view/FXMLCreateTask.fxml"));
        createTask.setController(ctc);
        Pane pane;
        try {
            pane = (Pane)createTask.load();
            Scene createTaskScene = new Scene(pane);
            Stage createTaskStage = new Stage();
            createTaskStage.setScene(createTaskScene);
            createTaskStage.setTitle("Add Task");
            createTaskStage.show();
            taskerStage.close();
        } catch (IOException ex) {
            Logger.getLogger(TaskerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    public void compruebaPrivilegios() {
        Connector conn = new Connector();
        privileges = conn.checkAdmin(username);

    }

    public Boolean getPrivileges() {
        return privileges;
    }

    @FXML
    private void changeEdit(ActionEvent event) throws IOException {
        System.out.println("changeEdit");
    }
    
    public void setUser(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
    

    public void getTasks(String usernameTask) throws IOException {
        list_done.getItems().clear();
        list_todo.getItems().clear();
        Connector conn = new Connector();
        for (Task task : conn.obtenerTareas(usernameTask)) {

            if (task.isState()) {
                list_done.getItems().add(task);
            } else {
                list_todo.getItems().add(task);
            }
        }
    }

    private void rellenarMenu() throws IOException {

        Connector conn = new Connector();

        for (User user : conn.obtenerUsuarios()) {
            MenuItem item = new MenuItem(user.getUsername());
            item.setOnAction(changeUser());
            menu_cambiarUser.getItems().add(item);
        }

    }
    

    @FXML
    private EventHandler<ActionEvent> changeUser() {
        return new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                MenuItem mItem = (MenuItem) event.getSource();
                String username2 = mItem.getText();
                label_viendo.setText("Viendo : "+username2);
                System.out.println(username);
                System.out.println(username2);
                if (username2.equals(username)) {
                    btn_add.setDisable(false);
                    edt_tarea.setDisable(false);
                    edt_completar.setDisable(false);
                    edt_ocultar.setDisable(false);
                } else if (!username2.equals(username) || !privileges) {
                    btn_add.setDisable(true);
                    edt_tarea.setDisable(true);
                    edt_completar.setDisable(true);
                    edt_ocultar.setDisable(true);
                }
                try {
                    getTasks(username2);
                } catch (IOException ex) {
                    Logger.getLogger(TaskerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
    }

    private EventHandler<ActionEvent> editarTarea() {
        return new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                System.out.println("Nos cambiamos");
            }
        };
    }

    private EventHandler<ActionEvent> completarTarea() {
        return new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                try {
                    completarTareaMetodo();
                } catch (IOException ex) {
                    Logger.getLogger(TaskerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

    }

    public void completarTareaMetodo() throws IOException {
        Task tarea = (Task) list_todo.getSelectionModel().getSelectedItem();
        String encargado = tarea.getAttendant();
        String titulo = tarea.getTitle();
        String fecha = tarea.getCreationDate();
        Connector conn = new Connector();
        conn.completarTarea(encargado, titulo, fecha);

        getTasks(encargado);

    }

    private EventHandler<ActionEvent> prueba() {
        return new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                try {
                    ocultar();
                } catch (IOException ex) {
                    Logger.getLogger(TaskerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
    }

    public void ocultar() throws IOException {
        Task tarea = (Task) list_done.getSelectionModel().getSelectedItem();
        String encargado = tarea.getAttendant();
        String titulo = tarea.getTitle();
        String fecha = tarea.getCreationDate();
        Connector conn = new Connector();
        conn.completarTarea(encargado, titulo, fecha);
        getTasks(encargado);

    }

}
