package edu.wayne.cs.discovery.example;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static boolean[] thumbnailsSelection;
    public static int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mRecyclerView = this.findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        count = getResources().obtainTypedArray(R.array.video_ids).length() +
                getResources().obtainTypedArray(R.array.img_ids).length();
        thumbnailsSelection = new boolean[count];

        SimpleAdapter mAdapter = new SimpleAdapter(this, getData());

        List<SectionedGridRecyclerViewAdapter.Section> sections =
                new ArrayList<>();

        sections.add(new SectionedGridRecyclerViewAdapter.Section(0, "Section 1"));
        sections.add(new SectionedGridRecyclerViewAdapter.Section(5, "Section 2"));


        SectionedGridRecyclerViewAdapter.Section[] dummy = new SectionedGridRecyclerViewAdapter.Section[sections.size()];
        SectionedGridRecyclerViewAdapter mSectionedAdapter = new
                SectionedGridRecyclerViewAdapter(this, R.layout.section, R.id.section_text, mRecyclerView, mAdapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));

        mRecyclerView.setAdapter(mSectionedAdapter);
    }

    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();

        String[] imgs_tags = getResources().getStringArray(R.array.img_tags);
        String[] imgs_ids = getResources().getStringArray(R.array.img_ids);
        for (int i = 0; i < imgs_ids.length; i++) {

            int rawId = getResources().getIdentifier(imgs_ids[i], "drawable", getPackageName());
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), rawId, options);
            imageItems.add(new ImageItem(bitmap, imgs_ids[i], imgs_tags[i]));
        }

        String[] video_tags = getResources().getStringArray(R.array.video_tags);
        String[] video_ids = getResources().getStringArray(R.array.video_ids);
        for (int i = 0; i < video_tags.length; i++) {

            int rawId = getResources().getIdentifier(video_ids[i], "raw", getPackageName());
            String mediaPath = "android.resource://" + getPackageName() + "/" + rawId;
            Uri videoURI = Uri.parse(mediaPath);
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(this, videoURI);
            Bitmap bitmap = retriever
                    .getFrameAtTime(100000, MediaMetadataRetriever.OPTION_PREVIOUS_SYNC);

            imageItems.add(new ImageItem(bitmap, video_ids[i], video_tags[i]));
        }
        return imageItems;
    }
}
