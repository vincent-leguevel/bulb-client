package fr.bulb.plugins;

import fr.bulb.controller.ClientController;
import fr.bulb.view.Client;

/**
 * @author Vincent Le Guevel (vincent.leguevel.sio@gmail.com)
 * @since 18/07/2017
 */
public interface PluginsBase {

    String getName();
    Double getVersion();
    int getCategory();
    String getDescription();
    void loadPlugin(ClientController cc);
    void unloadPlugin();
    void onAction(Object o);
}
