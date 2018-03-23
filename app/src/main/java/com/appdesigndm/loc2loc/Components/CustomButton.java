package com.appdesigndm.loc2loc.Components;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.appdesigndm.loc2loc.R;

public class CustomButton extends AppCompatButton {

    public CustomButton(Context context) {
        super(context);

        init();
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        setTextColor(getResources().getColor(R.color.button_text_color_light));
        setBackground(getResources().getDrawable(R.drawable.button_state_colors));
    }
}
