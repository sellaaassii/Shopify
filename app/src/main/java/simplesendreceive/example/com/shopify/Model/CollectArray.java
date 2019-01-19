package simplesendreceive.example.com.shopify.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CollectArray {

    @SerializedName("collects")
    @Expose
    private List<Collect> collects = null;

    public List<Collect> getCollects() {
        return collects;
    }

    public void setCollects(List<Collect> collects) {
        this.collects = collects;
    }

    public List<String> getAllProductIds(){
        List<String> productIds = new ArrayList<>();
        
        for(Collect collect: collects)
            productIds.add(String.valueOf(collect.getProductId()));

        return productIds;
    }

    @Override
    public String toString() {
        return "CollectArray {" +
                "collects=" + collects +
                "}";
    }
}
