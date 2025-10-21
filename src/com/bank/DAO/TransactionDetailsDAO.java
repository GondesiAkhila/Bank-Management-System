package com.bank.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

import com.bank.DTO.TransactionDetails;
import com.bank.util.DataBaseConnection;

public class TransactionDetailsDAO {
	private static final String insert_transaction_details="insert into transaction_details"
			+ "(transaction_type, transaction_amount, transaction_time, transaction_date,"
			+ " balance_amount, transaction_status, customer_account_number) values(?,?,?,?,?,?,?)";
	public boolean insertTransactionDetails(TransactionDetails transactionDetails) {
		try {
			Connection connection=DataBaseConnection.forMySqlConnection();
			PreparedStatement preparedStatement=connection.prepareStatement(insert_transaction_details);
			preparedStatement.setString(1, transactionDetails.getTransactiontype());
			preparedStatement.setDouble(2, transactionDetails.getTransactionamount());
			preparedStatement.setTime(3, Time.valueOf(transactionDetails.getTransactiontime()));
			preparedStatement.setDate(4, Date.valueOf(transactionDetails.getTransactiondate()));
			preparedStatement.setDouble(5, transactionDetails.getBalanceamount());
			preparedStatement.setString(6, transactionDetails.getTransactionstatus());
			preparedStatement.setLong(7, transactionDetails.getCustomeraccountnumber());
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
