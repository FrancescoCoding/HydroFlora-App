package gruosso.francesco.hydroflora;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import gruosso.francesco.hydroflora.database.models.Plant;

public class PlantScheduleViewAdapter extends RecyclerView.Adapter<PlantScheduleViewAdapter.PlantViewHolder> {

    // Fields
    private Context context;
    private List<Plant> plants;
    private String nextWateringString;

    androidx.core.app.NotificationManagerCompat NotificationManagerCompat;
    Notification notification;

    // Constructor
    public PlantScheduleViewAdapter(Context context, List<Plant> plants) {
        super();
        this.context = context;
        this.plants = plants;
    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).
                inflate(R.layout.plant_list_item, parent, false);
        PlantViewHolder viewHolder = new PlantViewHolder(itemView, this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int position) {
        // get the plant at position
        Plant plant = this.plants.get(position);

        // Create a nextWatering variable
        String nextWatering = calculateNextWateringDate(plant.getNextWatering());

        // Update the plant name
        TextView plantName = holder.plantItemView.findViewById(R.id.plant_item_name);
        plantName.setText(plant.getName());

        // Make text bold
        plantName.setTypeface(null, Typeface.NORMAL);

        // Handle day vs days
        nextWateringString = nextWatering + " day" + (nextWatering.equals("1") ? "" : "s");

        // If the plant has to be watered today, make the plant name bold
        if (nextWatering.equals("0")) {
            nextWateringString = "Today";
            plantName.setTypeface(null, Typeface.BOLD);

            // Send push notification
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("channel", "notificationChannel", NotificationManager.IMPORTANCE_DEFAULT);

                NotificationManager manager =  getSystemService(context, NotificationManager.class);

                manager.createNotificationChannel(channel);
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel")
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle("HydroFlora")
                    .setContentText("It's time to water " + plant.getName() + "!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            notification = builder.build();

            NotificationManagerCompat = NotificationManagerCompat.from(context);

            NotificationManagerCompat.notify(1, notification);
        }

        // If the has been watered yesterday, add the water cycle days
        //  to the next watering date so the cycle starts again
        if (nextWatering.equals("-1")) {
            plant.setNextWatering(new Date(plant.getNextWatering().getTime() + TimeUnit.DAYS.toMillis(plant.getWaterCycleDays() + 1)));

            nextWatering = calculateNextWateringDate(plant.getNextWatering());

            nextWateringString = nextWatering + " day" + (nextWatering.equals("1") ? "" : "s");
        }

        // update the plant next watering
        TextView plantNextWatering = holder.plantItemView.findViewById(R.id.plant_item_next_watering);
        plantNextWatering.setText(nextWateringString);

        // If nextWatering number of days is 0, background label should be water colour...
        if (Integer.parseInt(nextWatering) == 0) {
            plantNextWatering.setBackgroundResource(R.drawable.label);
        } else if (Integer.parseInt(nextWatering) < 7) {
            // ...if nextWatering number of days is less than 7, background label should be red...
            plantNextWatering.setBackgroundResource(R.drawable.label_red);
            plantNextWatering.setTextColor(context.getResources().getColor(R.color.white));
        } else if (Integer.parseInt(nextWatering) < 12) {
            // ...if the number of days is less than 12, background label should be yellow...
            plantNextWatering.setBackgroundResource(R.drawable.label_yellow);
        } else {
            // ...if the number of days is bigger than 12, background label should be blue...
            plantNextWatering.setBackgroundResource(R.drawable.label_blue);
            plantNextWatering.setTextColor(context.getResources().getColor(R.color.white));
        }

        // update the plant image
        ImageView plantImage = holder.plantItemView.findViewById(R.id.plant_item_image);
        plantImage.setImageBitmap(plant.getImage());

    }

    @Override
    public int getItemCount() {
        return plants.size();
    }

    class PlantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private View plantItemView;
        private PlantScheduleViewAdapter adapter;

        public PlantViewHolder(View plantItemView, PlantScheduleViewAdapter adapter) {
            super(plantItemView);
            this.plantItemView = plantItemView;
            this.adapter = adapter;
            this.plantItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();

            Plant plant = plants.get(position);

            // Creates a bundle to pass the plant info to the info fragment
            Bundle bundle = new Bundle();
            bundle.putString("plantName", plant.getName());

            bundle.putString("plantNextWatering", nextWateringString);

            bundle.putString("waterCycle", Integer.toString(plant.getWaterCycleDays()));

            bundle.putInt("plantId", plant.getId());

            bundle.putString("plantType", plant.getType());

            // navigate plantsSchedule to PlantInfo fragment
            Navigation.findNavController(view).navigate(R.id.action_plantsSchedule_to_plantInfo, bundle);

            // -- Initial Testing
            //Log.v("PlantRecyclerViewAdapter", "onClick: " + plant.getName());
            // Change that plant's name to "Clicked"
            //plant.setName("Clicked");
            // notify the adapter that the data has changed
            //adapter.notifyDataSetChanged();
        }
    }

    // TODO: To be moved and calculated from plant options
    // Adapted from:
    // https://stackabuse.com/how-to-get-the-number-of-days-between-dates-in-java/
    public static String calculateNextWateringDate(Date nextWateringDate) {
        Date today = new Date();
        Date nextWatering = nextWateringDate;

        // If the next watering date date has same day and month as today, return 0 (prints "today" in the label)
        if (nextWatering.getDate() == today.getDate() && nextWatering.getMonth() == today.getMonth()) {
            return "0";
        }

        long dateBeforeInMs = today.getTime();
        long dateAfterInMs = nextWatering.getTime();

        long timeDiff = Math.abs(dateAfterInMs - dateBeforeInMs);
        long daysDiff = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);

        String daysDiffString = String.valueOf(++daysDiff);

        return daysDiffString;
    }
}
