package simplesendreceive.example.com.shopify;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListActivity extends AppCompatActivity {
    private ProductAdapter productAdapter;
    private ListView productListView;
    private TextView collectionTitle;
    private DataPullService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list);

        apiService = RetrofitClient.getRetrofitClient().create(DataPullService.class);

        Intent intent = getIntent();
        CustomCollection selectedCollection = (CustomCollection) intent.getSerializableExtra("selectedCollection");

        populateList(selectedCollection);
    }

    public void populateList(final CustomCollection selectedCollection) {
//        final ImageView imageView = findViewById(R.id.imgCollection);

        collectionTitle = findViewById(R.id.collection_title);
        collectionTitle.setText(selectedCollection.getTitle());

        Thread dataPullThread = new Thread() {
            @Override
            public void run() {
                Call<CollectArray> collects = apiService.getCollects(selectedCollection.getId());
                collects.enqueue(new Callback<CollectArray>() {

                    @Override
                    public void onResponse(Call<CollectArray> call, Response<CollectArray> response) {
                        final CollectArray res = response.body();
                        List<String> product_ids = res.getAllProductIds();

                        //concatenate all the product ids for the selected collection's products
                        String joinedProductIds = product_ids.get(0);

                        for (int i = 0; i < product_ids.size(); i++)
                            joinedProductIds += "," + product_ids.get(i);

                        populateList(joinedProductIds);
                    }

                    @Override
                    public void onFailure(Call<CollectArray> call, Throwable t) {
                        Log.e("Exception", t.getMessage());
                    }
                });

            }

            public void populateList(String joinedProductIds) {
                Call<ProductArray> productsFromCollects = apiService.getProductsForIds(joinedProductIds);
                productsFromCollects.enqueue(new Callback<ProductArray>() {
                    @Override
                    public void onResponse(Call<ProductArray> call, Response<ProductArray> response) {
                        ProductArray newResponse = response.body();

                        //put product stuff onto list view
                        Log.e("THE TING NA NAAA SKRAA sizing up brooo", Integer.toString(newResponse.getProducts().size()));

                        productAdapter = new ProductAdapter(getApplicationContext(), newResponse.getProducts());

                        productListView = findViewById(R.id.lstProducts);
                        productListView.setAdapter(productAdapter);
                    }

                    @Override
                    public void onFailure(Call<ProductArray> call, Throwable t) {
                        Log.e("THE TING NA NAAA SKRAA", t.getMessage());
                    }
                });
            }
        };

        dataPullThread.run();
    }

    public void pullPicture() {

    }



}
