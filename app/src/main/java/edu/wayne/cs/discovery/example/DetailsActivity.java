package edu.wayne.cs.discovery.example;

/**
 * Created by elaheh barati elaheh@wayne.edu on 11/29/17.
 */


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

public class DetailsActivity extends AppCompatActivity {

    private int position = 0;
    private VideoView myVideoView;
    private MediaController mediaControls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        String title = getIntent().getStringExtra("title");

        myVideoView = findViewById(R.id.video_view);
        ImageView imageView = findViewById(R.id.image_view);

        int rawId = getResources().getIdentifier(title, "raw", getPackageName());
        if (rawId > 0) {
            imageView.setVisibility(View.INVISIBLE);

            if (mediaControls == null) {
                mediaControls = new MediaController(DetailsActivity.this);
            }

            try {
                myVideoView.setMediaController(mediaControls);

                myVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + rawId));

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            myVideoView.requestFocus();
            myVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                public void onPrepared(MediaPlayer mediaPlayer) {
                    myVideoView.seekTo(position);
                    if (position == 0) {
                        myVideoView.start();
                    } else {
                        myVideoView.pause();
                    }
                }
            });
        } else {
            myVideoView.setVisibility(View.INVISIBLE);
            rawId = getResources().getIdentifier(title, "drawable", getPackageName());
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), rawId, options);
            imageView.setImageBitmap(bitmap);

        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("Position", myVideoView.getCurrentPosition());
        myVideoView.pause();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        position = savedInstanceState.getInt("Position");
        myVideoView.seekTo(position);
    }
}
