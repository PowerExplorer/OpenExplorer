/*
    Open Explorer, an open source file explorer & text editor
    Copyright (C) 2011 Brandon Bowles <brandroid64@gmail.com>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.brandroid.openmanager.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.io.File;

import org.brandroid.openmanager.R;
import org.brandroid.openmanager.R.drawable;
import org.brandroid.openmanager.R.id;
import org.brandroid.openmanager.R.layout;
import org.brandroid.openmanager.data.OpenFile;

public class MultiSelectHandler {
	private static MultiSelectHandler mInstance = null;
	private static Context mContext;
	private static LayoutInflater mInflater;
	private static ArrayList<String> mFileList = null;
	
	private View view;
	private ThumbnailCreator mThumbnail = null;
	
	public static MultiSelectHandler getInstance(Context context) {
		//make this cleaner
		if(mInstance == null)
			mInstance = new MultiSelectHandler();
		if(mFileList == null)
			mFileList = new ArrayList<String>();
		
		mContext = context;
		mInflater = (LayoutInflater)mContext
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		return mInstance;
	}
	
	public View addFile(String file, ThumbnailCreator thumbs) {
		mThumbnail = thumbs;
		
		return addFile(file);
	}
	
	public View addFile(String file) {	
		if(mFileList.contains(file))
			return null;
		
		view = mInflater.inflate(R.layout.multiselect_layout, null);

		ImageView image = (ImageView)view.findViewById(R.id.multi_icon);
		TextView text = (TextView)view.findViewById(R.id.multi_text);
		String ext = "";
		
		if(new File(file).isDirectory()) {
			text.setText(file.substring(file.lastIndexOf("/") + 1, file.length()));
			ext = "dir";
		} else {
			text.setText(file.substring(file.lastIndexOf("/") + 1, file.lastIndexOf(".")));
			ext = file.substring(file.lastIndexOf(".") + 1, file.length());
		}
	
		if (mThumbnail == null) {
			setImage(ext, image);
			
		} else {
			if (ext.equalsIgnoreCase("png") || 
				ext.equalsIgnoreCase("jpg") ||
				ext.equalsIgnoreCase("jpeg")|| 
				ext.equalsIgnoreCase("gif")) {
				Bitmap b = mThumbnail.generateThumb(new OpenFile(file), 64, 64).get(); 
				image.setImageBitmap(b);
				
			} else {
				setImage(ext, image);
			}
		}
			
		mFileList.add(file);
		
		return view;
	}
	
	public ArrayList<String> getSelectedFiles() {
		return mFileList;
	}
	
	public int clearFileEntry(String path) {
		int index = mFileList.indexOf(path);
		
		if(index > -1)
			mFileList.remove(index);
		
		return index;
	}
	
	public void cancelMultiSelect() {
		mFileList.clear();
		mFileList = null;
		mInstance = null;		
	}
	
	private void setImage(String extension, ImageView image) {
		if(extension.equalsIgnoreCase("dir")) {
			image.setImageResource(R.drawable.folder);
		
		} else if(extension.equalsIgnoreCase("doc") || 
				  extension.equalsIgnoreCase("docx")) {
			image.setImageResource(R.drawable.doc);
			
		} else if(extension.equalsIgnoreCase("xls")  || 
				  extension.equalsIgnoreCase("xlsx") ||
				  extension.equalsIgnoreCase("xlsm")) {
			image.setImageResource(R.drawable.excel);
			
		} else if(extension.equalsIgnoreCase("ppt") || 
				  extension.equalsIgnoreCase("pptx")) {
			image.setImageResource(R.drawable.powerpoint);
			
		} else if(extension.equalsIgnoreCase("zip") || 
				  extension.equalsIgnoreCase("gzip")) {
			image.setImageResource(R.drawable.zip);
			
		} else if(extension.equalsIgnoreCase("rar")) {
			image.setImageResource(R.drawable.rar);
			
		} else if(extension.equalsIgnoreCase("apk")) {
			image.setImageResource(R.drawable.apk);
			
		} else if(extension.equalsIgnoreCase("pdf")) {
			image.setImageResource(R.drawable.pdf);
			
		} else if(extension.equalsIgnoreCase("xml") || 
				  extension.equalsIgnoreCase("html")) {
			image.setImageResource(R.drawable.xml_html);
			
		} else if(extension.equalsIgnoreCase("mp4") || extension.equalsIgnoreCase("3gp") ||
				extension.equalsIgnoreCase("webm")  || extension.equalsIgnoreCase("m4v")) {
			image.setImageResource(R.drawable.movie);
			
		} else if(extension.equalsIgnoreCase("mp3") || extension.equalsIgnoreCase("wav") ||
				extension.equalsIgnoreCase("wma")   || extension.equalsIgnoreCase("m4p") ||
				extension.equalsIgnoreCase("m4a")   || extension.equalsIgnoreCase("ogg")) {
			image.setImageResource(R.drawable.music);
			
		} else if(extension.equalsIgnoreCase("jpeg") || extension.equalsIgnoreCase("png") ||
				extension.equalsIgnoreCase("jpg")    || extension.equalsIgnoreCase("gif")) {
			image.setImageResource(R.drawable.photo);
			
		} else {
			image.setImageResource(R.drawable.unknown);
		}
	}
}