package com.example.topstories.Views;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.topstories.R;

public class NewsStoryItemDecoration extends RecyclerView.ItemDecoration {
    public NewsStoryItemDecoration() {

    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
            outRect.bottom = (int) view.getContext().getResources().getDimension(R.dimen.newsItemSpace);
        }
    }
}
