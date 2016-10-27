package anjuyi.cc.edeco.ui.activity.test.drawerlayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 作者：Mr.Lee on 2016-10-20 15:13
 * 邮箱：569932357@qq.com
 */

public class BezierView extends View {


    private  float mSupX;
    private  float mSupY;


    public BezierView(Context context) {
        this(context, null, 0);
    }

    public BezierView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint p = new Paint();
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(4);//设置画笔的宽度
        //设置是否抗锯齿
        p.setAntiAlias(true);
        Path path = new Path();
        canvas.drawPoint(mSupX,mSupY,p);
        path.moveTo(200, 200);
        path.quadTo(mSupX, mSupY, 400, 200);
        canvas.drawPath(path,p);
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                mSupX = event.getX();
                mSupY = event.getY();
                invalidate();
        }
        return true;
    }
//    Paint p = new Paint();
////设置画笔的颜色
//    p.setColor(Color.parseColor("#2EA4F2"));
////设置画笔的风格：全部填充FILL   只画轮廓STROKE
//    p.setStyle(Paint.Style.STROKE);
////设置画笔的宽度
//    p.setStrokeWidth(8);
////设置是否抗锯齿
//    p.setAntiAlias(true);
//
//    [java] view plain copy 在CODE上查看代码片派生到我的代码片
////设置文字大小
//    p.setTextSize(30);
////测量字符串的长度
//    p.MeasureText("Hello World");
//    当我们有了画笔后，就可以绘制基本图形。
//    线：
//            [java] view plain copy 在CODE上查看代码片派生到我的代码片
////绘制一条从0,0到100,100的线
//    canvas.drawLine(0,0,100,100,p);
//
//    三角形&多边形
//    是用Path类实现的。Path类提供了点绘制线的功能，看例子
//    [java] view plain copy 在CODE上查看代码片派生到我的代码片
//    path.MoveTo(0,0);//给定path的起点
//    path.LineTo(10,10);//往10，10绘制一条路径
//    path.LineTo(5,3);//继续从10，10往5,3绘制一条路径
//    path.close;//将绘制的线形成封闭空间
//    canvas.drawPath(path,p);
//
//    矩形：
//            [java] view plain copy 在CODE上查看代码片派生到我的代码片
////画一个矩形，左上角的坐标为0,0   右下角的坐标为100，50
//    canvas.drawRect(0,0,100,50,p);
//    圆角矩形：
//            [java] view plain copy 在CODE上查看代码片派生到我的代码片
//    //一个矩形
//    RectF rectF = new RectF(0,0,100,50);
////画一个圆角矩形，大小为rectF，20，20分表表示左边圆角的半径和右边圆角的半径
//    canvas.drawRoundRect(RectF,20,20,p);
//
//    圆形
//    [java] view plain copy 在CODE上查看代码片派生到我的代码片
////画一个圆，圆心为50，50  半径为100
//    canvas.drawCircle(50,50,100,p);
//
//    弧形   注意这里第二个参数，是从三点钟方向为0°计算，所以想从12点中方向开始绘制，那么就是270°。第四个参数是决定是否经过圆心（自己改变一下这个参数就知道区别了）。
//            [java] view plain copy 在CODE上查看代码片派生到我的代码片
////画一个弧，弧所在矩形为rectF  从270°开始，画90° 不经过圆心
//    canvas.drawArc(rectF,270,90,false,p);

}
