package com.example.john.flickrbrowserapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by John on 8/3/2016.
 */
public class FlickrRecyclerViewAdapter extends RecyclerView.Adapter<FlickrImageViewHolder>{
    private String LOG_TAG = FlickrRecyclerViewAdapter.class.getSimpleName();
    private Context mContext;
    private List<Photo> mPhotosList;

    public FlickrRecyclerViewAdapter(Context mContext, List<Photo> mPhotosList) {
        this.mContext = mContext;
        this.mPhotosList = mPhotosList;
    }

    @Override
    public FlickrImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse, null);
        FlickrImageViewHolder flickrImageViewHolder = new FlickrImageViewHolder(view);
        return flickrImageViewHolder;

    }

    @Override
    public int getItemCount() {
        return (null != mPhotosList ? mPhotosList.size() : 0);
    }

    public void loadNewData(List<Photo> newPhotos) {
        mPhotosList = newPhotos;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(FlickrImageViewHolder holder, int position) {
        Photo photoItem = mPhotosList.get(position);
        Log.d(LOG_TAG, "Processing + " + photoItem.getmTitle() + " --> " + Integer.toString(position));
        Picasso.with(mContext).load(photoItem.getmImage())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.thumbnail);
        holder.title.setText(photoItem.getmTitle());
    }
}
