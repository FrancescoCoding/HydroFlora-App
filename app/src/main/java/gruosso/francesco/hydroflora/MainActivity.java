package gruosso.francesco.hydroflora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import gruosso.francesco.hydroflora.database.PlantDatabase;
import gruosso.francesco.hydroflora.services.PlantRecognitionRequests;
import gruosso.francesco.hydroflora.services.PlantRepository;

public class MainActivity extends AppCompatActivity {

    // create a response filed
    private String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Call PlantRecognitionRequests class to make a request to the API
         PlantRecognitionRequests plantRecognitionRequests = new PlantRecognitionRequests();

        // make request and pass the view to the callback
         //plantRecognitionRequests.makeRequest();
        
    }

}