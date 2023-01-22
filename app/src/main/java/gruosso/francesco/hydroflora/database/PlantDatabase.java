package gruosso.francesco.hydroflora.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import gruosso.francesco.hydroflora.database.models.Plant;
import gruosso.francesco.hydroflora.database.models.PlantDAO;

@Database(entities = {Plant.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class PlantDatabase extends RoomDatabase {
    // Create a static instance of the database
    private static PlantDatabase instance;
    // Create an abstract method to access the DAO
    public abstract PlantDAO plantDAO();

    // Create a method to get the database instance
    public static synchronized PlantDatabase getDatabase(final Context context) {
        // Check if the instance is null
        if (instance == null) {
            // If it is null create a new instance
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    PlantDatabase.class, "new_plants")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        // Return the instance
        return instance;
    }
}