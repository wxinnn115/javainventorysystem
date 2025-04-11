/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package databaseconnection;

/**
 *
 * @author CYBORG 15
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    
    //check stock quantity for transaction page
    public static int getStockQuantity(String itemCode) {
        String query = "SELECT stockQuantity FROM item WHERE itemCode = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, itemCode);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("stockQuantity");
                } else {
                    throw new SQLException("Item not found!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving stock quantity: " + e.getMessage());
        }
    }
    
    //CREATE (INSERT new item with stockQuantity = 0)
    public static void addItem(String itemCode, String itemName, double price, String supplierCode) {
        String query = "INSERT INTO Item (itemCode, itemName, stockQuantity, price, supplierCode) VALUES (?, ?, 0, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, itemCode);
            pstmt.setString(2, itemName);
            pstmt.setDouble(3, price);
            pstmt.setString(4, supplierCode);

            pstmt.executeUpdate();
            System.out.println("Item added successfully! New item stockQuantity = 0!");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error: " + e.getMessage());
        }
    }

    //READ (Retrieve all items in ascending order)
    public static List<Item> getAllItemsSortedByCode() {
        List<Item> itemList = new ArrayList<>();
        String query = "SELECT itemCode, itemName, stockQuantity, price, supplierCode FROM item ORDER BY itemCode ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

           while(rs.next()){
               Item item = new Item(
                       rs.getString("itemCode"),
                        rs.getString("itemName"),
                        rs.getInt("stockQuantity"),
                        rs.getDouble("price"),
                        rs.getString("supplierCode")
               );
               itemList.add(item);
           }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving items: " + e.getMessage());
        }
        return itemList;
    } 
    
    //UPDATE (Modify item details excluding stockQuantity for existing items)
    public static void updateItemDetails(String itemCode, String itemName, double price, String supplierCode) {
        String query = "UPDATE Item SET itemName = ?, price = ?, supplierCode = ? WHERE itemCode = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, itemName);
            pstmt.setDouble(2, price);
            pstmt.setString(3, supplierCode);
            pstmt.setString(4, itemCode);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Item details updated!");
            } else {
                System.out.println("Item not found!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error: " + e.getMessage());
        }
    }

    //DELETE (Remove an item)
    public static void deleteItem(String itemCode) {
        String query = "DELETE FROM Item WHERE itemCode = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, itemCode);
            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Item deleted successfully!");
            } else {
                System.out.println("Item not found!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

