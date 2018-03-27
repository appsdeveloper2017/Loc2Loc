package com.appdesigndm.loc2loc.Models;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.appdesigndm.loc2loc.R;

public class ErrorModel {

    private Context context;
    private String title;
    private String description;
    private String positiveButton;
    private String negativeButton;
    private DialogInterface.OnClickListener positiveListener;
    private DialogInterface.OnClickListener negativeListener;

    public ErrorModel(Context context) {
        this.context = context;
    }

    public String getTitle() {
        return title;
    }

    public ErrorModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ErrorModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPositiveButton() {
        return positiveButton;
    }

    public ErrorModel setPositiveButton(String positiveButton) {
        this.positiveButton = positiveButton;
        return this;
    }

    public String getNegativeButton() {
        return negativeButton;
    }

    public ErrorModel setNegativeButton(String negativeButton) {
        this.negativeButton = negativeButton;
        return this;
    }

    public DialogInterface.OnClickListener getPositiveListener() {
        return positiveListener;
    }

    public ErrorModel setPositiveListener(DialogInterface.OnClickListener positiveListener) {
        this.positiveListener = positiveListener;
        return this;
    }

    public DialogInterface.OnClickListener getNegativeListener() {
        return negativeListener;
    }

    public ErrorModel setNegativeListener(DialogInterface.OnClickListener negativeListener) {
        this.negativeListener = negativeListener;
        return this;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle((title == null) ? context.getResources().getString(R.string.generic_error_title) : getTitle())
                .setMessage((description == null) ? context.getResources().getString(R.string.generic_error_descritpion) : description)
                .setPositiveButton((positiveButton == null) ? context.getResources().getString(R.string.generic_error_positive_button) : positiveButton,
                        (positiveListener))
                .setNegativeButton((negativeButton == null) ? context.getResources().getString(R.string.generic_error_negative) : negativeButton,
                        (negativeListener))
                .create()
                .show();
    }
}
