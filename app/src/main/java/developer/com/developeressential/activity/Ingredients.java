package developer.com.developeressential.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;

import developer.com.developeressential.R;

public class Ingredients extends Activity {

    TextView textView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        Intent intent = getIntent();
        String recipe = intent.getStringExtra("recipe");
        String ingredients = intent.getStringExtra("ingredients");
        byte[] imageArray = intent.getByteArrayExtra("image");
        Bitmap bitmap = getImage(imageArray);
        imageView = (ImageView) this.findViewById(R.id.recipeImage);
        imageView.setImageBitmap(bitmap);
        textView=(TextView)findViewById(R.id.recipeName);
        textView.setText(recipe);
        textView=(TextView)findViewById(R.id.ingredients);
        textView.setText(ingredients);
    }
    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
