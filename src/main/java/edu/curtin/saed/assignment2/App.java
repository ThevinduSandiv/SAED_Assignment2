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

    private static volatile boolean isRunning = false;

    public static void main(String[] args)
    {
        // Setup the key listener first
        App app = new App();
        app.setupKeyListener();

        logger.info("Starting...");
        clearScreen();

        Simulation sim = new Simulation();

        while(isRunning)
        {





            try
            {
                Thread.sleep(1000);
            } catch(InterruptedException e)
            {
                throw new RuntimeException(e);
            }
            clearScreen();
        }
    }

    public static void clearScreen() {

        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
        System.out.println("=== SCREEN CLEARED ==="); // Optional: show it worked

    }

    public void setupKeyListener() {
        // Disable JNativeHook logging
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);

        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(this);
            System.out.println("Key listener registered successfully");
        } catch (NativeHookException ex) {
            System.err.println("Failed to register native hook: " + ex.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent)
    {

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent)
    {
        switch (nativeKeyEvent.getKeyCode()) {
            case NativeKeyEvent.VC_ESCAPE:
                System.out.println("Exiting...");
                isRunning = false;
                try {
                    GlobalScreen.unregisterNativeHook();
                } catch (NativeHookException ex) {
                    ex.printStackTrace();
                }
                break;
            case NativeKeyEvent.VC_SPACE:
                System.out.println("Space bar pressed!");
                break;
            case NativeKeyEvent.VC_ENTER:
                System.out.println("Enter pressed!");
                break;
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent)
    {

    }
}