package simplesendreceive.example.com.shopify;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private CustomCollectionAdapter collectionAdapter;
    private ListView customCollectionsListView;
    private DataPullService apiService;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        apiService = RetrofitClient.getRetrofitClient().create(DataPullService.class);

        customCollectionsListView = findViewById(R.id.customCollectionsList);
        populateCollectionsList();
        addCollectionItemListener();
    }

    private void populateCollectionsList() {

        Thread pullCustomCollections = new Thread(){

            @Override
            public void run(){
                Call<CustomCollectionArray> customCollections = apiService.getCustomCollections();

                customCollections.enqueue(new Callback<CustomCollectionArray>(){

                    @Override
                    public void onResponse(Call<CustomCollectionArray> call, Response<CustomCollectionArray> response) {
                        CustomCollectionArray returnedCollections = response.body();

                        collectionAdapter = new CustomCollectionAdapter(context, returnedCollections.getCustomCollections());
                        customCollectionsListView.setAdapter(collectionAdapter);
                    }

                    @Override
                    public void onFailure(Call<CustomCollectionArray> call, Throwable t) {
                        Log.e("Exception", t.getMessage());
                    }

                });
            }
        };

        pullCustomCollections.start();

    }

    private void addCollectionItemListener(){
        //add an on-click event listener to the custom collection list view
        customCollectionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomCollection selectedCollection = (CustomCollection) parent.getItemAtPosition(position);

                Intent intent = new Intent(context, OtherActivity2.class);
                intent.putExtra("selectedCollection", selectedCollection);

                startActivity(intent);
            }
        });
    }


    //TODO: ON Back Pressed tingy


//    @Override
//    public void onBackPressed() {
//        //super.onBackPressed();
//        LayoutInflater inflater = getLayoutInflater();
//        View myView = inflater.inflate(R.layout.activity_main, null, false);
//        setContentView(myView);
//
//
//        customCollectionsListView = (ListView) findViewById(R.id.customCollectionsList);
//        populateCollectionsList();
//        addCollectionItemListener();
////        setContentView(R.layout.activity_main);
//        //customCollectionsListView = (ListView) findViewById(R.id.customCollectionsList);
//        //populateCollectionsList();
//        //addCollectionItemListener();
//    }
}
