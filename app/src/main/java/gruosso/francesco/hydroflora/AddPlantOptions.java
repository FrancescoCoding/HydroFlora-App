package gruosso.francesco.hydroflora;

import static android.app.Activity.RESULT_OK;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import gruosso.francesco.hydroflora.database.models.Plant;
import gruosso.francesco.hydroflora.services.PlantRecognitionRequests;
import gruosso.francesco.hydroflora.services.PlantRepository;

public class AddPlantOptions extends Fragment {
    private String plantName;
    private Calendar plantNextWatering = new GregorianCalendar();
    private Bitmap plantImage;
    private int plantWaterCycleDays;
    private String plantType;

    PlantRecognitionRequests plantRecognitionService = new PlantRecognitionRequests();

    private final int CAMERA_REQUEST_CODE = 16;

    public AddPlantOptions() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AddPlantOptions newInstance(String param1, String param2) {
        AddPlantOptions fragment = new AddPlantOptions();
        Bundle args = new Bundle();
        // --
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_plant_options, container, false);

        // Go back to plants schedule view
        // Get button
        Button backToPlantsButton = view.findViewById(R.id.back_to_plants_button);
        // On click listener to navigate back...
        backToPlantsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ...to the schedule fragment
                Navigation.findNavController(v).navigate(R.id.action_addPlantOptions_to_plantsSchedule);
            }
        });

        // -- CAMERA
        // Open camera intent
        // Get button
        ImageView cameraButton = view.findViewById(R.id.plant_camera_button);

        // Open camera service and pass the needed request code
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
            }
        });

        // -- CALENDAR
        // Open calendar view
        ImageView calendarButton = view.findViewById(R.id.date_picker_button);
        TextView wateringDate = view.findViewById(R.id.watering_date);

        // Open date picker service from android
        // Adapted from:
        // https://www.geeksforgeeks.org/how-to-popup-datepicker-while-clicking-on-edittext-in-android/
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our edit text.
                                wateringDate.setText("         " + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year + "        ");

                                // Set the calendar to the selected date something like this
                                // new GregorianCalendar(2022, 12 - 1, 25);
                                // Date christmasAsDate = christmas.getTime();
                                plantNextWatering = new GregorianCalendar(year, monthOfYear, dayOfMonth);

                                // If the date was not already selected, change calendar icon
                                if (calendarButton.getDrawable().getConstantState() != getResources().getDrawable(R.drawable.calendar_selected).getConstantState()) {
                                    calendarButton.setImageResource(R.drawable.calendar_selected);
                                }

                            }
                        },
                        // Pass the year, month and day for selected dates
                        year, month, day);
                // Display date picker dialog
                datePickerDialog.show();
            }
        });

        // -- PLANT NAME
        // Get plant name from input field
        TextView plantNameLabel = view.findViewById(R.id.plant_name_label);
        EditText plantNameInput = view.findViewById(R.id.plant_name_input);

        // Change plantName variable to user input on change
        plantNameInput.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                plantName = plantNameInput.getText().toString();

                return false;
            }
        });

        // -- PLANT TYPE
        // Get plant name from input field
        TextView plantTypeLabel = view.findViewById(R.id.plant_type_label);
        EditText plantTypeInput = view.findViewById(R.id.plant_type_input);

        // Change plantName variable to user input on change
        plantTypeInput.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                plantType = plantTypeInput.getText().toString();

                return false;
            }
        });

        // -- WATER CYCLE
        // Get water cycle from input field
        TextView waterCycleLabel = view.findViewById(R.id.water_cycle_label);
        EditText waterCycleInput = view.findViewById(R.id.water_cycle_input);

        // Change plantWaterCycleDays variable to user input on change
        waterCycleInput.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                try {
                    plantWaterCycleDays = Integer.parseInt(waterCycleInput.getText().toString());
                } catch (NumberFormatException ex) {
                    plantWaterCycleDays = 0;
                }
                return false;
            }
        });

        // -- SUBMIT OPTIONS BUTTON
        // Continue button
        Button createPlant = view.findViewById(R.id.create_plant);

        // On click listener create plant object
        createPlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // If the user did not select an image, warn him to select one
                if (plantImage == null) {
                    TextView imageLabel = getView().findViewById(R.id.plant_image_label);
                    imageLabel.setText("      Please select       \nyour image");
                    imageLabel.setTextColor(getResources().getColor(R.color.red));
                    imageLabel.setTypeface(null, Typeface.BOLD);
                    return;
                }

                // If the user did not select a name, the label field becomes red
                if (plantName == null || plantName.equals("")) {
                    plantNameLabel.setText("Plant name (required)");
                    plantNameLabel.setTextColor(getResources().getColor(R.color.red));
                    plantNameLabel.setTypeface(null, Typeface.BOLD);

                    // Reset label color and text style after 3 seconds
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            plantNameLabel.setText("Plant name");
                            plantNameLabel.setTextColor(getResources().getColor(R.color.white));
                            plantNameLabel.setTypeface(null, Typeface.NORMAL);
                        }
                    }, 3000);

                    return;
                }
                // If the user did not select a type, the label field becomes red
                if (plantType == null || plantType.equals("")) {
                    plantTypeLabel.setText("Plant type (required)");
                    plantTypeLabel.setTextColor(getResources().getColor(R.color.red));
                    plantTypeLabel.setTypeface(null, Typeface.BOLD);

                    // Reset label color and text style after 3 seconds
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            plantTypeLabel.setText("Plant type");
                            plantTypeLabel.setTextColor(getResources().getColor(R.color.white));
                            plantTypeLabel.setTypeface(null, Typeface.NORMAL);
                        }
                    }, 3000);

                    return;
                }
                if (plantWaterCycleDays == 0) {
                    waterCycleLabel.setText("Water cycle (required)");
                    waterCycleLabel.setTextColor(getResources().getColor(R.color.red));
                    waterCycleLabel.setTypeface(null, Typeface.BOLD);

                    // Reset label color and text style after 3 seconds
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            waterCycleLabel.setText("Water cycle");
                            waterCycleLabel.setTextColor(getResources().getColor(R.color.white));
                            waterCycleLabel.setTypeface(null, Typeface.NORMAL);
                        }
                    }, 3000);
                    return;
                }

                // Get date from the calendar
                Date wateringDate = plantNextWatering.getTime();

                // Create plant object
                Plant newPlant = new Plant(plantName, wateringDate, plantImage, plantWaterCycleDays, plantType);

                // Store plant in database
                Thread t = new Thread(() -> {
                    PlantRepository plantRepository = PlantRepository.getRepository(getContext());
                    // Store the new plant in the database
                    plantRepository.storePlant(newPlant);
                });

                t.start();

                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Clear db for testing
                //plantRepository.deleteAllPlants();


                // Navigate to action_addPlantOptions_to_plantAdded
                Navigation.findNavController(view).navigate(R.id.action_addPlantOptions_to_plantAdded);

            }
        });

        return view;
    }

    // -- CAMERA RESULT HANDLER
    // Method to get the image from camera and handle it
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // If the req code is the same as the one we passed
        // and the result is ok
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE) {
                // Get the image and make it a bit bigger
                Bitmap image = (Bitmap) (data.getExtras().get("data"));
                image = Bitmap.createScaledBitmap(image, 100, 100, false);

                TextView plantNameLabel = getView().findViewById(R.id.plant_image_label);
                plantNameLabel.setText("     That's a    \n    handsome plant!    ");
                plantNameLabel.setTextColor(getResources().getColor(R.color.white));
                plantNameLabel.setTypeface(null, Typeface.BOLD);

                // Change camera button to the image taken
                ImageView cameraButton = getView().findViewById(R.id.plant_camera_button);
                cameraButton.setImageBitmap(image);

                // Options to make the image fill the button
                cameraButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
                cameraButton.setPadding(0, 0, 0, 0);
                cameraButton.setCropToPadding(true);
                cameraButton.setClipToOutline(true);

                // try {
                //     plantRecognitionService.makeRequest(file);
                // } catch (IOException e) {
                //     e.printStackTrace();
                // }

                // Set picture image as the plant image
                plantImage = image;
            }
        }
    }
}