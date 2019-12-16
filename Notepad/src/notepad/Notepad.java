/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notepad;

import javafx.application.Application;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author DELL
 */
public class Notepad extends Application {
    private  static Stage stage;
    public  static Stage getStage() {return stage;} 
    
    @Override
    public void start(Stage stage) throws Exception {
       // Parent root = FXMLLoader.load(getClass().getResource("NotepadXML.fxml"));
        this.stage=stage;
        NotepadXMLBase rootXML=new NotepadXMLBase();
   //    rootXML.newItem.addEventHandler(EventType.ROOT,);
        
        
        Scene scene = new Scene(rootXML);
        stage.setScene(scene);
        stage.setTitle("Notepad Started...");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
