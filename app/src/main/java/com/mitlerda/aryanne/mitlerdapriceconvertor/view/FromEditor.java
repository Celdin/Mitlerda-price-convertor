package com.mitlerda.aryanne.mitlerdapriceconvertor.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.mitlerda.aryanne.mitlerdapriceconvertor.data.CouronneDivision;
import com.mitlerda.aryanne.mitlerdapriceconvertor.data.Division;
import com.mitlerda.aryanne.mitlerdapriceconvertor.data.LireDivision;
import com.mitlerda.aryanne.mitlerdapriceconvertor.data.MarkDivision;
import com.mitlerda.aryanne.mitlerdapriceconvertor.data.Monnaie;

import java.math.BigDecimal;

/**
 * Created by goulliarts on 26/09/2017.
 */

@RemoteViews.RemoteView
public class FromEditor extends ViewGroup {

    final static int padding = 10;
    private int childWidth;
    private int childHeight;

    Context context;

    public FromEditor(Context context) {
        super(context);
        this.context = context;
    }

    public FromEditor(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.context = context;
    }

    public FromEditor(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    public void updateField(Division division, Monnaie monnaieType, TextView.OnEditorActionListener onEditorActionListener){
        removeAllViews();
        int count = 0;
        int width = getWidth();
        int childWidth = 0;
        int padding = 3;
        int childHeight = getHeight() - 2 * padding;
        if (division instanceof CouronneDivision){
            count = CouronneDivision.values().length;
            childWidth = width / count - padding;
            for (int i = 0; i < count; i++){
                final CouronneDivision division1 = CouronneDivision.values()[i];
                final String hint = division1.getNom(monnaieType.getIndexNom());
                DivisionEditor child = new DivisionEditor(context, onEditorActionListener, division1, hint);
                addView(child);
            }
        }else if (division instanceof LireDivision){
            count = LireDivision.values().length;
            for (int i = 0; i < count; i++){
                final LireDivision division1 = LireDivision.values()[i];
                final String hint = division1.getNom(monnaieType.getIndexNom());
                DivisionEditor child = new DivisionEditor(context, onEditorActionListener, division1, hint);
                addView(child);
            }
        }else if(division instanceof MarkDivision){
            count = MarkDivision.values().length;
            for (int i = 0; i < count; i++){
                final MarkDivision division1 = MarkDivision.values()[i];
                final String hint = division1.getNom(monnaieType.getIndexNom());
                DivisionEditor child = new DivisionEditor(context, onEditorActionListener, division1, hint);
                addView(child);
            }
        }
        invalidate();
    }

    public BigDecimal getvalue(){
        final int count = getChildCount();
        BigDecimal value = BigDecimal.ZERO;
        for (int i = 0; i < count; i++) {
            final DivisionEditor child = (DivisionEditor) getChildAt(i);
            value = value.add(child.getValue());
        }
        return value;
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int parentWidth = 900;
        final int parentHeight = 130;
        final int count = getChildCount();
        if(count > 0) {
            childWidth = ((parentWidth - (padding * (count + 1))) / count);
            childHeight = parentHeight - 2 * padding;

            for (int i = 0; i < count; i++) {
                final View child = getChildAt(i);
                child.measure(childWidth, childHeight);
            }
        }
        setMeasuredDimension(parentWidth, parentHeight);
    }

    /**
     * Position all children within this layout.
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int count = getChildCount();
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                final View child = getChildAt(i);
                int x =  i * childWidth + ((i + 1) * padding);
                int y = padding;
                child.layout(x, y, x + childWidth, y + childHeight);
            }
        }
    }

    private class DivisionEditor extends AppCompatEditText{

        private Division division;

        public DivisionEditor(Context context, TextView.OnEditorActionListener onEditorActionListener, Division division, String hint) {
            super(context);
            this.division = division;
            setOnEditorActionListener(onEditorActionListener);
            setInputType(InputType.TYPE_CLASS_NUMBER);
            setTypeface(Typeface.create("cursive", Typeface.NORMAL));
            setHint(hint);
        }

        public BigDecimal getValue(){
            final String text = getText().toString();
            return  division.toReference(BigDecimal.valueOf(Double.valueOf(!"".equals(text)?text:"0")));
        }
    }
}
