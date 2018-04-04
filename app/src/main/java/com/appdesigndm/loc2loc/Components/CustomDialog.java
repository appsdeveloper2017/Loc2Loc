package com.appdesigndm.loc2loc.Components;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
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

    private String mTitle;
    private Severity mSeverity;
    private String mDescription;
    private String mLeftButtonText;
    private View.OnClickListener mLeftButtonListener;
    private String mRightButtonText;
    private View.OnClickListener mRightButtonListener;

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

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }

    private void loadView() {
        setCancelable(false);
        setupTitle(getTitle());
        setupSeverity(getSeverity());
        setupDescription(getDescription());
        setupLeftButton(getLeftButtonText(), getLeftButtonListener());
        setupRightButton(getRightButtonText(), getRightButtonListener());
    }

    private void setupTitle(String text) {
        if (text != null) {
            tvTitle.setText(text);
            tvTitle.setVisibility(View.VISIBLE);
            separator.setVisibility(View.VISIBLE);
        } else {
            tvDescription.setVisibility(View.GONE);
            separator.setVisibility(View.GONE);
        }
    }

    public void setupSeverity(Severity severity) {
        if (severity != null) {
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
        } else {
            severityIcon.setVisibility(View.GONE);
        }
    }

    public void setupDescription(String text) {
        if (text != null) {
            tvDescription.setText(text);
            tvDescription.setVisibility(View.VISIBLE);
        } else {
            tvDescription.setVisibility(View.GONE);
            separator.setVisibility(View.GONE);
        }
    }

    public void setupLeftButton(String text, View.OnClickListener listener) {
        if (text != null) {
            leftButton.setText(text);
            leftButton.setVisibility(View.VISIBLE);
            if (listener != null) {
                leftButton.setOnClickListener(listener);
            } else {
                leftButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });
            }
        } else {
            leftButton.setVisibility(View.GONE);
        }
    }

    public void setupRightButton(String text, View.OnClickListener listener) {
        if (text != null) {
            rightButton.setText(text);
            rightButton.setVisibility(View.VISIBLE);
            if (listener != null) {
                rightButton.setOnClickListener(listener);
            } else {
                rightButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });
            }
        } else {
            rightButton.setVisibility(View.GONE);
        }
    }

    public String getTitle() {
        return mTitle;
    }

    public CustomDialog setTitle(String title) {
        mTitle = title;

        return this;
    }

    public Severity getSeverity() {
        return mSeverity;
    }

    public CustomDialog setSeverity(Severity severity) {
        mSeverity = severity;

        return this;
    }

    public String getDescription() {
        return mDescription;
    }

    public CustomDialog setDescription(String description) {
        mDescription = description;

        return this;
    }

    public String getLeftButtonText() {
        return mLeftButtonText;
    }

    public CustomDialog setLeftButtonText(String leftButtonText) {
        mLeftButtonText = leftButtonText;

        return this;
    }

    public View.OnClickListener getLeftButtonListener() {
        return mLeftButtonListener;
    }

    public CustomDialog setLeftButtonListener(View.OnClickListener leftButtonListener) {
        mLeftButtonListener = leftButtonListener;

        return this;
    }

    public String getRightButtonText() {
        return mRightButtonText;
    }

    public CustomDialog setRightButtonText(String rightButtonText) {
        mRightButtonText = rightButtonText;

        return this;
    }

    public View.OnClickListener getRightButtonListener() {
        return mRightButtonListener;
    }

    public CustomDialog setRightButtonListener(View.OnClickListener rightButtonListener) {
        mRightButtonListener = rightButtonListener;

        return this;
    }
}
