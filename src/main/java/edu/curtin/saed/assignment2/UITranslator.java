package edu.curtin.saed.assignment2;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class UITranslator
{
    private static ResourceBundle bundle = ResourceBundle.getBundle("bundle");

    public static void setLanguage(String localeString)
    {
        try
        {
            Locale locale = Locale.forLanguageTag(localeString);
            bundle = ResourceBundle.getBundle("bundle", locale);
        }
        catch (Exception e)
        {
            System.out.println("Language not available, using default");
            bundle = ResourceBundle.getBundle("bundle");
        }
    }

    public static String get(String key) {
        try
        {
            return bundle.getString(key);
        }
        catch (Exception e)
        {
            return "[" + key + "]"; // Show key name if translation missing
        }
    }

    public static String get(String key, Object... args)
    {
        try
        {
            String pattern = bundle.getString(key);
            return MessageFormat.format(pattern, args);
        }
        catch (Exception e)
        {
            return "[" + key + "]";
        }
    }
}
