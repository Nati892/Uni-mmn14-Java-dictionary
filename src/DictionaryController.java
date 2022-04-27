import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.*;
import java.security.PublicKey;
import java.util.Iterator;

public class DictionaryController {

    private DictionaryMap currDictionary;
    private File currFile = null;

    @FXML
    private TableView TableView;

    @FXML
    private TableColumn<DictionaryEntry, String> C1Key;

    @FXML
    private TableColumn<DictionaryEntry, String> C2Value;


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
        openFile();
    }

    @FXML
    void btnSaveAsOnClick(ActionEvent event) {
        String file_name = JOptionPane.showInputDialog("please choose file name");
        if (file_name.trim().length() > 0) {
            saveNewFile(file_name);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid file name");


        }
    }

    @FXML
    void btnSaveOnClick(ActionEvent event) {
        SaveTofile(currFile);
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
        populateDictionaryList();//first population

    }


    private void populateDictionaryList() {

        for (int i = 1; i < 30; i++) {
            currDictionary.insert(new DictionaryEntry("key" + i, "val" + i));
        }
        System.out.println(currDictionary);
        Iterator<DictionaryEntry> iterator = currDictionary.iterator();
        while (iterator.hasNext()) {
            TableView.getItems().add(iterator.next());
        }

    }

    private void repopulateDictionaryList() {

        TableView.getItems().clear();
        Iterator<DictionaryEntry> iterator = currDictionary.iterator();
        while (iterator.hasNext()) {
            TableView.getItems().add(iterator.next());
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
            currDictionary.remove(entry);
            repopulateDictionaryList();
        } else {
            JOptionPane.showMessageDialog(null, "Please select an entry");
        }
    }


    private void editEntryValue() {
        DictionaryEntry entry = (DictionaryEntry) TableView.getSelectionModel().getSelectedItem();
        if (entry != null) {
            String newVal = JOptionPane.showInputDialog("Please enter a new value for '" + entry.getKey() + "'");
            if (newVal.trim().length() != 0) {
                int i = currDictionary.find(entry);
                currDictionary.getEntry(i).setValue(newVal);
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
        try {
            fileIn = new FileInputStream(file);
            objectIn = new ObjectInputStream(fileIn);

            result = (DictionaryMap) objectIn.readObject();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Cannot load Dictionary from file!");
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
            return result;
        }}


        private void saveNewFile (String newFileName){

            String path = System.getProperty("user.dir");
            path += "\\Db";
            File file = new File(path + "\\" + newFileName + ".mydb"); //initialize File object and passing path as argument
            SaveTofile(file);

        }

        private void SaveTofile (File file){
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

        private void openFile () {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Db File from this folder");
            String path = System.getProperty("user.dir");
            path += "\\Db";
            File userDirectory = new File(path);
            fileChooser.setInitialDirectory(userDirectory);
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
                DictionaryMap map = loadDictionaryFromFile(selectedFile);
                System.out.println(map);
                if (map != null) {
                    currFile = selectedFile;
                    currDictionary = map;
                    repopulateDictionaryList();
                }
            }

        }


    }









