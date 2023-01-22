package gruosso.francesco.hydroflora;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import gruosso.francesco.hydroflora.database.Converters;
import gruosso.francesco.hydroflora.services.PlantRepository;

public class PlantInfo extends Fragment implements View.OnClickListener {

    public PlantInfo() {
        // Required empty public constructor
    }

    public static PlantInfo newInstance() {
        PlantInfo fragment = new PlantInfo();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_plant_info, container, false);

        Button scheduleButton = view.findViewById(R.id.schedule_label);
        scheduleButton.setOnClickListener(this);

        String plantName = getArguments().getString("plantName");
        String plantType = getArguments().getString("plantType");
        String plantNextWatering = getArguments().getString("plantNextWatering");
        String plantWaterCycle = getArguments().getString("waterCycle");
        int plantId = getArguments().getInt("plantId");

        plantType = plantType.substring(0, 1).toUpperCase() + plantType.substring(1).toLowerCase();

        ((TextView) view.findViewById(R.id.plant_item_name)).setText(plantName);
        ((TextView) view.findViewById(R.id.plant_item_type)).setText(plantType);
        ((TextView) view.findViewById(R.id.plant_item_next_watering)).setText(plantNextWatering);
        ((TextView) view.findViewById(R.id.water_cycle_label)).setText("Next water cycle: every " + plantWaterCycle + " days");

        PlantRepository plantRepository = PlantRepository.getRepository(getContext());

        // Get big plant image
        ImageView plantImage = view.findViewById(R.id.plant_item_image_xl);

        // Get image string form local db
        Thread t = new Thread(() -> {
            // Store the new plant in the database
            String image = plantRepository.getPlantImageById(plantId);
            Bitmap bitmap = Converters.StringToBitMap(image);
            plantImage.setImageBitmap(bitmap);
        });

        t.start();

        try {
            t.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Using StringToBitMap from Converters class to convert the string to bitmap

        // Set the bitmap to the imageview
        plantImage.setPadding(0, 0, 0, 0);

        // Get the remove plant button
        Button removePlantButton = view.findViewById(R.id.remove_plant_button);

        // When clicking on remove plant, remove this plant from the database via id
        removePlantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread t = new Thread(() -> {
                    PlantRepository plantRepository = PlantRepository.getRepository(getContext());
                    // Store the new plant in the database
                    plantRepository.deletePlant(plantId);
                });

                t.start();

                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Navigation.findNavController(view).navigate(R.id.action_plantInfo_to_plantRemoved);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View view) {
        // Navigation back to Plants Schedule
        Navigation.findNavController(view).navigate(R.id.action_plantInfo_to_plantsSchedule);
    }
}