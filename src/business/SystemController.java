package business;

import java.util.*;
import java.util.function.Consumer;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public class SystemController implements ControllerInterface {
	public static Set<Author> authorSet = getAuthorSet();
	public static List<Address> addressList = setAddressList();
  
	public static Auth currentAuth = null;
  
  public static Set<Author> getAuthorSet() { 
    //since an author can have multiple books and thus be added many times we return a Set

    List<Book> books = new ArrayList<>();
    DataAccessFacade da = new DataAccessFacade();
  	books.addAll(da.readBooksMap().values());
    
    Set<Author> authorSet = new LinkedHashSet<>();
    books.forEach(new Consumer<Book>() {
			@Override
			public void accept(Book book) {
				authorSet.addAll(book.getAuthors());
			}
		});

    return authorSet;
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


	@Override
	public List<String> allAuthorNames() {
		List<String> authorNames = new ArrayList<>();
		authorSet.forEach(author -> authorNames.add(author.getFirstName() + " " + author.getLastName()));

		return authorNames;
	}

	@Override
	public void addBook(String Title, String ISBN, int checkoutLen, List<String> authorNames, int copies) {

		List<Author> bookAuthors = new ArrayList<>();
		for (String name: authorNames) {
			authorSet.forEach(author -> {
						String fullName = author.getFirstName() + " " + author.getLastName();
						if(fullName.equals(name)) bookAuthors.add(author);});
		}

		Book newBook = new Book(ISBN, Title, checkoutLen, bookAuthors);
		for (int i=1; i<copies; i++ ) newBook.addCopy();

		DataAccessFacade da = new DataAccessFacade();
		da.saveNewBook(newBook);
		System.out.println("Added Book as " + newBook);
	}


	public boolean AddNewMember(String fName, String lName,
			String phNo, String street, String city, 
			String state, String zip, String bio) throws LoginException {
		System.out.println(addressList.size());

		int oldSize = authorSet.size();
//		System.out.println("Old Size " + oldSize);
		
		Address address = new Address(street, city, state, zip);
		addressList.add(address); //to use from other function
		Author author = new Author(fName, lName, phNo, address, bio);
		authorSet.add(author); //to use in adding book
		
		System.out.println(addressList.size());
//		System.out.println("new Size " +authorSet.size());
		int newSize = authorSet.size();

		return oldSize < newSize; // to see if the member already existed in the set
	}

	
	public void persistNewLibraryMember(LibraryMember libraryMember) throws LoginException {
		//SERIALIZE NEW MEMBER INTO THE DATABASE FILE
	}

}
