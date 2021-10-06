package business;

import java.util.*;
import java.util.function.Consumer;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;

	private Set<Author> authorSet;
	
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
		DataAccess da = new DataAccessFacade();
		List<Book> books = new ArrayList<>();
		books.addAll(da.readBooksMap().values());
		authorSet = new LinkedHashSet<>();
		books.forEach(new Consumer<Book>() {
			@Override
			public void accept(Book book) {
				authorSet.addAll(book.getAuthors());
			}
		});

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


}
