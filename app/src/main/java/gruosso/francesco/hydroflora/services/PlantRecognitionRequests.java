package gruosso.francesco.hydroflora.services;

import android.util.Log;

import java.io.Console;
import java.io.File;
import java.io.IOException;

import gruosso.francesco.hydroflora.MainActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PlantRecognitionRequests {
    private static final String IMAGE1 = "https://watchandlearn.scholastic.com/content/dam/classroom-magazines/watchandlearn/videos/animals-and-plants/plants/what-are-plants-/What-Are-Plants.jpg";
    private static final String IMAGE2 = "https://www.thebrightonflowercompany.co.uk/wp-content/uploads/2021/01/cacti-3.jpeg";

    private static final String ORGAN = "auto";
    private static final String API_URL = "https://my-api.plantnet.org/v2/identify/all?api-key=<INSERT_YOUR_API_KEY>";

    OkHttpClient client = new OkHttpClient();

    public void makeRequest(File image) throws IOException {

        // Build multipart request body from image and add ORGAN as "organs" as form data
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("organs", ORGAN)
                .addFormDataPart("images", image.getName(), RequestBody.create(image, MediaType.parse("image/jpeg")))
                .build();

        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .build();

        // Make the request
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myResponse = response.body().string();
                    Log.d("TAG", "onResponse: " + myResponse);
                }
            }
        });

    }

}
