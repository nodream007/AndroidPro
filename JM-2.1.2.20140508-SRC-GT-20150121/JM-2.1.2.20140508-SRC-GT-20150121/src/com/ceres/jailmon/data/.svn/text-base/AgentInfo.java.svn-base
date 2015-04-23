package com.ceres.jailmon.data;

import java.util.ArrayList;
import java.util.Date;

import android.graphics.Bitmap;

public class AgentInfo {
	public String m_timeStart;
	public String m_timeEnd;
	public int m_tmStart;
	public int m_tmEnd;
	public String m_pname;
	public ArrayList<String> m_pid_list = null;  
	public ArrayList<Bitmap> m_pid_bitmap = new ArrayList<Bitmap>();
	
	static ArrayList<String> parse_pids( String pids )
	{
		ArrayList<String> info_list = new ArrayList<String>();
		String sub = null;
		
		char c;
		int len = pids.length();
		
		for( int i=0, j=0; i<len; i++)
		{
			c = pids.charAt(i);
			
			if( c == ',' )
			{
				sub = pids.substring( j, i );
				info_list.add( sub );	
				j = i+1;
			}
			else if( i==len-1)
			{
				sub = pids.substring( j, len );
				info_list.add( sub );	
			}					
		}	
				
		return info_list;
	}
	
	
	AgentInfo( String timeStart, String timeEnd, String pids,String pname )
	{
		m_timeStart = timeStart;
		m_timeEnd = timeEnd;
		m_pid_list = parse_pids(pids);
		m_pname = pname;
		
		m_tmStart = parseTime(m_timeStart);
		m_tmEnd = parseTime(m_timeEnd);
		
		if( m_tmEnd != -1 && m_tmEnd < m_tmStart )
			m_tmEnd += 24*60;
	}
	
	public static int parseTime( String text )
	{
		if( text == null )
			return -1;
		
		int pos = text.lastIndexOf(':');
		
		if( pos == -1 || pos == 0 )
			return -1;
		
		String strHour = text.substring(0, pos);
		String strMin = text.substring(pos+1, text.length() );
		
		if( strHour == null || strMin == null )
			return -1;
		
		int hour = Integer.parseInt(strHour);
		int min = Integer.parseInt(strMin);
		
		if( hour < 0 || hour >= 24 )
			return -1;
		
		if( min < 0 || min >= 60 )
			return -1;
		
		return hour * 60 + min;	
	}
}
