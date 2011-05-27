package com.jacksmartie.PhotoMem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	
	public ImageAdapter(Context c) {
		mContext = c;
	}
	
	@Override
	public int getCount() {
		return MainActivity.totalImages;
	}

	@Override
	public Object getItem(int position) {
		
		return null;
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ImageView imageView;
        if (view == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(MainActivity.imageViewDimension, MainActivity.imageViewDimension));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setBackgroundResource(R.drawable.icon);
            //imageView.setBackgroundDrawable(Drawable.createFromPath(MainActivity.imageStringArray[position]));
        } else {
            imageView = (ImageView) view;
        }
        return imageView;
	}
	
}