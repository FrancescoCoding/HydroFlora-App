package gruosso.francesco.hydroflora.database.models;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import java.util.Date;

import gruosso.francesco.hydroflora.R;

@Entity(tableName = "plant")
public class Plant {
    // Fields
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String name;
    private String type;
    private Date nextWatering;
    @Nullable
    // private int image;
    private Bitmap image;
    private int waterCycleDays;

    // Constructor
    public Plant(String name, Date nextWatering, Bitmap image, int waterCycleDays, String type) {
        this.name = name;
        this.waterCycleDays = waterCycleDays;
        this.nextWatering = nextWatering;
        this.image = image;
        this.type = type;
    }

    @Ignore
    // Constructor with dummy data for testing
    public Plant() {
        this.name = "Plant";
        this.nextWatering = new Date();
        this.image = null;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getNextWatering() {
        return nextWatering;
    }

    public void setNextWatering(Date nextWatering) {
        this.nextWatering = nextWatering;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getWaterCycleDays() {
        return waterCycleDays;
    }

    public void setWaterCycleDays(int waterCycleDays) {
        this.waterCycleDays = waterCycleDays;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getType() {
        return type;
    }

    public void setType(String plantType) {
        this.type = plantType;
    }
}
