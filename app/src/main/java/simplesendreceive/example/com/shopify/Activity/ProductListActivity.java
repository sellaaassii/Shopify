package simplesendreceive.example.com.shopify.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import simplesendreceive.example.com.shopify.Network.DataPullService;
import simplesendreceive.example.com.shopify.Model.CollectArray;
import simplesendreceive.example.com.shopify.Model.CustomCollection;
import simplesendreceive.example.com.shopify.Model.ProductArray;
import simplesendreceive.example.com.shopify.Adapter.ProductAdapter;
import simplesendreceive.example.com.shopify.Network.DownloadImageBackgroundTask;
import simplesendreceive.example.com.shopify.R;
import simplesendreceive.example.com.shopify.Network.RetrofitClient;

public class ProductListActivity extends AppCompatActivity {
    private ProductAdapter productAdapter;
    private ListView productListView;
    private DataPullService apiService;
    private ImageView collectionImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list);

        apiService = RetrofitClient.getRetrofitClient().create(DataPullService.class);

        //get collection the user selected
        Intent intent = getIntent();
        CustomCollection selectedCollection = (CustomCollection) intent.getSerializableExtra(getString(R.string.selectedCollection));

        Toolbar collectionTitle = findViewById(R.id.productToolbar);
        collectionTitle.setTitle(selectedCollection.getTitle());

        TextView collectionTitleTextView = findViewById(R.id.collectionTitle);
        collectionTitleTextView.setText(selectedCollection.getTitle());

        TextView collectionBodyHtmlTextView = findViewById(R.id.bodyHtml);
        collectionBodyHtmlTextView.setText(selectedCollection.getBodyHtml());

        collectionImageView = findViewById(R.id.collectionImage);
        collectionImageView.setImageBitmap(downloadCollectionImage(selectedCollection));


        populateListFromCollection(selectedCollection);
    }

    public Bitmap downloadCollectionImage(CustomCollection collection) {
        DownloadImageBackgroundTask getCollectionImage = new DownloadImageBackgroundTask();
        Bitmap bitmapImage = null;

        try {
            bitmapImage = (Bitmap) getCollectionImage.execute(collection.getImage().getSrc()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return bitmapImage;
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
                        Log.e(getString(R.string.exception), t.getMessage());
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
                        Log.e(getString(R.string.exception), t.getMessage());
                    }
                });
            }
        };

        getProductDataBackgroundThread.start();
    }


}
