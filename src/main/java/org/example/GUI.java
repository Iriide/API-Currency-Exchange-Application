package main.java.org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.File;

public class GUI extends Application{
    /**
     * This is the main class of the program. It creates the GUI and the listeners for the GUI.
     * @param APIRequest is the object that is used to make the API requests. It also calculates the exchange rates and converts the values.
     */
    APIRequest apiRequest = new APIRequest();
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        /*
        This method creates the GUI and the listeners for the GUI.
         */

        // Create GUI elements
        Label send=new Label("You send:");
        Label recieve = new Label("They recieve:");
        TextField tf1=new TextField("0.00");
        TextField tf2=new TextField("0.00");
        Label tf3=new Label();
        final Pane spring = new Pane();
        ListView<String> listView1 = new ListView<>(CurrencyList.getCodeAndName());
        ListView<String> listView2 = new ListView<>(CurrencyList.getCodeAndName());
        //Create UpdateValues object that is going to be used in the listeners
        //this object gets values from the GUI elements, calculates and updates the values after a change
        UpdateValues updateValues = new UpdateValues(tf1, tf2, tf3, listView1, listView2, apiRequest);

        // Create listeners for the GUI elements, the listeners are in the LVListener and TFListener classes
        tf1.textProperty().addListener(new TFListener(tf1, updateValues, true));
        tf2.textProperty().addListener(new TFListener(tf2, updateValues, false));
        listView1.getSelectionModel().selectedItemProperty().addListener(new LVListener(listView1, updateValues, false));
        listView2.getSelectionModel().selectedItemProperty().addListener(new LVListener(listView2, updateValues, true));

        // Create vBoxes and hBoxes for the GUI layout
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10, 10, 10, 10));

        VBox subVBox1 = new VBox();
        subVBox1.setSpacing(10);
        subVBox1.setPadding(new Insets(10, 10, 10, 10));
        subVBox1.getChildren().addAll(send, tf1);

        VBox subVBox2 = new VBox();
        subVBox2.setSpacing(10);
        subVBox2.setPadding(new Insets(10, 10, 10, 10));
        subVBox2.getChildren().addAll(recieve, tf2);

        HBox hBox1 = new HBox();
        hBox1.setSpacing(10);
        hBox1.setPrefSize(500, 200);
        hBox1.getChildren().addAll(subVBox1, listView1);
        HBox hBox2 = new HBox();
        hBox2.setSpacing(10);
        hBox2.setPrefSize(500, 200);
        hBox2.getChildren().addAll(subVBox2, listView2);
        HBox hBox3 = new HBox();
        hBox3.setSpacing(10);
        hBox3.setPrefSize(100, 50);
        hBox3.getChildren().addAll(tf3);

        // Set a fixed width for the ListView HBox
        HBox.setHgrow(tf1, Priority.ALWAYS);
        HBox.setHgrow(tf2, Priority.ALWAYS);
        tf1.setPrefWidth(200);
        tf2.setPrefWidth(200);
        HBox.setHgrow(send, Priority.ALWAYS);
        HBox.setHgrow(recieve, Priority.ALWAYS);
        send.setPrefWidth(200);
        recieve.setPrefWidth(200);
        HBox.setHgrow(listView1, Priority.ALWAYS);
        HBox.setHgrow(listView2, Priority.ALWAYS);
        listView1.setPrefWidth(300);
        listView2.setPrefWidth(300);
        HBox.setHgrow(tf3, Priority.ALWAYS);
        tf3.setPrefHeight(50);

        vBox.getChildren().addAll(hBox1, hBox2, hBox3);

        // Set the scene and the CSS file
        Scene scene = new Scene(vBox, 700, 450);
        File f = new File("src/resources/theme.css");
        scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
        primaryStage.setResizable(false);
        primaryStage.setTitle("API Currency Converter");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
