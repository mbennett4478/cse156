import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by TheWoz on 10/15/14.
 */
public class CustomerCorp extends Customer {
    public CustomerCorp(String customerCode, String name, Address address, Persons primaryContact, String customerType) {
        super(customerCode, name, address, primaryContact, customerType);
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

    @Override
    public String jsonProducer() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    public String getCustomerType() {
        return customerType;
    }
}
