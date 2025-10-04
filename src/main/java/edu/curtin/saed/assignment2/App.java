package edu.curtin.saed.assignment2;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.logging.Level;
import java.util.logging.Logger;

public class App implements NativeKeyListener
{
    private static volatile boolean isRunning = false;

    public static void main(String[] args)
    {

        // Setup the key listener first
        App app = new App();
        app.setupKeyListener();

        System.out.println("Starting...");
        clearScreen();

        while(isRunning)
        {
            System.out.println("Hi");




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



//        try {
//            String os = System.getProperty("os.name").toLowerCase();
//            if (os.contains("win")) {
//                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
//            } else {
//                System.out.print("\033[H\033[2J");
//                System.out.flush();
//            }
//        } catch (Exception e) {
//            System.err.println("Warning: Could not clear screen: " + e.getMessage());
//            for (int i = 0; i < 50; i++) {
//                System.out.println();
//            }
//        }
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