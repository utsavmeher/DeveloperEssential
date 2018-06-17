package developer.com.developeressential.service;

import android.widget.Toast;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import developer.com.developeressential.activity.ViewRecipe;
import developer.com.developeressential.model.Recipe;

public class RecipeService {

    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    Map<String, Recipe> recipes = new HashMap<String, Recipe>();
    public void addRecipe(String recipeName, String ingredients, byte[] bitmapdata){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://myapp.c7dtuh83ephj.us-east-1.rds.amazonaws.com:3306/myapp","admin","Utsav123$");
            String sql = "INSERT INTO RECIPES (NAME, INGREDIENTS, IMAGE) VALUES (?,?,?)";
            preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setString(1,recipeName);
            preparedStatement.setString(2,ingredients);
            preparedStatement.setBytes(3,bitmapdata);
            int i=preparedStatement.executeUpdate();
        } catch (Exception e){

        } finally {
            close();
        }
    }
    public Map<String, Recipe> getRecipe(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://myapp.c7dtuh83ephj.us-east-1.rds.amazonaws.com:3306/myapp","admin","Utsav123$");
            String sql = "select * from RECIPES";
            preparedStatement = connect.prepareStatement(sql);
            ResultSet rs=preparedStatement.executeQuery();
            while(rs.next()){
                Recipe recipe = new Recipe();
                byte[] imageArray;
                Blob blob;
                recipe.setName(rs.getString(2));
                recipe.setIngredients(rs.getString(3));
                blob=rs.getBlob(4);
                imageArray=blob.getBytes(1,(int)blob.length());
                recipe.setImageArray(imageArray);
                recipes.put(rs.getString(2), recipe);
            }
        } catch (Exception e) {

        } finally {
            close();
        }
        return recipes;
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
