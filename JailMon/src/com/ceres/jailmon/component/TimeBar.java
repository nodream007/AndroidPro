package com.ceres.jailmon.component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * @Title: TimeBar.java
 * @Package com.mobile.timebardemo
 * @Description:
 * @author shizhiping
 * @date 2013-1-18 下午7:46:27
 * @version V1.0
 */
public class TimeBar extends View
{
    private static final String    TAG                     = "TimeBar";
    
    /** 时间条总的刻度数，如24代表一个小时一个刻度（一格） */
    private static final int       mTotoalCellNumber       = 24;
    /** 屏幕上一次显示4格 */
    private int                    mShowCellNumber         = 4;
    /** 每一格的时间(ms) */
    private static final long      mCellMilliSeconds       = 3600 * 1000 * 24 / mTotoalCellNumber;
    /** 中间线时间字体 */
//    private static final Typeface         mMiddleTimefont         = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
    /** 中间线时间字体大小 */
    private static final float     mMiddleTimeFontSizeInSp = 16;
    private static int             mMiddleTimeFontSize;
    /** 刻度时间字体大小 */
    private static final float     mScaleTimeFontSizeInSp  = 13;
    private static int             mScaleTimeFontSize;
    private static final int       mLineWidth              = 2;
    
    /** 中间线颜色 */
    private static final int       mMiddleLineColor        = 0xffffaa00;
    
    /** 中间线时间颜色 */
    private static final int       mMiddleTimeColor        = 0xffffffff;
    
    /** 刻度颜色 */
    private int                    mScaleLineColor         = 0xffbfbfbf;
    /** 刻度时间颜色 */
    private int                    mScaleTimeColor         = 0xff333333;
    
    /** 文件列表颜色 */
    private int                    mFileInfoColor          = 0xff637fec;
    /** 时间格式 */
    private final SimpleDateFormat mTimeFormat             = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    /** 文件条显示高度 */
    private int                    mFileRectHeightDp;
    private int                    mFileRectHeight;
    /** 单元格宽度 */
    private float                  mCellWidth              = 0;
    /** 时间条宽度 */
    private int                    mWidth                  = 0;
    /** 时间条高度 */
    private int                    mHeight                 = 0;
    /** 每个像素表示的时间（ms） */
    private float                  mMilliSecondsPerPixel   = 0;
    /** 中间线X坐标 */
    private float                  mMiddleLineX            = 0;
    /** 中线时间Y轴坐标 */
    private float                  mMiddleLineTimeYInDp    = 15;
    private float                  mMiddleLineTimeY;
    /** 刻度时间Y轴坐标 */
    private float                  mScaleTimeY             = 45;
    /** 刻度线Y轴坐标 */
    private float                  mScaleLineY             = 58;
    /** 刻度线高度 */
    private float                  mScaleLineHeightDp      = 6;
    private float                  mScaleLineHeight;
    /** 上一次触摸的X坐标 */
    private float                  mLastTouchX             = 0;
    /** 是否有移动 */
    private boolean                mTouchMoved             = false;
    /** 移动敏感度，过小会导致刷新太频繁 */
    private float                  mMoveSensitive          = 0.2f;
    /** 刻度列表 */
    private List<ScaleInfo>        mScaleInfoList          = new LinkedList<ScaleInfo>();
    /** 文件显示列表 */
    private List<FileRect>         mFileRectList           = new LinkedList<FileRect>();
    /** 时间回调函数 */
    private TimePickedCallBack     mTimePickCallback;
    /** 中间线上显示的时间 */
    private GregorianCalendar      mMiddleLineTime         = new GregorianCalendar();
    /** 按下标志,按下时相应外部输入 */
    private boolean                mTouchDownFlag          = false;
    /** 拖动进度条重新定位后的时间 */
    private Calendar               mPickedTime             = new GregorianCalendar();
    /** 时间条被冻结标志，冻结后时间条不能被移动 */
    private boolean                mIsFrozen               = false;
    /** 时间条横竖屏幕切换模式 */
    private boolean                mIsLandscape            = false;
    
    public void reDrawTimeBar(boolean isLandscape)
    {
        mIsLandscape = isLandscape;
        mTouchDownFlag = false;
        invalidate();
    }
    
    /**
     * @Class TimePickedCallBack
     * @Description 时间选择回调接口，进度条拖动并放开后回调当前时间
     * @author shizhiping
     * @date 2013-1-19 下午7:26:26
     */
    public interface TimePickedCallBack
    {
        void onTimePickedCallback(Calendar currentTime);
        
        void onMoveTimeCallback(long milliseconds);
    }
    
    /**
     * @function setTimeBarCallback
     * @Description 设置时间回调函数
     * @param callback
     */
    public void setTimeBarCallback(TimePickedCallBack callback)
    {
        mTimePickCallback = callback;
    }
    
    /**
     * @param context
     */
    public TimeBar(Context context)
    {
        super(context);
        initView();
    }
    
    /**
     * @param context
     * @param attrs
     */
    public TimeBar(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initView();
    }
    
    /**
     * @function initView
     * @Description
     */
    private void initView()
    {
        initVectorScale();
        
        mMiddleTimeFontSize = sp2px(this.getContext(), mMiddleTimeFontSizeInSp);
        
//        CustomLog.i(TAG, "mMiddleTimeFontSize: " + mMiddleTimeFontSize);
        
        mScaleTimeFontSize = sp2px(this.getContext(), mScaleTimeFontSizeInSp);
        
//        CustomLog.i(TAG, "mScaleTimeFontSize: " + mScaleTimeFontSize);
        mMiddleLineTimeY = dp2px(this.getContext(), mMiddleLineTimeYInDp);
        mScaleLineHeight = dp2px(this.getContext(), mScaleLineHeightDp);
    }
    
    /**
     * @function initVectorScale
     * @Description 初始化刻度时间
     */
    private void initVectorScale()
    {
    	//初始化中间线时间-即开始播放的时间
        mMiddleLineTime.setTimeInMillis(0);
        for (int i = 0; i < mTotoalCellNumber; i++)
        {
            ScaleInfo info = new ScaleInfo(-1, (int)(mCellMilliSeconds / 1000 * i));
            info.setPosRange(0, (mTotoalCellNumber * mCellWidth));
            mScaleInfoList.add(info);
        }
    }
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        
        mWidth = w;
        mHeight = h;
        mMiddleLineX = w / 2;
        
        if (mIsLandscape)
        {
            mShowCellNumber = 6;
            //更新刻度时间Y坐标
            mScaleTimeY = (int)(h * 0.3);
            mFileRectHeightDp = 12;
            mFileRectHeight = dp2px(getContext(), mFileRectHeightDp);
            mScaleLineColor = 0x80000000;
            mScaleTimeColor = 0x80b3b3b3;
        }
        else
        {
            //更新刻度时间Y坐标
            mShowCellNumber = 4;
            mScaleTimeY = (int)(h * 0.5);
            mFileRectHeightDp = 18;
            mFileRectHeight = dp2px(getContext(), mFileRectHeightDp);
            mScaleLineColor = 0xffbfbfbf;
            mScaleTimeColor = 0xffffffff;
        }
        
        mCellWidth = ((float)w) / mShowCellNumber;
        mMilliSecondsPerPixel = mCellMilliSeconds / mCellWidth;
        mScaleLineY = mScaleTimeY + dp2px(getContext(), 3);
        
        //更新刻度
        updateScalePos();
        updateFileListPos();
    }
    
    /**
     * @function updateScalePos
     * @Description 更新刻度线位置
     */
    private void updateScalePos()
    {
        Iterator<ScaleInfo> iter = mScaleInfoList.iterator();
        while (iter.hasNext())
        {
            ScaleInfo scaleInfo = iter.next();
            long seconds =
                (scaleInfo.getHour() - mMiddleLineTime.get(Calendar.HOUR_OF_DAY)) * 3600
                    + (scaleInfo.getMinute() - mMiddleLineTime.get(Calendar.MINUTE)) * 60
                    + (scaleInfo.getSecond() - mMiddleLineTime.get(Calendar.SECOND));
            
            int scaleX = (int)mMiddleLineX + (int)(((double)(seconds * 1000)) / mCellMilliSeconds * mCellWidth);
            scaleInfo.setPosRange(0, mTotoalCellNumber * mCellWidth);
            scaleInfo.setPos(scaleX);
            //Log.i(TAG, "updateScalePos---time: " + scaleInfo.getTime() + "  x:" + scaleX);
        }
    }
    
    /**
     * @function updateFileListPos
     * @Description 更新文件位置
     */
    private void updateFileListPos()
    {
        Iterator<FileRect> iter = mFileRectList.iterator();
        while (iter.hasNext())
        {
            FileRect fileInfo = iter.next();
            
            long milliStartTime = fileInfo.getStartTimeInMillis();
            long milliStopTime = fileInfo.getStopTimeInMillis();
            
            long milliSeconds = milliStartTime - mMiddleLineTime.getTimeInMillis();
            float x = mMiddleLineX + (float)(((double)milliSeconds) / mCellMilliSeconds * mCellWidth);
            float y;
            //文件列表显示位置微调
            if (mIsLandscape)
            {
                y = mHeight - mFileRectHeight;
            }
            else
            {
                y = mHeight - mFileRectHeight - dp2px(getContext(), 2);
            }
            
            int width = (int)(((double)(milliStopTime - milliStartTime)) / mCellMilliSeconds * mCellWidth);
            if (0 == width)
            {
                width = 1;
            }
            int height = mFileRectHeight;
            fileInfo.setPos(x, y, width, height);
        }
    }
    
    /**
     * @function setCurrentTime
     * @Description 设置当前时间，显示在中线上
     * @param curTime
     */
    public void setCurrentTime(Date curDate)
    {
        if (null == curDate)
        {
            return;
        }
        
        if (!mTouchDownFlag)
        {
            mMiddleLineTime.setTime(curDate);
            //调整刻度和文件的位置
            updateScalePos();
            updateFileListPos();
            invalidate();
            
            //String curTimeStr = mTimeFormat.format(mMiddleLineTime.getTime());
            //Log.i(TAG, "setCurrentTime: " + curTimeStr);
        }
    }
    
    /**
     * @function addFileInfoList
     * @Description 添加文件信息列表
     * @param fileInfoList
     */
//    public void addFileInfoList(List<FileInfo> fileInfoList)
//    {
//        if (null == fileInfoList)
//        {
//            return;
//        }
//        
//        mFileRectList.clear();
//        Iterator<FileInfo> iter = fileInfoList.iterator();
//        while (iter.hasNext())
//        {
//            FileInfo fileInfo = iter.next();
//            if (fileInfo != null)
//            {
//                mFileRectList.add(createFileRectInfo(0, fileInfo.getStartTime(), fileInfo.getStopTime()));
//            }
//        }
//        //重绘
//        invalidate();
//    }
    
    /**
     * @function reset
     * @Description 重置时间条
     */
    public void reset(Date d)
    {
        mFileRectList.clear();
        setCurrentTime(d);
        invalidate();
    }
    
    public void setFrozen(boolean frozen)
    {
        mIsFrozen = frozen;
    }
    
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        //Log.e(TAG, "Middle line X=" + mMiddleLineX);
        Paint paint = new Paint();
        //画刻度线
        paint.setTextSize(mScaleTimeFontSize);
        paint.setStrokeWidth(mLineWidth);
        Iterator<ScaleInfo> scaleIter = mScaleInfoList.iterator();
        while (scaleIter.hasNext())
        {
            ScaleInfo scaleInfo = scaleIter.next();
            if (scaleInfo.isInRange(0, mWidth))
            {
                paint.setColor(mScaleTimeColor);
                canvas.drawText(scaleInfo.getTime(),
                    scaleInfo.getX() - paint.measureText("00:00") / 2,
                    mScaleTimeY,
                    paint);
                paint.setColor(mScaleLineColor);
                //画刻度线
                canvas.drawLine(scaleInfo.getX(), mScaleLineY, scaleInfo.getX(), mScaleLineY + mScaleLineHeight, paint);
//                Log.e(TAG, "xzh...Is in range, time: " + scaleInfo.getTime() + " X:" + scaleInfo.getX() + " Y:"
//                   + " width: " + mWidth);
            }
        }
        
        //画水平长线
        canvas.drawLine(0, mScaleLineY + mScaleLineHeight, mWidth, mScaleLineY + mScaleLineHeight, paint);
        
//        mFileInfoColor = getResources().getColor(R.color.red);
//        //画录像文件
//        paint.setColor(mFileInfoColor);
//        Iterator<FileRect> iter = mFileRectList.iterator();
//        while (iter.hasNext())
//        {
//            iter.next().draw(canvas, paint, 0, mWidth);
//        }
        
        //画中线
        paint.setColor(mMiddleLineColor);
        
        canvas.drawLine(mMiddleLineX, 0, mMiddleLineX, mHeight, paint);
        
        String curTimeStr = mTimeFormat.format(mMiddleLineTime.getTime());
        if (!mIsLandscape)
        {
            //画中线时间
            paint.setColor(mMiddleTimeColor);
            paint.setTextSize(mMiddleTimeFontSize);
            //计算时间起始位置与中线的偏差
            float midTimeOffset = paint.measureText(curTimeStr) / 2 + paint.measureText("0") + 3;
            canvas.drawText(curTimeStr, mMiddleLineX - midTimeOffset, mMiddleLineTimeY, paint);
//            paint.setTypeface(mMiddleTimefont);
        }
    }
    
    /**
     * @function updateMiddleLineTime
     * @Description 更新中间线时间
     * @param movedX 移动距离
     */
    private void updateMiddleLineTime(float movedX)
    {
        long moveMilliseconds = (long)(movedX * mMilliSecondsPerPixel);
        long middleLineTime = mMiddleLineTime.getTimeInMillis();
        middleLineTime -= moveMilliseconds;
      //回调
        if (mTimePickCallback != null)
        {
        	mMiddleLineTime.setTimeInMillis(middleLineTime);
            mTimePickCallback.onMoveTimeCallback(middleLineTime);
        }
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                OnActionDown(event);
                break;
            
            case MotionEvent.ACTION_UP:
                OnActionUp(event);
                break;
            case MotionEvent.ACTION_MOVE:
                OnActionMove(event);
                break;
            case MotionEvent.ACTION_OUTSIDE:
                break;
            default:
                break;
        }
        return true;
    }
    
    private void OnActionDown(MotionEvent event)
    {
        mLastTouchX = event.getX();
        mTouchDownFlag = true;
        //Log.i(TAG, "OnActionDown---x:" + event.getX());
    }
    
    private void OnActionUp(MotionEvent event)
    {
        if (mTouchMoved)
        {
            //回调
            if (mTimePickCallback != null)
            {
                mPickedTime.setTime(mMiddleLineTime.getTime());
                mTimePickCallback.onTimePickedCallback(mPickedTime);
            }
        }
        mTouchMoved = false;
        mTouchDownFlag = false;
        //Log.i(TAG, "OnActionUp---x:" + event.getX());
    }
    
    private void OnActionMove(MotionEvent event)
    {
        
        //判断移动距离，如果距离很小，不认为在移动（手指按下后，没有移动也会触发move事件）
        float movedX = event.getX() - mLastTouchX;
        if (Math.abs(movedX) < mMoveSensitive)
        {
            return;
        }
        
        //更新上一次按下的X坐标
        mLastTouchX = event.getX();
        
        //Log.i(TAG, "OnActionMove---x:" + event.getX());
        if (!mIsFrozen)
        {
            mTouchMoved = true;
            //更新坐标
            updateDataPos(movedX);
            //让窗口重绘
            invalidate();
        }
    }
    
    private void updateDataPos(float movedX)
    {
        //更新中线时间、刻度线、文件位置
        updateMiddleLineTime(movedX);
        updateScalePos();
        updateFileListPos();
    }
    
    private FileRect createFileRectInfo(int type, Calendar startTime, Calendar stopTime)
    {
        //根据startTime和StopTime计算x坐标和width
        long milliStartTime = startTime.getTimeInMillis();
        long milliStopTime = stopTime.getTimeInMillis();
        long milliMiddleLineTime = mMiddleLineTime.getTimeInMillis();
        float x =
            mMiddleLineX + (float)(((double)(milliStartTime - milliMiddleLineTime)) / mCellMilliSeconds * mCellWidth);
        int width = (int)(((double)(milliStopTime - milliStartTime)) / mCellMilliSeconds * mCellWidth);
        float y;
        //文件列表位置微调
        if (mIsLandscape)
        {
            y = mHeight - mFileRectHeight;
        }
        else
        {
            y = mHeight - mFileRectHeight - dp2px(getContext(), 2);
        }
        return new FileRect(x, y, width, mFileRectHeight, type, startTime, stopTime);
    }
    
    public int getFileCount()
    {
        return mFileRectList.size();
    }
    
    private int sp2px(Context ctx, float spValue)
    {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, ctx.getResources()
            .getDisplayMetrics());
    }
    
    private int dp2px(Context ctx, float dpValue)
    {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, ctx.getResources()
            .getDisplayMetrics());
    }
    
    /**
     * @function getTouchDownFlag
     * @Description
     * @author xiazehong
     * @date 2014年11月25日 上午10:10:21
     */
    public boolean getTouchDownFlag()
    {
        return mTouchDownFlag;
    }
    
}

/**
 * @Class ScaleInfo
 * @Description 时间刻度信息，保存X轴坐标，刻度时间
 * @author shizhiping
 * @date 2013-1-19 下午8:12:38
 */
class ScaleInfo
{
    private String mTime;
    private int    mHour;
    private int    mMinute;
    private int    mSecond;
    private float  mX;
    private float  mMaxX;
    private float  mMinX;
    
    ScaleInfo(int x, int seconds)
    {
        mX = x;
        mHour = seconds / 3600;
        mMinute = seconds % 3600 / 60;
        mSecond = seconds % 3600 % 60;
        mTime = String.format("%02d:%02d", mHour, mMinute);
    }
    
    public String getTime()
    {
        return mTime;
    }
    
    public int getHour()
    {
        return mHour;
    }
    
    public int getMinute()
    {
        return mMinute;
    }
    
    public int getSecond()
    {
        return mSecond;
    }
    
    public float getX()
    {
        return mX;
    }
    
    public void setPos(float x)
    {
        if (x < mMinX)
        {
            x = mMaxX - (mMinX - x);
        }
        else if (x > mMaxX)
        {
            x = mMinX + (x - mMaxX);
        }
        mX = x;
    }
    
    public void setPosRange(float min, float max)
    {
        mMinX = min;
        mMaxX = max;
    }
    
    public boolean isInRange(float xMin, float xMax)
    {
        if (mX >= xMin && mX <= xMax)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public void setPosByTime(String sCurTime, int iRefPos, float iCellWidth, int iCellSeconds)
    {
        int iSeconds = getPassedSeconds(sCurTime);
        int iPosMove = (int)((((double)iSeconds) / iCellSeconds) * iCellWidth);
        //修改坐标
        mX = iRefPos + iPosMove;
        if (mX < mMinX)
        {
            mX = mMaxX - (mMinX - mX);
        }
        else if (mX > mMaxX)
        {
            mX = mMinX + (mX - mMaxX);
        }
    }
    
    private int getPassedSeconds(String sCurTime)
    {
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date2;
        try
        {
            date2 = dateFormat2.parse(sCurTime);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 0;
        }
        GregorianCalendar calendar2 = new GregorianCalendar();
        calendar2.setTime(date2);
        
        int Hour = mHour - calendar2.get(Calendar.HOUR_OF_DAY);
        int Minute = mMinute - calendar2.get(Calendar.MINUTE);
        int Second = mSecond - calendar2.get(Calendar.SECOND);
        
        return Hour * 3600 + Minute * 60 + Second;
    }
}

/**
 * @Class FileRect
 * @Description 文件显示信息，保存坐标和宽高，开始时间、结束时间，实现画图
 * @author shizhiping
 * @date 2013-1-19 下午8:13:45
 */
class FileRect
{
    private float    mX;
    private float    mY;
    private float    mWidth;
    private float    mHeight;
    private int      mType;
    private Calendar mStartTime;
    private Calendar mStopTime;
    
    FileRect(float x, float y, int width, int height, int type, Calendar startTime, Calendar stopTime)
    {
        mX = x;
        mY = y;
        mWidth = width;
        mHeight = height;
        mType = type;
        mStartTime = startTime;
        mStopTime = stopTime;
    }
    
    public long getStartTimeInMillis()
    {
        return mStartTime.getTimeInMillis();
    }
    
    public long getStopTimeInMillis()
    {
        return mStopTime.getTimeInMillis();
    }
    
    public void setPos(float x, float y, int width, int height)
    {
        mX = x;
        mY = y;
        mWidth = width;
        mHeight = height;
    }
    
    public boolean isInRange(float left, float right)
    {
        if ((mX + mWidth) < left || mX > right)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    
    public void draw(Canvas canvas, Paint paint, int left, int right)
    {
        if (isInRange(left, right))
        {
            if ((mX < left) && (mX + mWidth) > left)
            {
                canvas.drawRect(left, mY, mX + mWidth + 1, mY + mHeight, paint);
            }
            else if ((mX < right) && ((mX + mWidth) > right))
            {
                canvas.drawRect(mX, mY, right + 1, mY + mHeight, paint);
            }
            else
            {
                canvas.drawRect(mX, mY, mX + mWidth + 1, mY + mHeight, paint);
            }
        }
    }
}

