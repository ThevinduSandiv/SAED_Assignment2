package edu.curtin.saed.assignment2;


import edu.curtin.saed.gameapis.CollectListener;

import java.util.logging.Logger;

public class Loader
{
    private static final Logger logger = Logger.getLogger(Loader.class.getName());
    public Loader()
    {
    }

    public void loadPlugin() // TODO: change to reflection later
    {
        GameExtensionPoint gameExtensionPoint = GameExtensionPoint.getInstance();

        try
        {
            // Fully qualified class name of the plugin
            Class<?> clazz = Class.forName("edu.curtin.gameplugins.Reveal");

            CollectListener pluginInstance = (CollectListener) Class.forName("edu.curtin.gameplugins.Reveal")
                    .getDeclaredConstructor(GameExtensionPoint.class)
                    .newInstance(GameExtensionPoint.getInstance());
            gameExtensionPoint.registerCollectListener(pluginInstance);
            logger.info("Plugin loaded: " + clazz.getName());

        }
        catch (ClassNotFoundException e)
        {
            logger.severe("Plugin class not found: " + e.getMessage());
        }
        catch (Exception e)
        {
            logger.severe("Plugin class instantiation failed: " + e.getMessage());
        }

    }
}
