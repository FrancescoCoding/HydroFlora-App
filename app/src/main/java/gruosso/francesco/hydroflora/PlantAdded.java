package gruosso.francesco.hydroflora;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlantAdded#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlantAdded extends Fragment {

    public PlantAdded() {
        // Required empty public constructor
    }

    public static PlantAdded newInstance() {
        PlantAdded fragment = new PlantAdded();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // After a delay of 3 seconds it goes back to the schedule action_plantAdded_to_plantsSchedule
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        Navigation.findNavController(getView()).navigate(R.id.action_plantAdded_to_plantsSchedule);
                    }
                },
                3000);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plant_added, container, false);
    }
}