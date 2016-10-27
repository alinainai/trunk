package anjuyi.cc.edeco.ui.activity.test.path;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import anjuyi.cc.edeco.R;

/**
 * Created by Matt
 */
public class AnimatedPathView extends View {

    Paint mPaint;
    Path mPath;
    int mStrokeColor;
    float mStrokeWidth;

    float mProgress = 0f;
    float mPathLength = 0f;


    public AnimatedPathView(Context context) {
        this(context, null);
        init();
    }

    public AnimatedPathView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public AnimatedPathView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AnimatedPathView);
        mStrokeColor = a.getColor(R.styleable.AnimatedPathView_strokeColor, 0xff00ff00);
        mStrokeWidth = a.getFloat(R.styleable.AnimatedPathView_strokeWidth, 8.0f);
        a.recycle();

        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setColor(mStrokeColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setAntiAlias(true);
        setPath(new Path());
    }

    /**
     * 测量Path的长度
     * @param p
     */
    public void setPath(Path p){
        mPath = p;
        //测量Path的长度:实现动画的前提是首先得到Path的长度，然后根据长度计算出每个时间节点应该显示的长度。因为系统给我们提供了测量长度的方法，就不需要我们去进行复杂的计算了。直接使用 PathMeasure 就可以了
        PathMeasure measure = new PathMeasure(mPath, false);
        mPathLength = measure.getLength();
    }

    /**
     * Set the drawn path using an array of array of floats. First is x parameter, second is y.
     * @param points The points to set on
     */
    public void setPath(float[]... points){
        if(points.length == 0)
            throw new IllegalArgumentException("Cannot have zero points in the line");

        Path p = new Path();
        p.moveTo(points[0][0], points[0][1]);

        for(int i=1; i < points.length; i++){
            p.lineTo(points[i][0], points[i][1]);
        }

        setPath(p);
    }

    public void setPercentage(float percentage){
        if(percentage < 0.0f || percentage > 1.0f)
            throw new IllegalArgumentException("setPercentage not between 0.0f and 1.0f");
        mProgress = percentage;
        invalidate();
    }

    public void scalePathBy(float x, float y){
        Matrix m = new Matrix();
        m.postScale(x, y);
        mPath.transform(m);
        PathMeasure measure = new PathMeasure(mPath, false);
        mPathLength = measure.getLength();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

       // 只绘制Path的一部分
        //为了让Path能够逐步显示出来，或者逐步隐藏。
        // 我们需要做到能够显示path的一部分，并且改变显示的长度。
        // 我们知道可以通过 DashPathEffect 来显示虚线效果。
        // 同时我们可以借助DashPathEffect让我们的实线和虚线的部分的长度分别为我们的Path的长度，然后来改变偏移量，实现只显示path的一部分。
        PathEffect pathEffect = new DashPathEffect(new float[]{mPathLength, mPathLength}, (mPathLength - mPathLength * mProgress));
        mPaint.setPathEffect(pathEffect);

        canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop());
        canvas.drawPath(mPath, mPaint);
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(widthMeasureSpec);

        int measuredWidth, measuredHeight;

        if(widthMode == MeasureSpec.AT_MOST)
            throw new IllegalStateException("AnimatedPathView cannot have a WRAP_CONTENT property");
        else
            measuredWidth = widthSize;
        if(heightMode == MeasureSpec.AT_MOST)
            throw new IllegalStateException("AnimatedPathView cannot have a WRAP_CONTENT property");
        else
            measuredHeight = heightSize;
        setMeasuredDimension(measuredWidth, measuredHeight);
    }
}
