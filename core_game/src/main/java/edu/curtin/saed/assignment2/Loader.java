package edu.curtin.saed.assignment2;

import edu.curtin.saed.gameapis.GameAPI;
import edu.curtin.saed.gameapis.MoveListener;
import edu.curtin.saed.gameapis.CollectListener;
import edu.curtin.saed.gameapis.MenuListener;

import java.util.logging.Logger;

public class Loader
{
    private static final Logger logger = Logger.getLogger(Loader.class.getName());
    public Loader()
    {
    }

    public void loadPlugin(String pluginClassName) // TODO: change to reflection later
    {
        GameExtensionPoint gameExtensionPoint = GameExtensionPoint.getInstance();

        try
        {
            Class<?> pluginClass = Class.forName(pluginClassName);
            Object pluginInstance = pluginClass.getDeclaredConstructor(GameAPI.class)
                    .newInstance(GameExtensionPoint.getInstance());

            // Check which interfaces the plugin implements
            if (MoveListener.class.isAssignableFrom(pluginClass))
            {
                gameExtensionPoint.registerMoveListener((MoveListener) pluginInstance);
            }

            if (CollectListener.class.isAssignableFrom(pluginClass))
            {
                gameExtensionPoint.registerCollectListener((CollectListener) pluginInstance);
            }

            if (MenuListener.class.isAssignableFrom(pluginClass))
            {
                gameExtensionPoint.registerMenuListener((MenuListener) pluginInstance);
            }
            logger.info(() -> "Plugin loaded: " + pluginClass.getName());

        }
        catch (ClassNotFoundException e)
        {
            logger.warning(() -> "Plugin class not found: " + e.getMessage());
        }
        catch (Exception e)
        {
            logger.severe(() -> "Plugin class instantiation failed: " + e.getMessage());
        }

    }



    public void loadPlugin2() // TODO: change to reflection later
    {
        GameExtensionPoint gameExtensionPoint = GameExtensionPoint.getInstance();

        try
        {
            // Fully qualified class name of the plugin
            Class<?> clazz = Class.forName("edu.curtin.gameplugins.Penalty");

            MoveListener pluginInstance = (MoveListener) Class.forName("edu.curtin.gameplugins.Penalty")
                    .getDeclaredConstructor(GameAPI.class)
                    .newInstance(GameExtensionPoint.getInstance());
            gameExtensionPoint.registerMoveListener(pluginInstance);
            logger.info(() -> "Plugin loaded: " + clazz.getName());

        }
        catch (ClassNotFoundException e)
        {
            logger.severe(() -> "Plugin class not found: " + e.getMessage());
        }
        catch (Exception e)
        {
            logger.severe(() -> "Plugin class instantiation failed: " + e.getMessage());
        }

    }


    public void loadPlugin1() // TODO: change to reflection later
    {
        GameExtensionPoint gameExtensionPoint = GameExtensionPoint.getInstance();

        try
        {
            // Fully qualified class name of the plugin
            Class<?> clazz = Class.forName("edu.curtin.gameplugins.Reveal");

            CollectListener pluginInstance = (CollectListener) Class.forName("edu.curtin.gameplugins.Reveal")
                    .getDeclaredConstructor(GameAPI.class)
                    .newInstance(GameExtensionPoint.getInstance());
            gameExtensionPoint.registerCollectListener(pluginInstance);
            logger.info("Plugin loaded: " + clazz.getName());

        }
        catch (ClassNotFoundException e)
        {
            logger.severe(() -> "Plugin class not found: " + e.getMessage());
        }
        catch (Exception e)
        {
            logger.severe(() -> "Plugin class instantiation failed: " + e.getMessage());
        }

    }
}
