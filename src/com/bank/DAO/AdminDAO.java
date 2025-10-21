package com.bank.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bank.DTO.CustomerDetails;
import com.bank.util.DataBaseConnection;
import com.mysql.cj.protocol.Resultset;

public class AdminDAO {
	public static final String admin_login = "select * from admin_details where emailid=? and password=?";
	private static final String get_all_customers_account_status="select * from customer_details where Account_Status=?";
	private static final String updatingPendingAccountToApproved="update customer_details"
			+ " set Customer_Account_Number=?,Customer_Pin=?,Account_Status='APPROVED' "
			+ "where Account_Status='PENDING'";
	private static final String delectingSinglePersonAccountByStatusClosing="delete from customer_details where (Customer_Account_Number=? and Customer_Pin=? and Account_Status='CLOSING')";
	private static final String deletingAllClosingStatusRecords="delete from customer_details where Account_Status='CLOSING'";
CustomerDAO customerDAO=new CustomerDAO();
	public boolean selectAdminDetailsByUsingEmailIdAndPassword(String emailid, String password) {
		try {
			Connection connection = DataBaseConnection.forMySqlConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(admin_login);
			preparedStatement.setString(1, emailid);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.isBeforeFirst()) {
				return true;
			} else {
				return false;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	List<CustomerDetails> allCustomerDetails=customerDAO.getAllCustomerDetails();
	public void printAllCutomerDetails() {
		for (CustomerDetails customerDetails : allCustomerDetails) {
			System.out.println("Customer Name :"+customerDetails.getName());
			System.out.println("Customer EmailID :"+customerDetails.getEmailid());
			System.out.println("Customer MobileNumber :"+customerDetails.getMobilenumber());
			System.out.println("Customer AadharNumber :"+customerDetails.getAadharnumber());
			System.out.println("Customer Address :"+customerDetails.getAddress());
			System.out.println("Customer Gender :"+customerDetails.getGender());
			System.out.println("Customer Amount :"+customerDetails.getAmount());
			System.out.println("Account Status :"+customerDetails.getAccountStatus());
			System.out.println("=============================<>================================");
		}
	}
	public List<CustomerDetails> getAllCustomerPendingDetails(String status) {
		Connection connection;
		try {
			connection = DataBaseConnection.forMySqlConnection();
			PreparedStatement preparedStatement=connection.prepareStatement(get_all_customers_account_status);
			preparedStatement.setString(1, status);
			ResultSet resultSet=preparedStatement.executeQuery();
			List<CustomerDetails> listOfPendingCustomerDetails=new ArrayList<CustomerDetails>();
			if(resultSet.isBeforeFirst())
			{
				while(resultSet.next())
				{
					CustomerDetails customerDetails=new CustomerDetails();
					customerDetails.setName(resultSet.getString("Customer_Name"));
					customerDetails.setEmailid(resultSet.getString("Customer_EmailId"));
					customerDetails.setMobilenumber(resultSet.getLong("Customer_MobileNumber"));
					customerDetails.setAadharnumber(resultSet.getLong("Customer_Aadhar_Number"));
					customerDetails.setAddress(resultSet.getString("Customer_Address"));
					customerDetails.setGender(resultSet.getString("Customer_Gender"));
					customerDetails.setAmount(resultSet.getDouble("Customer_Amount"));
					customerDetails.setAccountStatus(resultSet.getString("Account_Status"));
					customerDetails.setAccountnumber(resultSet.getLong("Customer_Account_Number"));
					customerDetails.setPin(resultSet.getInt("Customer_Pin"));
					System.out.println("-------------------------------------------------------");
					listOfPendingCustomerDetails.add(customerDetails);
				}
				return listOfPendingCustomerDetails;
			}
			else
			{
				System.out.println("No Pending details Found........");
				return null;
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	public boolean closingSpecificCustomerAccount(long accountno,int pin)
	{
		try {
			Connection connection=DataBaseConnection.forMySqlConnection();
			PreparedStatement preparedStatement=connection.prepareStatement(delectingSinglePersonAccountByStatusClosing);
			preparedStatement.setLong(1, accountno);
			preparedStatement.setInt(2, pin);
			int result=preparedStatement.executeUpdate();
			if(result>0)
			{
				return true;
			}
			else
			{
				return false;
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public boolean ClosingAllCustomerAccounts()
	{
		try {
			Connection connection=DataBaseConnection.forMySqlConnection();
			PreparedStatement preparedStatement=connection.prepareStatement(deletingAllClosingStatusRecords);
			int result=preparedStatement.executeUpdate();
			if(result>0)
			{
				return true;
			}
			else
			{
				return false;
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
