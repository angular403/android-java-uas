package id.lazday.streaming.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import id.lazday.streaming.R;
import id.lazday.streaming.data.model.Video;
import id.lazday.streaming.data.Constant;
import id.lazday.streaming.util.DateConverter;
import id.lazday.streaming.util.ImgDownload;

public class MainAdapter extends ArrayAdapter<Video> {

    public MainAdapter(@NonNull Context context, @NonNull List<Video> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Video video = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.adapter_grid, parent, false
            );
        }

        TextView text_title     = convertView.findViewById(R.id.text_title);
        ImageView img_image     = convertView.findViewById(R.id.img_image);
        TextView text_created   = convertView.findViewById(R.id.text_created);
        TextView text_genres     = convertView.findViewById(R.id.text_categories);

        Log.e("logPathCover", Constant.COVER_PATH + video.getCover());
        ImgDownload.Picasso(Constant.COVER_PATH + video.getCover(), img_image);
        text_title.setText( video.getTitle() );
        text_created.setText( DateConverter.convert( video.getCreated() ) );
        text_genres.setText( "#" + video.getCategory().replace(", ", " #"));

        return convertView;
    }

    @Override
    public int getCount() {
//        Log.e("_size", String.valueOf(  videos.size() ) );
        return super.getCount();
    }
}
