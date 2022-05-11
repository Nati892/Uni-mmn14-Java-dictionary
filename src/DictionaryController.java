import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.*;
import java.util.Map;

public class DictionaryController {

    private DictionaryMapDb currDictionaryMapDb;

    @FXML
    private TableView TableView;

    @FXML
    private TableColumn<DictionaryEntry, String> C1Key;

    @FXML
    private TableColumn<DictionaryEntry, String> C2Value;

    @FXML
    private Label LabelcurrFile;

    @FXML
    private TextField TFKey;

    @FXML
    private TextField TFValue;

    @FXML
    private Button btnAddEntry;

    @FXML
    private Button btnDeleteEntry;

    @FXML
    private Button btnOpen;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnSaveAs;

    @FXML
    private Button btnUpdateEntry;

    @FXML
    private Button btSearch;


    @FXML
    void btSearchOnClick(ActionEvent event) {
        String key = JOptionPane.showInputDialog("Enter key name to search");
        if (key != null) {
            key = key.trim();
            if (!SearchInTable(key))
                JOptionPane.showMessageDialog(null, "cant find \"" + key + "\"");
        }
    }

    @FXML
    void btnAddEntryOnClick(ActionEvent event) {
        addNewEntry();
    }

    @FXML
    void btnDeleteEntryOnClick(ActionEvent event) {
        removeFromList();
    }

    @FXML
    void btnOpenOnClick(ActionEvent event) {
        if (currDictionaryMapDb.chooseFileFromDir()) {
            repopulateDictionaryList();
            UpdateCurrFileLabel();
        }
    }

    @FXML
    void btnSaveAsOnClick(ActionEvent event) {
        saveToNewFile();
        UpdateCurrFileLabel();
    }

    @FXML
    void btnSaveOnClick(ActionEvent event) {
        if (currDictionaryMapDb.getSelectedFileName()!=null)
            currDictionaryMapDb.saveToCurrFile();
        else
            saveToNewFile();
        UpdateCurrFileLabel();
    }

    @FXML
    void btnUpdateEntryOnClick(ActionEvent event) {
        editEntryValue();
    }


    @FXML
    void initialize() {

        C1Key.setCellValueFactory(new PropertyValueFactory<>("key"));//setup cells
        C2Value.setCellValueFactory(new PropertyValueFactory<>("value"));
        currDictionaryMapDb = DictionaryMapDb.getInstance();

        populateDictionaryList();
        UpdateCurrFileLabel();

    }


    private void populateDictionaryList() {
        if (currDictionaryMapDb != null) {
            if (currDictionaryMapDb.entrySet() != null) {
                for (Map.Entry<String, DictionaryEntry> entry : currDictionaryMapDb.entrySet()) {
                    TableView.getItems().add(entry.getValue());
                }
            }
        }

    }

    private void repopulateDictionaryList() {//clear the table and populate it again
        if (currDictionaryMapDb != null) {
            TableView.getItems().clear();
            populateDictionaryList();
        }
    }


    private void addNewEntry() {
        if (TFKey.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Missing key!");
            return;
        }
        if (TFValue.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Missing Value!");
            return;
        }
        currDictionaryMapDb.addEntry(new DictionaryEntry(TFKey.getText().trim(), TFValue.getText().trim()));
        repopulateDictionaryList();
    }


    private void removeFromList() {
        DictionaryEntry entry = (DictionaryEntry) TableView.getSelectionModel().getSelectedItem();
        if (entry != null) {
            currDictionaryMapDb.removeEntry(entry.getKey());
            repopulateDictionaryList();
        } else {
            JOptionPane.showMessageDialog(null, "Please select an entry");
        }
    }


    //find an entry if exists and then edit it
    private void editEntryValue() {
        DictionaryEntry entry = (DictionaryEntry) TableView.getSelectionModel().getSelectedItem();
        if (entry != null) {
            String newVal = JOptionPane.showInputDialog("Please enter a new value for '" + entry.getKey() + "'",entry.getValue());
            if (newVal == null)//in case of cancel
                return;

            if (newVal.trim().length() != 0) {
                currDictionaryMapDb.getEntry(entry.getKey()).setValue(newVal);
                repopulateDictionaryList();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select an entry");
        }
    }


    private void saveToNewFile() {

        String file_name = JOptionPane.showInputDialog("please choose file name");
        if (file_name == null)
            return;
        file_name = file_name.trim();
        if (file_name.length() > 0) {
            currDictionaryMapDb.SaveToNewFile(file_name);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid file name");
        }
    }


    private void UpdateCurrFileLabel() {//update the current file name label
        if (currDictionaryMapDb.getSelectedFileName() == null) {
            LabelcurrFile.setText("No file selected");
        } else {
            LabelcurrFile.setText("Current file: " + currDictionaryMapDb.getSelectedFileName());
        }
    }


    private boolean SearchInTable(String key) {
        boolean result = true;
        DictionaryEntry found = null;
        if ((found = currDictionaryMapDb.getEntry(key)) != null) {
            TableView.getSelectionModel().select(found);//find the entry and choose it
            TableView.scrollTo(found);//go to found entry
        } else
            result = false;
        return result;
    }
}











