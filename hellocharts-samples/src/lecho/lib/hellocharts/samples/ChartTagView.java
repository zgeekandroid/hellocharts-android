package lecho.lib.hellocharts.samples;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import lecho.lib.hellocharts.model.Line;

/**
 * date        :  2016-03-07  18:30
 * author      :  Mickaecle gizthon
 * description :
 */
public class ChartTagView extends View {
    private Line line;
    private Paint paint = new Paint();
    private Paint paintLine = new Paint();
    private Paint paintLabel = new Paint();
    private Paint paintValue = new Paint();
    private Rect labelBound = new Rect();
    private Rect valueBound = new Rect();

    private String labelText = "标签";
    private String valueText = "0.0";
    private int labelSize = 50;
    private int valueSize = 50;
    private int valuePadding = 30;
    private int labelPadding = 10;
    private int linePadding = 20;
    private int strokeWidth = 3;

    private int labelColor = Color.parseColor("#818181");
    private int valueColor  = Color.parseColor("#FF5F57");
    private int valueColorBack  = Color.parseColor("#FF5F57");
    private int backGroundColor = Color.parseColor("#FFFFFF");

    private int lineColor = Color.GRAY;
    public ChartTagView(Context context) {
        super(context);
        init(context);
    }

    public ChartTagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ChartTagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setLabelText(String labelText) {
        this.labelText = labelText;
        postInvalidate();
    }

    public void setValueText(String valueText) {
        this.valueText = valueText;
        postInvalidate();
    }

    public void setChartLine(Line line) {
        this.line = line;
        lineColor = line.getColor();
//        strokeWidth = line.getStrokeWidth();
    }



    public Line getLine() {
        return line;
    }

    public void setLabelPadding(int labelPadding) {
        this.labelPadding = labelPadding;
    }

    public void setValuePadding(int valuePadding) {
        this.valuePadding = valuePadding;
    }

    public void setBackGroundColor(int backGroundColor) {
        this.backGroundColor = backGroundColor;
    }

    public void setValueColor(int valueColor) {
        this.valueColorBack = valueColor;
        this.valueColor = valueColor;
    }

    public void setLabelSize(int labelSize) {
        this.labelSize = labelSize;
//        postInvalidate();
    }

    public void setValueSize(int valueSize) {
        this.valueSize = valueSize;
    }

    public void init(Context context){

            paintLine.setAntiAlias(true);
            paintLine.setTextAlign(Paint.Align.CENTER);


        paintLabel.setAntiAlias(true);
        paintLabel.setTextAlign(Paint.Align.CENTER);


        paintValue.setAntiAlias(true);

        paintValue.setTextAlign(Paint.Align.CENTER);




        paintLabel.setTextSize(labelSize);
        paintValue.setTextSize(valueSize);
        paintLabel.getTextBounds(labelText, 0, labelText.length(), labelBound);
        paintValue.getTextBounds(valueText, 0, valueText.length(), valueBound);



        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (line == null){
                   return;
                }


                boolean toggle = valueColor == paintLabel.getColor();
                if (toggle) {
                        lineColor = line.getColor();
                         valueColor = valueColorBack;
                } else {
                        lineColor = paintLabel.getColor();
                        valueColor = paintLabel.getColor();
                }
                listener.onToggle(toggle);
                postInvalidate();
            }
        });

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(backGroundColor);

        paintLabel.setColor(labelColor);
        paintValue.setColor(valueColor);

            paintLine.setColor(lineColor);

        paintLabel.setTextSize(labelSize);
        paintValue.setTextSize(valueSize);
        paintLabel.getTextBounds(labelText, 0, labelText.length(), labelBound);
        paintValue.getTextBounds(valueText, 0, valueText.length(), valueBound);



        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint);

        int valueHeight = valueBound.height()+  valuePadding+getPaddingTop();
        canvas.drawText(valueText, getMeasuredWidth() / 2 - valueText.length() / 2, valueHeight, paintValue);

        int labelHeight = valueBound.height() + 2 * valuePadding  + labelPadding + labelBound.height()+getPaddingTop() ;
        canvas.drawText(labelText, getMeasuredWidth() / 2 - labelText.length() / 2, labelHeight, paintLabel);


        if (line != null){
            paintLine.setStrokeWidth(strokeWidth * 2);
            int lineHeight =labelBound.height() + 2 * labelPadding + valueBound.height() + 2 * valuePadding + linePadding+ strokeWidth * 4+getPaddingTop();
            canvas.drawLine(getMeasuredWidth() / 3, lineHeight, getMeasuredWidth() * 2 / 3, lineHeight, paintLine);
            canvas.drawCircle(getMeasuredWidth() / 2, lineHeight, strokeWidth * 4, paintLine);
        }



    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        int width = 0;
        int height = 0;


        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        switch (specMode)
        {
            case MeasureSpec.EXACTLY:
                width = getPaddingLeft() + getPaddingRight() + specSize;
                break;
            case MeasureSpec.AT_MOST:
                int labelWidth = getPaddingLeft() + getPaddingRight() + labelBound.width() + 2* labelPadding + 20;
                int valueWidth = getPaddingLeft() + getPaddingRight() + valueBound.width()+ 2*valuePadding + 20;
                width = labelWidth > valueWidth ? labelWidth : valueWidth;
                break;
        }

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (specMode)
        {
            case MeasureSpec.EXACTLY:
                height = getPaddingTop() + getPaddingBottom() + specSize;
                break;
            case MeasureSpec.AT_MOST:
                int totalHeight =  labelBound.height() + 2 * labelPadding + valueBound.height() + 2 * valuePadding + 2 * linePadding+ strokeWidth * 8;
                height = getPaddingTop() + getPaddingBottom() + totalHeight;
                break;
        }
        setMeasuredDimension(width, height);

    }




    public void setOnToggleListener(onToggleListener listener){
        this.listener = listener;
    }
    private onToggleListener listener;
    interface  onToggleListener{
        void onToggle(boolean toggle);
    }

}
