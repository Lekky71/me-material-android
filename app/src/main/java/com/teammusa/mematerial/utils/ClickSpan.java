package com.teammusa.mematerial.utils;

import android.content.Context;
import android.content.Intent;
import android.text.style.ClickableSpan;
import android.view.View;

public class ClickSpan extends ClickableSpan {
    private Context mContext;
    private Class where;

    public ClickSpan(Context context, Class whereTo) {
        this.mContext = context;
        this.where = whereTo;
    }

    public void onClick(View widget) {
        this.mContext.startActivity(new Intent(this.mContext, this.where));
    }
}
