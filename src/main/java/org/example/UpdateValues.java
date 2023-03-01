package main.java.org.example;

import javafx.scene.control.ListView;

import javafx.scene.control.*;

public class UpdateValues {
    /*
     * Class for updating values in GUI
     * @param isUpdating - Boolean used to prevent infinite loop of triggering listeners
     * @param tf1 - TextField with amount of currency 1
     * @param tf2 - TextField with amount of currency 2
     * @param tf3 - Label with exchange rate
     * @param listView1 - ListView with currency 1
     * @param listView2 - ListView with currency 2
     * @param apiRequest - APIRequest object
     */
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
        //prevent infinite loop of triggering listeners
        isUpdating = true;
        //check if all fields are filled
        if(listView1.getSelectionModel().getSelectedItem() == null || listView2.getSelectionModel().getSelectedItem() == null || tf1.getText().equals("") || tf2.getText().equals("")) {
            return;
        }
        //check which listener triggered update and execute the update
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
