package com.appdesigndm.loc2loc.Components;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.appdesigndm.loc2loc.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomDialog extends DialogFragment {

    @BindView(R.id.dialog_title)
    TextView tvTitle;

    @BindView(R.id.dialog_title_separator)
    ImageView separator;

    @BindView(R.id.dialog_severity_icon)
    ImageView severityIcon;

    @BindView(R.id.dialog_description)
    TextView tvDescription;

    @BindView(R.id.dialog_left_button)
    CustomButton leftButton;

    @BindView(R.id.dialog_right_button)
    CustomButton rightButton;

    private String title;
    private int icon;
    private String description;
    private String leftButtonText;
    private String leftButtonListener;
    private String rightButtonText;
    private String rightButtonListener;

    public enum Severity {ERROR, WARNING, INFO}

    public CustomDialog() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_dialog, container);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE); // Delete native title from dialog

        ButterKnife.bind(this, view);
        loadView();
        return view;
    }

    private void loadView() {
        setCancelable(false);
        setTitle("Un titulo");
//        setSeverity(Severity.ERROR);
        setDescription("Una descripcion");
        setLeftButton("Left", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setRightButton("Right", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public CustomDialog setTitle(String text) {
        tvTitle.setText(text);
        tvTitle.setVisibility(View.VISIBLE);
        separator.setVisibility(View.VISIBLE);

        return this;
    }

    public CustomDialog setSeverity(Severity severity) {
        switch (severity) {
            case ERROR:
                severityIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_error));
                break;
            case WARNING:
                severityIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_warning));
                break;
            case INFO:
                severityIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_info));
                break;
            default:
                break;
        }
        severityIcon.setVisibility(View.VISIBLE);

        return this;
    }

    public CustomDialog setDescription(String text) {
        tvDescription.setText(text);
        tvDescription.setVisibility(View.VISIBLE);

        return this;
    }

    public CustomDialog setLeftButton(String text, View.OnClickListener listener) {
        leftButton.setText(text);
        leftButton.setVisibility(View.VISIBLE);
        leftButton.setOnClickListener(listener);

        return this;
    }

    public CustomDialog setRightButton(String text, View.OnClickListener listener) {
        rightButton.setText(text);
        rightButton.setVisibility(View.VISIBLE);
        rightButton.setOnClickListener(listener);

        return this;
    }

}
