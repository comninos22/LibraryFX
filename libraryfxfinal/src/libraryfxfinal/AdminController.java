/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryfxfinal;

/**
 *
 * @author MemeMaster64
 */
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import static java.time.temporal.TemporalQueries.localDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.T;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AdminController implements Initializable {

    @FXML
    private StackPane rootPane;

    @FXML
    private Label adminName;

    @FXML
    private JFXTextField title;

    @FXML
    private JFXTextField rating;

    @FXML
    private JFXDatePicker dateOfRelease;

    @FXML
    private JFXComboBox<?> returnBook;

    @FXML
    private JFXTextField newAdminTextfield;

    @FXML
    private JFXTextField quantity;

    @FXML
    private JFXTextField monthlyFee;

    @FXML
    private JFXTextField pages;
    @FXML
    private JFXTextField author;
    @FXML
    private JFXTextField publisher;
    @FXML
    private JFXTextArea preview;
    @FXML
    private JFXComboBox<?> deleteMovieCombobox;
    @FXML
    private AnchorPane root;
    @FXML
    private JFXComboBox<?> addStockCombobox;
    private static double xOffset;
    private static double yOffset;
    @FXML
    private JFXTextField changeQuantityInput;
    private JFXDialog dialogState;
    private Image image;
    private InputStream fis;
    private File file = null;
    private DataBase db;
    private JFXDialogLayout content = new JFXDialogLayout();
    private ArrayList selectedCats;
    private Book[] library;
    @FXML
    private ImageView minimize;
    @FXML
    private ImageView exit;

    @FXML
    void registerBook(ActionEvent event) throws SQLException, IOException {
        boolean success = true;
        int valueofquantity = 0;
        int valueofpages = 0;
        int valueofRating = 0;
        double valueofmonthlyFee = 0;
        String stitle = title.getText();
        String srating = rating.getText();
        String spages = pages.getText();
        String squantity = quantity.getText();
        String spreview = preview.getText();
        String sauthor = author.getText();
        String spublisher = publisher.getText();
        LocalDate localdate = dateOfRelease.getValue();

        Instant instant = Instant.from(localdate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        String sdate = localdate.toString();
        String smonthlyFee = monthlyFee.getText();
        String error = "Έλεγξε την εγκυρότητα των στοιχείων που εισάγατε\n";
        if (stitle.equals("")) {
            success = false;
            error += "Ο τίτλος είναι κενός\n";
        }
        if (stitle.length() < 4) {
            success = false;
            error += "Ο τίτλος πρίπει να ίχει περισσότερα απο 4 γράμματα\n";
        }
        // if (category.equals("")) {
        //   success = false;
        // }
        if (sauthor.equals("")) {
            success = false;
            error += "Το πεδίο του συγγραφέα δεν πρέπει να είναι κενό\n";
        }
        if (spublisher.equals("")) {
            success = false;
            error += "Το πεδίο του εκδότη δεν πρέπει να είναι κενό\n";
        }
        if (srating.equals("") || (!srating.matches("\\d+") && parseInt(srating) <= 0)) {
            success = false;
            error += "Το πεδίο της τιμής δεν πρέπει να είναι κενό\n";

        } else {
            valueofRating = parseInt(srating);
        }
        if (smonthlyFee.equals("") || (!smonthlyFee.matches("\\d+") && parseInt(smonthlyFee) <= 0)) {
            success = false;
            error += "Το πεδίο της τιμής δεν πρέπει να είναι κενό\n";

        } else {
            valueofmonthlyFee = parseInt(smonthlyFee);
        }
        if (squantity.equals("") || (!squantity.matches("\\d+") && parseInt(squantity) <= 0)) {
            success = false;
            error += "Το πεδίο της ποσότητας δεν πρέπει να είναι κενό\n";

        } else {
            valueofquantity = parseInt(squantity);
        }
        if (spages.equals("") || (!spages.matches("\\d+") && parseInt(spages) <= 0)) {
            success = false;
            error += "Το πεδίο των σελίδων δεν πρέπει να είναι κενό\n";

        } else {
            valueofpages = parseInt(spages);
        }
        if (file == null) {
            error += "Πρέπει να διαλέξετε μια εικόνα\n";
            success = false;
        }
        if (spreview.equals("")) {
            error += "Το πεδίο της περιγραφής δεν πρέπει να είναι κενό";
        }
        if (success) {

            db.addBook(new Book(0, stitle, spreview, valueofRating, 0, 0, valueofmonthlyFee, null, spublisher, valueofquantity, valueofpages, sauthor, sdate, file, null));
            int latestBookId = db.getLatestAddition();
            while (!selectedCats.isEmpty()) {
                System.out.println(selectedCats);
                db.addGenreToBooks(latestBookId, (int) selectedCats.remove(0));
            }
            file = null;
        } else {
            showDialog(new Text("Κάτι πήγε λάθος"), new Text(error));

        }
    }

    JFXDialog showDialog(Node heading, Node body) {
        if (dialogState != null) {
            dialogState.setOverlayClose(true);
            dialogState.close();
        }

        JFXDialog dialog = new JFXDialog(rootPane, content, JFXDialog.DialogTransition.NONE);

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
        content.alignmentProperty().set(Pos.CENTER);
        dialog.show();
        return dialog;
    }

    @FXML
    void changeQuantity(ActionEvent event) throws SQLException {
        String s = (String) addStockCombobox.getValue();
        int id = parseInt(s.substring(s.indexOf(':') + 2, s.indexOf(':') + 9));
        int quantity = parseInt(changeQuantityInput.getText());
        changeQuantityInput.setText("");
        db.addQuantity(id, quantity);

        for (int i = 0; i < library.length; i++) {
            if (library[i].getId() == id) {
                library[i].setQuantity(quantity + library[i].getQuantity());
            }
        }
        ObservableList data = FXCollections.observableArrayList();
        for (int i = 0; i < library.length; i++) {
            String t = (library[i].getTitle() + "\n Kωδ: " + library[i].getId() + " Ποσότητα: " + library[i].getQuantity());
            data.add(t);
        }
        addStockCombobox.setItems(data);
        showDialog(new Text("Ανανέωση αποθέματος"), new Text("Η ανανέωση αποθέματος έγινε με επιτυχία"));
    }

    @FXML
    void deleteMovie(ActionEvent event) throws SQLException {
        String s = (String) deleteMovieCombobox.getValue();
        deleteMovieCombobox.getItems().remove(deleteMovieCombobox.getValue());
        int id = parseInt(s.substring(s.lastIndexOf(' ') + 1));
        deleteMovieCombobox.setPromptText("");
        db.removeBook(id);
        showDialog(new Text("Διαγραφή βιβλίου"), new Text("Η διαγραφή βιβλίου έγινε με επιτυχία"));
    }

    @FXML
    void chooseImage(ActionEvent event) throws FileNotFoundException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG, BMP, PNG", "jpg", "bmp", "png");
        chooser.setFileFilter(filter);
        Component parent = null;
        int returnVal = chooser.showOpenDialog(parent);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getAbsolutePath();
            fis = new FileInputStream(path);

            image = new Image(fis, 153, 219, false, true);
            ImageView img = new ImageView(image);

            showDialog(new Text("Επιλέξατε αυτήν την εικόνα"), img);

            file = chooser.getSelectedFile();
        }

    }

    @FXML
    void showCategoryDialog(ActionEvent event) {
        VBox v = new VBox(10);

        HBox h = new HBox(20);
        v.getChildren().add(h);
        h.setPadding(new Insets(10));
        VBox v1 = new VBox(20);
        VBox v2 = new VBox(20);
        VBox v3 = new VBox(20);
        ToggleGroup t = new ToggleGroup();
        JFXButton search = new JFXButton("Επιβεβαίωση");
        search.setStyle("-fx-background-color:#fff");
        search.setButtonType(JFXButton.ButtonType.RAISED);
        h.getChildren().addAll(v1, v2, v3);
        v.getChildren().add(search);
        JFXRadioButton[] cats = new JFXRadioButton[9];
        int i = 0;
        cats[i] = new JFXRadioButton("Ιστορια");
        cats[i++].setUserData(212);
        cats[i] = new JFXRadioButton("Νουβέλα");
        cats[i++].setUserData(213);
        cats[i] = new JFXRadioButton("Αυτοβιογραφία");
        cats[i++].setUserData(214);
        cats[i] = new JFXRadioButton("Φαντασίας");
        cats[i++].setUserData(215);
        cats[i] = new JFXRadioButton("Διήγημα");
        cats[i++].setUserData(216);
        cats[i] = new JFXRadioButton("Ψυχολογία");
        cats[i++].setUserData(220);
        cats[i] = new JFXRadioButton("Aυτοβελτίωση");
        cats[i++].setUserData(217);
        cats[i] = new JFXRadioButton("Λογοτεχνία");
        cats[i++].setUserData(219);
        cats[i] = new JFXRadioButton("Μυθιστόρημα");
        cats[i++].setUserData(218);
        i = 0;
        h.setPrefHeight(150);
        h.setPrefWidth(300);
        v1.getChildren().addAll(cats[i++], cats[i++], cats[i++]);
        v2.getChildren().addAll(cats[i++], cats[i++], cats[i++]);
        v3.getChildren().addAll(cats[i++], cats[i++], cats[i++]);
        v.setAlignment(Pos.CENTER);
        search.setOnAction((ActionEvent e) -> {
            selectedCats = new ArrayList();
            for (int j = 0; j < cats.length; j++) {
                if (cats[j].isSelected()) {
                    selectedCats.add(cats[j].getUserData());
                }
            }
            System.out.println(selectedCats);
            dialogState.close();
        });
        showDialog(new Text("Επιλογή κατηγοριών"), v);
    }

    @FXML
    void showBookLog(ActionEvent event) throws SQLException {
        String[] booklog = db.showAddedBookLogs();
        Text title = new Text(booklog[0]);
        Text quantity = new Text(booklog[1]);
        Text date = new Text(booklog[2]);
        HBox h = new HBox(30);
        h.getChildren().addAll(title, quantity, date);
        ScrollPane scroll = new ScrollPane(h);
        showDialog(new Text("Αρχειο login"), scroll);
    }

    @FXML
    void showChangeAddressLog(ActionEvent event) throws SQLException {
        String[] changeaddresslogs = db.showChangedAddressLogs();
        Text uname = new Text(changeaddresslogs[0]);
        Text oldaddress = new Text(changeaddresslogs[1]);
        Text newaddress = new Text(changeaddresslogs[2]);
        HBox h = new HBox(30);
        h.getChildren().addAll(uname, oldaddress, newaddress);
        ScrollPane scroll = new ScrollPane(h);
        showDialog(new Text("Αρχείο διευθύνσεων"), scroll);
    }

    @FXML
    void showEmailLog(ActionEvent event) throws SQLException {
        String[] changeemaillogs = db.showChangedEmailLogs();
        Text uname = new Text(changeemaillogs[0]);
        Text oldemail = new Text(changeemaillogs[1]);
        Text newemail = new Text(changeemaillogs[2]);
        HBox h = new HBox(30);
        h.getChildren().addAll(uname, oldemail, newemail);
        ScrollPane scroll = new ScrollPane(h);
        showDialog(new Text("Αρχείο email"), scroll);
    }

    @FXML
    void showAdminLoginLogs(ActionEvent event) throws SQLException {
       String[] loginlogs = db.showAdminLoginLogs();
        Text uname = new Text(loginlogs[0]);
        Text logindates = new Text(loginlogs[1]);
        HBox h = new HBox(10);
        h.getChildren().addAll(uname, logindates);
        ScrollPane scroll = new ScrollPane(h);
        showDialog(new Text("Αρχείο login"), scroll);

    }
    @FXML
    void showAdminLogoutLogs(ActionEvent event) throws SQLException {
       String[] loginlogs = db.showAdminLogoutLogs();
        Text uname = new Text(loginlogs[0]);
        Text logindates = new Text(loginlogs[1]);
        HBox h = new HBox(10);
        h.getChildren().addAll(uname, logindates);
        ScrollPane scroll = new ScrollPane(h);
        showDialog(new Text("Αρχείο login"), scroll);

    }

    @FXML
    void showAdminSignUpLogs(ActionEvent event) throws SQLException {
        String[] loginlogs = db.showAdminSignUpLogs();
        Text uname = new Text(loginlogs[0]);
        Text logindates = new Text(loginlogs[1]);
        HBox h = new HBox(30);
        h.getChildren().addAll(uname, logindates);
        ScrollPane scroll = new ScrollPane(h);
        showDialog(new Text("Αρχείο signup"), scroll);
    }

    @FXML
    void showLoginLog(ActionEvent event) throws SQLException {
        String[] loginlogs = db.showAdminLoginLogs();
        Text uname = new Text(loginlogs[0]);
        Text logindates = new Text(loginlogs[1]);
        
        HBox h = new HBox(30);
        h.getChildren().addAll(uname, logindates);
        ScrollPane scroll = new ScrollPane(h);
        showDialog(new Text("Αρχείο login"), scroll);

    }

    @FXML
    void showPhoneLog(ActionEvent event) throws SQLException {
        String[] changephonelogs = db.showChangedPhoneLogs();
        Text uname = new Text(changephonelogs[0]);
        Text oldphone = new Text(changephonelogs[1]);
        Text newphone = new Text(changephonelogs[2]);
        HBox h = new HBox(30);
        h.getChildren().addAll(uname, oldphone, newphone);
        ScrollPane scroll = new ScrollPane(h);
        showDialog(new Text("Αρχείο τηλεφώνων"), scroll);
    }

    @FXML
    void showSignUpLog(ActionEvent event) throws SQLException {
        String[] loginlogs = db.showSignUpLogs();
        Text uname = new Text(loginlogs[0]);
        Text logindates = new Text(loginlogs[1]);
        HBox h = new HBox(30);
        h.getChildren().addAll(uname, logindates);
        ScrollPane scroll = new ScrollPane(h);
        showDialog(new Text("Αρχείο signup"), scroll);
    }
    Book[] rentedBooks;

    @FXML
    void logout(ActionEvent event) {
        try {
            db.logout(db.user.getUname());
            db = null;
            db.user = null;
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
        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
            db = DataBase.getInstance();
            db.loadLibrary();
            System.out.println(db.user);
            // 
            db.loadRentedBooks();
            rentedBooks = db.getRentedBooks();
            System.out.println("liokikaasdasd " + db.getRentedBooks());
            library = db.getLibrary();

            String s = adminName.getText() + " " + db.user.getUname();

            adminName.setText(s);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList l;
        ObservableList data = FXCollections.observableArrayList();
        for (int i = 0; i < library.length; i++) {
            String s = (library[i].getTitle() + "\n Kωδ: " + library[i].getId());
            data.add(s);
        }

        deleteMovieCombobox.setItems(data);
        data = FXCollections.observableArrayList();
        for (int i = 0; i < library.length; i++) {
            String s = (library[i].getTitle() + "\n Kωδ: " + library[i].getId() + " Ποσότητα: " + library[i].getQuantity());
            data.add(s);
        }
        addStockCombobox.setItems(data);
        data = FXCollections.observableArrayList();
        for (int i = 0; i < rentedBooks.length; i++) {
            String s = (rentedBooks[i].getTitle() + "\n Kωδ. βιβλίου: " + rentedBooks[i].getId() + " Κωδ. επιστροφής: " + rentedBooks[i].getRentalId());
            data.add(s);
        }
        returnBook.setItems(data);

        System.out.println(data);

    }

    @FXML
    void makeAdmin(ActionEvent event) throws SQLException {
        String name = newAdminTextfield.getText();
        if (!name.matches("[a-zA-Z0-9]{4,16}")) {
            showDialog(new Text(""), new Text("Εισάγετε έγκυρο username ( 4-16 αλφαριθμιτικοί χαρακτήρες)"));
            newAdminTextfield.setText("");
            return;
        }
        if (DataBase.makeAdmin(name)) {
            showDialog(new Text("Επιτυχία"), new Text("Ο λογαριασμός " + name + " έχει πρόσβαση στην έκδοση διαχειριστών της εφαρμογής"));
        } else {
            showDialog(new Text("Σφάλμα"), new Text("Φαίνεται πως δεν υπάρχει λογαριασμός με αυτό το όνομα"));
        }
    }

    @FXML
    void returnBookButton(ActionEvent event) throws SQLException {

        String s = (String) returnBook.getValue();
        returnBook.setPromptText("");
        s = s.substring(s.lastIndexOf(":") + 2, s.lastIndexOf(":") + 9);
        System.out.println(s.length());
        DataBase.returnBook(parseInt(s));
        returnBook.getItems().remove(returnBook.getValue());
        showDialog(new Text(""), new Text("To βιβλίο με κωδικό δανεισμού " + s + " έχει επιστραφεί με επιτυχία"));
    }
}
