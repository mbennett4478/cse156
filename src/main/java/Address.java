/**
 * Created by TheWoz on 10/7/14.
 */
public class Address {
    private String street;
    private String city;
    private String state;
    private String zip;
    private String country;

    public Address(String street, String city, String state, String zip, String country) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setState(String state) {
        this.state = state;
    }
    public void setZip(String zip) {
        this.zip = zip;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getStreet() {
        return this.country;
    }
    public String getCity() {
        return this.city;
    }
    public String getState() {
        return this.state;
    }
    public String getZip() {
        return this.zip;
    }
    public String getCountry() {
        return this.country;
    }
    public String getWholeAddressLine() {
        return this.street + "\n  " + this.city + " " + this.state + " " + this.zip + " " + this.country;
    }
    public String getOneLineAddress() {
        return this.street + ", " + this.city+ ", " + this.state + ", " + this.zip + ", " + this.country;
    }
}

