package com.appdesigndm.loc2loc.Components;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.appdesigndm.loc2loc.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfilePhotoComponent extends ConstraintLayout {

    @BindView(R.id.iv_profile_photo)
    ImageView photo;

    @BindView(R.id.edit_profile_photo_image)
    ImageView editIcon;

    public ProfilePhotoComponent(Context context) {
        super(context);
        init(context);
    }

    public ProfilePhotoComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ProfilePhotoComponent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.profile_photo_component, this);

        ButterKnife.bind(this);
    }

    public void setPhoto(int imageId) {
        photo.setImageDrawable(getResources().getDrawable(imageId));
    }

    public void setEditable(boolean editable) {
        if (editable) {
            editIcon.setVisibility(VISIBLE);
        } else {
            editIcon.setVisibility(GONE);
        }
    }
}
