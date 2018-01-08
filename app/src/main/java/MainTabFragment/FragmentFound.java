package MainTabFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lostandfoudfirst.CustomGoodsProvider;
import com.example.lostandfoudfirst.R;

import MyAdapter.MembersAdapter;
import MyView.SpacesItemDecoration;

/**
 * Created by fulxtaw on 2017/12/8.
 */

public class FragmentFound extends android.support.v4.app.Fragment {
    private SwipeRefreshLayout srlMessageOrGoodsFound;
    private RecyclerView rvMessageOrGoodsFound;
    LinearLayoutManager layoutManagerFound;
    private MembersAdapter itemAdapterFound;


    private TextView textView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragmentfound,container,false);

        srlMessageOrGoodsFound = view.findViewById(R.id.srlMessageOrGoodsFound);
        rvMessageOrGoodsFound = view.findViewById(R.id.rvMessageOrGoodsFound);

        layoutManagerFound = new LinearLayoutManager(getActivity());

        int spacingInPixels = 8;

        rvMessageOrGoodsFound.setLayoutManager(layoutManagerFound);
        //rvMessageOrGoodsFound.addItemDecoration(new LCIMDividerItemDecoration(getApplicationContext()));
        rvMessageOrGoodsFound.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        itemAdapterFound = new MembersAdapter(R.layout.holder_all,MembersAdapter.FOUND,getContext());
        rvMessageOrGoodsFound.setAdapter(itemAdapterFound);
        refreshMembers();
        srlMessageOrGoodsFound.post(new Runnable() {
            @Override
            public void run() {
                if (rvMessageOrGoodsFound.getChildCount() != 0){
                    srlMessageOrGoodsFound.setRefreshing(true);
                    refreshMembers();
                }else {
                    srlMessageOrGoodsFound.setRefreshing(false);
                    return;
                }
            }
        });
        srlMessageOrGoodsFound.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
        itemAdapterFound.setMemberList(CustomGoodsProvider.getInstance().getAllGoods());
        itemAdapterFound.notifyDataSetChanged();
        srlMessageOrGoodsFound.setRefreshing(false);
    }
}
