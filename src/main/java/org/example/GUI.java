package org.example;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.swing.*;

public class GUI extends Application{
    APIRequest apiRequest = new APIRequest();
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){

        Label send=new Label("You send:");
        Label recieve = new Label("They recieve:");
        TextField tf1=new TextField("0.00");
        TextField tf2=new TextField("0.00");
        Label tf3=new Label();
        ListView<String> listView1 = new ListView<>(CurrencyList.getCodeAndName());
        ListView<String> listView2 = new ListView<>(CurrencyList.getCodeAndName());
        UpdateValues updateValues = new UpdateValues(tf1, tf2, tf3, listView1, listView2, apiRequest);

        tf1.textProperty().addListener(new TFListener(tf1, updateValues, true));
        tf2.textProperty().addListener(new TFListener(tf2, updateValues, false));
        listView1.getSelectionModel().selectedItemProperty().addListener(new LVListener(listView1, updateValues, false));
        listView2.getSelectionModel().selectedItemProperty().addListener(new LVListener(listView2, updateValues, true));



        GridPane root = new GridPane();
        root.addRow(0, send, tf1, listView1);
        root.addRow(1, recieve, tf2, listView2);
        root.addRow(2, tf3);
        Scene scene=new Scene(root,700,500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Text Field Example");
        primaryStage.show();
    }
}
