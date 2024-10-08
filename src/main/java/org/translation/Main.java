package org.translation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for this program.
 * Complete the code according to the "to do" notes.<br/>
 * The system will:<br/>
 * - prompt the user to pick a country name from a list<br/>
 * - prompt the user to pick the language they want it translated to from a list<br/>
 * - output the translation<br/>
 * - at any time, the user can type quit to quit the program<br/>
 */
public class Main {

    /**
     * This is the main entry point of our Translation System!<br/>
     * A class implementing the Translator interface is created and passed into a call to runProgram.
     * @param args not used by the program
     */
    public static final String quit = "quit";

    public static void main(String[] args) {


        Translator translator = new JSONTranslator();

        runProgram(translator);
    }

    /**
     * This is the method which we will use to test your overall program, since
     * it allows us to pass in whatever translator object that we want!
     * See the class Javadoc for a summary of what the program will do.
     * @param translator the Translator implementation to use in the program
     */
    public static void runProgram(Translator translator) {
        while (true) {
            String country = promptForCountry(translator);
            if (quit.equals(country)) {
                break;
            }
            CountryCodeConverter converter = new CountryCodeConverter();
            country = converter.fromCountry(country);
            String language = promptForLanguage(translator, country);
            if (quit.equals(language)) {
                break;
            }
            LanguageCodeConverter langConverter = new LanguageCodeConverter();
            String languageCode = langConverter.fromLanguage(language);
            System.out.println(country + " in " + language + " is " + translator
                    .translate(country, languageCode));
            System.out.println("Press enter to continue or quit to exit.");
            Scanner s = new Scanner(System.in);
            String textTyped = s.nextLine();

            if (quit.equals(textTyped)) {
                break;
            }
        }
    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForCountry(Translator translator) {
        List<String> countries = translator.getCountries();
        List<String> countries2 = new ArrayList<String>();
        CountryCodeConverter convertor = new CountryCodeConverter();
        for (String country : countries){
            String name = convertor.fromCountryCode(country);
            countries2.add(name);
        }
        Collections.sort(countries2);
        for (String country : countries2) {
            System.out.println(country);
        }
        System.out.println("select a country from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();

    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForLanguage(Translator translator, String country) {
        List<String> languages = translator.getCountryLanguages(country);
        List<String> languages2 = new ArrayList<String>();
        LanguageCodeConverter convertor = new LanguageCodeConverter();
        for (String language : languages) {
            String name = convertor.fromLanguageCode(language);
            languages2.add(name);
        }
        Collections.sort(languages2);
        for (String language : languages2) {
            System.out.println(language);
        }
        System.out.println("select a language from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }
}
