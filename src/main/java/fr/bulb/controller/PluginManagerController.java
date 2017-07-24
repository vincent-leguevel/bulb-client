package fr.bulb.controller;

import fr.bulb.constants.PluginAddStateConstant;
import fr.bulb.constants.PluginStateConstant;
import fr.bulb.plugins.Plugin;
import fr.bulb.plugins.PluginBean;
import fr.bulb.plugins.PluginsLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author Vincent Le Guevel (vincent.leguevel.sio@gmail.com)
 * @since 18/07/2017
 */
public class PluginManagerController {

    //fxml attributs
    @FXML
    private BorderPane borderPane;
    @FXML
    private TableView<PluginBean> tableView;
    @FXML
    private TableColumn<PluginBean,String> nameColumn;
    @FXML
    private TableColumn<PluginBean,String> descColumn;
    @FXML
    private TableColumn<PluginBean,Double> versionColumn;
    @FXML
    private TableColumn<PluginBean,String> categoryColumn;
    @FXML
    private TableColumn<PluginBean,PluginStateConstant> stateColumn;
    @FXML
    private ComboBox loadComboBox;
    @FXML
    private ComboBox disableComboBox;
    @FXML
    private Button loadConfirm;
    @FXML
    private Button disableConfirm;

    //controller attributs
    private ObservableList<PluginBean> observableList;
    private Map<Integer,Plugin> plugins;
    private ClientController clientController;

    @FXML
    private void initialize(){

        observableList = FXCollections.observableArrayList();

        nameColumn.setCellValueFactory(new PropertyValueFactory<PluginBean, String>("name"));
        descColumn.setCellValueFactory(new PropertyValueFactory<PluginBean, String>("description"));
        versionColumn.setCellValueFactory(new PropertyValueFactory<PluginBean, Double>("version"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<PluginBean, String>("category"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<PluginBean, PluginStateConstant>("pluginState"));

        tableView.setItems(observableList);
    }

    @FXML
    public void openChooser() throws IOException, ClassNotFoundException {
        Stage root = (Stage) borderPane.getScene().getWindow();
        FileChooser fc = new FileChooser();
        fc.setTitle("Ajoutez un plugin");
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JAR","*.jar"));

        PluginsLoader pl = new PluginsLoader(plugins);
        File file = fc.showOpenDialog(root);
        if(file != null){
            PluginAddStateConstant pluginAddStateConstant = pl.addPlugin(file);

            if(pluginAddStateConstant == PluginAddStateConstant.ADDED) {
                //Si le plugin est bien formé on l'ajoute à la liste des plugins en état ajouté
                PluginBean pb = pl.getPluginInformation(plugins.get(plugins.size()-1));
                observableList.add(pb);
                pl.loadPlugin(plugins.get(plugins.size()-1),clientController);
                pl.unloadPlugin(plugins.get(plugins.size()-1),clientController);
            }
        }
    }



    public Map<Integer,Plugin> getPlugins() {
        return plugins;
    }

    public void setPlugins(Map<Integer,Plugin> plugins) {
        this.plugins = plugins;
    }

    public ClientController getClientController() {
        return clientController;
    }

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }
}
