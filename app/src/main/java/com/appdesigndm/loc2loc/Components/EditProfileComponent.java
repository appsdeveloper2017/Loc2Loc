package com.appdesigndm.loc2loc.Components;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appdesigndm.loc2loc.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditProfileComponent extends ConstraintLayout {

    @BindView(R.id.edit_profile_component_title)
    TextView tvTitle;

    @BindView(R.id.image_icon_action)
    ImageView ivIcon;

    @BindView(R.id.edit_profile_component_text)
    EditText etText;

    public EditProfileComponent(@NonNull Context context) {
        super(context);
        init(context);
    }

    public EditProfileComponent(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EditProfileComponent(@NonNull Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.edit_profile_component, this);

        ButterKnife.bind(this);
    }

    public void setTitle(String title) {
        this.tvTitle.setText(title);
    }

    public void setIcon(int icon) {
        this.ivIcon.setImageDrawable(getResources().getDrawable(icon));
    }

    public String getText() {
        return etText.getText().toString();
    }

    public void setText(String text) {
        this.etText.setText(text);
    }
}
