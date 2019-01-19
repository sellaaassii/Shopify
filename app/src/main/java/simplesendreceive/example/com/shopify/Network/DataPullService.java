package simplesendreceive.example.com.shopify.Network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import simplesendreceive.example.com.shopify.Model.CustomCollectionArray;
import simplesendreceive.example.com.shopify.Model.CollectArray;
import simplesendreceive.example.com.shopify.Model.ProductArray;

public interface DataPullService {
    @GET("custom_collections.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6")
    Call<CustomCollectionArray> getCustomCollections();

    @GET("collects.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6")
    Call<CollectArray> getCollects(@Query("collection_id") long collectId);

    @GET("products.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6")
    Call<ProductArray> getProductsForIds(@Query("ids") String ids);

}
