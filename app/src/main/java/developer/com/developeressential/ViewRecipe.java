package developer.com.developeressential;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewRecipe extends Activity {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        AsynckTaskRunner runner = new AsynckTaskRunner();
        runner.execute("view");
    }

    private class AsynckTaskRunner extends AsyncTask<String, String, String> {
        private String resp;
        ProgressDialog progressDialog;
        List<String> recipeNames;
        Map<String, String> recipes = new HashMap<String, String>();

        @Override
        protected String doInBackground(String... params) {
            String whatTodo=params[0];
            publishProgress("Saving..."); // Calls onProgressUpdate()
            try {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    connect = DriverManager.getConnection("jdbc:mysql://myapp.c7dtuh83ephj.us-east-1.rds.amazonaws.com:3306/devessentials","admin","Utsav123$");
                    String sql = "select * from RECIPES";
                    preparedStatement = connect.prepareStatement(sql);
                    ResultSet rs=preparedStatement.executeQuery();
                    while(rs.next()){
                        recipes.put(rs.getString(2), rs.getString(3));
                    }
                } catch (Exception e) {
                    Toast.makeText(ViewRecipe.this, "Recipes Retrieved Failed", Toast.LENGTH_LONG).show();
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
            listView=(ListView)findViewById(R.id.recipies);
            List<String> recipeNames = new ArrayList<String>(recipes.keySet());
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ViewRecipe.this,R.layout.recipies, recipeNames );
            listView.setAdapter(arrayAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                // selected item
                                String recipeName = ((TextView) view).getText().toString();
                                // Launching new Activity on selecting single List Item
                                Intent i = new Intent(ViewRecipe.this, Ingredients.class);
                                // sending data to new activity
                                i.putExtra("recipe", recipeName);
                                i.putExtra("ingredients", recipes.get(recipeName));
                                startActivity(i);
                            }
                        });
            Toast.makeText(ViewRecipe.this, "Recipes Retrieved Successfully", Toast.LENGTH_LONG).show();
        }
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(ViewRecipe.this,
                    "Recipes",
                    "Fetching Recipes...");
        }
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
}
