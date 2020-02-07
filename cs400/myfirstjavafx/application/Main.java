
package application;
////////////////////ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
//Title: (p05 )
//Files: (My First Java Fx)
//Course: (cs 400, spring, and 2019)
//
//Author: (di bao)
//Email: (dbao5@wisc.edu email address)
//Lecturer's Name: (depper)
//
////////////////////PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
//Partner Name:
//Partner Email:
//Partner Lecturer's Name:
//
//VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//_x__ Write-up states that pair programming is allowed for this assignment.
//_x__ We have both read and understand the course Pair Programming Policy.
//_x__ We have registered our team prior to the team registration deadline.
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
//Students who get help from sources other than their partner must fully
//acknowledge and credit those sources of help here. Instructors and TAs do
//not need to be credited here, but tutors, friends, relatives, room mates,
//strangers, and others do. If you received no outside help from either type
//of source, then please explicitly indicate NONE.
//
//Persons: (identify each person and describe their help in detail)
//Online Sources: (identify each URL and describe their assistance in detail)
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
	
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
		  //use the observable list to show the option to choose 
		  ObservableList<String> options = 
		      FXCollections.observableArrayList(
		          "Option 1",
		          "Option 2",
		          "Option 3"
		      );
		  //create a new combo box instance to set the items 
		  ComboBox<String> comboBox = new ComboBox<String>(options);
		  comboBox.setItems(options);
		  //create a instance to set the image view 
		  Image image = new Image("Myface.png");
		  ImageView iv1 = new ImageView();
	         iv1.setImage(image);//make the image view available 
	         Button button1 = new Button("Done");//create a button that do nothing 
	         CheckBox cb = new CheckBox("Checked");//the new user interface named checked box 
		  Label greetingLabel = new Label("CS400 MyFirstJavaFX");
			BorderPane root = new BorderPane();
			// add all the things on the scene 
			root.setTop(greetingLabel);
			root.setLeft(comboBox);
			root.setCenter(iv1);
			root.setBottom(button1);
			root.setRight(cb);
			Scene scene = new Scene(root,1000,1000);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Di's First JavaFX program");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
