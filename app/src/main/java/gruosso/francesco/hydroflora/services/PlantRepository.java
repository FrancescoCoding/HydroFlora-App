package gruosso.francesco.hydroflora.services;

import android.content.Context;

import java.util.List;

import gruosso.francesco.hydroflora.database.PlantDatabase;
import gruosso.francesco.hydroflora.database.models.Plant;
import gruosso.francesco.hydroflora.database.models.PlantDAO;

public class PlantRepository {

    Context context;
    PlantDAO mPlantDAO;
    private static PlantRepository INSTANCE;

    private PlantRepository(Context context){
        super();
        this.context = context;

        // setup for taskDao for accessing the database
        mPlantDAO = PlantDatabase.getDatabase(context).plantDAO();
    }

    public static PlantRepository getRepository(Context context){
        if (INSTANCE == null){
            synchronized (PlantRepository.class) {
                if (INSTANCE == null)
                    INSTANCE = new PlantRepository(context);
            }
        }
        return INSTANCE;
    }

    // Store plant in local database
    public void storePlant(Plant plant){
        mPlantDAO.insert(plant);
    }

    // Get all plants from local database
    public List<Plant> getAllPlants(){
        return mPlantDAO.getAllPlants();
    }

    // Delete plant from local database by id
    public void deletePlant(int id){
        mPlantDAO.delete(id);
    }

    // delete all plants from local database
    public void deleteAllPlants(){
        mPlantDAO.deleteAll();
    }

    // Update plant in local database
    public void updatePlant(Plant plant){
        mPlantDAO.update(plant);
    }

    // Get plant image from local database by id
    public Plant getPlantById(int id){
        return mPlantDAO.getPlantById(id);
    }
    
    // getPlantImageById
    public String getPlantImageById(int id){
        return mPlantDAO.getPlantImageById(id);
    }
}
