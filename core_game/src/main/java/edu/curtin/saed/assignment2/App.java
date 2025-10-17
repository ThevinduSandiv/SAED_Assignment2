package edu.curtin.saed.assignment2;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App implements NativeKeyListener
{
    private static final Logger logger = Logger.getLogger(App.class.getName());

    private static volatile boolean isRunning = true;
    private static volatile boolean ignoreGameInput = false;
    private static StringBuilder localeInput = new StringBuilder();
    private static Simulation sim;
    private static Loader loader;

    public static void main(String[] args)
    {

        InputStream in = System.in;
        if (args.length > 0) {
            try {
                in = new FileInputStream(args[0]);
            } catch (Exception e) {
                logger.warning("Cannot open file: " + args[0]);
                return;
            }
        }

        UIManager.setLocale("en-AU");
        // Setup the key listener first
        App app = new App();
        app.setupKeyListener();

        int unusedVariable = 42;
        logger.info("Starting Simulation");
        //clearScreen();

        sim = new Simulation(App::endGame);
        readFile(sim, in);
        sim.testMapGenerator();
        sim.addWalls();

        GameExtensionPoint.getInstance().setSim(sim);

        loader = new Loader();
        loader.loadPlugin();

        redrawMap();
        while(isRunning)
        {} // TODO: For now keeps the main app running
    }

    private static void readFile(Simulation sim, InputStream in)
    {
        MyParser parser = new MyParser(in);

        try
        {
            parser.run(sim);
            logger.info("Input valid");
        }
        catch(edu.curtin.saed.assignment2.ParseException e)
        {
           logger.severe("Parsing error!");
           logger.severe(e.getMessage());
        }
    }


    private static void redrawMap()
    {
        clearScreen();
        System.out.println(sim.getMessagesToShow());
        System.out.println(sim.getDrawableMap());
        System.out.println(UIManager.getUIText("inventory") + ": " + sim.getPlayer().getInventoryAsString());
        System.out.println(UIManager.getUIText("date_label") + " " + UIManager.getFormattedDate(sim.getCurrentDate()));
        System.out.println(); // Blank Line
    }



    public static void clearScreen() {

        for (int i = 0; i < 50; i++) {
            System.out.println();
        }

    }

    private static void changeLocale()
    {
        try
        {
            while (System.in.available() > 0) {
                System.in.read();
            }
            logger.info("Clearing buffer");
        }
        catch (IOException e)
        {
            logger.info("Error clearing buffer: " + e.getMessage());
            // Ignore - buffer might already be clear
        }

        logger.info("Reading new locale");
        System.out.println(UIManager.getUIText("locale_change_msg"));
        Scanner input = new Scanner(System.in);
        String inputText = input.nextLine();
        String localeString = inputText.substring(1); // Removes "t" from the beginning
        UIManager.setLocale(localeString);
        redrawMap();
        resumeNativeHook();
    }

    private static void endGame()
    {
        System.out.println(UIManager.getUIText("exit_msg"));
        isRunning = false;
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException ex) {
            ex.printStackTrace();
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
            System.out.println(UIManager.getUIText("exit_msg"));
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
                endGame();
                break;

            case NativeKeyEvent.VC_SPACE:
                redrawMap();
                break;

            case NativeKeyEvent.VC_UP:
                logger.info("Player moved up - clicked");
                sim.movePlayer(-1, 0);
                redrawMap();
                break;

            case NativeKeyEvent.VC_DOWN:
                logger.info("Player moved down - clicked");
                sim.movePlayer(1, 0);
                redrawMap();
                break;

            case NativeKeyEvent.VC_LEFT:
                logger.info("Player moved left - clicked");
                sim.movePlayer(0, -1);
                redrawMap();
                break;

            case NativeKeyEvent.VC_RIGHT:
                logger.info("Player moved right - clicked");
                sim.movePlayer(0, 1);
                redrawMap();
                break;

            case NativeKeyEvent.VC_T:
                pauseNativeHook();
                changeLocale();
                logger.info("Locale change requested");
                ignoreGameInput = true;
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

    private static void pauseNativeHook() {
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
    }

    private static void resumeNativeHook() {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
    }
}