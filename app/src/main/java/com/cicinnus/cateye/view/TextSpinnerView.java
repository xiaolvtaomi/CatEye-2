package com.cicinnus.cateye.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cicinnus.cateye.R;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.angmarch.views.NiceSpinner;

/**
 * Created by lml on 18/5/31.
 */

public class TextSpinnerView extends LinearLayout {
    public TextSpinnerView(Context context) {
        super(context);
        this.mContext = context ;
        init(context);
    }
    
    public TextSpinnerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context ;
        init(context);
        setParams(attrs);
    }
    
    public TextSpinnerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context ;
        init(context);
        setParams(attrs);
    }
    
    private TextView tvLeft ;
    private TextView tvNesscery ;
    private NiceSpinner niceSpinner ;
    private Context mContext ;
    
    private String strLeft ;
    private String strRightHint ;
    private String strRight ;
    private boolean nesscery = false ;
    
    private void init(Context context){
    
        inflate(context, R.layout.base_text_spinner, this);
        tvLeft = (TextView) findViewById(R.id.tv_left);
        tvNesscery = (TextView) findViewById(R.id.tv_nesscery);
        niceSpinner = (NiceSpinner) findViewById(R.id.nice_spinner);
        
    }
    
    protected WeakReference<Activity> mActivityWeakReference ;
    protected WeakReference<Fragment> mFragmentWeakReference ;
    private CharSequence[] array ;
    
    public void setFragment(Fragment frag){
        mFragmentWeakReference = new WeakReference<Fragment>(frag);
    }
    public void setActivity(Activity act){
        mActivityWeakReference = new WeakReference<Activity>(act);
    }
    
    private void setParams(AttributeSet attrs){
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.textedit);
        nesscery = a.getBoolean(R.styleable.textedit_nesscery, false);
        tvNesscery.setVisibility(nesscery?VISIBLE:INVISIBLE);
        strLeft = a.getString(R.styleable.textedit_left_text);
        tvLeft.setText(""+strLeft);
        strRight = a.getString(R.styleable.textedit_right_text);
        strRightHint = a.getString(R.styleable.textedit_right_hint);
        array = a.getTextArray(R.styleable.textedit_liststr);
        List<CharSequence> dataset = new LinkedList<CharSequence>(Arrays.asList(array));
        niceSpinner.attachDataSource(dataset);
        niceSpinner.setSelectedIndex(0);
        niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(mTSVListener != null){
                    mTSVListener.onSelected(position, lastPos);
                  lastPos = position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    
    public String getValue(){
        return array[niceSpinner.getSelectedIndex()].toString();
    }
    
    public int getValuePos(){
        return niceSpinner.getSelectedIndex() ;
    }

    private int lastPos = 0 ;
    public interface TSVListener{
        public void onSelected(int newPos, int lastPos);
    }
    private TSVListener mTSVListener ;
    public void setSelectedListener(TSVListener _TSVListener){
        if(_TSVListener != null){
            mTSVListener = _TSVListener ;
        }
    }
}
