package com.example.jeka.learnenglish;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jeka.learnenglish.data.Word;
import com.github.florent37.shapeofview.shapes.RoundRectView;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private ItemClickListener mClickListener;
    private List<Word> mData;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mData.get(position).getText());
        switch (mData.get(position).getColorId()) {
            case 0:
                holder.imageView.setImageResource(R.color.color0);
                break;
            case 1:
                holder.imageView.setImageResource(R.color.color1);
                break;
            case 2:
                holder.imageView.setImageResource(R.color.color2);
                break;
            default:
                holder.imageView.setImageResource(R.color.colorPrimaryDark);
                break;
        }
        if (mData.get(position).isTarget())
            holder.roundRectView.setBorderWidthPx(20);
        if (mData.get(position).isConceal()){
            holder.roundRectView.removeAllViewsInLayout();
            holder.roundRectView.setBorderWidthPx(0);
            holder.roundRectView.setBorderColor(Color.WHITE);
            holder.roundRectView.setActivated(false);
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public Word getItem(int id) {
        return mData.get(id);
    }

    public void setData(List<Word> words){
        this.mData = words;
        notifyDataSetChanged();
    }

    public class ViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        @BindView(R.id.item_text) TextView mTextView;
        @BindView(R.id.roundRectItem) RoundRectView  roundRectView;
        @BindView(R.id.image_item) ImageView imageView;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null && !mData.get(getAdapterPosition()).isConceal())
                mClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public void setClickListener(ItemClickListener itemClickListener){
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
