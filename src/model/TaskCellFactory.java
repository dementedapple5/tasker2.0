/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.TaskCell;
import model.Task;
import java.awt.event.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class TaskCellFactory implements Callback<ListView<Task>, ListCell<Task>> {

    @Override
    public ListCell<Task> call(ListView<Task> param) {
           
           
        return new TaskCell();
    }
}
