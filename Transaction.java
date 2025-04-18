import java.sql.Timestamp;
import javax.swing.JOptionPane;

public class Transaction 
{
    private int transactionID;
    private String transactionType;
    private String itemCode;
    private String supplierCode;
    private String hospitalCode;
    private String userID;
    private int quantity;
    private Timestamp transactionDate;
    
    public Transaction()
    {
        
    }
    
    public Transaction(String transactionType, String itemCode, String supplierCode,String hospitalCode, String userID, int quantity) 
    {
        this.transactionType = transactionType;
        this.itemCode = itemCode;
        this.supplierCode = supplierCode;
        this.hospitalCode = hospitalCode;
        this.userID = userID;
        this.quantity = quantity;
    }


    public String getTransactionType()
    {
        return transactionType;
    }
    
    public void setTransactionType(String transactionType)
    {
        this.transactionType = transactionType;
    }

    public int getTransactionID()
    {
        return transactionID; 
    }
    
    public void setTransactionID(int transactionID) 
    {
        this.transactionID = transactionID;
    }

    public String getItemCode()
    {
        return itemCode;
    }
    
    public void setItemCode(String itemCode) 
    {
        this.itemCode = itemCode;
    }

    public String getSupplierCode() 
    {
        return supplierCode;
    }
    
    public void setSupplierCode(String supplierCode)
    {
        this.supplierCode = supplierCode;
    }

    public String getHospitalCode()
    {
        return hospitalCode;
    }
    
    public void setHospitalCode(String hospitalCode)
    {
        this.hospitalCode = hospitalCode;
    }

    public String getUserID()
    {
        return userID;
    }
    
    public void setUserID(String userID)
    {
        this.userID = userID;
    }

    public int getQuantity()
    {
        return quantity;
    }
    
    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public Timestamp getTransactionDate()
    {
        return transactionDate;
    }
    
    public void setTransactionDate(Timestamp transactionDate)
    {
        this.transactionDate = transactionDate;
    }
    

}