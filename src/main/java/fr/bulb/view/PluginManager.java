package fr.bulb.view;

import fr.bulb.controller.ClientController;
import fr.bulb.controller.PluginManagerController;
import fr.bulb.plugins.Plugin;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Vincent Le Guevel (vincent.leguevel.sio@gmail.com)
 * @since 18/07/2017
 */
public class PluginManager {

    ClientController clientController;
    Map<Integer,Plugin> plugins;

    public PluginManager(ClientController clientController, Map<Integer,Plugin> plugins) {
        this.clientController = clientController;
        this.plugins = plugins;
    }

    public void createView()  {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/pluginManager.fxml"));
            Parent root = loader.load();

            PluginManagerController pluginManagerController = loader.getController();
            pluginManagerController.setClientController(this.clientController);
            pluginManagerController.setPlugins(this.plugins);

            Stage stage = new Stage();
            stage.setTitle("Inscription");
            stage.getIcons().add(new Image("/image/Logo.png"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
