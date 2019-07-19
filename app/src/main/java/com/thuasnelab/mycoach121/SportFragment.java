package com.thuasnelab.mycoach121;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SportFragment.OnSessionClickListener} interface
 * to handle interaction events.
 * Use the {@link SportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SportFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TITLE = "title";
    private static final String ARG_COUNT = "count";
    private static final String ARG_ID = "id";

    private RecyclerView recyclerView;
    private List<Session> sessionList = new ArrayList<>();

    private String mTitle;
    private int mCount;
    private int mId;
    private Context mContext;

    private OnSessionClickListener mListener;

    public SportFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param sport Sport sessions to display.
     * @return A new instance of fragment SportFragment.
     */
    public static SportFragment newInstance(Sport sport) {
        SportFragment fragment = new SportFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, sport.getTitle());
        args.putInt(ARG_COUNT, sport.getCount());
        args.putInt(ARG_ID, sport.getId());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_TITLE);
            mCount = getArguments().getInt(ARG_COUNT);
            mId = getArguments().getInt(ARG_ID);
        }
        mContext = getContext();

        Session test = new Session("17/07/2019", mTitle, "Séance 4", 31.4, 152, "1:56");
        Session test2 = new Session("18/07/2019", mTitle, "Séance 4", 31.4, 152, "1:56");
        boolean working;
        long res;

        DAOSession sessionDb = new DAOSession(mContext);
        working = sessionDb.open();
        res = sessionDb.addSessionToDb(test);

        //sessionDb.addSessionToDb(test2);
        //sessionDb.removeSessionFromDb(test.getId());
        //sessionDb.modifySessionInDb(test.getId(), test2);
        //working = sessionDb.isInDb(test.getId());
        Cursor sessions = sessionDb.getAllSessionsInDb();

        Toast.makeText(mContext,
                //Boolean.toString(working),
                //sessions.getColumnName(0),
                Long.toString(res),
                Toast.LENGTH_LONG)
                .show();


        sessionDb.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View res =  inflater.inflate(R.layout.fragment_sport, container, false);
        recyclerView = res.findViewById(R.id.session_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(res.getContext()));
        recyclerView.setAdapter(new SportFragment.MySessionRecyclerViewAdapter(sessionList, mListener));

        FloatingActionButton fab = res.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   mListener.onAddSession(mTitle);
               }
           }
        );

        return res;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSessionClickListener) {
            mListener = (OnSessionClickListener) context;
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


    public void fillSessionList() {
        sessionList.add(new Session("17/07/2019", mTitle, "Séance 4", 31.4, 152, "1:56"));
        /**sessionList.add(new Session("12/06/2019", mTitle, "Séance 3", 0, 164, "1:43"));
        sessionList.add(new Session("09/06/2019", mTitle, "Séance 2", 44.6, 136, "2:20"));
        sessionList.add(new Session("31/05/2019", mTitle, "Séance 1", 27.8, 148, "1:07"));*/


        /**
        while (sessions.moveToNext()) {
            Session s = new Session(
                    sessions.getString(0),
                    sessions.getString(1),
                    sessions.getString(2),
                    sessions.getDouble(3),
                    sessions.getInt(4),
                    sessions.getString(5));
            s.setEnergy(sessions.getInt(6));
            sessionList.add(s);
        }*/

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
    public interface OnSessionClickListener {
        void onSessionClick(Session session);
        void onAddSession(String sportTitle);
    }

    public void setOnSessionClickListener(OnSessionClickListener listener) {
        this.mListener = listener;
    }

    /**********************************************************************************************
     *
     *                             ENCLOSED CLASS SESSIONVIEW HOLDER
     *
     *********************************************************************************************/
    public class SessionViewHolder extends RecyclerView.ViewHolder {

        // Widgets de session_cards.xml
        private ImageView imageView;
        private TextView sessionTitle;
        private TextView sessionDate;

        // itemView est la vue correspondant à 1 cellule
        public SessionViewHolder(@NonNull final View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.session_image);
            sessionTitle = itemView.findViewById(R.id.session_title);
            sessionDate = itemView.findViewById(R.id.session_date);
        }

        // remplir la cellule en fonction d'une Session
        public void bind(final Session session, int position, final OnSessionClickListener listener) {
            //session.setId(position);
            sessionTitle.setText(session.getTitle());
            sessionDate.setText(session.getDate());

            int imageId = session.getImageId();
            if(imageId != -1)
                imageView.setImageResource(imageId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onSessionClick(session);
                }
            });

        }
    }

    /**********************************************************************************************
     *
     *                             ENCLOSED CLASS RECYCLERVIEW ADAPTER
     *
     *********************************************************************************************/
    public class MySessionRecyclerViewAdapter extends RecyclerView.Adapter<SessionViewHolder> {

        private List<Session> sessionList;
        private OnSessionClickListener listener;

        public MySessionRecyclerViewAdapter(List<Session> list, OnSessionClickListener listener) {
            this.sessionList = list;
            this.listener = listener;
        }

        // Création des viewHolder en fonction de la vue à inflater
        @Override
        public SessionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.session_cards, parent, false);
            return new SessionViewHolder(view);
        }

        // Remplissage de la cellule avec le contenu de la session
        @Override
        public void onBindViewHolder(@NonNull SessionViewHolder holder, int position) {
            Session session = sessionList.get(position);
            holder.bind(session, position, listener);
        }

        @Override
        public int getItemCount() {
            return sessionList.size();
        }
    }
}
