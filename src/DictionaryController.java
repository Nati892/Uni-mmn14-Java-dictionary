import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Iterator;

public class DictionaryController {

    private DictionaryMap currDictionary;

    @FXML
    private TableView TableView;

    @FXML
    private TableColumn<DictionaryEntry, String> C1Key;

    @FXML
    private TableColumn<DictionaryEntry, String> C2Value;

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
        System.out.println("btnAddEntryOnClick clicked");//Debug
    }

    @FXML
    void btnDeleteEntryOnClick(ActionEvent event) {
        System.out.println("btnDeleteEntryOnClick clicked");//Debug

    }

    @FXML
    void btnOpenOnClick(ActionEvent event) {
        System.out.println("btnOpenOnClick clicked");//Debug

    }

    @FXML
    void btnSaveAsOnClick(ActionEvent event) {
        System.out.println("btnSaveAsOnClick clicked");//Debug

    }

    @FXML
    void btnSaveOnClick(ActionEvent event) {
        System.out.println("btnSaveOnClick clicked");//Debug

    }

    @FXML
    void btnUpdateEntryOnClick(ActionEvent event) {
        System.out.println("btnUpdateEntryOnClick clicked");//Debug
    }


    @FXML
    void initialize() {
        System.out.println("started");//Debug
        C1Key.setCellValueFactory(new PropertyValueFactory<>("key"));//setup cells
        C2Value.setCellValueFactory(new PropertyValueFactory<>("value"));
        currDictionary = new DictionaryMap();
        populateDictionaryList();//first population

    }


    private void populateDictionaryList() {

        for (int i = 1; i < 70; i++) {
            currDictionary.insert(new DictionaryEntry("key" + i, "val" + i));
        }
        System.out.println(currDictionary);
        Iterator<DictionaryEntry> iterator = currDictionary.iterator();
        while (iterator.hasNext())
        {    System.out.println("adding");
          TableView.getItems().add(iterator.next());}

    }

}









