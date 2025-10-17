package edu.curtin.saed.assignment2;


import edu.curtin.saed.gameapis.CollectListener;
import edu.curtin.saed.gameapis.GameAPI;
import edu.curtin.saed.gameapis.MoveListener;

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
            Object pluginInstance = Class.forName("edu.curtin.gameplugins.Prize")
                    .getDeclaredConstructor(GameAPI.class)
                    .newInstance(GameExtensionPoint.getInstance());

            gameExtensionPoint.registerMoveListener((MoveListener) pluginInstance);
            gameExtensionPoint.registerCollectListener((CollectListener) pluginInstance);

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
            logger.severe("Plugin class not found: " + e.getMessage());
        }
        catch (Exception e)
        {
            logger.severe("Plugin class instantiation failed: " + e.getMessage());
        }

    }
}
