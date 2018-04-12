package com.appdesigndm.loc2loc.Components;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.appdesigndm.loc2loc.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewProfileComponent extends ConstraintLayout {

    @BindView(R.id.view_profile_component_title)
    TextView tvTitle;

    @BindView(R.id.image_icon_action)
    ImageView ivIcon;

    @BindView(R.id.view_profile_component_text)
    TextView tvText;

    @BindView(R.id.edit_profile_component_text)
    EditText etText;

    @BindView(R.id.image_edit_action)
    ImageView ivActionIcon;

    public ViewProfileComponent(Context context) {
        super(context);
        init(context);
    }

    public ViewProfileComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ViewProfileComponent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_profile_component, this);

        ButterKnife.bind(this);
    }

    public void setTitle(String title) {
        this.tvTitle.setText(title);
    }

    public void setIcon(int icon) {
        this.ivIcon.setImageDrawable(getResources().getDrawable(icon));
    }

    public void setText(String text) {
        this.tvText.setText(text);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        ivActionIcon.setOnClickListener(onClickListener);
    }

    public void ofuscateText(){
        tvText.setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
    }
}
