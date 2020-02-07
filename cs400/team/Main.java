package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application implements EventHandler<ActionEvent> {

  private TableView<Computer> table = new TableView<Computer>();
  private final ObservableList<Computer> data =
      FXCollections.observableArrayList(
          new Computer("computer", "9313", "03/13"),
          new Computer("printer", "1234", "03/15")
          
);
  
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) {
    Scene scene = new Scene(new Group());
    stage.setTitle("CompHelp Inventory");
    stage.setWidth(450);
    stage.setHeight(550);

    final Label label = new Label("Information Search");
    label.setFont(new Font("Arial", 20));

    table.setEditable(true);

    TableColumn itemCol = new TableColumn("Item");
    itemCol.setMinWidth(200);
    itemCol.setCellValueFactory(new PropertyValueFactory<Computer, String>("firstName"));
    

    TableColumn locationCol = new TableColumn("Location");
    locationCol.setMinWidth(100);
    locationCol.setCellValueFactory(new PropertyValueFactory<Computer, String>("lastName"));

    TableColumn dateCol = new TableColumn("Date");
    dateCol.setMinWidth(100);
    dateCol.setCellValueFactory(new PropertyValueFactory<Computer, String>("email"));

    FilteredList<Computer> fileComputer = new FilteredList(data, p -> true);// Pass the data to a
                                                                            // filtered list
    table.setItems(fileComputer);// Set the table's items using the filtered list
    table.getColumns().addAll(itemCol, locationCol, dateCol);
    table.addEventHandler(MouseEvent.MOUSE_PRESSED,
        new EventHandler<MouseEvent>() {
      public void handle(MouseEvent me) {
        try {

          Stage new1 = new Stage();
          GridPane grid = new GridPane();
          Scene scene = new Scene(grid, 300, 200);

          scene.getStylesheets().add(getClass().getResource("application.css")
                  .toExternalForm());
          // Label label = new Label("Complete Inventory");
          new1.setTitle("Complete Inventory");

          Button returnButton = new Button("Return");
          returnButton.setOnAction(new EventHandler<ActionEvent>() {

              @Override
              public void handle(ActionEvent event) {
                   returnButton.setOnAction((ActionEvent e) -> {
                       new1.close();
                     });
              }
          });


          // hard-coded now, should be changed later
          Label itemName = new Label("Computer  ");
          Label location = new Label("Location  ");
          Label t = new Label("Placed There  ");

          HBox name = new HBox(itemName, new Text(table.getSelectionModel().getSelectedItem().getFirstName()));
          HBox loc = new HBox(location, new Text(table.getSelectionModel().getSelectedItem().getLastName()));
          HBox time = new HBox(t, new Text(table.getSelectionModel().getSelectedItem().getEmail()));


          grid.add(name, 0, 0);
          grid.add(loc, 0, 1);
          grid.add(time, 0, 2);
          grid.setAlignment(Pos.CENTER);
          grid.add(returnButton, 0, 3);
          grid.setHgap(10);
          grid.setVgap(10);

          // grid.add(hbButtons, 0, 2, 2, 1);

          new1.setScene(scene);
          new1.show();
      }catch (Exception e) {
        e.printStackTrace();
    }
      }
    });
    

    ChoiceBox<String> choiceBox = new ChoiceBox();
    choiceBox.getItems().addAll("Item", "Location", "Date");
    choiceBox.setValue("Item");

    TextField textField = new TextField();
    textField.setPromptText("Search here!");
    textField.setOnKeyReleased(keyEvent -> {
      switch (choiceBox.getValue())// Switch on choiceBox value
      {
        case "Item":
          fileComputer.setPredicate(p -> p.getFirstName().toLowerCase()
              .contains(textField.getText().toLowerCase().trim()));// filter table by first name
          break;
        case "Location":
          fileComputer.setPredicate(p -> p.getLastName().toLowerCase()
              .contains(textField.getText().toLowerCase().trim()));// filter table by first name
          break;
        case "Date":
          fileComputer.setPredicate(
              p -> p.getEmail().toLowerCase().contains(textField.getText().toLowerCase().trim()));
          break;
      }
    });

    choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
      if (newVal != null) {
        textField.setText("");
        fileComputer.setPredicate(null);// This is same as saying flPerson.setPredicate(p->true);
      }
    });
    HBox hBox = new HBox(choiceBox, textField);// Add choiceBox and textField to hBox
    hBox.setAlignment(Pos.CENTER);// Center HBox
    final VBox vbox = new VBox();
    vbox.setSpacing(5);
    vbox.setPadding(new Insets(10, 0, 0, 10));

    Button b1 = new Button("Add");
    b1.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {

        FlowPane pane = new FlowPane(Orientation.VERTICAL);
        pane.setHgap(5);
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(0, 0, 0, 0));

        TextField tf1 = new TextField("computer-1001");
        itemCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TextField tf2 = new TextField("location");
        locationCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        TextField tf3 = new TextField("dd/mm/yyyy");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        tf1.setPrefColumnCount(14);
        tf2.setPrefColumnCount(14);
        tf3.setPrefColumnCount(14);

        Label l1 = new Label("Item Name: ");
        Label l2 = new Label("Previous Room: ");
        Label l3 = new Label("Date Item Was Moved: ");

        pane.getChildren().addAll(l1, tf1, l2, tf2, l3, tf3);

        HBox vBox = new HBox();
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(pane);
        borderPane.setBottom(vBox);
        BorderPane.setAlignment(vBox, Pos.CENTER);

        Scene secondScene = new Scene(borderPane, 350, 450);

        Stage newWindow = new Stage();
        newWindow.setTitle("Add Item");
        newWindow.setScene(secondScene);

        newWindow.setX(600);
        newWindow.setY(600);

        newWindow.show();

        Button button1 = new Button("Add");
        Button button2 = new Button("Cancel");
        button1.setOnAction((ActionEvent e) -> {
          data.add(new Computer(tf1.getText(), tf2.getText(), tf3.getText()));
          tf1.clear();
          tf2.clear();
          tf3.clear();
          newWindow.close();
        });
        button2.setOnAction((ActionEvent e) -> {
          newWindow.close();
        });
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(button1, button2);
        vBox.setSpacing(20);

      }
    });
    vbox.getChildren().addAll(label, table, hBox, b1);

    ((Group) scene.getRoot()).getChildren().addAll(vbox);

    stage.setScene(scene);
    stage.show();
  }


  public void handle(ActionEvent event) {

  }

  public static class Computer {

    private final SimpleStringProperty item;
    private final SimpleStringProperty location;
    private final SimpleStringProperty date;

    private Computer(String itemName, String locationName, String dataName) {
      this.item = new SimpleStringProperty(itemName);
      this.location = new SimpleStringProperty(locationName);
      this.date = new SimpleStringProperty(dataName);
    }

    public String getFirstName() {
      return item.get();
    }

    public void setFirstName(String fName) {
      item.set(fName);
    }

    public String getLastName() {
      return location.get();
    }

    public void setLastName(String fName) {
      location.set(fName);
    }

    public String getEmail() {
      return date.get();
    }

    public void setEmail(String fName) {
      date.set(fName);
    }
  }
}
