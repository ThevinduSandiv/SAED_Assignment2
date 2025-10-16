package edu.curtin.saed.assignment2;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class UIManager
{
    private static final Logger logger = Logger.getLogger(UIManager.class.getName());

    private static ResourceBundle bundle = ResourceBundle.getBundle("bundle");

    private static Locale locale;

    public static void setLocale(String localeString)
    {
        try
        {
            locale = Locale.forLanguageTag(localeString);
            logger.info("Setting locale to " + localeString);
            bundle = ResourceBundle.getBundle("bundle", locale);
        }
        catch (Exception e)
        {
            logger.warning("Unavailable locale: " + localeString);
            bundle = ResourceBundle.getBundle("bundle");
        }
    }

    public static String getUIText(String key) {
        try
        {
            return bundle.getString(key);
        }
        catch (Exception e)
        {
            return "[" + key + "]"; // Show key name if translation missing
        }
    }

    public static String getUIText(String key, Object... args)
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

    public static String getFormattedDate(LocalDate localDate)
    {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofLocalizedDate(FormatStyle.SHORT)
                .withLocale(locale);

        return localDate.format(formatter);
    }
}
