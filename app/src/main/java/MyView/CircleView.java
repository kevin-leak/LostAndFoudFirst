package MyView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.example.lostandfoudfirst.R;


/**
 * Created by Administrator on 2017/11/15.
 */

public class CircleView extends android.support.v7.widget.AppCompatImageView{

    //定义画笔，画圆圈，画边界，画背景
    private Paint mPaintCircle;
    private Paint mPaintBorder;
    private Paint mPaintBackground;

    private BitmapShader mBitmapShader;//定义图像着色器
    private Matrix mMatrix; //定义图片变换处理器，来缩放图片适应veiw口控件的大小

    //定义控件的性质属性
    private int mWidth, mHeight, mRadius,mCircleBorderWidth, mCircleBorderColor, mCircleBackgroundColor;
    private Bitmap bitmap;

    /**
     * @return 返回一个位图，如果为null则为异常
     */
    public Bitmap getBitmap() {
        return bitmap;
    }

    /**
     * @param context 上下文对象，目录
     * @param attrs 属性
     * 为了再view初始化的时候获取到相关的属性值
     */
    public CircleView(Context context, AttributeSet attrs) {
        //对参数不明白
        super(context,attrs);
        //定义事先设置的属性R.styleable.CircleHead
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.CircleHead);
        int n = typedArray.getIndexCount();
        for (int i = 0; i < n; i++){
            int attr = typedArray.getIndex(i);
            switch (attr){
                case R.styleable.CircleHead_circleBorderHeadWidth:
                    mCircleBorderColor = (int) typedArray.getDimension(attr,0);
                    break;
                case R.styleable.CircleHead_ringHeadColor:
                    mCircleBorderColor = typedArray.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.CircleHead_backgroundHeadColor:
                    mCircleBackgroundColor = typedArray.getColor(attr,Color.GREEN);
                    break;
                    
            }
        }
        // 初始化
        init();
    }

    /**
     * @param widthMeasureSpec 获取的图片的长度
     * @param heightMeasureSpec 获取的图片的高度
     * 获取半径，取长款中最下的那个
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //设定高宽，一设置较小的值为准，防止不成圆
        mWidth = getWidth();
        mHeight = getHeight();
        int mCircleSize = Math.min(mHeight,mWidth);
        // 园半径为短的二分之一
        mRadius = mCircleSize/2 - mCircleBorderWidth;
    }

    /**
     * @param canvas huabu
     */
    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        //重新制定画布
        if(getDrawable() != null){
            setBitmapShader();
            canvas.drawRect(0,0,mWidth,mHeight,mPaintBackground); //定义了个背景色，并处理边角
            canvas.drawCircle(mWidth /2 ,mHeight /2 , mRadius,mPaintCircle);
            //画边框
            canvas.drawCircle(mWidth/2, mHeight/2, mRadius + mCircleBorderWidth/2, mPaintBorder);
        }else {
            super.onDraw(canvas);
        }
    }


    /**
     * 测试长度进行拉伸和缩放，将图片转化为位图
     */
    private void setBitmapShader() {
        //将图片转化为Bitmap
        Drawable drawable = getDrawable();
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        bitmap = bitmapDrawable.getBitmap();
        //将图片放到图片着色器，后面的模式x,y轴的缩放模式，CLAMP表示拉伸
        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
        float scale = 1.0f;
        int bitmapSize = Math.min(bitmap.getHeight(), bitmap.getWidth());
        scale = mWidth * 1.0f / bitmapSize;
        mMatrix.setScale(scale,scale);
        mBitmapShader.setLocalMatrix(mMatrix);
        mPaintCircle.setShader(mBitmapShader);
    }

    /**
     * 初始化处理图片的因为放大或者缩小出现的问题，并且给它加一些边框属性
     */
    private void init() {
        //初始化图片处理器
        mMatrix = new Matrix();

        mPaintCircle = new Paint();
        mPaintCircle.setAntiAlias(true);//设置为抗锯齿
        mPaintCircle.setStrokeWidth(12);

        //附加阴影效果；
//        this.setLayerType(LAYER_TYPE_SOFTWARE,mPaintCircle);
//        mPaintCircle.setShadowLayer(13.0f,5.0f,5.0f,Color.GRAY);

        // 给图片加边框
        mPaintBorder = new Paint();
        mPaintBorder.setAntiAlias(true);
        mPaintBorder.setStyle(Paint.Style.STROKE);
        mPaintBorder.setStrokeWidth(mCircleBorderWidth);
        mPaintBorder.setColor(mCircleBorderColor);

        //给图片加背景颜色
        mPaintBackground  = new Paint();
        mPaintBackground.setColor(mCircleBackgroundColor);
        mPaintBackground.setAntiAlias(true);
        mPaintBackground.setStyle(Paint.Style.FILL);

    }

}
