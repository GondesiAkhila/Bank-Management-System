package com.bank.main;

import java.util.Scanner;

import com.bank.service.AdminService;
import com.bank.service.CustomerService;

public class BankMainClass {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		CustomerService customerService = new CustomerService();
		AdminService adminService = new AdminService();
		System.out.println("Enter \n 1.Customer Registration \n 2.Customer Login \n 3.Admin Login");
		int choice = scan.nextInt();
		switch (choice) {
		case 1:
			System.out.println("<----------You Choosen Customer Registration---------->");
			customerService.customerRegistration();
			break;
		case 2:
			System.out.println("<----------You Choosen Customer Login---------->");
			customerService.customerLogin();
			break;
		case 3:
			System.out.println("<----------You Choosen Admin Login---------->");
			adminService.adminLogin();
			break;

		default:
			System.out.println("Invalid Request.....");
			break;
		}

	}
}
