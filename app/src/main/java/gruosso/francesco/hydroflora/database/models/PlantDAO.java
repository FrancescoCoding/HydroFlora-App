package gruosso.francesco.hydroflora.database.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PlantDAO {

    // Create a method to insert a plant in the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Plant plant);

    // Create a method to insert a plant in the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Plant> plant);

    // Create a method to delete a plant from the database by id
    @Query ("DELETE FROM plant WHERE id = :id")
    void delete(int id);

    // Create a method to update a plant in the database
    @Update
    void update(Plant plant);

    // Create a method to get all the plants from the database
    @Query("SELECT * FROM plant")
    List<Plant> getAllPlants();

    // Create a method to get a plant by id
    @Query("SELECT * FROM plant WHERE id = :id")
    Plant getPlantById(int id);

    // clear the database
    @Query("DELETE FROM plant")
    void deleteAll();

    // Get plant image by id
    @Query("SELECT image FROM plant WHERE id = :id")
    String getPlantImageById(int id);

    
}
