package fr.bulb.constants;

/**
 * @author Vincent Le Guevel (vincent.leguevel.sio@gmail.com)
 * @since 21/07/2017
 */
public enum PluginAddStateConstant {

    ADDED("Le plugin a été correctement ajouté"),
    NOT_IMPLEMENTED_INNTERFACES("L'interface PluginBase n'a pas été implémenté"),
    NOT_CLASS_FOUND("Le plugin ne comporte pas de .class"),
    ALREADY_ADDED("le plugin a déjà été ajouté"),
    IMPOSSIBLE_TO_ADD("Impossible de traiter le fichier séléctionné");

    private String name = "";

    PluginAddStateConstant(String name){
        this.name = name;
    }
    public String toString(){
        return name;
    }
}
