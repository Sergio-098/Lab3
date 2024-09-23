package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    private final Map<String, Map<String, String>> translations;
    private final List<String> codes;
    private final Map<String, List<String>> languages;

    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        // read the file to get the data to populate things...
        translations = new HashMap<>();
        codes = new ArrayList<>();
        languages = new HashMap<>();
        try {

            String jsonString = Files.readString(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject country = jsonArray.getJSONObject(i);
                Map<String, String> langs = new HashMap<>();
                List<String> langs2 = new ArrayList<>();
                int count = 0;
                for (String key : country.keySet()) {
                    if (count > 3){
                        langs.put(key, country.getString(key));
                        langs2.add(key);
                    }
                    count++;
                }
                String name = "alpha3";
                translations.put(country.getString(name), langs);
                codes.add(country.getString(name));
                languages.put(country.getString(name), langs2);
            }

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        return languages.get(country);
    }

    @Override
    public List<String> getCountries() {
        return codes;
    }

    @Override
    public String translate(String country, String language) {
        if (codes.contains(country) && languages.get(country)
                .contains(language)) {
            return translations.get(country).get(language);
        }
        return null;
    }
}
