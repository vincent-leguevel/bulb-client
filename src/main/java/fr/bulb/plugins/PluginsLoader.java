package fr.bulb.plugins;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import fr.bulb.constants.PluginAddStateConstant;
import fr.bulb.constants.PluginStateConstant;
import fr.bulb.controller.ClientController;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarFile;

/**
 * @author Vincent Le Guevel (vincent.leguevel.sio@gmail.com)
 * @since 19/07/2017
 */
public class PluginsLoader {

    private static final String NAME_INTERFACE = "interface fr.bulb.plugins.PluginsBase";
    private List<Plugin> plugins;

    public PluginsLoader(List<Plugin> plugins) {
        this.plugins = plugins;
    }


    /**
     * Vérifie si le plugin est correcte ( présence d'un .class ainsi que de l'implémentation de PluginBase )
     * @param file
     */
    public PluginAddStateConstant addPlugin(File file) {

        int classFound = 0;
        try {

            URLClassLoader loader = new URLClassLoader(new URL[] {file.toURI().toURL()});
            JarFile jar = new JarFile(file.getAbsolutePath());

            Enumeration enumeration = jar.entries();


            main:while(enumeration.hasMoreElements()){

                String contentName = enumeration.nextElement().toString();

                //On vérifie que le fichier courant est un .class
                if(contentName.length() > 6 && contentName.substring(contentName.length()-6).equals(".class")) {
                    classFound += 1;
                    contentName = contentName.substring(0,contentName.length()-6);
                    contentName = contentName.replaceAll("/",".");


                    Class contentClass = Class.forName(contentName ,true,loader);

                    // On vérifie que la classe courante implémente PluginBase
                    if(contentClass.getInterfaces().length == 0) return PluginAddStateConstant.NOT_IMPLEMENTED_INNTERFACES;
                    for(int i = 0 ; i < contentClass.getInterfaces().length; i ++ ) {
                        if (!contentClass.getInterfaces()[i].toString().equals(NAME_INTERFACE)) {

                            return PluginAddStateConstant.NOT_IMPLEMENTED_INNTERFACES;
                        }
                        plugins.add(new Plugin(contentClass, PluginStateConstant.ADDED));
                    }
                }
            }
        } catch (IOException | ClassNotFoundException | NoClassDefFoundError e) {
            return PluginAddStateConstant.IMPOSSIBLE_TO_ADD;
        }
        //On ajoute le plugin à la liste de plugins
        return classFound != 0 ? PluginAddStateConstant.ADDED : PluginAddStateConstant.NOT_CLASS_FOUND;
    }

    public PluginStateConstant loadPlugin(Plugin plugin, ClientController cc) {

        try {
            PluginsBase pb = (PluginsBase) plugin.getContentClass().newInstance();
            pb.loadPlugin(cc);
            plugin.setState(PluginStateConstant.LOADED);
        } catch (InstantiationException | IllegalAccessException e) {
            //Si une erreur se produit la méthode renvoie PluginStateConstant.ADDED
            return PluginStateConstant.IMPOSSIBLE_TO_LOAD;
        }


        return PluginStateConstant.LOADED;
    }

    public PluginBean fillTableView(Plugin plugin) {

        try {
            PluginsBase contentClass = (PluginsBase) plugin.getContentClass().newInstance();
            PluginBean pb = new PluginBean(contentClass.getName(),contentClass.getVersion(),contentClass.getDescription(),contentClass.getCategory())
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

//        return new PluginBean()
    }
}
