package simplesendreceive.example.com.shopify.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductArray {

    @SerializedName("products")
    @Expose
    private List<Product> products = null;

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "ProductArray {" +
                "products=" + products +
                '}';
    }
}
