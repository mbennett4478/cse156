
/**
 * Created by TheWoz on 10/7/14.
 */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Licenses extends Product{

    public Licenses(String code, String name, double serviceFee, double annualLicenseFee) {
        super(code, name, serviceFee, annualLicenseFee);
        this.code = code;
        this.name = name;
        this.serviceFee = serviceFee;
        this.annualLicenseFee = annualLicenseFee;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public double getServiceFee() {
        return serviceFee;
    }

    public double getAnnualLicenseFee() {
        return annualLicenseFee;
    }

    @Override
    public String jsonProducer() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}


