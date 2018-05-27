package developer.com.developeressential;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RecipeActivity extends Activity {

    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
    }

    public void saveRecipe(View view){
        AsynckTaskRunner runner = new AsynckTaskRunner();
        runner.execute("save");
    }

    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {
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
                        Class.forName("com.mysql.jdbc.Driver");
                        connect = DriverManager.getConnection("jdbc:mysql://myapp.c7dtuh83ephj.us-east-1.rds.amazonaws.com:3306/devessentials","admin","Utsav123$");
                        String sql = "INSERT INTO RECIPES (NAME, INGREDIENTS) VALUES (?,?)";
                        preparedStatement = connect.prepareStatement(sql);
                        preparedStatement.setString(1,recipeName);
                        preparedStatement.setString(2,ingredients);
                        int i=preparedStatement.executeUpdate();
                    } catch (Exception e) {
                        Toast.makeText(RecipeActivity.this, "Recipes Addition Failed", Toast.LENGTH_LONG).show();
                    } finally {
                        close();
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
}
