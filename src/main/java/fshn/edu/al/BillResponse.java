package fshn.edu.al;

import java.util.List;

public class BillResponse {

	public List<Bill> billList;
	public Bill bill;
	public String code;
	public String message;

	public BillResponse(List<Bill> billList, int found) {
		super();
		if (found == 0) {
			this.code = "1404";
			this.message = "No unpaid bills found for this contract = " + billList.get(0).getContractNumber();
		} else {
			this.code = "1200";
			this.message = billList.size() + " bills found for this contract = " + billList.get(0).getContractNumber();
			this.billList = billList;
		}
	}

	public BillResponse(Bill bill, int found) {
		super();
		if (found == 0) {
			this.code = "1404";
			this.message = "No unpaid bills found for this serial number = " + bill.getBillSerialNumber();
		} else {
			this.code = "1200";
			this.message = "Retreived data with success";
			this.bill = bill;
		}
	}

	public BillResponse(int found, String serialNumber) {
		super();
		if (found == 0) {
			this.code = "1404";
			this.message = "No unpaid bills found for this serial number = " + serialNumber;
		} else {
			this.code = "1200";
			this.message = "Retreived data with success";
			this.bill = bill;
		}
	}

	public BillResponse(int found, String contractNumber, char type) {
		super();
		if (found == 0) {
			this.code = "1404";
			this.message = "No paid bills found for this contract = " + contractNumber;
		} else {
			this.code = "1200";
			this.message = "Retreived data with success";
			this.bill = bill;
		}
	}

	public BillResponse(int found) {
		super();
		if (found == 0) {
			this.code = "1404";
			this.message = "Failed to pay because of incorrect contractnumber or amount values";

		} else {
			this.code = "1200";
			this.message = "Bill paid with success";
			this.bill = bill;
		}
	}

	public void setBillResponseForNotAuthorized() {
		this.code = "1401";
		this.message = "Authorization header is missing";
	}

	public void setBillResponseForNotAuthorizedRights() {
		this.code = "1401";
		this.message = "Request not allowed for this user";
	}

	public void setBillResponseForExistingPaymentReference() {
		this.code = "1409";
		this.message = "Payment reference number provided already is processed";
	}

	public void setBillResponseAuthorized() {
		this.code = "1200";
		this.message = "Authorization True";
	}

	public BillResponse(String body) {
		super();

	}

	public List<Bill> getBillList() {
		return billList;
	}

	public void setBillList(List<Bill> billList) {
		this.billList = billList;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
