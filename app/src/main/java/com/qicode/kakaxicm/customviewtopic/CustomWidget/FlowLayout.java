package com.qicode.kakaxicm.customviewtopic.CustomWidget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.qicode.kakaxicm.customviewtopic.R;
import com.qicode.kakaxicm.customviewtopic.SizeUtils.SizeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenming on 2018/5/2
 */
public class FlowLayout extends ViewGroup {

    private final int DIP_ITEM_GAP = 5;
    private int topGap = (int) SizeUtils.dp2Px(getResources(), DIP_ITEM_GAP);
    private int leftGap = (int) SizeUtils.dp2Px(getResources(), DIP_ITEM_GAP);
    private int bottomGap = (int) SizeUtils.dp2Px(getResources(), DIP_ITEM_GAP);
    private int rightGap = (int) SizeUtils.dp2Px(getResources(), DIP_ITEM_GAP);

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //Step1:拿到父View期望的大小
        int resultWidth = 0;
        int resultHeight = 0;
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        //最终的测量结果
        resultWidth = 0;
        resultHeight = 0;

        //step2:自己定义View的逻辑，根据子View大小确定父View大小
        //每一行的宽度，View的宽度取最大宽度resultWidth
        int lineWidth = 0;
        //每一行的高度，累加得到resultHeight
        int lineHeight = 0;

        int count = getChildCount();
        //遍历子View，测量子View，计算宽高
        for (int i = 0; i < count; i++) {
            //Step1 获得每个Child的宽高和Margin,得出每个child占据的空间
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            Log.e("Flow", "onMeasure:"+ i +":"+child.getMeasuredWidth());
            // 得到child的lp
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            //child占据空间
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;//注意，测量后,布局之前,只能取getMeasuredHeight，不能取getHeight

            //Step2.按行处理,注意这里的换行条件：判断剩下的空间是否容得下这个child
            if (lineWidth + childWidth < widthSize) {//未换行，宽度累加，每行的高度取child的最大值
                lineWidth = lineWidth + childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            } else {//换行,宽度取行宽度的最大值,高度累加
                resultWidth = Math.max(lineWidth, childWidth);
                resultHeight = resultHeight + lineHeight;

                lineWidth = childWidth;//行宽高重新开始计算
                lineHeight = childHeight;
            }

            //到最后一个，需要最后一行的宽高处理下,exp:两次换行，实际是3行
            if (i == count - 1) {
                resultWidth = Math.max(lineWidth, resultWidth);//宽度是行宽的最大值
                resultHeight = resultHeight + lineHeight;//最后一行加上去
            }

        }

        //如果是精确模式，则采用父布局指定的尺寸
        if (modeWidth == MeasureSpec.EXACTLY) {
            resultWidth = widthSize;
        }
        if (modeHeight == MeasureSpec.EXACTLY) {
            resultHeight = heightSize;
        }
        setMeasuredDimension(resultWidth, resultHeight);
    }

    /**
     * 存储所有的View，按行记录
     */
    private List<List<View>> mAllViews = new ArrayList<>();
    /**
     * 记录每一行的最大高度
     */
    private List<Integer> mLineHeights = new ArrayList<>();

    /**
     * 流式布局的布局要素:
     * 以行为单位布局，而行高必须得遍历完一整行才能得到,
     * 所以需要保存每一行的行高和每一行的View(或者布局参数,
     * 这里为了逻辑更清晰，每一行保存View)
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
        mLineHeights.clear();
        int parentWidth = getMeasuredWidth();
        int lineWidth = 0;
        int lineHeight = 0;

        int count = getChildCount();
        // 存储每一行所有的childView
        List<View> lineViews = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            Log.e("Flow", "onLayout:"+ child.getMeasuredWidth());
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            if(lineWidth + childWidth > parentWidth){
                //TODO 换行
                mAllViews.add(lineViews);//保存本行的View
                mLineHeights.add(lineHeight);//保存本行行高
                //新开辟一行
                lineViews = new ArrayList<>();
                lineWidth = 0;
                lineHeight = 0;
            }

            //一行中进行累加操作
            lineWidth += childWidth;
            lineHeight = Math.max(lineHeight, childHeight);
            lineViews.add(child);
        }

        //别忘了最后一行需要加入计算
        mAllViews.add(lineViews);
        mLineHeights.add(lineHeight);

        //有了每一行View和行高，下面进行布局
        /**
         * 布局的起点
         */
        int left = 0;
        int top = 0;
        //按行布局
        int lineNumbers = mAllViews.size();
        for (int i = 0; i < lineNumbers; i++) {
            lineViews = mAllViews.get(i);
            lineHeight = mLineHeights.get(i);
            //布局每一行
            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                if (child.getVisibility() == View.GONE) {
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                child.layout(lc, tc, rc, bc);
                //下一个
                left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            }

            //下一行开始布局
            left = 0;
            top += lineHeight;
        }


    }

    @Override
    public void addView(View child) {
        MarginLayoutParams lp = new MarginLayoutParams(MarginLayoutParams.WRAP_CONTENT, MarginLayoutParams.WRAP_CONTENT);
        lp.bottomMargin = bottomGap;
        lp.topMargin = topGap;
        lp.leftMargin = leftGap;
        lp.rightMargin = rightGap;
        child.setBackgroundResource(R.drawable.bg_button_gray);
//        child.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int section = (int) getTag();
//                int pos = (int) v.getTag();
//
////                //点击事件的bg处理
////                if(mTagSelectedListener != null) {
////                    mTagSelectedListener.onFlowTagSelected(section, pos);
////                }
////                updateItemBg(mCurrentSelectedIndex, pos);
////                mCurrentSelectedIndex = pos;
//            }
//        });
        super.addView(child, lp);
    }
}
