/**
* Author: Solrun Einarsdottir
*
* Solution to GoEuro's Java Developer Test
* As described at https://github.com/goeuro/dev-test
*/
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class GoEuroTest {
  /**
  * Takes a string representing a city name (CITY_NAME) as an input argument,
  * queries the API at http://api.goeuro.com/api/v2/position/suggest/en/CITY_NAME
  * and writes (a subset of) the response data into a CSV file.
  */
  public static void main(String[] args) {

    try {
      // Read city name from user
      String CITY_NAME = args[0];

      // Remove spaces from city name
      String cityName = CITY_NAME.replaceAll("\\s","");

      // Get json array for given city name from API
      JsonArray cityArray = fetchCityArray(cityName);

      // If no matches were found, print error message
      // and stop running
      if (cityArray.size() == 0) {
        System.out.println("No data found matching the city name " + CITY_NAME + ".");
        System.out.println("No CSV file will be created.");
        return;
      }

      // Create CSV file containing city data
      createCSV(cityArray, cityName);
    }
    // Handle exception if user gives no argument
    catch (ArrayIndexOutOfBoundsException e) {
      System.out.println("No city name given. Try running with the name of a city as an argument!");
      e.printStackTrace();
    }
  }

  /**
  * Queries the API and returns JSON array of data for the given city name.
  */
  private static JsonArray fetchCityArray(String cityName) {

      JsonArray cityArray = null;

      // API endpoint for the given city name
      String cityURLString = "http://api.goeuro.com/api/v2/position/suggest/en/" + cityName;

      try {
        // Connect to the API
        URL cityURL = new URL(cityURLString);

        try {
          InputStream cityStream = cityURL.openStream();

          // Get response, parse as JSON array
          JsonParser jParser = new JsonParser();
          cityArray = jParser.parse(new InputStreamReader(cityStream)).getAsJsonArray();
        }
        catch (IOException e) {
          System.out.println("Error in opening stream to read url contents. Check city name and network connection.");
          e.printStackTrace();
        }
      }
      catch (MalformedURLException e) {
        System.out.println("Error in forming legal url.");
        e.printStackTrace();
      }

      return cityArray;
  }

  /**
  * Creates CSV file cityName.csv containing appropriate data from cityArray.
  */
  private static void createCSV(JsonArray cityArray, String cityName) {

      // Prepare CSV file cityName.csv for writing
      String cityFileName = cityName + ".csv";
      FileWriter writeCSV = null;
      try {
        writeCSV = new FileWriter(cityFileName);
      }
      catch (IOException e) {
        System.out.println("Error in opening new CSV file writer.");
        e.printStackTrace();
      }

      // Write file header
      try {
        writeCSV.append("_id,name,type,latitude,longitude\n");
      }
      catch (IOException e) {
        System.out.println("Error in writing header for CSV file.");
        e.printStackTrace();
      }

      // Traverse through JSON objects in array
      // insert the data into model type for city data
      // and add line to CSV file for each city
      Gson gson = new Gson();
      for (JsonElement cityData : cityArray) {
        City aCity =  gson.fromJson(cityData, City.class);
        csvLine(aCity, writeCSV);
      }

      // Flush and close filewriter
      try {
        writeCSV.flush();
        writeCSV.close();
      }
      catch (IOException e) {
        System.out.println("Error while flushing/closing CSV file writer.");
        e.printStackTrace();
      }

      System.out.println("Data has been written to " + cityFileName);
  }

  /**
  * Writes City data to a line in a CSV file
  */
  private static void csvLine(City aCity, FileWriter writeCSV) {
    try {
      writeCSV.append(String.valueOf(aCity.getID()));
      writeCSV.append(",");
      writeCSV.append(aCity.getName());
      writeCSV.append(",");
      writeCSV.append(aCity.getType());
      writeCSV.append(",");
      writeCSV.append(String.valueOf(aCity.getLat()));
      writeCSV.append(",");
      writeCSV.append(String.valueOf(aCity.getLon()));
      writeCSV.append(",");
      writeCSV.append("\n");
    }
    catch (IOException e) {
      System.out.println("Error in writing to CSV file");
      e.printStackTrace();
    }
  }
}
