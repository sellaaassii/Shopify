package simplesendreceive.example.com.shopify;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListActivity extends AppCompatActivity {
    private ProductAdapter productAdapter;
    private ListView productListView;
    private DataPullService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list);

        apiService = RetrofitClient.getRetrofitClient().create(DataPullService.class);

        //get collection the user selected
        Intent intent = getIntent();
        CustomCollection selectedCollection = (CustomCollection) intent.getSerializableExtra("selectedCollection");

        Toolbar collectionTitle = findViewById(R.id.collection_title);
        collectionTitle.setTitle(selectedCollection.getTitle());

        populateListFromCollection(selectedCollection);
    }

    public void populateListFromCollection(final CustomCollection selectedCollection) {

        Thread getProductDataBackgroundThread = new Thread() {
            @Override
            public void run() {
                Call<CollectArray> collects = apiService.getCollects(selectedCollection.getId());

                collects.enqueue(new Callback<CollectArray>() {

                    @Override
                    public void onResponse(Call<CollectArray> call, Response<CollectArray> response) {
                        //get the list of collects
                        CollectArray returnedCollectArray = response.body();
                        List<String> allProductIds = returnedCollectArray.getAllProductIds();

                        //concatenate all the product ids for the selected collection's products
                        String joinedProductIds = allProductIds.get(0);

                        for (int i = 1; i < allProductIds.size(); i++)
                            joinedProductIds += "," + allProductIds.get(i);

                        loadProductDetails(joinedProductIds);
                    }

                    @Override
                    public void onFailure(Call<CollectArray> call, Throwable t) {
                        Log.e("Exception", t.getMessage());
                    }
                });

            }

            public void loadProductDetails(String joinedProductIds) {

                Call<ProductArray> productsFromCollects = apiService.getProductsForIds(joinedProductIds);

                productsFromCollects.enqueue(new Callback<ProductArray>() {
                    @Override
                    public void onResponse(Call<ProductArray> call, Response<ProductArray> response) {
                        ProductArray returnedProductArray = response.body();

                        productAdapter = new ProductAdapter(getApplicationContext(), returnedProductArray.getProducts());

                        productListView = findViewById(R.id.productList);
                        productListView.setAdapter(productAdapter);
                    }

                    @Override
                    public void onFailure(Call<ProductArray> call, Throwable t) {
                        Log.e("Exception", t.getMessage());
                    }
                });
            }
        };

        getProductDataBackgroundThread.start();
    }


}
