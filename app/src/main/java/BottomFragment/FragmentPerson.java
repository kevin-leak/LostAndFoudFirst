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

public class FragmentPerson extends Fragment {

    private TextView textView;
    private TextView tvUserInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_person,null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        tvUserInfo = view.findViewById(R.id.tvUserInfo);
        tvUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2017/12/20 建立个人信息
            }
        });
        super.onViewCreated(view, savedInstanceState);

    }
}
