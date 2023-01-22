package gruosso.francesco.hydroflora;

import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class LandingPage extends Fragment implements View.OnClickListener {

    public LandingPage() {
        // Required empty public constructor
    }

    public static LandingPage newInstance(String param1, String param2) {
        LandingPage fragment = new LandingPage();
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
        View view = inflater.inflate(R.layout.fragment_landing_page, container, false);

        Button getStartedButton = view.findViewById(R.id.get_started);

        getStartedButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        // Go to PlantsSchedule
        Navigation.findNavController(view).navigate(R.id.action_landingPage_to_plantsSchedule);
    }
}
