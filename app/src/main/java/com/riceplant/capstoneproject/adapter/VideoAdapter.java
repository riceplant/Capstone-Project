package com.riceplant.capstoneproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.riceplant.capstoneproject.R;
import com.riceplant.capstoneproject.data.Video;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoAdapterViewHolder>{

    private final String VIDEO_BASE_URL = "https://www.youtube.com/watch?v=";
    private List<Video> mVideoData;
    private static TextView mVideoListTextView = null;
    Context mContext;

    public VideoAdapter(List<Video> videoData, Context context) {
        mVideoData = videoData;
        mContext = context;
    }

    @NonNull
    @Override
    public VideoAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.video_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new VideoAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapterViewHolder holder, int position) {
        Video currentVideo = mVideoData.get(position);
        String videoToBind = currentVideo.getName();
        final String videoToWatch = mVideoData.get(position).getVideoId();
        mVideoListTextView.setText(videoToBind);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri openVideo = Uri.parse(VIDEO_BASE_URL + videoToWatch);
                Intent videoIntent = new Intent(Intent.ACTION_VIEW, openVideo);
                mContext.startActivity(videoIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mVideoData == null) {
            return 0;
        }
        return mVideoData.size();
    }

    public class VideoAdapterViewHolder extends RecyclerView.ViewHolder {

        public VideoAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mVideoListTextView = itemView.findViewById(R.id.video_name);
            mVideoListTextView.setPaintFlags(mVideoListTextView.getPaintFlags() |
                    Paint.UNDERLINE_TEXT_FLAG);
        }
    }
}
