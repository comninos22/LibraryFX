/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryfxfinal;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JPanel;

/**
 *
 * @author MemeMaster64
 */
public class CustomerController implements Initializable {

    @FXML
    private StackPane dialogDisplay;
    private JFXDialogLayout content = new JFXDialogLayout();

    @FXML
    private JFXListView navList;
    @FXML
    private VBox root;
    @FXML
    private VBox contentArea;
    private Label l;
    private Pane p;
    private AnchorPane pane = new AnchorPane();
    private ContextMenu contextMenu = new ContextMenu();
    private JFXListView popupcontainer = new JFXListView();
    private Book[] library = null;
    private Book[] activeSubCat = null;
    private Stack<Book> activeSubCatStack = new Stack();
    private DataBase db;
    private Stack<HBox> rowBoxes = new Stack();
    private Customer customer;
    private JFXDialog dialogState;
    private HBox searchState = null;
    private static double xOffset;
    private static double yOffset;
    @FXML
    private ImageView minimize;
    @FXML
    private ImageView exit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Platform.exit();
            }
        });
        minimize.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ((Stage) root.getScene().getWindow()).setIconified(true);
            }
        });
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ((Stage) root.getScene().getWindow()).setX(event.getScreenX() - xOffset);
                ((Stage) root.getScene().getWindow()).setY(event.getScreenY() - yOffset);
            }
        });
        try {
            customer = (Customer) DataBase.getInstance().user;
            db = DataBase.getInstance();
            db.loadLibrary();
            db.loadRentedBooks(customer.getId());
            library = db.getLibrary();
            initContentArea();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        root.setStyle("-fx-focus-color: transparent;");
        for (int i = 0; i < library.length; i++) {
            System.out.println(library[i].getGenres().toString());

        }
    }

    private void initContentArea() throws SQLException {
        createNavbar();
        initSearchBox();
        fillWithBooks(library, false);
    }

    private void initSearchBox() throws SQLException {
        HBox h = new HBox(20);
        h.setPadding(new Insets(10));
        VBox v1 = new VBox(10);
        VBox v2 = new VBox(10);
        VBox v3 = new VBox(10);
        ToggleGroup t = new ToggleGroup();
        JFXButton search = new JFXButton("Αναζήτηση");
        search.setStyle("-fx-background-color:#fff");
        search.setButtonType(JFXButton.ButtonType.RAISED);
        h.setStyle("-fx-background-color:#e1e6e9");
        h.getChildren().addAll(v1, v2, v3, search);
        JFXRadioButton oles = new JFXRadioButton("Ολες");
        oles.setToggleGroup(t);
        oles.setSelected(true);
        JFXRadioButton front = new JFXRadioButton("Νουβέλα");
        front.setToggleGroup(t);
        JFXRadioButton psixo = new JFXRadioButton("Ψυχολογία");
        psixo.setToggleGroup(t);
        JFXRadioButton misti = new JFXRadioButton("Φαντασίας");
        misti.setToggleGroup(t);
        JFXRadioButton astin = new JFXRadioButton("Διήγημα");
        astin.setToggleGroup(t);
        JFXRadioButton syggr = new JFXRadioButton("Aυτοβελτίωση");
        syggr.setToggleGroup(t);
        JFXRadioButton aytov = new JFXRadioButton("Αυτοβιογραφία");
        aytov.setToggleGroup(t);
        JFXRadioButton logot = new JFXRadioButton("Λογοτεχνία");
        logot.setToggleGroup(t);
        JFXRadioButton istor = new JFXRadioButton("Μυθιστόρημα");
        istor.setToggleGroup(t);
        search.setOnAction((ActionEvent e) -> {

            JFXRadioButton r = (JFXRadioButton) t.getSelectedToggle();
            if (!r.getText().equals("Ολες")) {
                for (int i = 0; i < library.length; i++) {
                    ArrayList temp = (ArrayList) library[i].getGenres().clone();
                    while (!temp.isEmpty()) {
                        if (temp.remove(0).equals(r.getText())) {
                            activeSubCatStack.push(library[i]);
                        }
                    }

                }
                activeSubCat = new Book[activeSubCatStack.size()];
                for (int i = 0; i < activeSubCat.length; i++) {
                    activeSubCat[i] = activeSubCatStack.pop();
                }
                fillWithBooks(activeSubCat, false);
            } else {
                fillWithBooks(library, false);
            }

            System.out.println(r.getText());
        });

        v1.getChildren().addAll(oles, psixo, misti);
        v2.getChildren().addAll(astin, syggr, aytov);
        v3.getChildren().addAll(logot, istor, front);

        db.bindGenreToBooks(psixo.getText());
        db.bindGenreToBooks(misti.getText());
        db.bindGenreToBooks(astin.getText());
        db.bindGenreToBooks(syggr.getText());
        db.bindGenreToBooks(aytov.getText());
        db.bindGenreToBooks(logot.getText());
        db.bindGenreToBooks(istor.getText());
        db.bindGenreToBooks(front.getText());
        h.alignmentProperty().setValue(Pos.CENTER);
        contentArea.getChildren().add(h);
        searchState = h;
    }

    private VBox getContainer(Book b, boolean isRental) {
        VBox pane = new VBox(0);
        pane.prefHeight(273.0);
        pane.prefWidth(205.0);
        pane.setBackground(new Background(new BackgroundFill(Color.web("#dddddd"), CornerRadii.EMPTY, Insets.EMPTY)));
        File f = new File("");
        Image i = new Image(getClass().getResource("icon-close-13.png").toString(), 120, 120, true, true);
        ImageView bookImage = new ImageView(b.getImg());
        Text text = isRental ? new Text(b.getTitle() + "\n Κωδ. Δαν.: " + b.getRentalId()) : new Text(b.getTitle());
        text.setWrappingWidth(120);
        text.setTextAlignment(TextAlignment.CENTER);

        HBox.setMargin(pane, new Insets(20));
        VBox.setMargin(bookImage, new Insets(5));
        VBox.setMargin(text, new Insets(5));
        pane.getChildren().add(bookImage);
        pane.getChildren().add(text);
        System.out.println(b.getId());
        Text tempText = new Text(String.valueOf(b.getId()));
        pane.getChildren().add(tempText);
        tempText.setVisible(false);
        pane.setCursor(Cursor.HAND);

        pane.setOnMouseClicked((Event e) -> {
            Text t = (Text) pane.getChildren().get(2);
            System.out.println(t);

            try {
                loadPreview(DataBase.getInstance().findBookWithID(parseInt(t.getText())), isRental);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        pane.setStyle("-fx-border-color:#ccc ");
        pane.setEffect(new DropShadow(5, 3, 3, Color.GRAY));
        return pane;
    }

    private void fillWithBooks(Book[] b, boolean isRental) {
        System.out.println(b);
        int rows = b.length / 6;
        int remainter = b.length % 6;
        System.out.println(contentArea);
        System.out.println(contentArea.getChildren().size());
        if (contentArea.getChildren().size() > 1 || !contentArea.getChildren().contains(searchState)) {

            while (!rowBoxes.empty()) {
                contentArea.getChildren().remove(rowBoxes.pop());
            }
        }
        int k = 0;
        for (int i = 0; i < rows; i++) {
            HBox Hbox = new HBox(45);
            rowBoxes.push(Hbox);
            contentArea.getChildren().add(Hbox);
            for (int j = 0; j < 6; j++) {
                Pane container = getContainer(b[k++], isRental);
                Hbox.getChildren().add(container);
                VBox.setMargin(Hbox, new Insets(10));
                HBox.setMargin(container, new Insets(0, 0, 0, 0));
            }
        }
        if (remainter != 0) {
            HBox Hbox = new HBox(45);
            rowBoxes.push(Hbox);
            contentArea.getChildren().add(Hbox);
            for (int i = 0; i < remainter; i++) {
                Pane container = getContainer(b[k++], isRental);
                Hbox.getChildren().add(container);
                VBox.setMargin(Hbox, new Insets(10));
                HBox.setMargin(container, new Insets(0, 0, 0, 0));
            }
        }
        System.out.println(b.length / 4);
    }

    private void createNavbar() {
        l = new Label("Ο λογαριασμος μου");

        p = new Pane(l);
        p.setPrefSize(190, 20);
        p.setOnMouseClicked((MouseEvent a) -> {
            VBox v = new VBox(10);
            v.getChildren().add(new Text("Username: " + customer.getUname()));
            v.getChildren().add(new Text("Email: " + customer.getEmail()));
            v.getChildren().add(new Text("Τηλέφωνο: " + customer.getPhone()));
            v.getChildren().add(new Text("Διεύθυνση: " + customer.getAddress()));
            showDialog(new Text("Στοιχεία λογαριασμού"), v);
        });
        navList.getItems().add(p);

        l = new Label("Εμφάνιση βιβλίων..");
        p = new Pane(l);
        p.setPrefSize(190, 20);
        p.setOnMouseClicked((MouseEvent a) -> {

            library = db.getLibrary();
            fillWithBooks(library, false);

            if (!contentArea.getChildren().contains(searchState)) {
                contentArea.getChildren().add(0, searchState);
            }
        });
        navList.getItems().add(p);

        l = new Label("Εμφάνιση συναλλαγών");
        p = new Pane(l);
        p.setPrefSize(190, 20);
        p.setOnMouseClicked((MouseEvent a) -> {

            fillWithBooks(db.getRentedBooks(), true);
            if (contentArea.getChildren().contains(searchState)) {
                contentArea.getChildren().remove(searchState);
            }
        });
        navList.getItems().add(p);

        l = new Label("Αλλαγή στοιχείων λογαριασμού..");
        p = new Pane(l);
        p.setPrefSize(190, 20);
        System.out.println("jaja");
        p.setOnMouseClicked((MouseEvent a) -> {
            VBox v = new VBox(10);

            l = new Label("Νέο email:");
            JFXTextField email = new JFXTextField(customer.getEmail());
            v.getChildren().addAll(l, email);
            l = new Label("Νέο τηλεφωνο:");
            JFXTextField phone = new JFXTextField(customer.getPhone());
            v.getChildren().addAll(l, phone);
            Label l = new Label("Νέα διεύθυνση:");
            JFXTextField address = new JFXTextField(customer.getAddress());
            JFXButton b = new JFXButton("Επιβεβαίωση");

            v.getChildren().addAll(l, address, b);
            b.alignmentProperty().setValue(Pos.CENTER);
            b.setStyle("-fx-background-color:#ddd");
            b.setButtonType(JFXButton.ButtonType.RAISED);
            b.setOnAction((ActionEvent e) -> {
                customer.setAddress(address.getText());
                customer.setEmail(email.getText());
                customer.setPhone(phone.getText());
                System.out.println(customer);
                try {
                    db.changecredentials(customer);
                    showDialog(new Text(""), new Label("Η αλλαγή στοιχείων ήταν επιτυχής"));
                } catch (SQLException ex) {
                    Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            showDialog(new Text("Αλλαγή στοιχείων λογαριασμού"), v);
        });
        navList.getItems().add(p);

        l = new Label("Αποσύνδεση");
        p = new Pane(l);
        p.setOnMouseClicked((MouseEvent a) -> {
            try {
                db = null;
                customer = null;
                Stage r = (Stage) root.getScene().getWindow();
                r.close();
                Parent root = FXMLLoader.load(getClass().getResource("loginFormFXML.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        p.setPrefSize(190, 20);
        navList.getItems().add(p);

        navList.setStyle("-fx-background-color:#333");
        navList.setStyle("-fx-cell-vertical-margin:50.0");
        navList.setPadding(new Insets(30.0));
        navList.setVerticalGap(20.0);
        navList.setExpanded(true);
        navList.depthProperty().setValue(1);
        System.out.println(navList.getItems().get(0));

    }

    JFXDialog showDialog(Node heading, Node body) {
        if (dialogState != null) {
            dialogState.setOverlayClose(true);
            dialogState.close();
        }

        JFXDialog dialog = new JFXDialog(dialogDisplay, content, JFXDialog.DialogTransition.NONE);
        dialogState = dialog;
        content.setHeading(heading);
        content.setBody(body);
        JFXButton button = new JFXButton("Επιστροφή");
        button.setStyle("-fx-background-color:#ddd");
        button.setButtonType(JFXButton.ButtonType.RAISED);
        button.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event
            ) {
                dialog.close();
            }
        }
        );
        content.setActions(button);
        dialog.show();
        return dialog;
    }

    private void loadPreview(Book b, boolean isRental) {

        Text title = new Text("Τίτλος: " + b.getTitle());
        title.setWrappingWidth(260);
        Text genre = new Text("Κατηγορία: " + b.getGenres().toString());
        genre.wrappingWidthProperty().setValue(260);
        Text publisher = new Text("Εκδότης: " + b.getPublisher());
        Text bookID = new Text("Κωδικός Βιβλίου: " + b.getId());
        Text author = new Text("Συγγραφέας: " + b.getAuthor());
        Text pages = new Text("Σελίδες: " + b.getPages());
        Text extras = isRental ? new Text("Κωδικός δανεισμού: " + b.getRentalId()) : new Text("Διαθέσημη ποσότητα: " + b.getQuantity());
        Text rentDate = isRental ? new Text("Ημερομηνία δανεισμού: " + b.getRentDate().toString()) : new Text("");
        Text returnDate = isRental ? new Text("Ημερομηνία επιστροφής: " + b.getReturnDate().toString()) : new Text("");
        VBox container = new VBox(10);
        Text t = new Text(b.getTitle());
        t.setWrappingWidth(456);
        JFXDialog d = showDialog(t, container);
        container.prefHeight(544);

        container.prefWidth(456);
        HBox imgDetailsContainer = new HBox(5);

        Image i = new Image(getClass().getResource("icon-close-13.png").toString(), 120, 120, true, true);
        ImageView bookPreview = new ImageView(b.getImg());
        container.getChildren().add(imgDetailsContainer);
        VBox.setMargin(imgDetailsContainer, new Insets(20, 0, 0, 30));
        imgDetailsContainer.getChildren().add(bookPreview);
        VBox textContainer = new VBox(15);
        imgDetailsContainer.getChildren().add(textContainer);
        textContainer.getChildren().add(title);
        textContainer.getChildren().add(genre);
        textContainer.getChildren().add(publisher);
        textContainer.getChildren().add(bookID);
        textContainer.getChildren().add(author);
        textContainer.getChildren().add(pages);
        textContainer.getChildren().add(extras);
        textContainer.getChildren().add(rentDate);
        textContainer.getChildren().add(returnDate);
        JFXButton rent = new JFXButton("Αίτημα δανεισμού");
        rent.setButtonType(JFXButton.ButtonType.RAISED);
        rent.setStyle("-fx-background-color:#eee");
        rent.setOnAction((ActionEvent a) -> {
            try {
                int rentID = db.rentBook(db.user.getId(), parseInt(bookID.getText().substring(bookID.getText().lastIndexOf(" ") + 1)));
                if (rentID != 0) {
                    showDialog(new Text("Αίτημα δανεισμού"), new Text("Το αίτημα δανεισμού έγινε με επιτυχία \n"
                            + "μπορείτε να περάσετε απο την βιβλιοθήκη να το παραλάβετε με τον κωδικό: " + rentID));
                    db.loadRentedBooks(customer.id);
                } else {
                    showDialog(new Text("Αίτημα δανεισμού"), new Text("Φαίνεται ότι έχετε ήδη ενοικιάσει αυτό το βιβλίο, παρακαλώ επιλέξτε ένα άλλο"));

                }

                System.out.println(d);
            } catch (Exception ex) {
                Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        VBox.setMargin(rent, new Insets(10, 0, 0, 150));
        Text descriptionTitle = new Text("ΠΕΡΙΓΡΑΦΗ");
        descriptionTitle.setTextAlignment(TextAlignment.CENTER);
        descriptionTitle.setFont(new Font(18));
        container.getChildren().add(descriptionTitle);
        VBox.setMargin(descriptionTitle, new Insets(20, 0, 0, 150));
        Text descriptionText = new Text(b.getDescription());
        descriptionTitle.setTextAlignment(TextAlignment.CENTER);
        descriptionTitle.setFont(new Font(22));
        VBox.setMargin(descriptionText, new Insets(0, 20, 0, 20));
        descriptionText.setWrappingWidth(400);
        container.getChildren().add(descriptionText);
        if (!isRental) {
            container.getChildren().add(rent);
        }

    }
}
