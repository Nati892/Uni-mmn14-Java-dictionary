import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.*;
import java.util.Map;
import java.util.Set;

/**
 * This is a singleton class that handles communication with the current DictionaryMap and files
 */
public class DictionaryMapDb {

    private static DictionaryMapDb DictionaryMapDb_instance = null;

    private DictionaryMap dictionaryMap = null;
    private File currentFile = null;
    private final String DB_Dir_PATH = (System.getProperty("user.dir") + "\\Db"); //the Db directory path
    private final String DB_FILE_EXTENSION = ".mydb";//the db files extension


    public static DictionaryMapDb getInstance() {
        if (DictionaryMapDb_instance == null)
            DictionaryMapDb_instance = new DictionaryMapDb();
        return DictionaryMapDb_instance;
    }


    private DictionaryMapDb() {
        chooseInitialFile();
        DictionaryMap temp = loadDictionaryFromFile(currentFile);
        if (temp != null)
            dictionaryMap = temp;// load the dictionary from the file selected
        else
            dictionaryMap = new DictionaryMap();
    }


    private void chooseFile() {
        FileChooser fileChooser = new FileChooser();//using preset file chooser
        fileChooser.setTitle("Choose Db File from this folder only!");

        File userDirectory = new File(DB_Dir_PATH);
        fileChooser.setInitialDirectory(userDirectory);
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            DictionaryMap map = loadDictionaryFromFile(selectedFile);
            if (map != null) {
                currentFile = selectedFile;
                dictionaryMap = map;
            }

        }
        if (dictionaryMap == null)
            dictionaryMap = new DictionaryMap();
    }


    private DictionaryMap loadDictionaryFromFile(File file) {
        DictionaryMap result = null; //the new dictionary map to work with
        FileInputStream fileIn = null;
        ObjectInputStream objectIn = null;
        if (file != null) {
            try {//try to open and read from the file
                fileIn = new FileInputStream(file);
                objectIn = new ObjectInputStream(fileIn);

                result = (DictionaryMap) objectIn.readObject();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Cannot load Dictionary from file! file name: " + file.getName());
                result = null;
            } finally {

                try {//try to close file related objects
                    objectIn.close();
                } catch (Exception e) {
                }
                try {
                    fileIn.close();
                } catch (Exception e) {
                }
            }
        } else
            result = null;
        return result;
    }


    public boolean chooseFileFromDir() {
        boolean newFileChosen = false;

        FileChooser fileChooser = new FileChooser();//using preset file chooser
        fileChooser.setTitle("Choose Db File from this folder only!");
        File userDirectory = new File(DB_Dir_PATH);
        fileChooser.setInitialDirectory(userDirectory);
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            DictionaryMap map = loadDictionaryFromFile(selectedFile);
            if (map != null) {
                currentFile = selectedFile;
                dictionaryMap = map;
                newFileChosen = true;
            } else {
                if (dictionaryMap == null)
                    dictionaryMap = new DictionaryMap();
            }
        }
        return newFileChosen;
    }


    private void chooseInitialFile() {// called on startup, and chooses first file
        File folder = new File(DB_Dir_PATH);
        File[] listOfFiles = folder.listFiles();
        File iFile = null;
        boolean foundOne = false;
        if (listOfFiles != null) {
            for (int i = 0; i < listOfFiles.length && !foundOne; i++) {//catch the first file that as the right extension and try to work with that one
                iFile = listOfFiles[i];
                if (iFile.isFile()) {
                    if (iFile.getName().endsWith(DB_FILE_EXTENSION)) ;
                    {
                        foundOne = true;
                    }
                }
            }
            if (foundOne) {
                currentFile = iFile;
            }
        }
    }


    public boolean addEntry(DictionaryEntry newEntry) {
        boolean isAdded = false;
        if (dictionaryMap != null && newEntry != null) {
            dictionaryMap.insert(newEntry);//insert a new object to the map and reload table
            isAdded = true;
        }
        return isAdded;
    }

    public boolean removeEntry(DictionaryEntry newEntry) {
        boolean isRemoved = false;
        if (dictionaryMap != null && newEntry != null) {
            dictionaryMap.remove(newEntry.getKey());//insert a new object to the map and reload table
            isRemoved = true;
        }
        return isRemoved;
    }

    public boolean removeEntry(String key) {
        boolean isRemoved = false;
        if (dictionaryMap != null) {
            dictionaryMap.remove(key);//insert a new object to the map and reload table
            isRemoved = true;
        }
        return isRemoved;
    }

    public boolean EditEntry(DictionaryEntry newEntry) {
        boolean isEdited = false;
        if (dictionaryMap != null && newEntry != null) {
            dictionaryMap.getEntry(newEntry.getKey()).setValue(newEntry.getValue());
            isEdited = true;
        }
        return isEdited;
    }

    public String getSelectedFileName() {
        if (currentFile != null)
            return currentFile.getName();
        return null;
    }

    private void SaveTofile(File file) {
        if (file == null) {
            file = currentFile;
        }
        try {
            if (!file.equals(currentFile))
                file.createNewFile();  //creates a new file
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(dictionaryMap);
            oos.close();
            currentFile = file;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Cannot save file!");
        }
    }

    public void saveToCurrFile() {
        if (currentFile != null)
            SaveTofile(currentFile);
    }

    public void SaveToNewFile(String fileName) {
        File file = new File(DB_Dir_PATH + "\\" + fileName + DB_FILE_EXTENSION); //initializing File object, and passing path as argument
        SaveTofile(file);
    }

    public DictionaryEntry getEntry(String key) {
        if (dictionaryMap != null)
            return dictionaryMap.getEntry(key);
        return null;
    }

    public DictionaryEntry getEntry(int index) {
        if (dictionaryMap != null)
            return dictionaryMap.getEntry(index);
        return null;
    }

    public Set<Map.Entry<String, DictionaryEntry>> entrySet() {
        if (dictionaryMap != null)
            return dictionaryMap.entrySet();
        return null;
    }


}



