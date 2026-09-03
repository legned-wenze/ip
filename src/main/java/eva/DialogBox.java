package eva;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Displays one message together with a label identifying its speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private Label avatar;

    private DialogBox(String text, String speaker) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException("Unable to load dialog box.", e);
        }

        dialog.setText(text);
        avatar.setText(speaker);
    }

    /**
     * Creates a dialog aligned as a user message.
     *
     * @param text Message text.
     * @param speaker Label identifying the user.
     * @return User dialog box.
     */
    public static DialogBox getUserDialog(String text, String speaker) {
        return new DialogBox(text, speaker);
    }

    /**
     * Creates a dialog aligned as an Eva response.
     *
     * @param text Message text.
     * @param speaker Label identifying Eva.
     * @return Eva dialog box.
     */
    public static DialogBox getEvaDialog(String text, String speaker) {
        DialogBox dialogBox = new DialogBox(text, speaker);
        dialogBox.flip();
        return dialogBox;
    }

    private void flip() {
        ObservableList<Node> children =
                FXCollections.observableArrayList(getChildren());
        Collections.reverse(children);
        getChildren().setAll(children);
        setAlignment(Pos.TOP_LEFT);
    }
}
