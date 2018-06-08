package developer.com.developeressential.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import developer.com.developeressential.R;
import developer.com.developeressential.service.RecipeService;

public class RecipeActivity extends Activity {


    private static final int CAMERA_REQUEST = 1888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
    }

    public void saveRecipe(View view){
        AsynckTaskRunner runner = new AsynckTaskRunner();
        runner.execute("save");
    }

    public void openCamera(View view) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ImageView imageView = (ImageView) this.findViewById(R.id.imageView);
            imageView.setImageBitmap(photo);
        }
    }

    private class AsynckTaskRunner extends AsyncTask<String, String, String>{
        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            String whatTodo=params[0];
                publishProgress("Saving..."); // Calls onProgressUpdate()
                try {
                    try {
                        String recipeName = ((EditText) findViewById(R.id.recipeName)).getText().toString();
                        String ingredients = ((EditText) findViewById(R.id.ingridients)).getText().toString();
                        ImageView imageView = (ImageView)findViewById(R.id.imageView);
                        imageView.setDrawingCacheEnabled(true);
                        imageView.buildDrawingCache();
                        Bitmap bitmap = Bitmap.createBitmap(imageView.getDrawingCache());
                        byte[] bitmapdata = getBytes(bitmap);
                        RecipeService recipeService=new RecipeService();
                        recipeService.addRecipe(recipeName, ingredients, bitmapdata);
                    } catch (Exception e) {
                        Toast.makeText(RecipeActivity.this, "Recipes Addition Failed", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    resp = e.getMessage();
                }
            return resp;
        }
        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            progressDialog.dismiss();
            Toast.makeText(RecipeActivity.this, "Recipes Added Successfully", Toast.LENGTH_LONG).show();
        }
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(RecipeActivity.this,
                    "Recipes",
                    "Saving Recipe...");
        }
    }
    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }
}
