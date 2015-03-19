package com.esbr.feirafacilsmartphone.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewHolder {
	private TextView title;
    private ImageView icon;
    private LinearLayout colorLinear;

    public ViewHolder(final TextView t, final ImageView ic, final LinearLayout cl) {
        this.setTitle(t);
        this.setIcon(ic);
        this.setColorLinear(cl);
    }

    /**
     * Get a value of tittle.
     * 
     * @return TextView tv
     */
    public final TextView getTitle() {
        return title;
    }

    /**
     * Set a value of title.
     * 
     * @param t t
     */
    public final void setTitle(final TextView t) {
        this.title = t;
    }

    /**
     * Get a value of icon.
     * 
     * @return ImageView iv
     */
    public final ImageView getIcon() {
        return icon;
    }

    /**
     * Set a value of icon.
     * 
     * @param ic ic
     */
    public final void setIcon(final ImageView ic) {
        this.icon = ic;
    }

    /**
     * Get a value of colorLinear.
     * 
     * @return LinerLayout ll
     */
    public final LinearLayout getColorLinear() {
        return colorLinear;
    }

    /**
     * Set a value of colorLinear.
     * 
     * @param cl cl
     */
    public final void setColorLinear(final LinearLayout cl) {
        this.colorLinear = cl;
    }

}

