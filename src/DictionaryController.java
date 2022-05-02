import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.*;
import java.util.Map;

public class DictionaryController {

    private DictionaryMap currDictionary;
    private File currFile = null;

    private final String DB_Dir_PATH = (System.getProperty("user.dir") + "\\Db");
    private final String DB_FILE_EXTENSION = ".mydb";


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
    void btnAddEntryOnClick(ActionEvent event) {
        addNewEntry();
    }

    @FXML
    void btnDeleteEntryOnClick(ActionEvent event) {
        removeFromList();
    }

    @FXML
    void btnOpenOnClick(ActionEvent event) {
        chooseFile();
        UpdateCurrFileLabel();
    }

    @FXML
    void btnSaveAsOnClick(ActionEvent event) {
        saveToNewFile();
        UpdateCurrFileLabel();
    }

    @FXML
    void btnSaveOnClick(ActionEvent event) {
        SaveTofile(currFile);
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
        currDictionary = new DictionaryMap();
        chooseInitialFile();
        currDictionary = loadDictionaryFromFile(currFile);
        populateDictionaryList();
        UpdateCurrFileLabel();
    }


    private void populateDictionaryList() {

        if (currDictionary != null) {

            for (Map.Entry<String, DictionaryEntry> entry : currDictionary.entrySet()) {
                TableView.getItems().add(entry.getValue());
            }
        }

    }

    private void repopulateDictionaryList() {
        if (currDictionary != null) {
            TableView.getItems().clear();
            for (Map.Entry<String, DictionaryEntry> entry : currDictionary.entrySet()) {
                TableView.getItems().add(entry.getValue());
            }
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
        addToList(currDictionary, new DictionaryEntry(TFKey.getText().trim(), TFValue.getText().trim()));
    }


    private void addToList(DictionaryMap map, DictionaryEntry newEntry) {
        if (map != null && newEntry != null) {


            map.insert(newEntry);
            repopulateDictionaryList();
        }
    }


    private void removeFromList() {
        DictionaryEntry entry = (DictionaryEntry) TableView.getSelectionModel().getSelectedItem();
        if (entry != null) {
            currDictionary.remove(entry.getKey());
            repopulateDictionaryList();
        } else {
            JOptionPane.showMessageDialog(null, "Please select an entry");
        }
    }


    private void editEntryValue() {
        DictionaryEntry entry = (DictionaryEntry) TableView.getSelectionModel().getSelectedItem();
        if (entry != null) {
            String newVal = JOptionPane.showInputDialog("Please enter a new value for '" + entry.getKey() + "'");
            if (newVal == null)//in case of cancel
                return;

            if (newVal.trim().length() != 0) {
                currDictionary.find(entry.getKey()).setValue(newVal);
                repopulateDictionaryList();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select an entry");


        }

    }

    private DictionaryMap loadDictionaryFromFile(File file) {
        DictionaryMap result = null;

        FileInputStream fileIn = null;
        ObjectInputStream objectIn = null;
        if (file != null) {
            try {
                fileIn = new FileInputStream(file);
                objectIn = new ObjectInputStream(fileIn);

                result = (DictionaryMap) objectIn.readObject();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Cannot load Dictionary from file! file name: " + file.getName());
                result = null;
            } finally {

                try {
                    objectIn.close();
                } catch (Exception e) {
                }
                try {
                    fileIn.close();
                } catch (Exception e) {
                }
                if (result == null)
                    result = new DictionaryMap();
            }
        } else
            result = new DictionaryMap();
        return result;
    }


    private void saveToNewFile() {


        String file_name = JOptionPane.showInputDialog("please choose file name");
        if (file_name == null)
            return;
        if (file_name.trim().length() > 0) {
            File file = new File(DB_Dir_PATH + "\\" + file_name + DB_FILE_EXTENSION); //initialize File object and passing path as argument
            SaveTofile(file);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid file name");
        }

    }

    private void SaveTofile(File file) {
        if (file == null) {
            JOptionPane.showMessageDialog(null, "Cannot save file!");
            return;
        }

        try {
            boolean result = file.createNewFile();  //creates a new file
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(currDictionary);
            oos.close();
            currFile = file;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Cannot save file!");
        }
    }

    private void chooseFile() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Db File from this folder");

        File userDirectory = new File(DB_Dir_PATH);
        fileChooser.setInitialDirectory(userDirectory);
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            DictionaryMap map = loadDictionaryFromFile(selectedFile);
            if (map != null) {
                currFile = selectedFile;
                currDictionary = map;
                repopulateDictionaryList();
            }
        }
    }

    private void chooseInitialFile() {
        File folder = new File(DB_Dir_PATH);
        File[] listOfFiles = folder.listFiles();

        File iFile = null;
        boolean foundOne = false;

        for (int i = 0; i < listOfFiles.length && !foundOne; i++) {
            iFile = listOfFiles[i];
            if (iFile.isFile()) {
                if (iFile.getName().endsWith(DB_FILE_EXTENSION)) ;
                {
                    foundOne = true;
                }
            }
        }
        if (foundOne) {
            currFile = iFile;
        }
    }

    private void UpdateCurrFileLabel() {
        if (currFile == null) {
            LabelcurrFile.setText("No file selected");
        } else {
            LabelcurrFile.setText("Current file: " + currFile.getName());
        }
    }
}











