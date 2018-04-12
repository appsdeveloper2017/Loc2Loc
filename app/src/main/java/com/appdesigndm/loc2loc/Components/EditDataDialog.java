package com.appdesigndm.loc2loc.Components;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.appdesigndm.loc2loc.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditDataDialog extends DialogFragment {

    @BindView(R.id.dialog_title)
    TextView tvTitle;

    @BindView(R.id.dialog_title_separator)
    ImageView separator;

    @BindView(R.id.dialog_description)
    EditText etDescription;

    @BindView(R.id.dialog_left_button)
    CustomButton leftButton;

    @BindView(R.id.dialog_right_button)
    CustomButton rightButton;

    private View view;
    private String mTitle;
    private String mDescription;
    private String mLeftButtonText;
    private View.OnClickListener mLeftButtonListener;
    private String mRightButtonText;
    private View.OnClickListener mRightButtonListener;

    public EditDataDialog() {
        // Required empty constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.edit_data_dialog, container);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE); // Delete native title from dialog

        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        loadView();
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }

    private void loadView() {
        setupView();
        setupTitle(getTitle());
        setupDescription(getDescription());
        setupLeftButton(getLeftButtonText(), getLeftButtonListener());
        setupRightButton(getRightButtonText(), getRightButtonListener());
    }

    // The setup view must be in onResume method
    private void setupView() {
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = getScreenWidth();
        window.setAttributes(params);
        window.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_rounded_white_filled));
    }

    private void setupTitle(String text) {
        if (text != null) {
            tvTitle.setText(text);
            tvTitle.setVisibility(View.VISIBLE);
            separator.setVisibility(View.VISIBLE);
        } else {
            etDescription.setVisibility(View.GONE);
            separator.setVisibility(View.GONE);
        }
    }

    private void setupDescription(String text) {
        if (text != null) {
            etDescription.setText(text);
            etDescription.setVisibility(View.VISIBLE);
        } else {
            etDescription.setVisibility(View.GONE);
            separator.setVisibility(View.GONE);
        }
        etDescription.requestFocus();
        etDescription.setSelection(text.length());
    }

    private void setupLeftButton(String text, View.OnClickListener listener) {
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

    private void setupRightButton(String text, View.OnClickListener listener) {
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

    public EditDataDialog setTitle(String title) {
        mTitle = title;

        return this;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getDescriptionContent() {
        return etDescription.getText().toString();
    }

    public EditDataDialog setDescription(String description) {
        mDescription = description;

        return this;
    }

    public String getLeftButtonText() {
        return mLeftButtonText;
    }

    public EditDataDialog setLeftButtonText(String leftButtonText) {
        mLeftButtonText = leftButtonText;

        return this;
    }

    public View.OnClickListener getLeftButtonListener() {
        return mLeftButtonListener;
    }

    public EditDataDialog setLeftButtonListener(View.OnClickListener leftButtonListener) {
        mLeftButtonListener = leftButtonListener;

        return this;
    }

    public String getRightButtonText() {
        return mRightButtonText;
    }

    public EditDataDialog setRightButtonText(String rightButtonText) {
        mRightButtonText = rightButtonText;

        return this;
    }

    public View.OnClickListener getRightButtonListener() {
        return mRightButtonListener;
    }

    public EditDataDialog setRightButtonListener(View.OnClickListener rightButtonListener) {
        mRightButtonListener = rightButtonListener;

        return this;
    }

    private int getScreenWidth() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = ((size.x / 4) * 3);
        return width;
    }
}
