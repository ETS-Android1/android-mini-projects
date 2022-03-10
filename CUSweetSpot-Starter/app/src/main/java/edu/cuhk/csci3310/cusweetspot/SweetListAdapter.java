package edu.cuhk.csci3310.cusweetspot;

//
// Name: Yeung Man Wai
// SID: 1155126854
//

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.LinkedList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import android.content.Intent;

public class SweetListAdapter extends Adapter<SweetListAdapter.SweetViewHolder>  {
    private static final String TAG = "SweetListAdapter";
    private final Context context;
    private final LayoutInflater mInflater;

    private LinkedList<String> mImagePathList;
    private LinkedList<Sweet> sweetList;

    // the following pre-set res image path is for debugging, but good to let students to start with
    private String mDrawableFilePath = "android.resource://edu.cuhk.csci3310.cusweetspot/drawable/";

    class SweetViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageItemView;
        public TextView nameTextView, locTextView, wikiTextView;
        public RatingBar flowerRichnessBar;
        final SweetListAdapter mAdapter;

        public SweetViewHolder(View itemView, SweetListAdapter adapter) {
            super(itemView);
            imageItemView = itemView.findViewById(R.id.image);
            this.mAdapter = adapter;

            // TODO:
            // Add event handler for image to transit to another DetailActivity
            imageItemView.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view) {
                    // create bundle and pass data by intent
                    Intent intent = new Intent(context, DetailActivity.class);
                    Bundle mBundle = new Bundle();

                    // find file path from imagelist (in case the image list does not have the same order as the csv)
                    String filename = sweetList.get(getAdapterPosition()).getFilename();
                    String filePath = "";
                    for(String path : mImagePathList){
                        if(path.contains(filename.replace(".jpg", ""))){
                            filePath = path;
                        }
                    }
                    mBundle.putInt("position", getAdapterPosition());
                    mBundle.putString("filename", filePath);
                    mBundle.putString("sweet_name", sweetList.get(getAdapterPosition()).getSweet_name());
                    mBundle.putString("resto", sweetList.get(getAdapterPosition()).getResto());
                    intent.putExtras(mBundle);
                    context.startActivity(intent);
                }
            });

        }
    }

    public SweetListAdapter(Context context,
                            LinkedList<String> imagePathList, LinkedList<Sweet> sweetList) {
        mInflater = LayoutInflater.from(context);
        this.mImagePathList = imagePathList;
        this.sweetList = sweetList;
        this.context = context;
    }

    // TODO:
    // Update the following callback methods as needed
    //
    @NonNull
    @Override
    public SweetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.imagelist_item, parent, false);
        return new SweetViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull SweetViewHolder holder, int position) {
        String mImagePath = mImagePathList.get(position);
        Uri uri = Uri.parse(mImagePath);
        holder.imageItemView.setImageURI(uri);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mImagePathList.size();
    }

    public void updateData(LinkedList<String> imagePathList) {
        this.mImagePathList = imagePathList;
        // Notify the adapter, that the data has changed so it can
        // update the RecyclerView to display the data.
        notifyDataSetChanged();
    }
}