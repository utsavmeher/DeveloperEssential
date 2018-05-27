package developer.com.developeressential;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class Ingredients extends Activity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        Intent intent = getIntent();
        String recipe = intent.getStringExtra("recipe");
        String ingredients = intent.getStringExtra("ingredients");
        textView=(TextView)findViewById(R.id.recipeName);
        textView.setText(recipe);
        textView=(TextView)findViewById(R.id.ingredients);
        textView.setText(ingredients);
    }
}
