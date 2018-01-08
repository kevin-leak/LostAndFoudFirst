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

public class FragmentLost extends android.support.v4.app.Fragment{

    private SwipeRefreshLayout srlMessageOrGoodsLost;
    private RecyclerView rvMessageOrGoodsLost;
    LinearLayoutManager layoutManagerLost;
    private MembersAdapter itemAdapterLost;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragmentlost,container,false);

        srlMessageOrGoodsLost = view.findViewById(R.id.srlMessageOrGoodsLost);
        rvMessageOrGoodsLost = view.findViewById(R.id.rvMessageOrGoodsLost);

        layoutManagerLost = new LinearLayoutManager(getContext());

        int spacingInPixels = 8;

        rvMessageOrGoodsLost.setLayoutManager(layoutManagerLost);
        //rvMessageOrGoodsLost.addItemDecoration(new LCIMDividerItemDecoration(getApplicationContext()));
        rvMessageOrGoodsLost.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        itemAdapterLost = new MembersAdapter(R.layout.holder_all,MembersAdapter.LOST,getContext());
        rvMessageOrGoodsLost.setAdapter(itemAdapterLost);
        refreshMembers();
        srlMessageOrGoodsLost.post(new Runnable() {
            @Override
            public void run() {
                if (rvMessageOrGoodsLost.getChildCount() != 0){
                    srlMessageOrGoodsLost.setRefreshing(true);
                    refreshMembers();
                }else {
                    srlMessageOrGoodsLost.setRefreshing(false);
                    return;
                }
            }
        });

        srlMessageOrGoodsLost.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
        srlMessageOrGoodsLost.setRefreshing(false);
    }
}
