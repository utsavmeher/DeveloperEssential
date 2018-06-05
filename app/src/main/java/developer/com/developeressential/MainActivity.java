package developer.com.developeressential;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private Button mapButton;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openCamera(View view) {
        Intent intent = new Intent(MainActivity.this, CameraActivity.class);
        startActivity(intent);
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
