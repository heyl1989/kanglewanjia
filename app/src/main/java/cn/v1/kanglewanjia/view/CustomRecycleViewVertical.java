package cn.v1.kanglewanjia.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Scroller;

/**
 * Created by txt on 2015/12/3.
 */
public class CustomRecycleViewVertical extends RecyclerView {
    private static final String TAG = CustomRecycleViewVertical.class.getSimpleName();
    private Scroller mScroller;
    private int mLastY = 0;
    private int mTargetPos;
    //用于设置自动平移时候的速度
    private float mPxPerMillsec = 0;

    public CustomRecycleViewVertical(Context context) {
        super(context);
        init(context);
    }

    public CustomRecycleViewVertical(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomRecycleViewVertical(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context){
        mScroller = new Scroller(context);
    }


    @Override
    public void computeScroll() {
        super.computeScroll();
        //computeScrollOffset返回true表示滚动还在继续，持续时间应该就是startScroll设置的时间
        if(mScroller!=null && mScroller.computeScrollOffset()){
            Log.d(TAG, "getCurrX = " + mScroller.getCurrY());
            scrollBy(0,mLastY - mScroller.getCurrY());
            mLastY = mScroller.getCurrY();
            postInvalidate();//让系统继续重绘，则会继续重复执行computeScroll
        }
    }

    //调用此方法滚动到目标位置
    public void smoothScrollTo(int fx, int fy,int duration) {
        int dx=0;
        int dy=0;
        if(fx!=0) {
            dx = fx - mScroller.getFinalX();
        }
        if(fy!=0) {
            dy = fy - mScroller.getFinalY();
        }
        Log.i(TAG, "fx:" + fx + "  getfinalx:" + mScroller.getFinalX() + "  dx:" + dx);
        smoothScrollBy(dx, dy,duration);
    }

    //调用此方法设置滚动的相对偏移
    public void smoothScrollBy(int dx, int dy,int duration) {
        if(duration>0){
            mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy,duration);
        }else {
            //设置mScroller的滚动偏移量
            mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
        }
        /**
         * 重绘整个view，重绘过程会调用到computeScroll()方法
         */
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    public void checkAutoAdjust(int position){
        int childcount = getChildCount();
        //获取可视范围内的选项的头尾位置
        int firstvisiableposition = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
        int lastvisiableposition = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
        Log.d(TAG, "childcount:" + childcount + "  position:" + position + "  firstvisiableposition:" + firstvisiableposition
                + "  lastvisiableposition:" + lastvisiableposition);
        if(position == (firstvisiableposition + 1) || position == firstvisiableposition){
            //当前位置需要向右平移
            leftScrollBy(position, firstvisiableposition);
        }
        else if(position == (lastvisiableposition - 1) || position == lastvisiableposition){
            //当前位置需要向做平移
            rightScrollBy(position, lastvisiableposition);
        }
    }

    private void leftScrollBy(int position, int firstvisiableposition){
        View leftChild = getChildAt(0);
        if(leftChild != null){
            int leftx = leftChild.getLeft();
            Log.d(TAG, "leftChild left:" + leftx);
            int startleft = leftx;
            int endleft = position == firstvisiableposition?leftChild.getWidth():0;
            Log.d(TAG, "startleft:" + startleft + " endleft" + endleft);
            autoAdjustScroll(startleft, endleft);
        }
    }


    private void rightScrollBy(int position, int lastvisiableposition){
        int childcount = getChildCount();
        View rightChild = getChildAt(childcount - 1);
        if(rightChild != null){
            int rightx = rightChild.getRight();
            int dx = rightx - getWidth();
            Log.d(TAG, "rightChild right:" + rightx + " dx:" + dx);
            int startright = dx;
            int endright = position == lastvisiableposition?-1 * rightChild.getWidth():0;
            Log.d(TAG,"startright:" + startright + " endright:" + endright);
            autoAdjustScroll(startright, endright);
        }
    }

    /**
     *
     * @param start 滑动起始位置
     * @param end 滑动结束位置
     */
    private void autoAdjustScroll(int start, int end){
        int duration = 0;
        if(mPxPerMillsec != 0){
            duration = (int)((Math.abs(end - start)/mPxPerMillsec));
        }
        Log.d(TAG, "duration:" + duration);
        mLastY = start;
        if(duration>0) {
            mScroller.startScroll(0,start, 0, end - start, duration);
        }else{
            mScroller.startScroll(0,start, 0, end - start);
        }
        postInvalidate();
    }

    /**
     * 将指定item平滑移动到整个view的中间位置
     * @param position
     */
    public void smoothToCenter(int position){
        int parentHeight = getHeight();//获取父视图的高
        int childCount = getChildCount();//获取当前视图可见子view的总数
        //获取可视范围内的选项的头尾位置
        int firstvisiableposition = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
        int lastvisiableposition = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
        int count = ((LinearLayoutManager)getLayoutManager()).getItemCount();//获取item总数
        Log.i(TAG,"count:"+count);
        mTargetPos = Math.max(0, Math.min(count - 1, position));//获取目标item的位置（参考listview中的smoothScrollToPosition方法）
        Log.i(TAG, "firstposition:" + firstvisiableposition + "   lastposition:" + lastvisiableposition + "   position:" + position+
                "   mTargetPos:"+mTargetPos);
        View targetChild = getChildAt(mTargetPos-firstvisiableposition);//获取目标item在当前可见视图item集合中的位置
        View firstChild = getChildAt(0);//当前可见视图集合中的最上view
        View lastChild = getChildAt(childCount-1);//当前可见视图集合中的最下view
        Log.i(TAG,"first-->top:"+firstChild.getTop()+"   bottom:"+firstChild.getBottom());
        Log.i(TAG, "last-->top:" + lastChild.getTop() + "   bottom:" + lastChild.getBottom());
        int childTopPx = targetChild.getTop();//子view相对于父view的左边距
        int childBottomPx = targetChild.getBottom();//子view相对于父view的右边距
        Log.i(TAG, "target-->top:" + targetChild.getTop() + "   bottom:" + targetChild.getBottom());


        int childHeight = targetChild.getHeight();
        int centerTop = parentHeight/2-childHeight/2;//计算子view居中后相对于父view的上边距
        int centerBottom = parentHeight/2+childHeight/2;//计算子view居中后相对于父view的下边距
        Log.i(TAG,"rv height:"+parentHeight+"   item height:"+childHeight+"   centerTop:"+centerTop+"   centerBottom:"+centerBottom);
        if(childTopPx>centerTop){//子view上边距比居中view大（说明子view靠父view的下边，此时需要把子view向上平移
            //平移的起始位置就是子view的左边距，平移的距离就是两者之差
            mLastY = childTopPx;
            mScroller.startScroll(0,childTopPx,0,centerTop-childTopPx,600);//600为移动时长，可自行设定
            postInvalidate();
        }else if(childBottomPx<centerBottom){
            mLastY = childBottomPx;
            mScroller.startScroll(0,childBottomPx,0,centerBottom-childBottomPx,600);
            postInvalidate();
        }


    }
}
