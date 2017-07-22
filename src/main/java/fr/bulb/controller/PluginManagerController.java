package fr.bulb.controller;

import fr.bulb.constants.PluginAddStateConstant;
import fr.bulb.constants.PluginStateConstant;
import fr.bulb.plugins.Plugin;
import fr.bulb.plugins.PluginBean;
import fr.bulb.plugins.PluginsLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Vincent Le Guevel (vincent.leguevel.sio@gmail.com)
 * @since 18/07/2017
 */
public class PluginManagerController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private TableView<PluginBean> tableView;
    @FXML
    private TableColumn<PluginBean,String> nameColumn;
    @FXML
    private TableColumn<PluginBean,String> descColumn;
    @FXML
    private TableColumn<PluginBean,Float> versionColumn;
    @FXML
    private TableColumn<PluginBean,String> categoryColumn;
    @FXML
    private TableColumn<PluginBean,PluginStateConstant> stateColumn;


    private List<Plugin> plugins;
    private ClientController clientController;

    @FXML
    private void initialize(){
        System.out.println("bonjour");
        ObservableList<PluginBean> observableList = FXCollections.observableArrayList();
        observableList.add(new PluginBean("UnNom",1F,"Un plugin","Une catégorie",PluginStateConstant.LOADED));

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        versionColumn.setCellValueFactory(new PropertyValueFactory<>("version"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("pluginState"));

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
                pl.fillTableView(plugins.get(plugins.size()-1));
            }
        }
    }



    public List<Plugin> getPlugins() {
        return plugins;
    }

    public void setPlugins(List<Plugin> plugins) {
        this.plugins = plugins;
    }

    public ClientController getClientController() {
        return clientController;
    }

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }
}
