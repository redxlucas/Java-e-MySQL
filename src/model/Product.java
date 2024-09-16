package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Product {
    private int id;
    private String name;
    private double value;
    private ConnectionMySQL connectionMySQL = new ConnectionMySQL();
//#region
    public Product(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public Product(){
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getValue() {
        return value;
    }
    public void setValue(double value) {
        this.value = value;
    }
//#endregion
    public void insertCascade(){
        Connection newConnection = connectionMySQL.getConnection();
        PreparedStatement preparedStatement = null;

        String insertInto = "INSERT INTO product (name, value)"
            + "VALUES(?,?)"; 
        
        try {
            preparedStatement = newConnection.prepareStatement(insertInto, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, this.name);
            preparedStatement.setDouble(2, this.value);
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()) this.id = resultSet.getInt(1);

            String insertIntoProduct_Category = "INSERT INTO product_category (product_id, category_id)"
                + "VALUES (?,?)";

            for (Category category : this.category) {
                PreparedStatement preparedStatement2 = newConnection.prepareStatement(insertIntoProduct_Category);
                preparedStatement2.setInt(1, this.id);
                preparedStatement2.setInt(2, category.getId());
                preparedStatement2.executeUpdate();
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            try{
                if(preparedStatement != null) preparedStatement.close();
                if(newConnection != null) newConnection.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    public Product get(int id){
        Connection newConnection = connectionMySQL.getConnection();
        PreparedStatement preparedStatement = null;
        Product product = new Product();

        String selectWhere = "SELECT * FROM Product WHERE id = ?";

        try{
            preparedStatement = newConnection.prepareStatement(selectWhere);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setValue(resultSet.getDouble("value"));
            }

        } catch(SQLException e){
            e.printStackTrace();
        } finally{
            try{
                if(preparedStatement != null) preparedStatement.close();
                if(newConnection != null) newConnection.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return product;
    }

    public ArrayList<Product> getAll(){
        Connection newConnection = connectionMySQL.getConnection();
        Statement statement = null;
        ArrayList<Product> productList = new ArrayList<>();

        String select = "SELECT * FROM Product";

        try{
            statement = newConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            while(resultSet.next()){
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setValue(resultSet.getDouble("value"));
                productList.add(product);
            }
        } catch(SQLException e){
            e.printStackTrace();
        } finally{
            try{
                if(statement != null) statement.close();
                if(newConnection != null) newConnection.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return productList;
    }

    public void update(int id){
        Connection newConnection = connectionMySQL.getConnection();
        PreparedStatement preparedStatement = null;

        String updateWhere = "UPDATE Product SET name = ?, value = ? WHERE id = ?";

        try{
            preparedStatement = newConnection.prepareStatement(updateWhere);
            preparedStatement.setString(1, this.name);
            preparedStatement.setDouble(2, this.value);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        } finally{
            try{
                if(preparedStatement != null) preparedStatement.close();
                if(newConnection != null) newConnection.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    public boolean delete(int id){
        Connection newConnection = connectionMySQL.getConnection();
        PreparedStatement preparedStatement = null;

        String deleteWhere = "DELETE FROM Product WHERE id = ?";

        try{
            preparedStatement = newConnection.prepareStatement(deleteWhere);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        } finally{
            try{
                if(preparedStatement != null) preparedStatement.close();
                if(newConnection != null) newConnection.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return true;
    }
}
