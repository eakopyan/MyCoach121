package com.thuasnelab.mycoach121;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SessionFragment.OnButtonClickListener} interface
 * to handle interaction events.
 * Use the {@link SessionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SessionFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TITLE = "title";
    private static final String ARG_ID = "id";
    private static final String ARG_IMAGE = "image";
    private final static String ARG_DATE = "date";

    private String mTitle;
    private int mId;
    private int mImage;
    private String mDate;
    private Context mContext;

    private ImageView sessionImage;

    private TextView sessionTitle;
    private TextView sessionDate;
    private TextView sessionFC;
    private TextView sessionDistance;
    private TextView sessionEnergy;

    private Button modButton;
    private Button delButton;

    private OnButtonClickListener mListener;

    public SessionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param session Session details to display.
     * @return A new instance of fragment SessionFragment.
     */
    public static SessionFragment newInstance(Session session) {
        SessionFragment fragment = new SessionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, session.getTitle());
        //args.putInt(ARG_ID, session.getId());
        args.putInt(ARG_IMAGE, session.getImageId());
        args.putString(ARG_DATE, session.getDate());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_TITLE);
            //mId = getArguments().getInt(ARG_ID);
            mImage = getArguments().getInt(ARG_IMAGE);
            mDate = getArguments().getString(ARG_DATE);
        }
        mContext = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View res = inflater.inflate(R.layout.fragment_session, container, false);

        sessionImage = res.findViewById(R.id.session_image);
        sessionTitle = res.findViewById(R.id.session_title);
        sessionDate = res.findViewById(R.id.session_date);
        sessionFC = res.findViewById(R.id.fc_moyenne);
        sessionDistance = res.findViewById(R.id.distance);
        sessionEnergy = res.findViewById(R.id.energie);

        modButton = res.findViewById(R.id.button_mod);
        delButton = res.findViewById(R.id.button_del);

        sessionImage.setImageResource(mImage);
        sessionTitle.setText(mTitle);
        sessionDate.setText(mDate);

        modButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClick(R.id.button_mod);
            }
        });

        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClick(R.id.button_del);
            }
        });

        return res;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnButtonClickListener) {
            mListener = (OnButtonClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
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
    public interface OnButtonClickListener {
        void onButtonClick(int buttonId);
    }
}
