package melissadata.businesscoder.view;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javafx.application.HostServices;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import melissadata.businesscoder.Main;

public class RootLayoutController {
    // Reference to the main application
    private Main main;

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(Main main) {
        this.main = main;
    }

    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Melissa Data About");
        alert.setHeaderText("About");
        alert.setContentText("Author: Melissa Data");

        alert.showAndWait();
    }

    @FXML
    private void handleWIKI() {
        try {
            main.getHostServices().showDocument("http://wiki.melissadata.com");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }


}
