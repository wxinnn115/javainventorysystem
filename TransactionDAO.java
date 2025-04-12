import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    
    public static void insertTransaction(Transaction t) throws InsufficientStockException {
        String insertQuery = "INSERT INTO transactions (transactionType, itemCode, supplierCode, hospitalCode, userID, quantity, transactionDate) " +
                            "VALUES (?, ?, ?, ?, ?, ?, CURDATE())";
        String updateQuery = "UPDATE item SET stockQuantity = stockQuantity " +
                            (t.getTransactionType().equals("RECEIVED") ? "+ ?" : "- ?") + " WHERE itemCode = ?";

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            
            if (t.getTransactionType().equals("DISTRIBUTED")) {
                int currentStock = itemDAO.getStockQuantity(t.getItemCode());
                if (currentStock < t.getQuantity()) {
                    throw new InsufficientStockException("Insufficient stock! Current stock: " + currentStock + ". Requested: " + t.getQuantity());
                }
            }

            
            try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                insertStmt.setString(1, t.getTransactionType());
                insertStmt.setString(2, t.getItemCode());
                insertStmt.setString(3, t.getSupplierCode());
                insertStmt.setString(4, t.getHospitalCode());
                insertStmt.setString(5, t.getUserID());
                insertStmt.setInt(6, t.getQuantity());
                insertStmt.executeUpdate();
            }

            
            try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                updateStmt.setInt(1, t.getQuantity());
                updateStmt.setString(2, t.getItemCode());
                int rowsUpdated = updateStmt.executeUpdate();
                if (rowsUpdated == 0) {
                    throw new SQLException("Item not found: " + t.getItemCode());
                }
            }

            conn.commit(); 
            System.out.println("Transaction inserted and stock updated successfully!");
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); 
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
            throw new RuntimeException("Database error: " + e.getMessage());
        } catch (InsufficientStockException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
    }

    
public static List<Transaction> getDistributedTransactions(String startDate, String endDate) {
    List<Transaction> list = new ArrayList<>();
    String query = "SELECT * FROM transactions WHERE transactionType = 'DISTRIBUTED' ORDER BY transactionDate DESC";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query);
         ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
            Transaction t = new Transaction();
            t.setTransactionID(rs.getInt("transactionID"));
            t.setTransactionType(rs.getString("transactionType"));
            t.setItemCode(rs.getString("itemCode"));
            t.setHospitalCode(rs.getString("hospitalCode"));
            t.setUserID(rs.getString("userID"));
            t.setQuantity(rs.getInt("quantity"));
            t.setTransactionDate(rs.getTimestamp("transactionDate"));
            list.add(t);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Error retrieving distributed transactions: " + e.getMessage());
    }
    return list;
}


public static List<Transaction> getReceivedTransactions(String startDate, String endDate) {
    List<Transaction> list = new ArrayList<>();
    String query = "SELECT * FROM transactions WHERE transactionType = 'RECEIVED' ORDER BY transactionDate DESC";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query);
         ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
            Transaction t = new Transaction();
            t.setTransactionID(rs.getInt("transactionID"));
            t.setTransactionType(rs.getString("transactionType"));
            t.setItemCode(rs.getString("itemCode"));
            t.setSupplierCode(rs.getString("supplierCode"));
            t.setUserID(rs.getString("userID"));
            t.setQuantity(rs.getInt("quantity"));
            t.setTransactionDate(rs.getTimestamp("transactionDate"));
            list.add(t);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Error retrieving received transactions: " + e.getMessage());
    }
    return list;
}

    
    public static List<Transaction> searchByItemCode(String itemCode) {
        List<Transaction> list = new ArrayList<>();
        String query = "SELECT * FROM transactions WHERE itemCode = ? ORDER BY transactionDate DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, itemCode);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Transaction t = new Transaction();
                    t.setTransactionID(rs.getInt("transactionID"));
                    t.setTransactionType(rs.getString("transactionType"));
                    t.setItemCode(rs.getString("itemCode"));
                    t.setSupplierCode(rs.getString("supplierCode"));
                    t.setHospitalCode(rs.getString("hospitalCode"));
                    t.setUserID(rs.getString("userID"));
                    t.setQuantity(rs.getInt("quantity"));
                    t.setTransactionDate(rs.getTimestamp("transactionDate"));
                    list.add(t);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error searching transactions: " + e.getMessage());
        }
        return list;
    }

    
    public static List<Transaction> getReceivedOnDate(String date) {
        List<Transaction> list = new ArrayList<>();
        String query = "SELECT * FROM transactions WHERE transactionType = 'RECEIVED' AND DATE(transactionDate) = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, date);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Transaction t = new Transaction();
                    t.setTransactionID(rs.getInt("transactionID"));
                    t.setTransactionType(rs.getString("transactionType"));
                    t.setItemCode(rs.getString("itemCode"));
                    t.setSupplierCode(rs.getString("supplierCode"));
                    t.setHospitalCode(rs.getString("hospitalCode"));
                    t.setUserID(rs.getString("userID"));
                    t.setQuantity(rs.getInt("quantity"));
                    t.setTransactionDate(rs.getTimestamp("transactionDate"));
                    list.add(t);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving received items: " + e.getMessage());
        }
        return list;
    }
}

class InsufficientStockException extends Exception {
    public InsufficientStockException(String message) {
        super(message);
    }
}