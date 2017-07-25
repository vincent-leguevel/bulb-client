package fr.bulb.controller;

import fr.bulb.constants.PluginAddStateConstant;
import fr.bulb.constants.PluginStateConstant;
import fr.bulb.plugins.Plugin;
import fr.bulb.plugins.PluginBean;
import fr.bulb.plugins.PluginsLoader;
import fr.bulb.view.PluginManager;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import java.util.List;
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
    private Button loadUnloadButton;

    //controller attributs
    private ObservableList<PluginBean> observableList;
    private List<Plugin> plugins;
    private ClientController clientController;
    private PluginsLoader pluginsLoader;

    @FXML
    private void initialize(){

        observableList = FXCollections.observableArrayList();

        nameColumn.setCellValueFactory(new PropertyValueFactory<PluginBean, String>("name"));
        descColumn.setCellValueFactory(new PropertyValueFactory<PluginBean, String>("description"));
        versionColumn.setCellValueFactory(new PropertyValueFactory<PluginBean, Double>("version"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<PluginBean, String>("category"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<PluginBean, PluginStateConstant>("pluginState"));
        tableView.setItems(observableList);

//        Lorsque la liste est vide, on désactive le bouton
        loadUnloadButton.disableProperty().bind(Bindings.size(observableList).isEqualTo(0));
//        Lorsque aucune sélection n'est détectée, on désactive le bouton
        loadUnloadButton.disableProperty().bind(Bindings.isEmpty(tableView.getSelectionModel().getSelectedItems()));
    }


    @FXML
    public void openChooser() throws IOException, ClassNotFoundException {
        Stage root = (Stage) borderPane.getScene().getWindow();
        FileChooser fc = new FileChooser();
        fc.setTitle("Ajoutez un plugin");
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JAR","*.jar"));

        File file = fc.showOpenDialog(root);
        if(file != null){
            PluginAddStateConstant pluginAddStateConstant = pluginsLoader.addPlugin(file);

            if(pluginAddStateConstant == PluginAddStateConstant.ADDED) {
                //Si le plugin est bien formé on l'ajoute à la liste des plugins avec l'état ajouté
                pluginsLoader.getPluginInformation(plugins.get(plugins.size()-1));
                observableList.add(plugins.get(plugins.size()-1).getPluginBean());
            }
        }
    }



    public List<Plugin> getPlugins() {
        return plugins;
    }

    public void setPlugins(List<Plugin> plugins) {
        this.plugins = plugins;
        setPluginsLoader();
    }

    private void setPluginsLoader(){
        pluginsLoader = new PluginsLoader(plugins);
    }

    public ClientController getClientController() {
        return clientController;
    }

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    /**
     * Permet d'afficher les plugins dans la liste avec leur état lors de l'ouverture de la fenetre
     */
    public void initializeAllListsPlugins() {

        //TableView Ajoute la liste des plugin à la table
        for(Plugin plugin : plugins){
            observableList.add(plugin.getPluginBean());
        }
    }

    @FXML
    public void loadUnloadPlugin(ActionEvent event) {

        Plugin currentPlugin = plugins.get(tableView.getSelectionModel().getSelectedIndex());
        if(currentPlugin.getPluginBean().getPluginState() == PluginStateConstant.DISABLED || currentPlugin.getPluginBean().getPluginState() == PluginStateConstant.ADDED) {
            pluginsLoader.loadPlugin(currentPlugin,clientController);
        } else if(currentPlugin.getPluginBean().getPluginState() == PluginStateConstant.LOADED) {
            pluginsLoader.unloadPlugin(currentPlugin,clientController);
        }

        tableView.refresh();
    }
}