package io.github.leniumc.noteschool;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RankingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RankingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters

    private static final String SERVER_IP = "http://10.8.1.248/NoteSchool/";

    private LinearLayoutManager linearLayoutManager;
    private RankingAdapter adapter;

    private List<RankingData> dataList;

    public RankingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RankingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RankingFragment newInstance() {
        return new RankingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_ranking, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view_ranking);

        if (dataList == null) {
            dataList = new ArrayList<>();
        }

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RankingAdapter(getContext(), dataList);
        recyclerView.setAdapter(adapter);

        if (dataList.size() == 0) {
            loadData();
        }

        return rootView;
    }

    private void loadData() {
        // TODO: get 50 rankings
        int[] scores = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        for (int i = 0; i < scores.length; i++) {
            int score = scores[i];
            RankingData data = new RankingData("https://upload.wikimedia.org/wikipedia/commons/thumb/2/2d/Google-favicon-2015.png/150px-Google-favicon-2015.png",
                    "Name", score, i + 1);
            dataList.add(data);
        }
        adapter.notifyDataSetChanged();
    }
}
