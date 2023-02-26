package org.example;

import javafx.scene.control.ListView;

import javafx.scene.control.*;

import javax.swing.*;
import java.util.Arrays;

public class UpdateValues {
    Boolean isUpdating = false;
    private TextField tf1;
    private TextField tf2;
    private Label tf3;
    private ListView<String> listView1;
    private ListView<String> listView2;
    private APIRequest apiRequest;

    UpdateValues(TextField tf1, TextField tf2, Label tf3, ListView<String> listView1, ListView<String> listView2, APIRequest apiRequest) {
        this.tf1 = tf1;
        this.tf2 = tf2;
        this.tf3 = tf3;
        this.listView1 = listView1;
        this.listView2 = listView2;
        this.apiRequest = apiRequest;
    }
    public void update(Boolean chooseListener) throws Exception {
        isUpdating = true;
        if(listView1.getSelectionModel().getSelectedItem() == null || listView2.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        if(chooseListener) {
            String[] result = apiRequest.calculate(listView1.getSelectionModel().getSelectedItem(), listView2.getSelectionModel().getSelectedItem(), Double.parseDouble(tf1.getText()));
            tf2.setText(result[0]);
            tf3.setText(result[1]);
        }
        else{
            String[] result = apiRequest.calculate(listView2.getSelectionModel().getSelectedItem(), listView1.getSelectionModel().getSelectedItem(), Double.parseDouble(tf2.getText()));
            tf1.setText(result[0]);
            tf3.setText(result[1]);
        }
        isUpdating = false;
    }
}
