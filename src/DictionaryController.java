import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;

import java.awt.event.MouseEvent;

public class DictionaryController {


    @FXML
    private ListView<String> DictionaryList;

    @FXML
    private ScrollBar ScrollBar;

    @FXML
    void OnDragEntered(DragEvent event) {

    }

    @FXML
    void OnDragExited(DragEvent event) {

    }

    @FXML
    void OnDragOver(DragEvent event) {

    }

    @FXML
    void OnMouseDragOver(MouseDragEvent event) {

    }

    @FXML
    void OnMouseDragReleased(MouseDragEvent event) {

    }

    @FXML
    void ScrollBarOnMouseClicked(MouseEvent event) {

    }

    @FXML
    void onMouseDragEntered(MouseDragEvent event) {

    }

    @FXML
    public void initialize() {
        System.out.println("a");
     //   populateDictionaryList();
        //TODO set vbox to not resizable
        //TODO draw grid lines

    }

    private void populateDictionaryList(){
        char c='0';
        for (int i=0;i<70;i++)
        {
            c++;
            DictionaryList.getItems().addAll(Character.toString(c));
        }
    }




}

