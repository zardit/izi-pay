package fshn.edu.al;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "billing", schema = "public")
public class Bill {

	@Id
	@GeneratedValue
	private int id;
	@Column(name = "bill_serial_number")
	private String billSerialNumber;

	@Column(name = "contract_number")
	private String contractNumber;

	@Column(name = "interest")
	private double interest;

	@Column(name = "bill_amount")
	private double billAmount;

	
	@Column(name = "bill_month")
	private String month;

	@Column(name = "bill_year")
	private String year;

	@Column(name = "customer_name")
	private String customerName;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "payment_reference_number")
	private String paymentReferenceNumber;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBillSerialNumber() {
		return billSerialNumber;
	}

	public void setBillSerialNumber(String billSerialNumber) {
		this.billSerialNumber = billSerialNumber;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public double getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(double billAmount) {
		this.billAmount = billAmount;
	}

	
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPaymentReferenceNumber() {
		return paymentReferenceNumber;
	}

	public void setPaymentReferenceNumber(String paymentReferenceNumber) {
		this.paymentReferenceNumber = paymentReferenceNumber;
	}
}