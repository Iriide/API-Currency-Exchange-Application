package org.example;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class TFListener implements ChangeListener<String> {
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
        if (updateValues.isUpdating) {
            return;
        }
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
