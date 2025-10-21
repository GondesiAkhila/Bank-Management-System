package com.bank.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.bank.DAO.AdminDAO;
import com.bank.DAO.CustomerDAO;
import com.bank.DAO.TransactionDetailsDAO;
import com.bank.DTO.CustomerDetails;
import com.bank.DTO.TransactionDetails;
import com.bank.exceptions.CustomerDataInvalidException;
import com.bank.exceptions.InvalidCaptchaException;
import com.bank.exceptions.InvalidDetailsException;
import com.bank.exceptions.InvalidLoginDetailsException;

public class CustomerService {
	Scanner scan = new Scanner(System.in);
	CustomerDetails customerDetails = new CustomerDetails();
	CustomerDAO customerDAO = new CustomerDAO();
	AdminDAO adminDAO = new AdminDAO();
	TransactionDetailsDAO transactionDetailsDAO=new TransactionDetailsDAO();

	public void customerRegistration() {
		List<CustomerDetails> allCustomerDetails = customerDAO.getAllCustomerDetails();
		boolean cnamestatus = true;
		System.out.println("Enter Customer Name");
		while (cnamestatus) {
			try {
				String name = scan.next();
				if (name.length() >= 3) {
					for (int i = 0; i <= name.length() - 1; i++) {
						char ch = name.charAt(i);
						if ((ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122)) {
							customerDetails.setName(name);
							cnamestatus = false;
						} else {
							throw new CustomerDataInvalidException(
									"Invalid Name Entered.. Name Should Contain Only Alphabets..");
						}
					}
				} else {
					throw new CustomerDataInvalidException(
							"Invalid Name Entered.. Entered name length must be greater than 2");
				}
			} catch (CustomerDataInvalidException e) {
				System.out.println(e.getExceptiomMsg());
				System.out.println("Re-enter the Customer Mobile Number");
			}
		}

		System.out.println("Enter Customer EmailId");
		boolean emailStatus = true;
		while (emailStatus) {

			try {
				String email = scan.next();
				if (email.length() >= 12) {
					if (email.endsWith("@gmail.com")) {
						for (CustomerDetails customerDetails2 : allCustomerDetails) {
							if (!customerDetails2.getEmailid().equalsIgnoreCase(email)) {
								customerDetails.setEmailid(email);
								emailStatus = false;
							} else {
								throw new CustomerDataInvalidException(
										"Duplicate EmailId Entered.. Please Re-Enter the EmailId");
							}
						}
					} else {
						throw new CustomerDataInvalidException(
								"Invalid EmailID Entered.. Entered EmailID Should Ends with **'@gmail.com'**");
					}
				} else {
					throw new CustomerDataInvalidException(
							"Invalid EmailID ENtered.. Entered EmailID Length Should be Greater than 12");
				}
			} catch (CustomerDataInvalidException e) {
				System.out.println(e.getExceptiomMsg());
				System.out.println("Re-enter the Customer EmailID");
			}

		}

		System.out.println("Enter Customer Mobile Number");
		boolean mbStatus = true;
		while (mbStatus) {
			try {
				long mobilenumber = scan.nextLong();
				if (mobilenumber >= 6000000000l && mobilenumber <= 9999999999l) {
					for (CustomerDetails customerDetails2 : allCustomerDetails) {
						if (customerDetails2.getMobilenumber() != mobilenumber) {
							customerDetails.setMobilenumber(mobilenumber);
							mbStatus = false;
						} else {
							throw new CustomerDataInvalidException(
									"Duplicate Mobile Number ENtered..Please ENter Valid Mobile Number...");
						}
					}
				} else {
					throw new CustomerDataInvalidException();
				}
			} catch (CustomerDataInvalidException e) {
				e.setExceptiomMsg("INVALID DETAILS ENTERED");
				System.out.println(e.getExceptiomMsg());
				System.out.println("Re-enter the Customer Mobile Number");
			}
		}
		System.out.println("Enter Customer Aadhar Number");
		boolean aadharStatus = true;
		while (aadharStatus) {
			try {
				long aadharnumber = scan.nextLong();
				if (aadharnumber >= 100000000000l && aadharnumber <= 999999999999l) {
					for (CustomerDetails customerDetails2 : allCustomerDetails) {
						if (customerDetails2.getAadharnumber() != aadharnumber) {
							customerDetails.setAadharnumber(aadharnumber);
							aadharStatus = false;
						} else {
							throw new CustomerDataInvalidException(
									"Duplicate Aadhar Number Entered..Please Re-Enter the Aadhar Number... ");
						}
					}
				} else {
					throw new CustomerDataInvalidException(
							"Invalid Aadhar Number.. Entered Aadhar Number Should Contain 12 Digits");
				}
			} catch (CustomerDataInvalidException e) {
				System.out.println(e.getExceptiomMsg());
				System.out.println("Re-enter the Customer Aadhar Number");
			}
		}

		System.out.println("Enter Customer Address");
		boolean addressStatus = true;
		while (addressStatus) {
			try {
				String address = scan.next();
				if (address != null) {
					customerDetails.setAddress(address);
					addressStatus = false;
				} else {
					throw new CustomerDataInvalidException("Invalid Address Entered");
				}

			} catch (CustomerDataInvalidException e) {
				System.out.println(e.getExceptiomMsg());
				System.out.println("Re-enter the Customer Address");
			}
		}
		System.out.println("Enter Customer Gender");
		boolean genderStatus = true;
		while (genderStatus) {
			try {
				String gender = scan.next();
				if (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female")
						|| gender.equalsIgnoreCase("Others")) {
					customerDetails.setGender(gender);
					genderStatus = false;
				} else {
					throw new CustomerDataInvalidException(
							"Invalid Gender Entered.. Gender Should be Either 'Male' Or 'Female' Or 'Others'");
				}
			} catch (CustomerDataInvalidException e) {
				System.out.println(e.getExceptiomMsg());
				System.out.println("Re-Enter the Customer Gender");
			}
		}

		System.out.println("Enter Customer Amount");
		boolean amountStatus = true;
		while (amountStatus) {
			try {
				double amount = scan.nextDouble();
				if (amount >= 0) {
					customerDetails.setAmount(amount);
					amountStatus = false;
				} else {
					throw new CustomerDataInvalidException(
							"Invalid Amount Entered.. Entered AMount Should be Greater Than **'0'**");
				}
			} catch (CustomerDataInvalidException e) {
				System.out.println(e.getExceptiomMsg());
				System.out.println("Re-Enter the Customer Gender");
			}
		}

		if (customerDAO.insertCustomerDetails(customerDetails)) {
			System.out.println("Data Inserted Successfully....");
		} else {
			System.err.println("Server Error..");
		}

	}

	public String captchaGeneration() {
		Random rand = new Random();

		String captcha = "";
		char lowerCharacter1 = (char) ('a' + rand.nextInt(26));
		captcha += lowerCharacter1;
		char upperCharacter = (char) ('A' + rand.nextInt(26));
		captcha += upperCharacter;
		int number = rand.nextInt(10);
		captcha += number;
		char lowerCharacter2 = (char) ('a' + rand.nextInt(26));
		captcha += lowerCharacter2;
		return captcha;

	}

	public void closingOperation() {
		int chance = 0;
		boolean isValid = true;
		while (isValid) {
			try {
				System.out.println("Enter AccountNo");
				int accountno = scan.nextInt();
				System.err.println("Enter EmailId");
				String emailid = scan.next();
				if (customerDAO.closingOperation(emailid, accountno)) {
					System.out.println("Your Request Was Sended to Bank For Closing....");
					isValid = false;
				} else {
					throw new InvalidDetailsException();
				}
			} catch (InvalidDetailsException e) {
				if (chance == 3) {
					System.out.println(
							"Your Chances are Exhausted.....You Entered Invalid Data Multiple times...Please Try Again Later");
				} else {
					System.err.println("Your Chances Left For Entering Correct Data :" + (3 - chance) + " chances");
					System.err.println("Re-Enter the Details.....");
					chance--;
				}
			}
		}
	}
	TransactionDetails transactionDetails=new TransactionDetails();
	public void debit(int firstEnteredPin) {
		System.out.println("Enter Customer Account Number");
		long accountNumber = scan.nextLong();
		System.out.println("Enter Customer PIN");
		int newpin = scan.nextInt();
		double accountBalance=customerDAO.selectCustomerDetailsByUsingAccountNumberAndPINnumber(accountNumber, newpin);
		if (firstEnteredPin == newpin) {
			if (accountBalance!=0.0) {
				System.out.println("Enter Amount");
				double amount = scan.nextDouble();
				if (amount >= 0 && amount<customerDAO.selectCustomerDetailsByUsingAccountNumberAndPINnumber(accountNumber, newpin)) {
					customerDAO.updatingAccountBalanceAfterValidDebitOperation(amount,accountBalance,accountNumber,newpin);
					transactionDetails.setTransactionamount(amount);
					transactionDetails.setCustomeraccountnumber(accountNumber);
					transactionDetails.setBalanceamount(accountBalance);
					transactionDetails.setTransactiondate(LocalDate.now());
					transactionDetails.setTransactiontime(LocalTime.now());
					transactionDetails.setTransactionstatus("Transfered");
					transactionDetails.setTransactiontype("Debit");
					transactionDetailsDAO.insertTransactionDetails(transactionDetails);
				} else {
					System.err.println("Insufficient Amount");
				}
			}
		} else {
			System.err.println("**********Invalid Credentials**********");
		}
	}
	
	public void credit(int firstEnteredPin) {
		System.out.println("Enter Customer Account Number");
		long accountNumber = scan.nextLong();
		System.out.println("Enter Customer PIN");
		int newpin = scan.nextInt();
		double accountBalance=customerDAO.selectCustomerDetailsByUsingAccountNumberAndPINnumber(accountNumber, newpin);
		if (firstEnteredPin == newpin) {
				System.out.println("Enter Amount");
				double amount = scan.nextDouble();
				if (amount >= 0) {
					customerDAO.updatingAccountBalanceAfterValidCreditOperation(amount,accountBalance, accountNumber, newpin);
				} else {
					System.err.println("Insufficient Amount");
				}
		} else {
			System.err.println("**********Invalid Credentials**********");
		}
	}

	public void customerLogin() {
		int chance = 0;
		int chance1 = 0;
		boolean captchaStatus = true;
		boolean loginStatus = true;
		while (loginStatus) {
			System.out.println("Enter Customer EmailID");
			String emailIdOrAccountNo = scan.next();
			System.out.println("Enter Customer PIN");
			int pin = scan.nextInt();
			try {
				CustomerDetails customerDetails = customerDAO.customerLogin(emailIdOrAccountNo, pin);
				if (customerDetails != null) {
					if (captchaStatus) {
						try {
							String generatedCaptcha = captchaGeneration();
							System.err.println("CAPTCHA : " + generatedCaptcha);
							String enteredCaptcha = scan.next();
							if (enteredCaptcha.equals(generatedCaptcha)) {
								if (customerDetails.getGender().equalsIgnoreCase("male")) {
									System.out.println("HELLO \nMR :" + customerDetails.getName());
									captchaStatus = false;
									loginStatus = false;

								}
								if (customerDetails.getGender().equalsIgnoreCase("female")) {
									System.out.println("HELLO \nMISS :" + customerDetails.getName());
									captchaStatus = false;
									loginStatus = false;
								}
								System.out.println("=========================================================");
								System.out.println(
										"  Enter \n1.CustomerDetails \n2.WithDrawAmount \n3.CreditAmount \n4.Account Transaction Details \n5.Update PIN \n6.Check Balance \n7.Close the Account ");
								int choice = scan.nextInt();
								switch (choice) {
								case 1:
									System.out.println("<---------------Customer Details--------------------->");
									adminDAO.printAllCutomerDetails();
									break;
								case 2:
									System.out.println("<---------------WithDraw Amount--------------------->");
									debit(pin);
									break;
								case 3:
									System.out.println("<---------------CreditAmount--------------------->");
									credit(pin);
									break;
								case 4:
									System.out.println(
											"<---------------Account Transaction Details--------------------->");
									break;
								case 5:
									System.out.println("<---------------Updating PIN--------------------->");
									System.out.println("Enter Your Old PIN");
									int oldpin = scan.nextInt();
									AdminService adminService = new AdminService();
									boolean isUpdated = false;
									int newpin = adminService.generatePinNumber();
									if (pin == oldpin) {
										System.out.println("Your Old PIN :" + oldpin);
										while (!isUpdated) {
											System.out.println("New PIN :" + newpin);
											System.out.println(
													"Press 1 If You are Ok With Above Generated PIN to Update \n Press 2 for Another Pin Generation");
											int choice1 = scan.nextInt();
											if (choice1 == 1) {
												if (customerDAO.updatingPin(emailIdOrAccountNo, oldpin, newpin)) {
													System.out.println("New PIN Updated Successfully....");
													isUpdated = true;
													break;
												} else {
													System.err.println("Something Went Wrong....");
												}
											} else if (choice1 == 2) {
												newpin = adminService.generatePinNumber();
											}
										}
									}
									break;
								case 6:
									System.err.println("<---------------Account Balance--------------------->");
									System.out.println("Enter Pin For Account Balance");
									int accountBalancepin = scan.nextInt();
									if (accountBalancepin == pin) {
										System.out.println("Account Balance :"
												+ customerDAO.checkingAccountBalance(accountBalancepin) + "/-");
									} else {
										System.err.println("Something Went Wrong... Please try Again...");
									}
									break;
								case 7:
									System.out.println("<---------------Closing Details--------------------->");
									closingOperation();
									break;
								default:
									break;
								}

							} else {
								throw new InvalidCaptchaException();
							}
						} catch (InvalidCaptchaException e) {
							if (chance == 3) {
								System.err.println(
										"Your Chances are Exhausted...You Entered Invalid Data Multiple Times..Please try Again Later,,,");
								captchaStatus = false;

							} else {
								System.out.println(
										"Your Chances Left For Entering Correct CAPTCHA :" + (3 - chance) + " Chances");
								System.out.println("Re-Enter CAPTCHA");
								chance++;
							}
						}
					}
				} else {
					throw new InvalidLoginDetailsException();
				}
			} catch (InvalidLoginDetailsException e) {
				if (chance1 == 3) {
					System.err.println(
							"Your Chances are Exhausted......You Entered Invalid Data Multiple Times..Please try Again Later");
					loginStatus = false;

				} else {
					System.out.println(
							"Your Chances Left For Entering Correct Customer Details:" + (3 - chance1) + " Chances");
					System.out.println("Re-Enter Customer Details..");
					chance1++;
				}
			}
		}

	}

}
