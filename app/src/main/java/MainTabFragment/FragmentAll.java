package MainTabFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lostandfoudfirst.CustomGoodsProvider;
import com.example.lostandfoudfirst.R;

import MyAdapter.MembersAdapter;
import MyView.SpacesItemDecoration;

/**
 * Created by fulxtaw on 2017/12/8.
 */

public class FragmentAll extends android.support.v4.app.Fragment {



    private SwipeRefreshLayout srlMessageOrGoodsAll;
    private RecyclerView rvMessageOrGoodsAll;
    LinearLayoutManager layoutManagerAll;
    private MembersAdapter itemAdapterAll;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragmentall,container,false);

        srlMessageOrGoodsAll = view.findViewById(R.id.srlMessageOrGoodsAll);
        rvMessageOrGoodsAll = view.findViewById(R.id.rvMessageOrGoodsAll);

        layoutManagerAll = new LinearLayoutManager(getContext());

        int spacingInPixels = 8;

        rvMessageOrGoodsAll.setLayoutManager(layoutManagerAll);
        //rvMessageOrGoodsAll.addItemDecoration(new LCIMDividerItemDecoration(getApplicationContext()));
        rvMessageOrGoodsAll.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        itemAdapterAll = new MembersAdapter(R.layout.holder_all,MembersAdapter.ALL,getContext());
        rvMessageOrGoodsAll.setAdapter(itemAdapterAll);
        srlMessageOrGoodsAll.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
        itemAdapterAll.setMemberList(CustomGoodsProvider.getInstance().getAllGoods());
        itemAdapterAll.notifyDataSetChanged();
        srlMessageOrGoodsAll.setRefreshing(false);
    }
}
