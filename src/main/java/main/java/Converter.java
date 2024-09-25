package main.java;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Converter {

    // Define API URL and Key
    private static final String API_KEY = "7b4fe3728bae0a5a547635aa"; //  API key
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";

    //method to make api call and receive exchange rate
    public static double getExchangeRate(String baseCurrency, String targetCurrency) throws Exception{
        String urlString = API_URL + baseCurrency; // API calls for base currency
        URL url = new URL(urlString);
        HttpURLConnection connect = (HttpURLConnection) url.openConnection();
        connect.setRequestMethod("GET");

        //Read Response from API
        BufferedReader in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
        String InputLine;
        StringBuilder content = new StringBuilder();

        while((InputLine = in.readLine()) != null){
            content.append(InputLine);
        }
        in.close();
        connect.disconnect();

        //Parse JSON response to get exchange rate
        JSONObject json = new JSONObject(content.toString());
        return json.getJSONObject("conversion_rates").getDouble(targetCurrency);
    }
    public static void main(String [] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Currency Converter!");
        System.out.println("I support over 160 world currencies");
        System.out.println();

        //Loop for continuous conversions
        boolean continueConversion = true;
        while (continueConversion) {

            //Get user input for base and target currencies
            System.out.println("Enter the currency you have that you want to convert (e.g. USD EUR YEN...): ");
            String baseCurrency = scanner.next().toUpperCase();

            System.out.println("Enter the target currency (e.g. USD EUR YEN...): ");
            String targetCurrency = scanner.next().toUpperCase();

            System.out.println("Enter the amount in " + baseCurrency + ": ");
            double amount = scanner.nextDouble();

            try {
                //Fetch exchange rate from API
                double exchangeRate = getExchangeRate(baseCurrency, targetCurrency);
                double convertedAmount = amount * exchangeRate;
                System.out.printf("%.2f %s is equivalent to %.2f %s%n", amount, baseCurrency, convertedAmount, targetCurrency);
            } catch (Exception e) {
                System.out.println("An error has occurred: " + e.getMessage());
            }
            //Asking if the user wants to convert more currency
            System.out.println("Do you want to convert more currency? (Yes/No): ");
            String response = scanner.next().toUpperCase();

            //Check the user's response
            if (response.equals("yes")) {
                continueConversion = true; // continue loop if the user's answer is yes
            }else {
                continueConversion = false; //exit loop
            }
        }


            System.out.println("Thanks for using the Currency Converter! ");
            scanner.close();
        }



}
