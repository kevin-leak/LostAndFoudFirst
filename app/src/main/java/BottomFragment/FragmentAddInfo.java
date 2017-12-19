package BottomFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lostandfoudfirst.R;

/**
 * Created by fulxtaw on 2017/12/9.
 */

public class FragmentAddInfo extends Fragment {
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_add,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView=(TextView)view.findViewById(R.id.tv_add);
        textView.setText("添加丢失和找到的物品信息");
        Log.d("AddFragment","______________CreatedAddInfo_______________");

        //

    }
}
