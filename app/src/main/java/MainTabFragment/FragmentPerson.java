package MainTabFragment;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_person,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        textView=(TextView)view.findViewById(R.id.tv_person);
        textView.setText("个人信息");
        Log.d("PersonFragment","______________CreatedPerson_______________");
        super.onViewCreated(view, savedInstanceState);

    }
}
