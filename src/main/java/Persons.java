/**
 * Created by TheWoz on 10/7/14.
 */
public class Persons{
    private String personCode;
    private String firstName;
    private String lastName;
    private Address address;
    private String[] emailAddress;

    public Persons(String personCode, String firstName, String lastName, Address address, String[] emailAddress) {
        this.personCode = personCode;
        this.firstName = firstName;
        this.lastName =  lastName;
        this.address = address;
        this.emailAddress = emailAddress;
    }

    public Persons(String personCode, String firstName, String lastName, Address address) {
        this.personCode = personCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPersonCode() {
        return personCode;
    }

    public String getName() {
        return lastName + ", " + firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Address getAddress() {
        return address;
    }

    public String[] getEmailAddress() {
        return emailAddress;
    }

    public String getStringEmail() {
        String temp = "";
        try {
            for (int i = 0; i < getEmailAddress().length; i++) {
                if (i == getEmailAddress().length - 1) {
                    temp = getEmailAddress()[i] + temp;
                } else {
                    temp = getEmailAddress()[i] + ", " + temp;
                }
            }
            return temp;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

//    @Override
//    public String jsonProducer() {
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        return gson.toJson(this);
//    }
}
