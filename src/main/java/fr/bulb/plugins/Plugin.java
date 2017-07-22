package fr.bulb.plugins;

import fr.bulb.constants.PluginStateConstant;

/**
 * @author Vincent Le Guevel (vincent.leguevel.sio@gmail.com)
 * @since 21/07/2017
 * Permet de stocker le plugin et de lui asscier un état
 */
public class Plugin {

    private Class contentClass;
    private PluginBean pluginBean;
    private PluginStateConstant state;


    public Plugin(Class contentClass,PluginStateConstant state) {
        this.contentClass = contentClass;
        this.state = state;
    }

    public Class getContentClass() {
        return contentClass;
    }

    public void setContentClass(Class contentClass) {
        this.contentClass = contentClass;
    }

    public PluginStateConstant getState() {
        return state;
    }

    public void setState(PluginStateConstant state) {
        this.state = state;
    }

    public PluginBean getPluginBean() {
        return pluginBean;
    }

    public void setPluginBean(PluginBean pluginBean) {
        this.pluginBean = pluginBean;
    }
}
