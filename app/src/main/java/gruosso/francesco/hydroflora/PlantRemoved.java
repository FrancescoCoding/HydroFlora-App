package gruosso.francesco.hydroflora;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PlantRemoved extends Fragment {

    public PlantRemoved() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PlantRemoved newInstance(String param1, String param2) {
        PlantRemoved fragment = new PlantRemoved();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // After a delay of 3 seconds the animation it goes back to the schedule
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        Navigation.findNavController(getView()).navigate(R.id.action_plantRemoved_to_plantsSchedule);
                    }
                },
                2600);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plant_removed, container, false);
    }
}