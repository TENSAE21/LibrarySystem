package business;

import java.time.LocalDate;

public class CheckoutEntry {
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
		return "CheckoutEntry [book=" + book + ", checkoutDate=" + checkoutDate + ", dueDate=" + dueDate + "]";
	}
    
    
}
