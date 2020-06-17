package com.riceplant.capstoneproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.riceplant.capstoneproject.R;
import com.riceplant.capstoneproject.data.Cover;
import com.riceplant.capstoneproject.data.Game;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameAdapterViewHolder> {

    private ArrayList<Game> mGameData;
    private final GameAdapterOnClickHandler mClickHandler;

    public GameAdapter(ArrayList<Game> gameData, GameAdapterOnClickHandler clickHandler) {
        mGameData = gameData;
        mClickHandler = clickHandler;
    }

    public interface GameAdapterOnClickHandler {
        void onClick(int adapterPosition);
    }

    public class GameAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mGameCover;

        public GameAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mGameCover = itemView.findViewById(R.id.game_cover_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(adapterPosition);
        }
    }

    @NonNull
    @Override
    public GameAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.game_list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(layoutIdForListItem, parent, false);
        return new GameAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameAdapterViewHolder holder, int position) {
        final String IMAGE_URL = "https://images.igdb.com/igdb/image/upload/t_720p/";
        final String IMAGE_FORMAT = ".jpg";
        Cover cover = mGameData.get(position).getCover();
        String currentGame = null;
        String currentGameCoverImageId = null;
        if (cover != null)
            currentGameCoverImageId = cover.getImageId();
            currentGame = IMAGE_URL + currentGameCoverImageId + IMAGE_FORMAT;
        Picasso.get()
                .load(currentGame)
                .placeholder(R.drawable.image_loading)
                .error(R.drawable.image_not_found)
                .into(holder.mGameCover);
    }

    @Override
    public int getItemCount() {
        if (mGameData == null) {
            return 0;
        }
        return mGameData.size();
    }

}
