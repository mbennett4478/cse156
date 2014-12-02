/**
 * Created by TheWoz on 10/7/14.
 */
public abstract class Product implements Parse {
    protected String code;
    protected String name;
    protected Persons personCode;
    protected double pricePerUnit;
    abstract public String jsonProducer();
    protected double serviceFee;
    protected double annualLicenseFee;

    protected Product(Persons personCode, String name, double pricePerUnit) {
        this.personCode = personCode;
        this.name = name;
        this.pricePerUnit = pricePerUnit;
    }

    protected Product(String code, String name, double pricePerUnit) {
        this.code = code;
        this.name = name;
        this.pricePerUnit = pricePerUnit;
    }

    protected Product(String code, String name, double serviceFee, double annualLicenseFee) {
        this.code = code;
        this.name = name;
        this.serviceFee = serviceFee;
        this.annualLicenseFee = annualLicenseFee;
    }
    public Persons getPersonCode() {
        return personCode;
    }

    public double getServiceFee() {
        return serviceFee;
    }

    public double getAnnualLicenseFee() {
        return annualLicenseFee;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
