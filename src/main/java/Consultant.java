import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by TheWoz on 10/7/14.
 */
public class Consultant extends Product {
    private Persons consultantCode;

    public Consultant(String code, String name, Persons consultantCode, double hourlyFee) {
        super(code, name, hourlyFee);
        this.code = code;
        this.name = name;
        this.pricePerUnit= hourlyFee;
        this.consultantCode = consultantCode;
    }
    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getConsultantCode() {
        return consultantCode.getPersonCode();
    }

    public double getPricePerUnit() {
        return this.pricePerUnit;
    }

    @Override
    public String jsonProducer() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}

