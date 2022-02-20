package com.example.launcher;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class APIData extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<RecyclerViewData> data = new ArrayList<>();
    ProgressBar loadingPB;
    String city_name, country_name, temperature_Celsius, description_detail;

    // Array of URLs of APIs to be parsed
    String[] links = new String[]{"http://weather.bfsah.com/beijing", "http://weather.bfsah.com/berlin",
            "http://weather.bfsah.com/cardiff", "http://weather.bfsah.com/edinburgh",
            "http://weather.bfsah.com/london"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.api_data);
        // set up the RecyclerView
        recyclerView = findViewById(R.id.apidata);
        // set up the progress bar
        loadingPB = findViewById(R.id.idLoadingPB);
        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(APIData.this);

        // as our data is in json object format so we are using
        // json object request to make data request from our url.
        // in below line we are making a json object
        // request and creating a new json object request.
        // inside our json object request we are calling a
        // method to get the data, "url" from where we are
        // calling our data,"null" as we are not passing any data.
        // later on we are calling response listener method
        // to get the response from all APIs.
        for (int i = 0; i < links.length; i++) {
            String url = links[i];
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //For hiding our progress bar.
                    loadingPB.setVisibility(View.GONE);

                    // Recyclerview is visible after getting all the data.
                    recyclerView.setVisibility(View.VISIBLE);
                    try {
                        //the response JSON Object
                        //and converts them into javascript objects
                        String text_city = response.getString("city");
                        String text_country = response.getString("country");
                        String text_temperature = response.getString("temperature");
                        String text_description = response.getString("description");

                        city_name = "City name: " + text_city;
                        country_name = "Country: " + text_country;
                        temperature_Celsius = "Current temperature: " + text_temperature;
                        description_detail = "Current description : " + text_description;


                        data.add(new RecyclerViewData(city_name, country_name, temperature_Celsius, description_detail));
                        RecyclerViewAdapter adapter = new RecyclerViewAdapter(data);
                        recyclerView.setAdapter(adapter);

                        // To set RecyclerView's layout
                        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(linearLayoutManager);

                        //To put Divider between each API data
                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                                linearLayoutManager.getOrientation());
                        recyclerView.addItemDecoration(dividerItemDecoration);
                    }
                    // Try and catch are included to handle any errors due to JSON
                    catch (JSONException e) {
                        // If an error occurs, this prints the error to the log
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                /**
                 * Callback method that an error has been occurred with the provided error code and optional
                 * user-readable message.
                 *
                 * @param error
                 */
                @Override
                public void onErrorResponse(VolleyError error) {
                    // below line is use to display a toast message along with our error.
                    Toast.makeText(APIData.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
                }
            });
            // at last we are adding our json
            // object request to our request
            // queue to fetch all the json data.
            queue.add(jsonObjectRequest);
            //System.out.println(city_name);

        }
    }
}

