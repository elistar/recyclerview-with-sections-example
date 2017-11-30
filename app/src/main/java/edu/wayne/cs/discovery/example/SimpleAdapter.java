package edu.wayne.cs.discovery.example;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 * modified by elaheh barati elaheh@wayne.edu on 11/29/17.
 */
public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.SimpleViewHolder> {
    private static final int COUNT = MainActivity.count ;

    private final Context mContext;
    private final ArrayList<ImageItem> mItems;
    private int mCurrentItemId = 0;


    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        final ImageView image;
        final CheckBox checkbox;

        SimpleViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            image = view.findViewById(R.id.image);
            checkbox = view.findViewById(R.id.checkbox);
        }
    }

    SimpleAdapter(Context context, ArrayList<ImageItem> data) {
        mContext = context;
        mItems = new ArrayList<>(COUNT);
        for (int i = 0; i < COUNT; i++) {
            addItem(i, data.get(i));
        }
    }

    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {

        holder.image.setId(position);
        holder.checkbox.setId(position);

        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox cb = (CheckBox) view;
                int id = cb.getId();
                if (MainActivity.thumbnailsSelection[id]) {
                    cb.setChecked(false);
                    MainActivity.thumbnailsSelection[id] = false;
                } else {
                    cb.setChecked(true);
                    MainActivity.thumbnailsSelection[id] = true;
                }
            }
        });

        holder.image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailsActivity.class);
                intent.putExtra("title", mItems.get(position).getTitle());

                mContext.startActivity(intent);
            }
        });

        holder.title.setText(mItems.get(position).getTag());
        holder.image.setImageBitmap(mItems.get(position).getImage());
        holder.checkbox.setChecked(MainActivity.thumbnailsSelection[position]);

    }

    public void addItem(int position, ImageItem item) {
        final int id = mCurrentItemId++;
        mItems.add(position, item);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


}