/*
 * Description: Main menu
 * 
 * Programmed by Jie Zhuang
 * 
 * (c) 2013, CeresLink IT Co.Ltd
 */
package com.ceres.jailmon;


import java.util.List;
import com.ceres.jailmon.data.MapLocation;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

class MapView extends View{

	Bitmap m_map;
	Bitmap[] m_location;
	int m_nCurrent = 0;
	Rect m_rectmap;
	Rect m_rectview;
	Paint m_paint = new Paint();
	List<MapLocation> m_list;

	
	public MapView(Context context) {
		super(context);
		
	}
	
	 public MapView(Context context,AttributeSet paramAttributeSet)
	 {
	       super(context,paramAttributeSet);
	 }
	 
	 void loadAnimation()
	 {
		m_location = new Bitmap[5];
		m_location[0] = BitmapFactory.decodeResource( getContext().getResources(), R.drawable.location01);
		m_location[1] = BitmapFactory.decodeResource( getContext().getResources(), R.drawable.location02);
		m_location[2] = BitmapFactory.decodeResource( getContext().getResources(), R.drawable.location03);
		m_location[3] = BitmapFactory.decodeResource( getContext().getResources(), R.drawable.location04);
		m_location[4] = BitmapFactory.decodeResource( getContext().getResources(), R.drawable.location05);		
	 }
	 
	 void doAnimation()
	 {
		 if( m_location != null )
		 {
		 m_nCurrent++;
		 
		 if( m_nCurrent>=5 )
			 m_nCurrent = 0;
		 }
	 }
	 
	 void drawAnimation( Canvas canvas, Paint paint, int x, int y )
	 {
		 if( m_location!=null & m_location[m_nCurrent]!=null)
			 canvas.drawBitmap( m_location[m_nCurrent], x, y, paint);
	 }

	public void setMap( Bitmap bmp )
	{
		m_map = bmp;
		m_rectmap = new Rect( 0, 0, m_map.getWidth(), m_map.getHeight() );
		RunAnimation();
		invalidate();
	}
	
	public void setLocation( List<MapLocation> list )
	{
		m_list = list;
		m_nCurrent = 0;
		invalidate();
	}
	
	boolean m_bAnimation = true;

	private void RunAnimation() {
		m_bAnimation = true;
		final Handler handler = new Handler();
		Runnable runable = new Runnable() {
			public void run() {

				doAnimation();
				invalidate();
				handler.postDelayed(this, 100);
			}
		};

		handler.postDelayed(runable, 100);
	}

	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		if( m_rectview == null )
		{
			m_rectview = new Rect( 0, 0, this.getWidth(), this.getHeight() );
		}
		
		if( m_location == null )
		{
			loadAnimation();
		}
		
		if( m_map !=null )
			canvas.drawBitmap(m_map , m_rectmap, m_rectview, m_paint) ;
		
		if( m_list != null && m_location!=null)
		{
			MapLocation info;
			
			for( int i=0; i<m_list.size(); i++ )
			{
				info = m_list.get(i);
				
				drawAnimation( canvas, m_paint, info.x, info.y);
			}
		}
	}
}
