package developer.com.developeressential.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import developer.com.developeressential.R;

public class MainActivity extends Activity {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private Button mapButton;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addList(View view) {
        Intent intent = new Intent(MainActivity.this, RecipeActivity.class);
        startActivity(intent);
    }

    public void viewList(View view) {
        Intent intent = new Intent(MainActivity.this, ViewRecipe.class);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("MyRecipes");
        alert.show();
        setContentView(R.layout.activity_main);
        return super.onKeyDown(keyCode, event);
    }
}
