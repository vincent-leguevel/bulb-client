package fr.bulb.constants;

/**
 * @author Vincent Le Guevel (vincent.leguevel.sio@gmail.com)
 * @since 20/07/2017
 */
public enum PluginStateConstant {
    ADDED("Ajouté"),
    LOADED("Chargé"),
    DISABLED("désactivé"),
    IMPOSSIBLE_TO_LOAD("Impossible de charger le plugin"),
    IMPOSSIBLE_TO_UNLOAD("Impossible de décharger le plugin");

    private String name = "";

    PluginStateConstant(String name){
        this.name = name;
    }
    public String toString(){
        return name;
    }
}
