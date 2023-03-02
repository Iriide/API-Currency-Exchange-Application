package main.java.org.example;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;


public class LVListener implements ChangeListener<String> {
    /**
     * Class for getting the values from the textfields and listviews and updating the values after a change
     * @param updateValues - UpdateValues object used to update the values in the textfields
     *
     * @param chooseListener - boolean used to choose which listener is used (we have two listeners for the listviews,
     *                         and they need to change the values of the opposite textfield)
     * @param lv - ComboBox<String> object the listener is attached to
     */
    UpdateValues updateValues;
    Boolean chooseListener;
    ComboBox<String> lv;
    public LVListener(ComboBox<String> listView, UpdateValues updateValues, boolean chooseListener){
        this.lv = listView;
        this.updateValues = updateValues;
        this.chooseListener = chooseListener;
    }


    @Override
    public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
        try {
            updateValues.update(chooseListener);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
