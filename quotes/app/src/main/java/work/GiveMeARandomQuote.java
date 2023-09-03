package work;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GiveMeARandomQuote {
    private static final String DEFAULT_QUOTE = "There are no quotes available at the moment.";
    private static int status=0;
    private static final String filePath = "./app/src/main/resources/recentquotes.json";
    private static final String apiUrl = "http://api.forismatic.com/api/1.0/";
    private static final String method = "getQuote";
    private static final String lang = "en";
    private static final String format = "json";
    public static String kindlyGiveMeARandomQuote() {
        String oneQuote = DEFAULT_QUOTE;
        ArrayList<Quote> jsonQuotes=new ArrayList<>();
        jsonQuotes=getArr();
        Random rand = new Random();

        if (jsonQuotes.size() > 0) {
                int randomIndex =rand.nextInt(jsonQuotes.size());
                oneQuote = jsonQuotes.get(randomIndex).getText();

        }



        return oneQuote;
    }
    public static String kindlyGiveMeARandomQuoteFromApi() {
        String oneQuote = DEFAULT_QUOTE;

        try {
            String query = String.format("method=%s&lang=%s&format=%s",
                    URLEncoder.encode(method, "UTF-8"),
                    URLEncoder.encode(lang, "UTF-8"),
                    URLEncoder.encode(format, "UTF-8"));

            URL pokeUrl = new URL(apiUrl + "?" + query);
            HttpURLConnection pokeUrlConnection = (HttpURLConnection) pokeUrl.openConnection();
            pokeUrlConnection.setRequestMethod("GET");

            int status = pokeUrlConnection.getResponseCode();
            GiveMeARandomQuote.status = status;
            System.out.println("Response Code: " + status);

            if (status == 200) {
                InputStreamReader reader = new InputStreamReader(pokeUrlConnection.getInputStream());
                BufferedReader pokeBufferReader = new BufferedReader(reader);
                StringBuilder response = new StringBuilder(); // To store the response text
                String line;
                while ((line = pokeBufferReader.readLine()) != null) {
                    response.append(line);
                }
               // https://docs.oracle.com/javaee/7/api/javax/json/JsonObject.html can read any json file without the need to create a class
                //https://stackoverflow.com/questions/34120882/gson-jsonelement-getasstring-vs-jsonelement-tostring
                JsonObject object = new Gson().fromJson(response.toString(), JsonObject.class);
                if (object.has("quoteText")&&object.has("quoteAuthor")) {
                    oneQuote = object.get("quoteText").getAsString()+" "+object.get("quoteAuthor").getAsString();
                    System.out.println("from api");
                    ArrayList<Quote> qoutes=getArr();
                    Gson gson=new Gson();
                    String [] arr={};
                    qoutes.add(new Quote( arr,object.get("quoteAuthor").getAsString(),"",object.get("quoteText").getAsString()));
                    String json = gson.toJson(qoutes);
                    try (FileWriter writer = new FileWriter(filePath)) {
                        writer.write(json);
                        System.out.println("Quotes saved to quotes.json");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                oneQuote = kindlyGiveMeARandomQuote();
                System.out.println("NOT from api");

            }
        } catch (UnknownHostException e){
            oneQuote = kindlyGiveMeARandomQuote();
            System.out.println("NOT from api");
        } catch (IOException e) {
            System.out.println(e);
        }
        return oneQuote;
    }

    public static ArrayList<Quote> getArr(){
        Gson gson = new Gson();
        String filePath = GiveMeARandomQuote.filePath;
        Quote[] arrQoutes1=null;
        ArrayList<Quote> arrQuotes=new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            arrQoutes1 = gson.fromJson(reader, Quote[].class);
            for (Quote quote:arrQoutes1) {
                arrQuotes.add(quote);
            }       } catch (FileNotFoundException e) {
            System.out.println("Error: The quotes file was not found."
            );} catch (IOException e) {
            System.out.println(  "Error: An IOException occurred while reading the quotes file.");
        }
        System.out.println("quotes returned successfully");
        return arrQuotes;
    }

    public static int getStatus() {
        return status;
    }

}

