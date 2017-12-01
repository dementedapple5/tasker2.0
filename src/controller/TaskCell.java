/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Task;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.ContextMenuEvent;

public class TaskCell extends ListCell<Task> {
    
   

    @FXML
    private Label titleLabel;

    @FXML
    private Label commentLabel;

    @FXML
    private Label descriptionLabel;
    
    @FXML
    private Label dateLabel;
    

    public TaskCell() {
        loadFXML();
    }
    
  

    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/task_cell.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void updateItem(Task item, boolean empty) {
        super.updateItem(item, empty);

        if(empty) {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
            setStyle("-fx-border-width: 0 0 0 0; -fx-border-color: white");
        }
        else {
            
            
            
            if(item.getPriority() == 1){
                setStyle("-fx-border-width: 0 0 0 5; -fx-border-color: red");
            }else if(item.getPriority() == 1){
                setStyle("-fx-border-width: 0 0 0 5; -fx-border-color: yellow");
            }else{
                setStyle("-fx-border-width: 0 0 0 5; -fx-border-color: green");
            }
            titleLabel.setText(item.getTitle());
            commentLabel.setText(item.getComment());
            descriptionLabel.setText(item.getDescription());
            dateLabel.setText(item.getCreationDate());

            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
 
             
        }
    }
    
    
    
    
}