package fr.bulb.plugins;

import fr.bulb.constants.PluginStateConstant;

/**
 * @author Vincent Le Guevel (vincent.leguevel.sio@gmail.com)
 * @since 22/07/2017
 */
public class PluginBean {

    private String name;
    private Double version;
    private String description;
    private String category;
    private PluginStateConstant pluginState;


    public PluginBean(String name, Double version, String description, String category, PluginStateConstant pluginState) {
        this.name = name;
        this.version = version;
        this.description = description;
        this.category = category;
        this.pluginState = pluginState;
    }

    public PluginBean(String name, Double version, String description, String category) {
        this.name = name;
        this.version = version;
        this.description = description;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public PluginStateConstant getPluginState() {
        return pluginState;
    }

    public void setPluginState(PluginStateConstant pluginState) {
        this.pluginState = pluginState;
    }
}

