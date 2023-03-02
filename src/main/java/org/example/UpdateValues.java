package main.java.org.example;

import javafx.scene.control.*;

public class UpdateValues {
    /*
     * Class for updating values in GUI
     * @param isUpdating - Boolean used to prevent infinite loop of triggering listeners
     * @param tf1 - TextField with amount of currency 1
     * @param tf2 - TextField with amount of currency 2
     * @param tf3 - Label with exchange rate
     * @param box1 - ComboBox with currency 1
     * @param box2 - ComboBox with currency 2
     * @param apiRequest - APIRequest object
     */
    Boolean isUpdating = false;
    private TextField tf1;
    private TextField tf2;
    private Label tf3;
    private ComboBox<String> box1;
    private ComboBox<String> box2;
    private APIRequest apiRequest;

    UpdateValues(TextField tf1, TextField tf2, Label tf3, ComboBox<String> listView1, ComboBox<String> listView2, APIRequest apiRequest) {
        this.tf1 = tf1;
        this.tf2 = tf2;
        this.tf3 = tf3;
        this.box1 = listView1;
        this.box2 = listView2;
        this.apiRequest = apiRequest;
    }
    public void update(Boolean chooseListener) throws Exception {
        //prevent infinite loop of triggering listeners
        isUpdating = true;
        //check if all fields are filled
        if(box1.getSelectionModel().getSelectedItem() == null || box2.getSelectionModel().getSelectedItem() == null || tf1.getText().equals("") || tf2.getText().equals("")) {
            return;
        }
        //check which listener triggered update and execute the update
        if(chooseListener) {
            String[] result = apiRequest.calculate(box1.getSelectionModel().getSelectedItem(), box2.getSelectionModel().getSelectedItem(), Double.parseDouble(tf1.getText()));
            tf2.setText(result[0]);
            tf3.setText(result[1]);
        }
        else{
            String[] result = apiRequest.calculate(box1.getSelectionModel().getSelectedItem(), box2.getSelectionModel().getSelectedItem(), Double.parseDouble(tf2.getText()));
            tf1.setText(result[0]);
            tf3.setText(result[1]);
        }
        isUpdating = false;
    }
}
