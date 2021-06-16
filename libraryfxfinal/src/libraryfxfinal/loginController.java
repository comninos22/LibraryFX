/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryfxfinal;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author MemeMaster64
 */
public class loginController implements Initializable {

    private DataBase db;
    @FXML
    private Label label;
    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;
    @FXML
    private VBox signupArea;
    @FXML
    private StackPane rootpane;
    JFXDialogLayout content = new JFXDialogLayout();
    @FXML
    private AnchorPane root;

    @FXML
    void exit(MouseEvent event) {
        Platform.exit();
    }

    @FXML
    void loginAction(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        if (db.login(username.getText(), password.getText())) {
            showDialog(new Text(), new Text("Συνδεθήκατε επιτυχώς"));
            Stage r = (Stage) rootpane.getScene().getWindow();
            r.close();
            if (db.user instanceof Customer) {

                Parent root = FXMLLoader.load(getClass().getResource("customerFXML.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } else {
                Parent root = FXMLLoader.load(getClass().getResource("adminFXML.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            }
        }else{
            showDialog(new Text("Σφάλμα"),new Text("Λάθος κωδίκος ή username"));
        }
    }

    @FXML
    void minimize(MouseEvent event) {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setIconified(true);

    }
    private static double xOffset;
    private static double yOffset;

    @FXML
    void drag(MouseEvent event) {

    }

    @FXML
    void save(MouseEvent event) {

    }

    @FXML
    void signupAction(ActionEvent event) {

        signupArea.getChildren().remove(0, signupArea.getChildren().size());
        signupArea.setStyle("-fx-background-color: #d9e4dd");
        signupArea.setPadding(new Insets(0, 20, 5, 20));
        Text t = new Text("Συμπληρώστε τα στοιχεία για εγγραφή");
        t.setFont(Font.font("Liberation Sans", FontWeight.BOLD, 20.0));
        signupArea.getChildren().add(t);

        JFXTextField uname = new JFXTextField();
        uname.setPromptText("Όνομα χρήστη");
        signupArea.getChildren().add(uname);

        JFXPasswordField passwd = new JFXPasswordField();
        passwd.setPromptText("Κωδικός");
        signupArea.getChildren().add(passwd);

        JFXPasswordField passwdver = new JFXPasswordField();
        passwdver.setPromptText("Επαλήθευση Κωδικού");
        signupArea.getChildren().add(passwdver);

        JFXTextField email = new JFXTextField();
        email.setPromptText("Email");
        signupArea.getChildren().add(email);

        JFXTextField rname = new JFXTextField();
        rname.setPromptText("Όνομα");
        signupArea.getChildren().add(rname);

        JFXTextField phone = new JFXTextField();
        phone.setPromptText("Τηλέφωνο");
        signupArea.getChildren().add(phone);

        JFXTextField address = new JFXTextField();
        address.setPromptText("Διεύθυνση");
        signupArea.getChildren().add(address);

        JFXButton signupbtn = new JFXButton("ΕΓΓΡΑΦΗ");
        signupbtn.setButtonType(JFXButton.ButtonType.RAISED);
        signupbtn.setStyle("-fx-background-color:#ddd");
        signupArea.getChildren().add(signupbtn);

        signupbtn.setOnAction((ActionEvent a) -> {
            String error = "";
            if (uname.getText().equals("")) {
                error += "To όνομα χρήστη δεν πρέπει να είναι κενό\n\n";
            } else if (uname.getText().length() < 5) {
                error += "Το όνομα πρέπει να έχει πάνω απο 5 χαρακτήρες\n\n";
            }
            if (passwd.getText().equals("")) {
                error += "Ο κωδικός δεν πρέπει να είναι κενός\n\n";
            } else if (!passwd.getText().equals(passwdver.getText())) {
                error += "Οι κωδικοί δεν ταιριάζουν\n\n";
            }
            if (email.getText().equals("")) {
                error += "Το email δεν πρέπει να είναι κενό\n\n";
            } else if (!Pattern.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*"
                    + "|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\"
                    + "x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-"
                    + "]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]"
                    + "|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53"
                    + "-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])", email.getText())) {
                error += "Το email δέν είναι της μορφής example@mailhost.domain\n";
            }
            if (phone.getText().equals("")) {
                error += "Το τηλέφωνο δεν πρέπει να είναι κενό\n\n";
            } else if (!Pattern.matches("^[0-9]{10}$", phone.getText())) {
                error += "Το τηλέφωνο είναι άκυρο\n\n";
            }
            if (address.getText().equals("")) {
                error += "Η διεύθυνση δεν πρέπει να είναι κενή\n\n";
            }
            if (rname.getText().equals("")) {
                error += "Το όνομα δεν πρέπει να είναι κενό\n\n";
            }

            if (error.equals("")) {
                System.out.println(uname.getText());
                int state = 3;
                try {
                    state = db.signup(uname.getText(), passwd.getText(), email.getText(), address.getText(), phone.getText(), rname.getText());
                    System.out.println(state);
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
                switch (state) {
                    case 0:
                        username.setText(uname.getText());
                        password.setText(passwd.getText());
                        showDialog(new Text("Επιτυχία εγγραφής"), new Text("Η εγγραφή ήταν επιτυχής, μπορείτε να συνδεθείτε με τον λογαριασμό σας"));
                        break;
                    case 1:
                        error += "Λογαριασμός με αυτό το όνομα χρήστη υπάρχει ήδη";
                        break;
                    case 2:
                        error += "Λογιαριασμός με αυτό το email υπάρχει ήδη";
                        break;
                    default:
                        error += "Απρόσμενο σφάλμα";

                        break;
                }
                if (!error.equals("")) {
                    showDialog(new Text("Προέκηψε σφάλμα"), new Text(error));
                }

            } else {
                showDialog(new Text("Προέκηψε σφάλμα"), new Text(error));

            }

        });
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
                ((Stage)root.getScene().getWindow()).setX(event.getScreenX() - xOffset);
                ((Stage)root.getScene().getWindow()).setY(event.getScreenY() - yOffset);
            }
        });
        File directory = new File(".\\");
        for (File f
                : directory.listFiles()) {
            if (f.getName().startsWith("temp")) {
                f.delete();
            }
        }

        try {
            db = DataBase.getInstance();
            //System.out.println(db.login("kom", "arxidis"));
            //db.loadLibrary();
            // Book[] library = db.getLibrary();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void showDialog(Text heading, Text body) {
        JFXDialog dialog = new JFXDialog(rootpane, content, JFXDialog.DialogTransition.NONE);
        content.setHeading(heading);
        content.setBody(body);
        JFXButton button = new JFXButton("Okay");
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
    }
}
