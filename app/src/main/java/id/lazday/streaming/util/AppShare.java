package id.lazday.streaming.util;

import android.content.Context;
import android.content.Intent;

public class AppShare {
    public static void share(Context context){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Belajar membuat Aplikasi Android kapan saja, dimana saja.\n\n>> https://lazday.com";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
}
