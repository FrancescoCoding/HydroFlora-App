package gruosso.francesco.hydroflora.database;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class Converters {
    // Converters class is used to convert data types that Room doesn't support
    // For example, Date is not supported by Room, so we need to convert it to a Long
    // and vice versa

    // Convert a Date to a Long
    @androidx.room.TypeConverter
    public static Long fromDate(Date date) {
        if (date == null) {
            return null;
        }
        return date.getTime();
    }

    // Convert a Long to a Date
    @androidx.room.TypeConverter
    public static Date toDate(Long timestamp) {
        if (timestamp == null) {
            return null;
        }
        return new Date(timestamp);
    }

    // Convert a String to a Date
    @androidx.room.TypeConverter
    public static Date stringToDate(String date) {
        if (date == null) {
            return null;
        }
        return new Date(date);
    }

    // Convert a Date to a String
    @androidx.room.TypeConverter
    public static String dateToString(Date date) {
        if (date == null) {
            return null;
        }
        return date.toString();
    }

    // Convert a Bitmap to String
    // Adapted from:
    // https://medium.com/@uttam.cooch/save-images-in-room-persistence-library-c71b60865b7e
    @androidx.room.TypeConverter
    public static String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        if (temp == null) {
            return null;
        } else
            return temp;
    }

    @androidx.room.TypeConverter
    public static Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            if (bitmap == null) {
                return null;
            } else {
                return bitmap;
            }

        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}
