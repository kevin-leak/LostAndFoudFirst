package com.example.lostandfoudfirst.myView.fragment.BottomFragment.MainTabFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lostandfoudfirst.bean.CustomGoodsProvider;
import com.example.lostandfoudfirst.R;

import com.example.lostandfoudfirst.adapter.MembersAdapter;
import com.example.lostandfoudfirst.myView.SpacesItemDecoration;


/**
 * Created by fulxtaw on 2017/12/8.
 */

public class FragmentImportant extends android.support.v4.app.Fragment {

    private SwipeRefreshLayout srlMessageOrGoodsImportant;
    private RecyclerView rvMessageOrGoodsLostImportant;
    LinearLayoutManager layoutManagerLostImportant;
    private MembersAdapter itemAdapterLost;





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragmentimportant,container,false);

        srlMessageOrGoodsImportant = view.findViewById(R.id.srlMessageOrGoodsImportant);
        rvMessageOrGoodsLostImportant = view.findViewById(R.id.rvMessageOrGoodsImportant);

        layoutManagerLostImportant = new LinearLayoutManager(getContext());

        int spacingInPixels = 8;

        rvMessageOrGoodsLostImportant.setLayoutManager(layoutManagerLostImportant);
        //rvMessageOrGoodsLostImportant.addItemDecoration(new LCIMDividerItemDecoration(getApplicationContext()));
        rvMessageOrGoodsLostImportant.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        itemAdapterLost = new MembersAdapter(R.layout.holder_all,MembersAdapter.IMPORTANT,getContext());
        rvMessageOrGoodsLostImportant.setAdapter(itemAdapterLost);
        refreshMembers();
        srlMessageOrGoodsImportant.post(new Runnable() {
            @Override
            public void run() {
                if (rvMessageOrGoodsLostImportant.getChildCount() != 0){
                    srlMessageOrGoodsImportant.setRefreshing(true);
                    refreshMembers();
                }else {

                }
            }
        });
        srlMessageOrGoodsImportant.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshMembers();
            }
        });

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        refreshMembers();
    }

    private void refreshMembers() {
        itemAdapterLost.setMemberList(CustomGoodsProvider.getInstance().getAllGoods());
        itemAdapterLost.notifyDataSetChanged();
        srlMessageOrGoodsImportant.setRefreshing(false);
    }
}
