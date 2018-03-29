package com.appdesigndm.loc2loc.Components;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.appdesigndm.loc2loc.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomDialogBuilder extends DialogFragment {

    @BindView(R.id.dialog_title)
    TextView title;

    @BindView(R.id.dialog_title_separator)
    ImageView separator;

    @BindView(R.id.dialog_severity_icon)
    ImageView severityIcon;

    @BindView(R.id.dialog_description)
    TextView description;

    @BindView(R.id.dialog_left_button)
    CustomButton leftButton;

    @BindView(R.id.dialog_right_button)
    CustomButton rightButton;

    Context mContext;
    public enum Severity {ERROR, WARNING, INFO};

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        setContentView(R.layout.custom_dialog);

        ButterKnife.bind(this);
    }

    public CustomDialogBuilder setTitle(String text) {
        title.setText(text);
        title.setVisibility(View.VISIBLE);
        separator.setVisibility(View.VISIBLE);

        return this;
    }

    public CustomDialogBuilder setSeverity(Severity severity) {
        switch (severity)  {
            case ERROR:
                severityIcon.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_error));
                break;
            case WARNING:
                severityIcon.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_warning));
                break;
            case INFO:
                severityIcon.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_info));
                break;
            default:
                break;
        }
        severityIcon.setVisibility(View.VISIBLE);

        return this;
    }

    public CustomDialogBuilder setDescription(String text) {
        description.setText(text);
        description.setVisibility(View.VISIBLE);

        return this;
    }

    public CustomDialogBuilder setLeftButton(String text, View.OnClickListener listener) {
        leftButton.setText(text);
        leftButton.setVisibility(View.VISIBLE);
        leftButton.setOnClickListener(listener);

        return this;
    }

    public CustomDialogBuilder setRightButton(String text, View.OnClickListener listener) {
        rightButton.setText(text);
        rightButton.setVisibility(View.VISIBLE);
        rightButton.setOnClickListener(listener);

        return this;
    }

}
