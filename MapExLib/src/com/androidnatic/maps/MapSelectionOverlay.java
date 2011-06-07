/*
 * Copyright (C) 2011 by Vinicius Carvalho (vinnie@androidnatic.com)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.androidnatic.maps;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;


import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;


public class MapSelectionOverlay extends Overlay {
	
	private Bitmap layer;
	private BitmapDrawable on, off;
	private Point topLeft;
	
	private Point bottomRight;
	private Point finger;
	private Mode mode = Mode.RESIZE;
	private Paint clearPaint;
	private int padding;
	private int color;
	private Rect activateRect;
	private DashPathEffect dashPath = new DashPathEffect(new float[]{20,5}, 1);
	private MapView mapview;
	
	public MapSelectionOverlay(Context context){
		this(context,Color.RED);
		
	}
	
	public MapSelectionOverlay(Context context, int color){
		this.color = color;
		clearPaint = new Paint();
		this.padding = 10;
		this.on = (BitmapDrawable) context.getResources().getDrawable(R.drawable.pencil_on);
		this.off = (BitmapDrawable) context.getResources().getDrawable(R.drawable.pencil_off);
		clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
	}
	
	private boolean override = true;
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, shadow);
		if(this.mapview == null){
			this.mapview = mapView;
		}
		if(layer != null){
			canvas.drawBitmap(layer, 0,0,null);
			Bitmap pencil = (override ? on.getBitmap() : off.getBitmap());
			canvas.drawBitmap(pencil,(mapview.getWidth()-padding-pencil.getWidth()),(mapview.getHeight()-padding-pencil.getHeight()), null);
			this.activateRect = new Rect(mapview.getWidth()-padding-pencil.getWidth(),mapview.getHeight()-padding-pencil.getHeight(),mapview.getWidth()-padding,mapview.getHeight()-padding);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent e, MapView mapView) {
		
		switch (e.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if(activateRect != null && activateRect.contains((int)e.getX(), (int)e.getY())){
				this.override = !this.override;
				return override;
			}
			if(insideBounds(e.getX(), e.getY())){
				mode = Mode.DRAG;
				finger = new Point((int)e.getX(), (int)e.getY());
			}else{
				topLeft = new Point((int)e.getX(), (int)e.getY());
				mode = Mode.RESIZE;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if(mode == Mode.RESIZE){
				bottomRight = new Point((int)e.getX(), (int)e.getY());
			}else{
				int dx = (int)e.getX() - finger.x;
				int dy = (int)e.getY() - finger.y;
				topLeft.x += dx;
				topLeft.y += dy;
				bottomRight.x += dx;
				bottomRight.y += dy;
				finger = new Point((int)e.getX(), (int)e.getY());
			}
			drawRectangle();
			break;
		case MotionEvent.ACTION_UP:
			
			if(mode == Mode.RESIZE){
				bottomRight = new Point((int)e.getX(), (int)e.getY());
			}
			else if(mode == Mode.DRAG){
				mode = Mode.RESIZE;
			}
			drawRectangle();
			break;
		
		}
		return override;
	}
	
	public int[][] getBounds(){
		if(topLeft == null || bottomRight == null){
			return null;
		}
		GeoPoint tl = this.mapview.getProjection().fromPixels(topLeft.x, topLeft.y);
		GeoPoint br = this.mapview.getProjection().fromPixels(bottomRight.x, bottomRight.y);
		int[][] bounds = {{tl.getLatitudeE6(),tl.getLongitudeE6()},{br.getLatitudeE6(),br.getLongitudeE6()}};
		return bounds;
	}
	
	private boolean insideBounds(float x, float y){
		if(topLeft == null || bottomRight == null)
			return false;
		return ((x >= topLeft.x && x <= bottomRight.x) && (y >= topLeft.y && y <= bottomRight.y));
	}
	
	
	
	private void drawRectangle(){
		if(layer == null){
			
			layer = Bitmap.createBitmap(mapview.getWidth(), mapview.getHeight(), Bitmap.Config.ARGB_8888);
		}
		layer.eraseColor(Color.TRANSPARENT);
		Canvas myCanvas = new Canvas(layer);
		Paint p = new Paint();
		p.setStyle(Paint.Style.STROKE);
		if(mode == Mode.DRAG){
			
			p.setPathEffect(dashPath);
		}
		p.setColor(color);
		p.setStrokeWidth(3.0f);
		myCanvas.drawRect(topLeft.x, topLeft.y, bottomRight.x, bottomRight.y, p);
	
	}
	
	public void setColor(int color){
		this.color = color;
	}
	
	private static  enum Mode {
		DRAG, RESIZE;
	}
}
