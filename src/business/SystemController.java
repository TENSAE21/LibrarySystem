package business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public class SystemController implements ControllerInterface {
	private DataAccessFacade da = new DataAccessFacade();
	public static Set<Author> authorSet = getAuthorSet();
	public static List<Address> addressList = setAddressList();
	//when system started, this list will be null. because there is no place to save for permanent

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
	public Book addBook(String Title, String ISBN, int checkoutLen, List<String> authorNames, int copies) {

		List<Author> bookAuthors = new ArrayList<>();
		for (String name: authorNames) {
			authorSet.forEach(author -> {
				String fullName = author.getFirstName() + " " + author.getLastName();
				if(fullName.equals(name)) bookAuthors.add(author);});
		}

		Book newBook = new Book(ISBN, Title, checkoutLen, bookAuthors);
		for (int i=1; i<copies; i++ ) newBook.addCopy();

		try {
			DataAccessFacade da = new DataAccessFacade();
			da.saveNewBook(newBook);
			System.out.println("Added Book as " + newBook);
			return newBook;
		}catch (Exception e) {
			System.out.println("ERROR WHILE TRYING TO PERSIST NEW BOOK: " + e.getMessage());
			return null;
		}
	}

	public void addBookCopies(Book book, int numberOfCopies) {
		for (int i = 0; i < numberOfCopies; i++) book.addCopy();
		try {
			da.saveNewBook(book);
		}catch (Exception e) {
			System.out.println("ERROR WHILE TRYING TO UPDATE NUMBER OF COPIES: " + e.getMessage());
		}
	}


	public boolean addNewAuthor(String fName, String lName,
			String phNo, String street, String city, 
			String state, String zip, String bio) throws LoginException {
		System.out.println(addressList.size());

		int oldSize = authorSet.size();
		Address address = new Address(street, city, state, zip);
		addressList.add(address); //to use from other function
		Author author = new Author(fName, lName, phNo, address, bio);
		authorSet.add(author); //to use in adding book

		System.out.println(addressList.size());
		int newSize = authorSet.size();

		return oldSize < newSize; // to see if the author already existed in the set
	}


	public LibraryMember persistNewLibraryMember(String fname, String lname, String phone, String street, String city, String state, String zipcode) throws LoginException {
		LibraryMember libraryMember = LibraryMemberFactory.create(
				fname,
				lname,
				phone,
				street,
				city,
				state,
				zipcode
				);
		try {
			da.saveNewMember(libraryMember);
			System.out.println("New member successfully created");
			return libraryMember;
		}catch (Exception e) {
			System.out.println("ERROR WHILE TRYING TO CREATE NEW MEMBER: " + e.getMessage());
			return null;
		}
	}
	
	public void updateLibraryMember(LibraryMember libraryMember) {
		try {
			da.saveNewMember(libraryMember);
			System.out.println("Member successfully updated");
		}catch (Exception e) {
			System.out.println("ERROR WHILE TRYING TO CREATE NEW MEMBER: " + e.getMessage());
		}
	}


	public HashMap<String, LibraryMember> getLibraryMembers() {
		return da.readMemberMap();
	}

	public HashMap<String, Book> getBooks() {
		return da.readBooksMap();
	}

	//check out process
	public LibraryMember findMemberByID(String id) {
		LibraryMember result = null;
		DataAccess da = new DataAccessFacade();
		var existingMembers = da.readMemberMap().values();
		for(LibraryMember lb : existingMembers) {
			if(lb.getMemberId().equals(id.trim())) {
				result = lb;
			}
		}
		return result;
	} 

	public BookCopy findBookByISBN(String isbn) {
		BookCopy result = null;
		DataAccess da = new DataAccessFacade();
		var existingBooks = da.readBooksMap().values();

		for(Book b : existingBooks) {
			//System.out.println(b.getTitle() + "___"+ b.getIsbn()+"___"+b.getMaxCheckoutLength()+"___"+b.getCopies().length);
			if(b.getIsbn()!=null && b.getIsbn().equals(isbn.trim())) {
				for(BookCopy bc : b.getCopies()) {
					if(bc.isAvailable()) {
						result = bc;
						return result;
					}
				}
			}
		}
		return result;
	}

	public Boolean saveCheckOut(LibraryMember member, BookCopy copy) {
		if(member == null || copy == null) return false;
		//adding check out record
		CheckoutRecord record = (member.getRecord() == null) ? new CheckoutRecord() : member.getRecord();
		record.add(copy);
		member.setRecord(record);

		//update data
		DataAccess da = new DataAccessFacade();
		copy.changeAvailability();

		//saving to data access
		da.saveNewMember(member); //because check out record added 
		da.saveNewBook(copy.getBook()); //because isAvailable value changed
		return true;
	}
}
