package id.lazday.streaming.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import id.lazday.streaming.R;
import id.lazday.streaming.data.Constant;
import id.lazday.streaming.data.model.CallResponse;
import id.lazday.streaming.rest.Api;
import id.lazday.streaming.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayActivity extends AppCompatActivity {

    VideoView video_view;
    MediaController mediaControls;
    ProgressBar progress_bar;
    RelativeLayout progress_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**  remove title | fullscreen */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_play);

        Bundle bundle   = getIntent().getExtras();
        Log.e("_logStreaming",  Constant.VIDEO_PATH + bundle.getString("FILENAME"));
        Uri uri         = Uri.parse( Constant.VIDEO_PATH + bundle.getString("FILENAME") );

        progress_bar    = findViewById(R.id.progress_bar);
        progress_layout = findViewById(R.id.progress_layout);

        video_view  = findViewById(R.id.video_view);
        if (mediaControls == null) {
            // create an object of media controller class
            mediaControls = new MediaController(PlayActivity.this);
            mediaControls.setAnchorView(video_view);
        }

        video_view.start(); // start the video
        video_view.requestFocus();

        video_view.setMediaController(mediaControls);
        video_view.setVideoURI(uri);
        video_view.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                progress_layout.setVisibility(View.GONE);
                progress_bar.setVisibility(View.GONE);

                mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        switch (what) {
                            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                                progress_bar.setVisibility(View.VISIBLE);
                                break;
                            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                                progress_bar.setVisibility(View.GONE);
                                break;
                        }
                        return false;
                    }
                });
            }
        });
        video_view.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // when an video is completed, code here
                finish();
            }
        });
        video_view.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                // when an error is occured while playing an video, code here
                return false;
            }
        });

        postView(bundle.getString("LIST_ID"));
    }

    private void postView(String list_id){
        ApiInterface apiService = Api.getUrl().create(ApiInterface.class);
        Call<CallResponse> call = apiService.postView(list_id);
        call.enqueue(new Callback<CallResponse>() {
            @Override
            public void onResponse(Call<CallResponse> call, Response<CallResponse> response) {
                Log.e("_logResponse", response.body().getResponse());
            }

            @Override
            public void onFailure(Call<CallResponse> call, Throwable t) {
                Log.e("_logErr", t.toString());
            }
        });
    }
}
