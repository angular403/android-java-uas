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
import id.lazday.streaming.data.model.ListVideo;
import id.lazday.streaming.util.DateConverter;

public class ListAdapter extends ArrayAdapter<ListVideo> {

    public ListAdapter(@NonNull Context context, @NonNull List<ListVideo> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ListVideo listVideo = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.adapter_list, parent, false
            );
        }

        TextView text_title     = convertView.findViewById(R.id.text_title);
        TextView text_created   = convertView.findViewById(R.id.text_created);
        TextView text_views     = convertView.findViewById(R.id.text_views);

        text_title.setText( listVideo.getTitle() );
        text_created.setText( DateConverter.convert( listVideo.getCreated() ) );
        text_views.setText( listVideo.getView() + " views" );

        return convertView;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }
}
