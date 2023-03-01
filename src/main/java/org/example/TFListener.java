package main.java.org.example;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class TFListener implements ChangeListener<String> {
    /**
     * Class for getting the values from the textfields and listviews and updating the values after a change
     * @param updateValues - UpdateValues object used to update the values in the textfields
     *
     * @param chooseListener - boolean used to choose which listener is used (we have two listeners for the listviews,
     *                         and they need to change the values of the opposite textfield)
     * @param lv - ListView<String> object the listener is attached to
     */
    UpdateValues updateValues;
    Boolean chooseListener;
    TextField tf;
    public TFListener(TextField tf, UpdateValues updateValues, boolean chooseListener){
        this.tf = tf;
        this.updateValues = updateValues;
        this.chooseListener = chooseListener;
    }
    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        // prevents the listener from updating the values when the values are being updated
        if (updateValues.isUpdating) {
            return;
        }
        // prevents the user from entering anything but numbers and a dot in the textfield
        if (!newValue.matches("\\d*\\.?\\d*")) {
            tf.setText(oldValue);
        }
        try {
            updateValues.update(chooseListener);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
