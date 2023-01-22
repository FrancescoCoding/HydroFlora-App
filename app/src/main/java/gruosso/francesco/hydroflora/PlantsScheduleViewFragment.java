package gruosso.francesco.hydroflora;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import gruosso.francesco.hydroflora.database.models.Plant;
import gruosso.francesco.hydroflora.services.PlantRepository;

public class PlantsScheduleViewFragment<plant1> extends Fragment {

    PlantRepository plantRepository;

    private static final int REQUEST_CODE = 24;

    // Create the list of plants
    List<Plant> plants = new ArrayList<>();
    // Get list of plants from database
    //List<Plant> plants = plantRepository.getAllPlants();


    // Christmas day for testing app and having a Christmas countdown!
    Calendar christmas = new GregorianCalendar(2022, 12 - 1, 25);
    Date christmasAsDate = christmas.getTime();

    //
    // Create 2 plants Plant(String name, Date nextWatering, Date nextFertilizing, int image, String status)
    // Plant plant1 = new Plant("Christmas Tree", christmasAsDate, 0, 4);
    // Plant plant2 = new Plant("Plantogenes", new Date(), 0, 4);
    // Plant plant3 = new Plant("Plantocrates", new Date(), 0, 4);
    // Plant plant4 = new Plant("Planto", new Date(), 0, 4);
    // Plant plant5 = new Plant("Plant Aurelius", new Date(), 0, 4);
    // Plant plant6 = new Plant("Plantenides", new Date(), 0, 4);
    // Plant plant7 = new Plant("Planthagoras", new Date(), 0, 4);
    // Plant plant8 = new Plant("Alexander the Plant", new Date(), 0, 4);

    public PlantsScheduleViewFragment() {
        // Required empty public constructor
    }

    public static PlantsScheduleViewFragment newInstance() {
        PlantsScheduleViewFragment fragment = new PlantsScheduleViewFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  Get all plants from local database on a different thread
        Thread t = new Thread(() -> {
            plantRepository = PlantRepository.getRepository(getContext());
            plants = plantRepository.getAllPlants();
        });

        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // Sort the plants by watering date so the one with the closest date is at the top
        // Ugly nested loop sort attempt - to be made more efficient probably...
        for (int i = 0; i < plants.size(); i++) {
            for (int j = 0; j < plants.size(); j++) {
                if (plants.get(i).getNextWatering().before(plants.get(j).getNextWatering())) {
                    Plant temp = plants.get(i);
                    plants.set(i, plants.get(j));
                    plants.set(j, temp);
                }
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plants_schedule, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Testing
        // Add all the plants to the list
        //  plants.add(plant1);
        //  plants.add(plant2);
        //  plants.add(plant3);
        //  plants.add(plant4);
        //  plants.add(plant5);
        //  plants.add(plant6);
        //  plants.add(plant7);
        //  plants.add(plant8);

        // Get open_camera_button from xml
        ImageView openCameraButton = view.findViewById(R.id.add_plant_options);
        // Set onClickListener on open_camera_button
        openCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_plantsSchedule_to_addPlantOptions);
            }
        });

        // add plant 1 with the plantDAO
        // PlantRepository plantRepository = PlantRepository.getRepository(getContext());
        // plantRepository.storePlant(plant1);

        //Get the recycler view
        RecyclerView recyclerView = view.findViewById(R.id.local_plants_list);

        // if the plants list is empty, show the empty view and show a message to the user TODO
        if (plants.isEmpty()) {
            view.findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
            view.findViewById(R.id.local_plants_list).setVisibility(View.GONE);
        } else {
            view.findViewById(R.id.empty_view).setVisibility(View.GONE);
            view.findViewById(R.id.local_plants_list).setVisibility(View.VISIBLE);
        }

        // create a new Adapter for the RecyclerView
        PlantScheduleViewAdapter adapter = new PlantScheduleViewAdapter(
                getContext(), plants);

        // Set the adapter to the RecyclerView
        recyclerView.setAdapter(adapter);

        // Set the layout manager to the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

}