package com.bank.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.bank.DAO.AdminDAO;
import com.bank.DAO.CustomerDAO;
import com.bank.DTO.AdminDetails;
import com.bank.DTO.CustomerDetails;
import com.bank.exceptions.AdminDataInvalidException;
import com.bank.exceptions.CustomerDataInvalidException;
import com.bank.exceptions.InvalidDetailsException;

public class AdminService {
	Scanner scan = new Scanner(System.in);
	CustomerDAO customerDAO = new CustomerDAO();
	CustomerDetails customerDetails = new CustomerDetails();
	AdminDAO adminDAO = new AdminDAO();
	List<CustomerDetails> allCustomerDetails = customerDAO.getAllCustomerDetails();

	public long generateAccountNumber() {
		Random rand = new Random();
		int accountNumber = rand.nextInt(8000000);
		for (CustomerDetails customerDetails : allCustomerDetails) {
			if (customerDetails.getAccountnumber() != accountNumber) {
				return 10000000 + accountNumber;
			}
		}
		return 0;
	}

	public int generatePinNumber() {
		Random rand = new Random();
		int pin = rand.nextInt(1000);
		for (CustomerDetails customerDetails : allCustomerDetails) {
			if (customerDetails.getPin() != pin) {
				return 1000 + pin;
			}
		}
		return 0;
	}

	public void adminLogin() {
		int chance = 0;
		boolean adminLoginStatus = true;
		while (adminLoginStatus) {
			try {
				System.out.println("Enter Admin EmailId");
				String emailid = scan.next();
				System.out.println("Enter Admin Password");
				String password = scan.next();
				if (adminDAO.selectAdminDetailsByUsingEmailIdAndPassword(emailid, password)) {
					System.out.println("Enter \n 1.To get All Customer Details "
							+ "\n 2. To get all Account Request Details " + "\n 3. To get All Account Closing Request");
					int choice = scan.nextInt();
					switch (choice) {
					case 1:
						System.out.println("<----------All Customer Details----------->");
						adminDAO.printAllCutomerDetails();
						break;
					case 2:
						System.out.println(
								"Enter \n 1.For Pending Details \n 2.For Accepted Details \n 3. For Closing Details");
						System.out.println("<----------All Account Request Details----------->");
						int choice1 = scan.nextInt();
						String status = null;
						if (choice1 == 1) {
							status = "PENDING";
						} else if (choice1 == 2) {
							status = "ACCEPTED";
						} else if (choice1 == 3) {
							status = "ClOSING";
						} else {
							System.out.println("Enter Valid Option...");
						}

						List<CustomerDetails> allPendingDetails = adminDAO.getAllCustomerPendingDetails(status);
						for (CustomerDetails customerDetails : allPendingDetails) {
							System.out.println("Customer Name :" + customerDetails.getName());
							System.out.println("Customer EmailID :" + customerDetails.getEmailid());
							System.out.println("Customer Mobilenumber :" + customerDetails.getMobilenumber());
							System.out.println("----------------------------------------------------");
						}
						System.out.println("Enter \n 1. To Generate Account Number For One Person \n "
								+ "2. To Select all the Generate Account Number");
						int choice2 = scan.nextInt();
						switch (choice2) {
						case 1:
							System.out.println("Enter EmailID For Accountnumber and Pin Generation");
							String emailid1 = scan.next();
							customerDAO.generatingPinAndAccountNumberForOnePerson(emailid1);
							break;
						case 2:
							for (CustomerDetails customerDetails : allPendingDetails) {
								customerDetails.setAccountnumber(generateAccountNumber());
								customerDetails.setPin(generatePinNumber());
							}
							customerDAO.updateAccountAndPinByUsingId(allPendingDetails);
						default:
							break;
						}
						break;

					case 3:
						System.err.println("<----------All Account Closing Details----------->");
						System.out.println("Enter \n1.For Closing Specific Customer Account..\n2.For Closing All Customer Accounts");
						int choice3=scan.nextInt();
						switch (choice3) {
						case 1:
							System.out.println("Enter Account No");
							long accountNo=scan.nextLong();
							System.out.println("Enter Pin");
							int pin=scan.nextInt();
							CustomerDetails customerDetails=customerDAO.customerLogin(emailid, pin);
							if(adminDAO.closingSpecificCustomerAccount(accountNo, pin))
							{
								if(customerDetails.getName()!=null)
								{
									System.out.println("Account Closed For Customer :"+customerDetails.getName());
								}
							}
							else
							{
								throw new InvalidDetailsException();
							}
							break;
						case 2:
							if(adminDAO.ClosingAllCustomerAccounts())
							{
								System.out.println("All Customer details Closed..");
							}
							else
							{
								System.err.println("Something Went Wrong...!");
							}
						default:
							break;
						}
						
						break;

					default:
						System.out.println("Invalid Request....");
						break;
					}
					break;
				} else {
					throw new AdminDataInvalidException("Invalid EmailId Or Password");
				}
			} catch (AdminDataInvalidException e) {
				if (chance == 3) {
					System.out.println("You Entered Invalid Details Multiple Times..Try Again Later.....");
					adminLoginStatus = false;
				} else {
					System.out.println("Your Chances Left to Enter Valid Data :" + (3 - chance) + " Chances");
					chance++;
				}
			}
		}
	}
}
