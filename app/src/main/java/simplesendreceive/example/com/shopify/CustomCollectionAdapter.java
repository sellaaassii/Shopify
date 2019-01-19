package simplesendreceive.example.com.shopify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

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

        //extract actual collection name from the collectiontitle
        String splitTitle = selectedCollection.getTitle().split("collection")[0];
        selectedCollection.setTitle(splitTitle);
        collectionTitle.setText(selectedCollection.getTitle());

        return convertView;
    }



}
