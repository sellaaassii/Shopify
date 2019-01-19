package simplesendreceive.example.com.shopify.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomCollectionArray {

    @SerializedName("custom_collections")
    @Expose
    private List<CustomCollection> customCollections = null;

    public List<CustomCollection> getCustomCollections() {
        return customCollections;
    }

    @Override
    public String toString() {
        return "CustomCollectionArray {" +
                "customCollections=" + customCollections +
                '}';
    }
}
