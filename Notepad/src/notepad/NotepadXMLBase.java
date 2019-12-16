package notepad;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import java.io.File; 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException; 
import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class NotepadXMLBase extends AnchorPane {

    protected final MenuBar menuBar;
    final Clipboard clipboard;
    protected final Menu fileItem;
    protected final MenuItem newItem;
    protected final MenuItem openItem;
    protected final MenuItem saveItem;
    protected final MenuItem exitItem;
    protected final Menu editItem;
    protected final MenuItem cutItem;
    protected final MenuItem copyItem;
    protected final MenuItem pasteItem;
    protected final MenuItem deleteItem;
    protected final MenuItem selectAllItem;
    protected final Menu helpItem;
    protected final MenuItem aboutItem;
    protected final TextArea textArea;
    private String firstPath; //dir path save
    private String text;
    private final FileChooser fileChooser;
    boolean toClear;
    final ClipboardContent content;
    private String path;
    
    public NotepadXMLBase() {
        
        toClear=false;
        path=null;
        menuBar = new MenuBar();
        fileItem = new Menu();
        newItem = new MenuItem();
        openItem = new MenuItem();
        saveItem = new MenuItem();
        exitItem = new MenuItem();
        editItem = new Menu();
        cutItem = new MenuItem();
        copyItem = new MenuItem();
        pasteItem = new MenuItem();
        deleteItem = new MenuItem();
        selectAllItem = new MenuItem();
        helpItem = new Menu();
        aboutItem = new MenuItem();
        textArea = new TextArea();
        fileChooser = new FileChooser();
        clipboard = Clipboard.getSystemClipboard();
        content = new ClipboardContent();
        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(640.0);
        setPrefWidth(720.0);

        AnchorPane.setBottomAnchor(menuBar, 615.0);
        AnchorPane.setLeftAnchor(menuBar, 0.0);
        AnchorPane.setRightAnchor(menuBar, 588.0);
        AnchorPane.setTopAnchor(menuBar, 0.0);
        menuBar.setLayoutX(14.0);
        menuBar.setLayoutY(14.0);
        menuBar.setStyle("-fx-background-color: white;");

        fileItem.setMnemonicParsing(false);
        fileItem.setText("File");

        newItem.setMnemonicParsing(false);
        newItem.setOnAction(this::newItemAction);
        newItem.setText("New");
        newItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));

        openItem.setMnemonicParsing(false);
        openItem.setOnAction(this::openItemAction);
        openItem.setText("Open");
        openItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));

        saveItem.setMnemonicParsing(false);
        saveItem.setOnAction(this::saveItenAction);
        saveItem.setText("Save");
        saveItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));

        exitItem.setMnemonicParsing(false);
        exitItem.setOnAction(this::exitItemAction);
        exitItem.setText("Exit");
        exitItem.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));

        editItem.setMnemonicParsing(false);
        editItem.setText("Edit");

        cutItem.setMnemonicParsing(false);
        cutItem.setOnAction(this::cutItemAction);
        cutItem.setText("Cut");

        copyItem.setMnemonicParsing(false);
        copyItem.setOnAction(this::copyItemAction);
        copyItem.setText("Copy");

        pasteItem.setMnemonicParsing(false);
        pasteItem.setOnAction(this::pasteItemAction);
        pasteItem.setText("Paste");

        deleteItem.setMnemonicParsing(false);
        deleteItem.setOnAction(this::deleteItemAction);
        deleteItem.setText("Delete");

        selectAllItem.setMnemonicParsing(false);
        selectAllItem.setOnAction(this::selectAllItemAction);
        selectAllItem.setText("Select All");

        helpItem.setMnemonicParsing(false);
        helpItem.setText("Help");

        aboutItem.setMnemonicParsing(false);
        aboutItem.setOnAction(this::aboutItemAction);
        aboutItem.setText("About");

        AnchorPane.setBottomAnchor(textArea, 0.0);
        AnchorPane.setLeftAnchor(textArea, 0.0);
        AnchorPane.setRightAnchor(textArea, 0.0);
        AnchorPane.setTopAnchor(textArea, 25.0);
        textArea.setLayoutX(76.0);
        textArea.setLayoutY(89.0);
        textArea.setPrefHeight(200.0);
        textArea.setPrefWidth(200.0);
        textArea.setPromptText("Write Something...");
        textArea.setFont(new Font(14.0));

        fileItem.getItems().add(newItem);
        fileItem.getItems().add(openItem);
        fileItem.getItems().add(saveItem);
        fileItem.getItems().add(exitItem);
        menuBar.getMenus().add(fileItem);
        editItem.getItems().add(cutItem);
        editItem.getItems().add(copyItem);
        editItem.getItems().add(pasteItem);
        editItem.getItems().add(deleteItem);
        editItem.getItems().add(selectAllItem);
        menuBar.getMenus().add(editItem);
        helpItem.getItems().add(aboutItem);
        menuBar.getMenus().add(helpItem);
        getChildren().add(menuBar);
        getChildren().add(textArea);

    }
    protected void readItemLowStream(File file){
        try{ 
            if (file != null) {
              FileInputStream inputLowStream =new FileInputStream(file);
              byte[] inputBytes =new byte[inputLowStream.available()];
              int check = inputLowStream.read(inputBytes);
              text=new String(inputBytes);
              textArea.setText(text);
              path=file.getAbsolutePath();
        System.out.println(path);
        path=file.getPath();
        System.out.println(path);
              inputLowStream.close();
        }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    protected void readItemHighStream(File file){
        try{ 
            if (file != null){
              FileReader fr =new FileReader(file);
              char[] inputChars=new char[4096]; 
              fr.read(inputChars);
              text=new String(inputChars);
              textArea.setText(text);
              fr.close();
        }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    protected boolean writeItemLowStream(File file){
        try{ 
            if (file != null) {
            FileOutputStream outputLowStream = new FileOutputStream(file);
            byte[] outputBytes = textArea.getText().getBytes();
            outputLowStream.write(outputBytes);
            outputLowStream.flush();
            outputLowStream.close();
            return true;
        }
            return false;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } 
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }  
    }
    protected boolean writeItemHighStream(File file){
        try{ 
            if (file != null) {
            FileWriter fw= new FileWriter(file);
            String outputString = textArea.getText();
            fw.write(outputString);
            fw.flush();
            fw.close();
            return true;
        }
            return false;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } 
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }  
    }
    protected void newItemAction(javafx.event.ActionEvent actionEvent){
        text= textArea.getText();
        if(!(text.isEmpty())){
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Notepad");
            alert.setHeaderText("Do you want to save the changes? ");
            alert.setContentText("Choose your option.");
            ButtonType buttonYes = new ButtonType("Yes");
            ButtonType buttonNo = new ButtonType("No");
            alert.getButtonTypes().setAll(buttonYes, buttonNo);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonYes){
                fileChooser.setTitle("Save As");
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(extFilter);
                File file = fileChooser.showSaveDialog(this.getScene().getWindow());
                Notepad.getStage().setTitle(path);
                toClear=writeItemLowStream(file);
                
            if(toClear)
            {
               textArea.clear();
               toClear=false;
               path=null;
               Notepad.getStage().setTitle("Unsaved File");
            }}
            else if (result.get() == buttonNo) {
            textArea.clear();
            Notepad.getStage().setTitle("Unsaved File");
            toClear=false;
    }} 
        else{
            Notepad.getStage().setTitle("Unsaved File");
        }
    }
    protected void openItemAction(javafx.event.ActionEvent actionEvent){
        fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Text", "*.txt"),
                        new FileChooser.ExtensionFilter("All Files", "*.*"));
        fileChooser.setTitle("Open Resource File"); 
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(this.getScene().getWindow());
         if (file != null) {
            textArea.clear();
        //    readItemLowStream(file);
            readItemHighStream(file);
            path= file.getAbsolutePath();
            Notepad.getStage().setTitle(path);
        }
    }

    protected void saveItem(){
        File file;
        if(path ==null){
        fileChooser.setTitle("Save As");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        file = fileChooser.showSaveDialog(this.getScene().getWindow());
        
        
        }
        else{
            file= new File(path);
        }
        if (file !=null)
        {
        path=file.getAbsolutePath();
        Notepad.getStage().setTitle(path);
        text= textArea.getText();
        if(!(text.isEmpty())){
            //toClear=writeItemLowStream(file);
            toClear= writeItemHighStream(file);
        }
        if(toClear)
        {
            toClear=false;
        }
        }
    }
    protected void saveItenAction(javafx.event.ActionEvent actionEvent){
       saveItem();
    }
    protected void exitItemAction(javafx.event.ActionEvent actionEvent){
        text= textArea.getText();
        if(!(text.isEmpty())){
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Notepad");
            alert.setHeaderText("Do you want to save the changes? ");
            alert.setContentText("Choose your option.");
            ButtonType buttonYes = new ButtonType("Yes");
            ButtonType buttonNo = new ButtonType("No");
            alert.getButtonTypes().setAll(buttonYes, buttonNo);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonYes){
                if(path==null){
                fileChooser.setTitle("Save As");
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(extFilter);
                File file = fileChooser.showSaveDialog(this.getScene().getWindow());
                toClear=writeItemLowStream(file);
                }
                else{
                    File file=new File(path);
                    toClear=writeItemLowStream(file);
                }
                if(toClear)
                {
                   toClear=false;
                   Platform.exit();
                }
                
            }
            else if (result.get() == buttonNo) {
                toClear=false;
                Platform.exit();
            
    }} 
    }

    protected void cutItemAction(javafx.event.ActionEvent actionEvent){
        textArea.cut();
    }

    protected void copyItemAction(javafx.event.ActionEvent actionEvent){
        /*String copyText = textArea.getSelectedText().toString();
        content.putString(copyText);
        clipboard.setContent(content);
        */
        textArea.copy();
    }

    protected void pasteItemAction(javafx.event.ActionEvent actionEvent){
        textArea.paste();
    }

    protected void deleteItemAction(javafx.event.ActionEvent actionEvent){
        textArea.replaceSelection("");
    }

    protected void selectAllItemAction(javafx.event.ActionEvent actionEvent){
        textArea.selectAll();
    }

    protected void aboutItemAction(javafx.event.ActionEvent actionEvent){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("A project just for fun BLA BLA BLA");
        alert.setContentText("Manar Bla Bla");
        alert.showAndWait();  
    }

}
