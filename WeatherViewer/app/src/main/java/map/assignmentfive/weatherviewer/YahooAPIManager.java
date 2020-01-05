package map.assignmentfive.weatherviewer;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class YahooAPIManager
{
    private String myCity, appID = "U7Mank32";

    public YahooAPIManager(String incomingCity)
    {
        this.myCity = incomingCity;
    }

    public WeatherData getWeather() throws APIException
    {
        JSONObject json = makeRequest();
        WeatherData weatherData = parseJSON(json);

        return weatherData;
    }

    private JSONObject makeRequest() throws APIException
    {
        final String baseURL = "https://weather-ydn-yql.media.yahoo.com/forecastrss";

        String URLString = baseURL + "?location=" + myCity + "&format=json&u=c";

        HttpURLConnection urlConnection;
        JSONObject jsonObject = null;

        try{
            URL url = new URL(URLString);
            String authorizationLine = OAuthManager.GetAuthorization(baseURL, myCity);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Yahoo-App-Id",appID);
            urlConnection.setRequestProperty("Authorization",authorizationLine);
            urlConnection.setRequestProperty("Content-Type","application/json");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String temp;
            StringBuilder response = new StringBuilder();

            while ((temp = bufferedReader.readLine()) != null)
            {
                response.append(temp);
            }

            jsonObject = (JSONObject) new JSONTokener(response.toString()).nextValue();

        } catch (IOException | JSONException err)
        {
            Log.e(MainActivity.LOG_KEY, err.getClass().getName() + ": "+ err.getMessage());
            throw new APIException("Unable to process request");
        }

        Log.e(MainActivity.LOG_KEY,jsonObject.toString());

        return jsonObject;
    }

    private WeatherData parseJSON(JSONObject jsonObject) throws APIException
    {
        WeatherData weatherData = null;

        if (jsonObject == null)
        {
            Log.e(MainActivity.LOG_KEY, "parseJSON: parameter is null!");
            return null;
        }

        try {
            JSONObject condition = jsonObject.getJSONObject("current_observation").getJSONObject("condition");

            int currentTemperature = condition.getInt("temperature");
            String description = condition.getString("text");

            JSONArray forecast = jsonObject.getJSONArray("forecasts");

            int highTemperature = forecast.getJSONObject(0).getInt("high");
            int lowTemperature = forecast.getJSONObject(0).getInt("low");

            int futureHighTemperature = forecast.getJSONObject(1).getInt("high");
            int futureLowTemperature = forecast.getJSONObject(1).getInt("low");

            weatherData = new WeatherData(myCity, currentTemperature, highTemperature, lowTemperature, futureHighTemperature, futureLowTemperature, description);
        } catch (JSONException err)
        {
            Log.e(MainActivity.LOG_KEY, err.getClass().getName() + ": "+ err.getMessage());
            throw new APIException("Error parsing JSON");
        }

        return weatherData;
    }
}
