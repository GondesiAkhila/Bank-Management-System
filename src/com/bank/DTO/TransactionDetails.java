package com.bank.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

public class TransactionDetails {
	private int transactionid;
	private String transactiontype;
	private double transactionamount;
	private LocalTime transactiontime;
	private LocalDate transactiondate;
	private double balanceamount;
	private String transactionstatus;
	private long customeraccountnumber;

	public TransactionDetails() {
		// TODO Auto-generated constructor stub
	}

	public TransactionDetails(int transactionid, String transactiontype, double transactionamount,
			LocalTime transactiontime, LocalDate transactiondate, double balanceamount, String transactionstatus,
			long customeraccountnumber) {
		super();
		this.transactionid = transactionid;
		this.transactiontype = transactiontype;
		this.transactionamount = transactionamount;
		this.transactiontime = transactiontime;
		this.transactiondate = transactiondate;
		this.balanceamount = balanceamount;
		this.transactionstatus = transactionstatus;
		this.customeraccountnumber = customeraccountnumber;
	}

	public int getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(int transactionid) {
		this.transactionid = transactionid;
	}

	public String getTransactiontype() {
		return transactiontype;
	}

	public void setTransactiontype(String transactiontype) {
		this.transactiontype = transactiontype;
	}

	public double getTransactionamount() {
		return transactionamount;
	}

	public void setTransactionamount(double transactionamount) {
		this.transactionamount = transactionamount;
	}

	public LocalTime getTransactiontime() {
		return transactiontime;
	}

	public void setTransactiontime(LocalTime transactiontime) {
		this.transactiontime = transactiontime;
	}

	public LocalDate getTransactiondate() {
		return transactiondate;
	}

	public void setTransactiondate(LocalDate transactiondate) {
		this.transactiondate = transactiondate;
	}

	public double getBalanceamount() {
		return balanceamount;
	}

	public void setBalanceamount(double balanceamount) {
		this.balanceamount = balanceamount;
	}

	public String getTransactionstatus() {
		return transactionstatus;
	}

	public void setTransactionstatus(String transactionstatus) {
		this.transactionstatus = transactionstatus;
	}

	public long getCustomeraccountnumber() {
		return customeraccountnumber;
	}

	public void setCustomeraccountnumber(long customeraccountnumber) {
		this.customeraccountnumber = customeraccountnumber;
	}

	@Override
	public String toString() {
		return "TransactionDetails [transactionid=" + transactionid + ", transactiontype=" + transactiontype
				+ ", transactionamount=" + transactionamount + ", transactiontime=" + transactiontime
				+ ", transactiondate=" + transactiondate + ", balanceamount=" + balanceamount + ", transactionstatus="
				+ transactionstatus + ", customeraccountnumber=" + customeraccountnumber + "]";
	}

}
