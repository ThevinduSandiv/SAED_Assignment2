package edu.curtin.saed.assignment2;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class App implements NativeKeyListener
{

    private static final Logger logger = Logger.getLogger(App.class.getName());

    private static volatile boolean isRunning = true;
    private static volatile boolean isKeyPressed = false;
    private static Simulation sim;

    public static void main(String[] args)
    {
        // Setup the key listener first
        App app = new App();
        app.setupKeyListener();

        logger.info("Starting Simulation");
        //clearScreen();

        sim = new Simulation(12, 13);
        sim.testMapGenerator();

        redrawMap();
        while(isRunning)
        {} // For now keeps the main app running
    }


    private static void redrawMap()
    {
        clearScreen();
        System.out.println(sim.getDrawableMap());
    }



    public static void clearScreen() {

        for (int i = 0; i < 50; i++) {
            System.out.println();
        }

    }

    public void setupKeyListener() {
        // Disable JNativeHook logging
        Logger l = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        l.setLevel(Level.OFF);
        l.setUseParentHandlers(false);

        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(this);
            logger.info("Native hook registered successfully.");
        } catch (NativeHookException ex) {
            logger.severe("Failed to register native hook: " + ex.getMessage());
            System.out.println("Exiting...");
            isRunning = false;
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent)
    {
        // DO Nothing
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent)
    {
        switch (nativeKeyEvent.getKeyCode()) {
            case NativeKeyEvent.VC_ESCAPE:
                System.out.println("Exiting...");
                isRunning = false;
                isKeyPressed = true;
                try {
                    GlobalScreen.unregisterNativeHook();
                } catch (NativeHookException ex) {
                    ex.printStackTrace();
                }
                break;
            case NativeKeyEvent.VC_SPACE:
                isKeyPressed = true;
                redrawMap();
                break;

            case NativeKeyEvent.VC_UP:
                logger.info("Player moved up - clicked");
                sim.movePlayer(0, -1);
                redrawMap();
                break;

            case NativeKeyEvent.VC_DOWN:
                logger.info("Player moved down - clicked");
                sim.movePlayer(0, 1);
                redrawMap();
                break;

            case NativeKeyEvent.VC_LEFT:
                logger.info("Player moved left - clicked");
                sim.movePlayer(-1, 0);
                redrawMap();
                break;

            case NativeKeyEvent.VC_RIGHT:
                logger.info("Player moved right - clicked");
                sim.movePlayer(1, 0);
                redrawMap();
                break;

            case NativeKeyEvent.VC_ENTER:
                System.out.println("Enter pressed!");
                break;
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent)
    {
        // Do Nothing
    }
}