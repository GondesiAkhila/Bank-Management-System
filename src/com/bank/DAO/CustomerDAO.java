package com.bank.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bank.DTO.CustomerDetails;
import com.bank.service.AdminService;
import com.bank.service.CustomerService;
import com.bank.util.DataBaseConnection;

public class CustomerDAO {
	private static final String insert = "insert into customer_details"
			+ "(Customer_Name, Customer_EmailId, Customer_MobileNumber, Customer_Aadhar_Number, Customer_Address, Customer_Gender,Customer_Amount)"
			+ "values(?,?,?,?,?,?,?)";
	private static final String select_all_customer_details = "select * from customer_details";
	private static final String customer_login = "select * from customer_details where (Customer_EmailId=? or Customer_Account_Number=?) and Customer_Pin=?";
	private static final String select_one_person_details = "update customer_details"
			+ " set Customer_Account_Number=?,Customer_Pin=?,Account_Status=? " + "where Customer_EmailId=?";
	private static final String gender="select Customer_Gender from customer_details where Customer_EmailId=? and Customer_Pin=?";
	private static final String updating_AccountStatusToClosing="update customer_details set Account_Status='CLOSING' where (Customer_EmailId=? and Customer_Account_Number=?)";
	private static final String gettingAccountBalance="select Customer_Amount from customer_details where Customer_Pin=?";
	private static final String updatingPin="update customer_details set Customer_Pin=? where Customer_EmailId=? and Customer_Pin=?";
	private static final String selectCustomerDetailsByUsingAccountNumberAndPIN="select * from customer_details where (Customer_Account_Number=? and Customer_Pin=?)";
	private static final String updatingAccountBalanceAfterValidDebitOperation="update customer_details set Customer_Amount=? where (Customer_Account_Number=? and Customer_Pin=?)";
	public boolean insertCustomerDetails(CustomerDetails customerDetails) {
		try {
			Connection connection = DataBaseConnection.forMySqlConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(insert);
			preparedStatement.setString(1, customerDetails.getName());
			preparedStatement.setString(2, customerDetails.getEmailid());
			preparedStatement.setLong(3, customerDetails.getMobilenumber());
			preparedStatement.setLong(4, customerDetails.getAadharnumber());
			preparedStatement.setString(5, customerDetails.getAddress());
			preparedStatement.setString(6, customerDetails.getGender());
			preparedStatement.setDouble(7, customerDetails.getAmount());

			int result = preparedStatement.executeUpdate();
			if (result != 0) {
				return true;
			} else {
				return false;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch blocks
			e.printStackTrace();
			return false;
		}
	}

	public List<CustomerDetails> getAllCustomerDetails() {
		try {
			Connection connection = DataBaseConnection.forMySqlConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(select_all_customer_details);
			ResultSet resultSet = preparedStatement.executeQuery();
			List<CustomerDetails> listOfCustomerDetails = new ArrayList<CustomerDetails>();
			if (resultSet.isBeforeFirst()) {
				while (resultSet.next()) {
					CustomerDetails customerDetails = new CustomerDetails();
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
					listOfCustomerDetails.add(customerDetails);
				}
				return listOfCustomerDetails;
			} else {
				return null;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public void updateAccountAndPinByUsingId(List<CustomerDetails> List) {
		try {
			Connection connection = DataBaseConnection.forMySqlConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(select_one_person_details);
			for (CustomerDetails customerDetails2 : List) {
				preparedStatement.setLong(1, customerDetails2.getAccountnumber());
				preparedStatement.setInt(2, customerDetails2.getPin());
				preparedStatement.setString(3, "ACCEPTED");
				preparedStatement.setString(4, customerDetails2.getEmailid());
				preparedStatement.addBatch();
			}
			System.out.println(preparedStatement);
			int[] batch = preparedStatement.executeBatch();
			System.out.println("AccountNo And Pin Generated Successfully For all Pending Customers....");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void generatingPinAndAccountNumberForOnePerson(String emailid) {
		AdminService adminService = new AdminService();
		String[] name=emailid.split("@");
		try {
			Connection connection = DataBaseConnection.forMySqlConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(select_one_person_details);
			preparedStatement.setLong(1, adminService.generateAccountNumber());
			preparedStatement.setInt(2, adminService.generatePinNumber());
			preparedStatement.setString(3, "ACCEPTED");
			preparedStatement.setString(4, emailid);
			int result = preparedStatement.executeUpdate();
			if (result > 0) {
				System.out.println(preparedStatement);
				System.out.println("Account Number And Pin Generated Successfully for "+ name[0]);
			} else {
				System.err.println("Entered EmailID not Found....Invalid EmailID Entered");
			}

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public CustomerDetails customerLogin(String emailid, int pin) {
		try {
			Connection connection = DataBaseConnection.forMySqlConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(customer_login);
			preparedStatement.setString(1, emailid);
			preparedStatement.setString(2, emailid);
			preparedStatement.setInt(3, pin);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				CustomerDetails customerDetails=new CustomerDetails();
				customerDetails.setName(resultSet.getString("Customer_Name"));
				customerDetails.setGender(resultSet.getString("Customer_Gender"));
				return customerDetails;
			} else {
				return null;
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public boolean closingOperation(String email,int AccountNo)
	{
		try {
			Connection connection = DataBaseConnection.forMySqlConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(updating_AccountStatusToClosing);
			preparedStatement.setString(1, email);
			preparedStatement.setInt(2, AccountNo);
			int result=preparedStatement.executeUpdate();
			if(result>0)
			{
				return true;
			}
			else
			{
				System.err.println("Invalid Details Entered...");
				return false;
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public double checkingAccountBalance(int pin) {
		try {
			Connection connection = DataBaseConnection.forMySqlConnection();
			PreparedStatement preparedStatement=connection.prepareStatement(gettingAccountBalance);
			preparedStatement.setInt(1, pin);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.isBeforeFirst())
			{
				if(resultSet.next())
				{
					return resultSet.getDouble("Customer_Amount");
				}
				else
				{
					return 0.0;
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0.0;
		}
		return 0.0;
	}
	public boolean updatingPin(String emailOrAccountNo,int oldpin,int newpin)
	{
		try {
			Connection connection=DataBaseConnection.forMySqlConnection();
			PreparedStatement preparedStatement=connection.prepareStatement(updatingPin);
			preparedStatement.setInt(1, newpin);
			preparedStatement.setString(2, emailOrAccountNo);
			preparedStatement.setInt(3, oldpin);
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
	
	public double selectCustomerDetailsByUsingAccountNumberAndPINnumber(long accountnumber,int pin)
	{
		try {
			Connection connection=DataBaseConnection.forMySqlConnection();
			PreparedStatement preparedStatement=connection.prepareStatement(selectCustomerDetailsByUsingAccountNumberAndPIN);
			preparedStatement.setLong(1, accountnumber);
			preparedStatement.setInt(2, pin);
			ResultSet resultSet=preparedStatement.executeQuery();
			List<CustomerDetails> list=new ArrayList<CustomerDetails>();
			if(resultSet.isBeforeFirst())
			{
				if(resultSet.next())
				{
					 return resultSet.getDouble("Customer_Amount");
				}
			}
			else
			{
				return 0.0;
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0.0;
		}
		return 0.0;
	}
	public void updatingAccountBalanceAfterValidDebitOperation(double amount,double preAmount,long accountNo,int pin)
	{
		try {
			Connection connection=DataBaseConnection.forMySqlConnection();
			PreparedStatement preparedStatement=connection.prepareStatement(updatingAccountBalanceAfterValidDebitOperation);
			double newAmount=preAmount-amount;
			preparedStatement.setDouble(1, newAmount);
			preparedStatement.setLong(2,accountNo);
			preparedStatement.setInt(3, pin);
			int result=preparedStatement.executeUpdate();
			if(result>0)
			{
				System.out.println(amount+" is Debited Successfully");
			}
			else
			{
				System.out.println("Something Went Wrong");
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void updatingAccountBalanceAfterValidCreditOperation(double amount,double preAmount,long accountNo,int pin)
	{
		try {
			Connection connection=DataBaseConnection.forMySqlConnection();
			PreparedStatement preparedStatement=connection.prepareStatement(updatingAccountBalanceAfterValidDebitOperation);
			double newAmount=preAmount+amount;
			preparedStatement.setDouble(1, newAmount);
			preparedStatement.setLong(2,accountNo);
			preparedStatement.setInt(3, pin);
			int result=preparedStatement.executeUpdate();
			if(result>0)
			{
				System.out.println(amount+" is Credited Successfully");
			}
			else
			{
				System.out.println("Something Went Wrong");
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
