package com.example.lostandfoudfirst.myView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.lostandfoudfirst.R;

/**
 * Created by Administrator on 2017/12/21.
 */

public class SelectPicDialog extends Dialog {

    public SelectPicDialog(@NonNull Context context) {
        super(context);
    }

    public SelectPicDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected SelectPicDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dailog_select_pic);
    }
}
