/**
 * Created by TheWoz on 10/7/14.
 */
public abstract class Customer implements Parse {
    protected String customerCode;
    protected String name;
    protected Address address;
    protected Persons primaryContact;
    protected String customerType;
    abstract public String jsonProducer();

    public Customer(String customerCode, String name, Address address, Persons primaryContact, String customerType) {
        this.customerCode = customerCode;
        this.name = name;
        this.address = address;
        this.primaryContact = primaryContact;
        this.customerType = customerType;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public Persons getPrimaryContact() {
        return primaryContact;
    }

    public String getCustomerType() {
        return customerType;
    }
}
