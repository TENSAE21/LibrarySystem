package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

final public class LibraryMember extends Person implements Serializable {
	private String memberId;
	private CheckoutRecord record;
	
	public LibraryMember(String fname, String lname, String tel,Address add, CheckoutRecord record) {
		super(fname,lname, tel, add);
		this.memberId = UUID.randomUUID().toString();
		this.record = record;
	}
	
	
	public CheckoutRecord getRecord() {
		return record;
	}

	public String getMemberId() {
		return memberId;
	}
	
	@Override
	public String toString() {
		return "Member Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName() + 
				", " + getTelephone() + " " + getAddress();
	}

	private static final long serialVersionUID = -2226197306790714013L;
}
