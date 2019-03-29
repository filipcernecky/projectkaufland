package Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;


public class Internet  {

	    public static Double getUSDrate() throws IOException {
	        URL urlForGetRequest = new URL("http://data.fixer.io/api/latest?access_key=1bb0aff1f8b21c79723aefa5140f4477");

	        double result;

	        HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();
	        connection.connect();

	            JsonParser jp = new JsonParser();
	            JsonElement root = jp.parse(new InputStreamReader((InputStream) connection.getContent()));
	            JsonObject jsonObj = root.getAsJsonObject();
	            jsonObj = jsonObj.getAsJsonObject("rates");
	            result = jsonObj.get("USD").getAsDouble();

	        return  result;

	    }
	}