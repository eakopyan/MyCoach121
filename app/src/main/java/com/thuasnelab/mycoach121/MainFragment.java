package com.thuasnelab.mycoach121;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_COUNT = "count";

    private RecyclerView recyclerView;
    private List<Sport> sportList = new ArrayList<>();
    private Context mContext;

    private OnFragmentInteractionListener mListener;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param nbSports Number of sports to display.
     * @return A new instance of fragment MainFragment.
     */
    public static MainFragment newInstance(int nbSports) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COUNT, nbSports);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        fillSportList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View res = inflater.inflate(R.layout.fragment_main, container, false);

        // Initialisation de l'affichage de la liste de sports
        recyclerView = res.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(res.getContext()));
        recyclerView.setAdapter(new MySportRecyclerViewAdapter(sportList, mListener));

        return res;
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
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void fillSportList() {
        sportList.add(new Sport("roller_hockey", 2));
        sportList.add(new Sport("velo_route", 19));
        sportList.add(new Sport("roller_vitesse", 34));
        sportList.add(new Sport("danse", 12));
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
        void onSportClick(Sport sport);
    }

    public void setOnFragmentInteractionListener(OnFragmentInteractionListener listener) {
        this.mListener = listener;
    }


    /**********************************************************************************************
     *
     *                             ENCLOSED CLASS SPORTVIEW HOLDER
     *
     *********************************************************************************************/
    public class SportViewHolder extends RecyclerView.ViewHolder {

        // Widgets de sport_cards.xml
        private ImageView imageView;
        private TextView sportTitle;
        private TextView sportCount;

        // itemView est la vue correspondant à 1 cellule
        public SportViewHolder(@NonNull final View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.sport_image);
            sportTitle = itemView.findViewById(R.id.sport_title);
            sportCount = itemView.findViewById(R.id.sport_count);
        }

        // remplir la cellule en fonction d'une Session
        public void bind(final Sport sport, int position, final OnFragmentInteractionListener listener) {
            sport.setId(position);

            int imageId = sport.getImageId();
            if(imageId != -1)
                imageView.setImageResource(imageId);
            switch(sport.getTitle()) {
                case "roller_hockey":
                    sportTitle.setText("Roller hockey");
                    break;
                case "velo_route":
                    sportTitle.setText("Vélo sur route");
                    break;
                case "roller_vitesse":
                    sportTitle.setText("Roller de vitesse");
                    break;
                case "danse":
                    sportTitle.setText("Danse hip-hop");
                    break;
                default: break;
            }
            sportCount.setText(sport.getCount() + " séance(s)");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onSportClick(sport);
                }
            });

        }
    }


    /**********************************************************************************************
     *
     *                             ENCLOSED CLASS RECYCLERVIEW ADAPTER
     *
     *********************************************************************************************/
    public class MySportRecyclerViewAdapter extends RecyclerView.Adapter<SportViewHolder> {

        private List<Sport> sportList;
        private OnFragmentInteractionListener listener;

        public MySportRecyclerViewAdapter(List<Sport> list, OnFragmentInteractionListener listener) {
            this.sportList = list;
            this.listener = listener;
        }

        // Création des viewHolder en fonction de la vue à inflater
        @NonNull
        @Override
        public SportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.sport_cards, parent, false);
            return new SportViewHolder(view);
        }

        // Remplissage de la cellule avec le contenu du sport
        @Override
        public void onBindViewHolder(@NonNull SportViewHolder holder, int position) {
            Sport sport = sportList.get(position);
            holder.bind(sport, position, listener);
        }

        @Override
        public int getItemCount() {
            return sportList.size();
        }
    }
}
