package com.appdesigndm.loc2loc.MenuOptionsFloatBar;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appdesigndm.loc2loc.Models.UserModel;
import com.appdesigndm.loc2loc.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentLinkUser extends Fragment {

    //Title and input edit text;
    @BindView(R.id.tuser)
    TextView title_link_user;

    @BindView(R.id.buscar_link)
    EditText inputlink;

    @BindView(R.id.linear_link)
    LinearLayout layout_link_visivility;

    @BindView(R.id.separator_line)
    View line;

    private static final String EMAIL = "EMAIL";

    // TODO: Rename and change types of parameters
    private String emailUser;
    private boolean iftransition = false;

    private OnFragmentInteractionListener mListener;


    public FragmentLinkUser() {
        // Required empty public constructor
    }

    public static FragmentLinkUser newInstance(String emailUser) {
        FragmentLinkUser fragment = new FragmentLinkUser();
        Bundle args = new Bundle();
        args.putString(EMAIL, emailUser);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            emailUser = getArguments().getString(EMAIL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_link_user, container, false);
        ButterKnife.bind(this, view);

        ArrayList<UserModel> lista = new ArrayList<>(5);
        lista.add(new UserModel("alexito", "alex", "alex@alex.com", null));
        lista.add(new UserModel("bea","beatriz","bea@bea.com",null));
        lista.add(new UserModel("alexito", "alex", "alex@alex.com", null));
        lista.add(new UserModel("bea","beatriz","bea@bea.com",null));
        lista.add(new UserModel("alexito", "alex", "alex@alex.com", null));

        openAnimationLayout();

        inputlink.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
            if (!iftransition){
                closeAnimationLayout();
            }
            }
        });


        inputlink.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchUsers();
                }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    private void searchUsers() {
        emailUser = String.valueOf(inputlink.getText());
    }

    private void openAnimationLayout() {

        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.title_transition_open);
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                title_link_user.setVisibility(View.VISIBLE);
                line.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        layout_link_visivility.startAnimation(animation);
    }

    private void closeAnimationLayout() {

        iftransition = true;

        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.title_transition_close);
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                title_link_user.setVisibility(View.GONE);
                line.setVisibility(View.GONE);

                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        /*width*/ ViewGroup.LayoutParams.MATCH_PARENT,
                        /*height*/ ViewGroup.LayoutParams.WRAP_CONTENT
                );
                layout_link_visivility.setLayoutParams(param);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        title_link_user.startAnimation(animation);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
