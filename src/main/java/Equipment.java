import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by TheWoz on 10/9/14.
 */
public class Equipment extends Product{
    public Equipment(String code, String name, double pricePerUnit) {
        super(code, name, pricePerUnit);
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    @Override
    public String jsonProducer() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}

