package business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public class SystemController implements ControllerInterface {
	public static List<Author> authorList = setAuthorList();
	public static List<Address> addressList = setAddressList();
	
	public static Auth currentAuth = null;

	public static List<Author> setAuthorList() { //as there is no file to save external 
		List<Author> authors = new ArrayList<Author>();
		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> retval = da.readBooksMap();

		for(Book b : retval.values()) {
			for(Author a : b.getAuthors()) {
				authors.add(a);
			}
		}
		return authors;
	}
	
	public static List<Address> setAddressList() { 
		List<Address> addresses = new ArrayList<Address>();
		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> retval = da.readBooksMap();

		for(Book b : retval.values()) {
			for(Author a : b.getAuthors()) {
				addresses.add(a.getAddress());
			}
		}
		return addresses;
	}

	public void login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if(!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}
		currentAuth = map.get(id).getAuthorization();

	}
	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}

	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}

	public void AddNewMember(String fName, String lName,
			String phNo, String street, String city, 
			String state, String zip, String bio) throws LoginException {
		System.out.println(addressList.size());
		System.out.println(authorList.size());
		
		Address address = new Address(street, city, state, zip);
		addressList.add(address); //to use from other function
		Author author = new Author(fName, lName, phNo, address, bio);
		authorList.add(author); //to use in adding book
		
		System.out.println(addressList.size());
		System.out.println(authorList.size());
	}
	
	public void persistNewLibraryMember(LibraryMember libraryMember) throws LoginException {
		//SERIALIZE NEW MEMBER INTO THE DATABASE FILE
	}
}
