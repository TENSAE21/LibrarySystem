package business;

public abstract class LibraryMemberFactory {

    public static LibraryMember create(String fname, String lname, String tel, String street, String city, String state, String zipcode) {
        Address address = new Address(street, city, state, zipcode);
        CheckoutRecord record = new CheckoutRecord();
        LibraryMember lm = new LibraryMember(fname, lname, tel, address, record);
        record.setLibraryMember(lm);

        return lm;
    }
}
