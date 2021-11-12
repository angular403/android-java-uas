package id.lazday.streaming.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import id.lazday.streaming.R;
import id.lazday.streaming.data.model.Category;

public class CategoryAdapter extends ArrayAdapter<Category> {

    public CategoryAdapter(@NonNull Context context, @NonNull List<Category> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Category category = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.adapter_category, parent, false
            );
        }

        TextView text_cat_id    = convertView.findViewById(R.id.text_cat_id);
        TextView text_category  = convertView.findViewById(R.id.text_category);
        text_cat_id.setText( category.getCat_id() );
        text_category.setText( category.getCategory() );

        return convertView;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }
}
