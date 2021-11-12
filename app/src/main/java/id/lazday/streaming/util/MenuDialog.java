package id.lazday.streaming.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import id.lazday.streaming.R;
import id.lazday.streaming.activity.LikeActivity;
import id.lazday.streaming.activity.PlayActivity;
import id.lazday.streaming.data.SessionManager;
import id.lazday.streaming.data.model.CallResponse;
import id.lazday.streaming.rest.Api;
import id.lazday.streaming.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuDialog {

    private SessionManager sessionManager;

    public void showDialog(final Context context, final String list_id, final String filename, final boolean like){
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.dialog_menu, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptsView);
        final AlertDialog alertDialog = alertDialogBuilder.create();

        final TextView text_play = promptsView .findViewById(R.id.text_play);
        final TextView text_book = promptsView .findViewById(R.id.text_like);

        sessionManager = new SessionManager(context);
        final HashMap<String, String> users = sessionManager.getData();

        if (like){ text_book.setText("Like"); } else { text_book.setText("Unlike"); }

        text_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( context, PlayActivity.class );
                intent.putExtra("LIST_ID", list_id);
                intent.putExtra("FILENAME", filename);
                context.startActivity(intent);

                alertDialog.dismiss();
            }
        });

        text_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ApiInterface apiService = Api.getUrl().create(ApiInterface.class);
                Call<CallResponse> call;

                if (like){
                    call  = apiService.postLike(users.get(sessionManager.ANDROID_ID), list_id);
                } else {
                    call  = apiService.postUnlike(users.get(sessionManager.ANDROID_ID), list_id);
                }

                call.enqueue(new Callback<CallResponse>() {
                    @Override
                    public void onResponse(Call<CallResponse> call, Response<CallResponse> response) {
                        Log.e("_logResponse", response.body().getResponse());
                        if (response.body().getResponse().equals("success")){
                            if (like){
                                Toast.makeText(context, "Liked", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, "Unliked", Toast.LENGTH_LONG).show();
                                ((LikeActivity) context).onResume();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CallResponse> call, Throwable t) {
                        Log.e("_logErr", t.toString());
                    }
                });

                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }
}
