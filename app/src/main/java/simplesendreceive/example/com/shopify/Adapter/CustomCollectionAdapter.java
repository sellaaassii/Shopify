package simplesendreceive.example.com.shopify.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.common.base.Splitter;

import java.util.List;

import simplesendreceive.example.com.shopify.Model.CustomCollection;
import simplesendreceive.example.com.shopify.R;

public class CustomCollectionAdapter extends ArrayAdapter<CustomCollection> {

    public CustomCollectionAdapter(Context context, List<CustomCollection> collections) {
        super(context, 0, collections);
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        CustomCollection selectedCollection = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.collection_item, parent, false);
        }

        TextView collectionTitle = convertView.findViewById(R.id.collection_title);

        //remove "collection" substring from collection title
        selectedCollection.setTitle(Splitter.on("collection").split(selectedCollection.getTitle()).iterator().next());

        collectionTitle.setText(selectedCollection.getTitle());

        return convertView;
    }



}
