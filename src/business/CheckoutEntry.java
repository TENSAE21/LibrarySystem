package business;

import java.io.Serializable;
import java.time.LocalDate;

public class CheckoutEntry implements Serializable{
	private static final long serialVersionUID = 1L;
	private BookCopy book;
    private LocalDate checkoutDate;
    private LocalDate dueDate;

    CheckoutEntry(BookCopy book){
        this.book = book;
        this.checkoutDate = LocalDate.now();
        this.dueDate = this.checkoutDate.plusDays(this.book.getBook().getMaxCheckoutLength());
    }

    @Override
	public String toString() {
		return "CheckoutEntry:  \nbook: " + book + "\ncheckoutDate: " + checkoutDate + "\ndueDate: " + dueDate + "\n\n";
	}

	public BookCopy getBookCopy() {
		return book;
	}

	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}
}
