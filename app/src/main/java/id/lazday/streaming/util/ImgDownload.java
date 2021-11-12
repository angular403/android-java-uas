package id.lazday.streaming.util;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import id.lazday.streaming.R;

public class ImgDownload {
    public static void Picasso(String URL, ImageView view){
        Picasso.get().load(URL).placeholder(R.drawable.no_image).error(R.drawable.no_preview)
                .into(view);
    }
}
