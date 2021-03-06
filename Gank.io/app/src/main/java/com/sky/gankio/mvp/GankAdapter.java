package com.sky.gankio.mvp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sky.gankio.R;
import com.sky.gankio.entity.GankEntity;
import com.sky.gankio.utils.ImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tonycheng on 2016/11/24.
 */

public class GankAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_PLAIN_TEXT = 0;
    private static final int TYPE_TEXT_WITH_IMAGES = 1;
    private static final int TYPE_FOOTER = 2;

    private List<GankEntity> mData;
    private boolean isShowFooter = true;
    private Context mContext;

    public GankAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getItemCount() {
        int begin = isShowFooter ? 1 : 0;
        if (mData == null) {
            return begin;
        }
        return mData.size() + begin;
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowFooter && position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        GankEntity gankEntity = mData.get(position);
        if (gankEntity.getImages() == null || gankEntity.getImages().size() == 0) {
            return TYPE_PLAIN_TEXT;
        } else {
            return TYPE_TEXT_WITH_IMAGES;
        }
    }


    public void setData(List<GankEntity> data) {
        mData = data;
        notifyDataSetChanged();
    }


    public void setShowFooter(boolean showFooter) {
        if (isShowFooter == showFooter) {
            return;
        }
        isShowFooter = showFooter;
        if (showFooter) {
            notifyItemInserted(getItemCount());
        } else {
            notifyItemRemoved(getItemCount() - 1);
        }
    }

    public boolean isShowFooter() {
        return isShowFooter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_PLAIN_TEXT:
                View rootTextView = LayoutInflater.from(mContext).inflate(R.layout
                        .item_gank_with_text, parent, false);
                return new TextViewHolder(rootTextView);
            case TYPE_TEXT_WITH_IMAGES:
                View rootImageView = LayoutInflater.from(mContext).inflate(R.layout
                        .item_gank_with_image, parent, false);
                return new ImageViewHolder(rootImageView);
            case TYPE_FOOTER:
                View rootFooterView = LayoutInflater.from(mContext).inflate(R.layout
                        .view_footer, parent, false);
                return new FooterViewHolder(rootFooterView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        int type = getItemViewType(position);

        switch (type) {
            case TYPE_PLAIN_TEXT:
                GankEntity textEntity = mData.get(position);
                TextViewHolder textViewHolder = (TextViewHolder) holder;
                textViewHolder.mTitle.setText(textEntity.getDesc());
                if (textEntity.getWho() != null) {
                    textViewHolder.mAuthor.setText(String.format("via %s", textEntity.getWho()));
                } else {
                    textViewHolder.mAuthor.setText("");
                }
                textViewHolder.mTime.setText(textEntity.getPublishedAt().split("T")[0]);
                break;
            case TYPE_TEXT_WITH_IMAGES:
                GankEntity imageEntity = mData.get(position);
                ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
                imageViewHolder.mBanner.setImages(imageEntity.getImages())
                        .setImageLoader(new ImageLoader())
                        .setBannerAnimation(Transformer.DepthPage)
                        .start();
                imageViewHolder.mTitle.setText(imageEntity.getDesc());
                if (imageEntity.getWho() != null) {
                    imageViewHolder.mAuthor.setText(String.format("via %s", imageEntity.getWho()));
                } else {
                    imageViewHolder.mAuthor.setText("");
                }
                imageViewHolder.mTime.setText(imageEntity.getPublishedAt().split("T")[0]);
                break;
        }

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    static class TextViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title_text_view)
        TextView mTitle;
        @BindView(R.id.author_text_view)
        TextView mAuthor;
        @BindView(R.id.time_text_view)
        TextView mTime;

        public TextViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_banner)
        Banner mBanner;//Support auto rotation
        @BindView(R.id.img_title_text_view)
        TextView mTitle;
        @BindView(R.id.img_author_text_view)
        TextView mAuthor;
        @BindView(R.id.img_time_text_view)
        TextView mTime;

        public ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }


    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
