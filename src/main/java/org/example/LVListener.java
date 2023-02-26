package org.example;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ListView;

import java.util.concurrent.CopyOnWriteArrayList;

public class LVListener implements ChangeListener<String> {
    UpdateValues updateValues;
    Boolean chooseListener;
    ListView<String> lv;
    public LVListener(ListView<String> listView, UpdateValues updateValues, boolean chooseListener){
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
