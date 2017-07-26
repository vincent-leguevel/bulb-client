package fr.bulb.plugins;

import fr.bulb.constants.PluginAddStateConstant;
import fr.bulb.constants.PluginStateConstant;
import fr.bulb.controller.ClientController;
import fr.bulb.plugins.annotation.LoadPlugin;
import fr.bulb.plugins.annotation.UnloadPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
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


            while(enumeration.hasMoreElements()){

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
                        // Si l'interface a été trouvé on ajoute le plugin à la liste et on stop la boucle
                        plugins.add(plugins.size(),new Plugin(contentClass, PluginStateConstant.ADDED));
                        break;
                    }
                }
            }
        } catch (IOException | ClassNotFoundException | NoClassDefFoundError e) {
            return PluginAddStateConstant.IMPOSSIBLE_TO_ADD;
        }
        //On ajoute le plugin à la liste de plugins
        return classFound != 0 ? PluginAddStateConstant.ADDED : PluginAddStateConstant.NOT_CLASS_FOUND;
    }

    /**
     * Charge le plugin (via l'annotation LoadPlugin)
     * @param plugin
     * @param cc
     * @return
     */
    public PluginStateConstant loadPlugin(Plugin plugin, ClientController cc) {

        Class contentClass = plugin.getContentClass();

        try {
            PluginsBase pb = (PluginsBase) contentClass.newInstance();

            main:for(Method method : contentClass.getDeclaredMethods()) {
                for(Annotation annotation : method.getAnnotations()){
                    if(annotation instanceof LoadPlugin) {
                        method.invoke(contentClass.newInstance(),cc);
                        plugin.getPluginBean().setPluginState(PluginStateConstant.LOADED);
                        plugin.setState(PluginStateConstant.LOADED);
                        break main;
                    }
                }
            }
            //Lorsque l'annotation LoadPlugin n'est pas trouvé
            if(plugin.getState() == null) {
                plugin.getPluginBean().setPluginState(PluginStateConstant.IMPOSSIBLE_TO_LOAD);
                plugin.setState(PluginStateConstant.IMPOSSIBLE_TO_LOAD);
                return PluginStateConstant.IMPOSSIBLE_TO_LOAD;
            }

        } catch (InstantiationException | IllegalAccessException e) {
            //Si une erreur se produit la méthode renvoie PluginStateConstant.IMPOSSIBLE_TO_LOAD
            plugin.getPluginBean().setPluginState(PluginStateConstant.IMPOSSIBLE_TO_LOAD);
            plugin.setState(PluginStateConstant.IMPOSSIBLE_TO_LOAD);
            return PluginStateConstant.IMPOSSIBLE_TO_LOAD;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        return PluginStateConstant.LOADED;
    }

    /**
     * Désactive un plugin ( via l'annotatinon UnloadPlugin )
     * @param plugin
     * @param cc
     * @return
     */
    public PluginStateConstant unloadPlugin(Plugin plugin,ClientController cc){

        Class contentClass = plugin.getContentClass();

        try {
            main:for(Method method : contentClass.getDeclaredMethods()) {
                for(Annotation annotation : method.getAnnotations()){
                    if(annotation instanceof UnloadPlugin) {
                        method.invoke(contentClass.newInstance(),cc);
                        plugin.setState(PluginStateConstant.DISABLED);
                        plugin.getPluginBean().setPluginState(PluginStateConstant.DISABLED);
                        break main;
                    }
                }
            }
            //Lorsque l'annotation UnloadLoadPlugin n'est pas trouvé
            if(plugin.getState().equals(PluginStateConstant.LOADED)) {
                plugin.getPluginBean().setPluginState(PluginStateConstant.IMPOSSIBLE_TO_UNLOAD);
                plugin.setState(PluginStateConstant.IMPOSSIBLE_TO_UNLOAD);
                return PluginStateConstant.IMPOSSIBLE_TO_UNLOAD;
            }


        } catch (InstantiationException | IllegalAccessException e) {
            //Si une erreur se produit la méthode renvoie PluginStateConstant.IMPOSSIBLE_TO_LOAD
            plugin.getPluginBean().setPluginState(PluginStateConstant.IMPOSSIBLE_TO_UNLOAD);
            plugin.setState(PluginStateConstant.IMPOSSIBLE_TO_UNLOAD);
            return PluginStateConstant.IMPOSSIBLE_TO_LOAD;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        return PluginStateConstant.DISABLED;

    }

    /**
     * Récupère les information du plugin (Nom, version, description, catégorie)
     * @param plugin
     * @return
     */
    public void getPluginInformation(Plugin plugin) {

        PluginBean pb = null;
        try {
            PluginsBase contentClass = (PluginsBase) plugin.getContentClass().newInstance();
            pb = new PluginBean(contentClass.getName(),contentClass.getVersion(),contentClass.getDescription(),contentClass.getCategory(),PluginStateConstant.ADDED);
            plugin.setPluginBean(pb);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
