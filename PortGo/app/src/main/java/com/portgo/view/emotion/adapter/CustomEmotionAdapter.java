package com.portgo.view.emotion.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.portgo.PortApplication;
import com.portgo.R;
import com.portgo.view.emotion.EmotionView;
import com.portgo.view.emotion.data.Emoticon;
import com.portgo.view.emotion.data.EmotionData;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/13.
 */
public class CustomEmotionAdapter extends BaseEmotionAdapter<CustomEmotionAdapter.CustomEmotionListAdapter> {
    /**
     */
    private List<Emoticon> customImgPathList;
    private List<CustomEmotionListAdapter> pageAdapterList = new ArrayList<>();

    public CustomEmotionAdapter(Context context, ViewPager viewPager, EmotionData emotionData, EmotionView.EmotionClickListener emotionClickListener) {
        super(context, viewPager, emotionData, emotionClickListener);
        initData(emotionData);
    }

    private void initData(EmotionData emotionData) {
        if (emotionData.getUniqueItem() != null) {
            customImgPathList = new ArrayList<>(emotionData.getEmotionList());
            customImgPathList.add(0, emotionData.getUniqueItem());
        } else {
            customImgPathList = emotionData.getEmotionList();
        }
    }

    @Override
    protected int calcPageNumber(EmotionData emotionData, int pageCount) {
        int listSize = emotionData.getEmotionList().size();
        if (emotionData.getUniqueItem() != null)
            listSize++;

        int pageNumber;
        if (listSize % mPageCount > 0)
            pageNumber = listSize / mPageCount + 1;
        else {
            pageNumber = listSize / mPageCount;
        }
        return pageNumber;
    }

    @NonNull
    @Override
    public GridView instantiateGridView() {
        GridView gridView = (GridView) LayoutInflater.from(mContext).inflate(R.layout.bx_emotion, null);
        gridView.setScrollContainer(false);
        // 去除 GridView 在点击元素后出现的变色效果
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        return gridView;
    }

    @NonNull
    @Override
    public CustomEmotionListAdapter createListAdapter(int currentPageNumber) {
        CustomEmotionListAdapter adapter = new CustomEmotionListAdapter();
        pageAdapterList.add(adapter);
        return adapter;
    }

    @Override
    public CustomEmotionListAdapter bingData(CustomEmotionListAdapter listAdapter, int position) {
        int startP = position * mPageCount;
        int endP = (position + 1) * mPageCount > customImgPathList.size() ? customImgPathList.size() : (position + 1) * mPageCount;
        Emoticon[] list = customImgPathList.subList(startP, endP).toArray(new Emoticon[endP - startP]);
        listAdapter.setData(list);
        return listAdapter;
    }

    @Override
    public EmotionData getEmotionData() {
        return mEmotionData;
    }

    @Override
    public void setEmotionData(EmotionData emotionData) {
        List<Emoticon> list = emotionData.getEmotionList();
        customImgPathList = new ArrayList<>(list);
        customImgPathList.add(0, emotionData.getUniqueItem());
        notifyDataSetChanged();
        for (CustomEmotionListAdapter adapter : pageAdapterList) {
            adapter.notifyDataSetChanged();
        }
    }

    class CustomEmotionListAdapter extends BaseEmotionAdapter.BaseListAdapter {
        private Emoticon[] emoticonArr;

        public void setData(Emoticon[] pathList) {
            this.emoticonArr = pathList;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return emoticonArr == null ? 0 : emoticonArr.length;
        }

        @Override
        public Emoticon getEmoticonItem(int position) {
            return emoticonArr == null ? null : emoticonArr[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            RelativeLayout containerLayout = new RelativeLayout(mContext);
            if (emoticonArr == null) {
                return containerLayout;
            }
            if (emoticonArr[position] != null) {
                ImageView img = new ImageView(mContext);
                img.setBackgroundResource(R.drawable.sticker_style);
                containerLayout.setLayoutParams(new GridView.LayoutParams(mSize, mSize));
                containerLayout.setGravity(Gravity.CENTER);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.setMargins(mSize / 9, mSize / 9, mSize / 9, mSize / 9);
                img.setLayoutParams(params);

                if (emoticonArr[position].getImagePath() != null) {
                    Glide.with(mContext).load(emoticonArr[position].getImagePath()).into(img);
                } else {

                }

                containerLayout.addView(img);
            }

            return containerLayout;
        }
    }

}
