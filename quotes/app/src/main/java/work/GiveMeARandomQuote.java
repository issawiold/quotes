package work;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class GiveMeARandomQuote {
    private static final String DEFAULT_QUOTE = "There are no quotes available at the moment.";


    public static String kindlyGiveMeARandomQuote() {
        Gson gson = new Gson();
        String filePath = "C:\\gradlyProjects\\quotes\\app\\src\\main\\resources\\recentquotes.json";
        String oneQuote = DEFAULT_QUOTE;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            Quote[] jsonQuotes = gson.fromJson(reader, Quote[].class);
//             = quotes.getArr();

            if (jsonQuotes.length > 0) {
                int randomIndex = ThreadLocalRandom.current().nextInt(jsonQuotes.length);
                oneQuote = jsonQuotes[randomIndex].getText();
            }

        } catch (FileNotFoundException e) {
            oneQuote = "Error: The quotes file was not found.";
        } catch (IOException e) {
            oneQuote = "Error: An IOException occurred while reading the quotes file.";
        }

        return oneQuote;
    }
    public Quote[] getArr(){
        Gson gson = new Gson();
        String filePath = "C:\\gradlyProjects\\quotes\\app\\src\\main\\resources\\recentquotes.json";
        Quote[] arrQoutes={};
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            arrQoutes = gson.fromJson(reader, Quote[].class);

        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
        return arrQoutes;
    }

}

