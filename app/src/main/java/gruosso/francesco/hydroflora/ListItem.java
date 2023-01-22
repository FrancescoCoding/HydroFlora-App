package gruosso.francesco.hydroflora;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListItem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListItem extends Fragment {
    public ListItem() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ListItem newInstance(String param1, String param2) {
        ListItem fragment = new ListItem();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.plant_list_item, container, false);

        // Just setting padding 0000 here
        ImageView plantImage = view.findViewById(R.id.plant_item_image);
        plantImage.setPadding(0, 0, 0, 0);

        return view;
    }
}