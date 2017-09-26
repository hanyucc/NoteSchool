package io.github.leniumc.noteschool;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private CustomAdapter adapter;
    private FloatingActionButton floatingActionButton;

    private List<PostData> dataList;
    private int loadThreshold = 3;
    private int[] favPosts, upvotePosts;

    // TODO: Rename and change types of parameters


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        return new HomeFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_home);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.add_button);

        if (dataList == null) {
            dataList = new ArrayList<>();
        }

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new CustomAdapter(getContext(), dataList);
        recyclerView.setAdapter(adapter);

        if (dataList.size() == 0) {
            loadData(dataList.size());
        }

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                Log.d("complete", String.valueOf(linearLayoutManager.findLastCompletelyVisibleItemPosition()));
                Log.d("size", String.valueOf(dataList.size()));
                if (dy > 0 && floatingActionButton.getVisibility() == View.VISIBLE) {
                    floatingActionButton.hide();
                } else if (dy < 0 && floatingActionButton.getVisibility() != View.VISIBLE) {
                    floatingActionButton.show();
                }
                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() ==
                        dataList.size() - loadThreshold) {
                    loadData(dataList.size());
                }
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PublishActivity.class);
                getContext().startActivity(intent);
            }
        });

        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clearRecyclerView();
            }
        });

        return rootView;
    }

    private void loadData(int id) {
        // TODO: get 10 posts
        for (int i = id; i < id + 10; i++) {
            String repeated = new String(new char[100]).replace("\0", String.valueOf(i));
            String[] default_url = new String[1];
            default_url[0] = "nothing";
            PostData data = new PostData(
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2d/Google-favicon-2015.png/150px-Google-favicon-2015.png",
                    "Name", "Grade", repeated, default_url, 0, i);
            dataList.add(data);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.top_menu_home, menu);
    }

    public void clearRecyclerView() {
        int size = dataList.size();
        dataList.clear();
        adapter.notifyItemRangeRemoved(0, size);
        loadData(0);
        swipeRefreshLayout.setRefreshing(false);
    }
}