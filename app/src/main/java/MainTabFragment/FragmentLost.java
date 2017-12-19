package MainTabFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lostandfoudfirst.R;

/**
 * Created by fulxtaw on 2017/12/8.
 */

public class FragmentLost extends android.support.v4.app.Fragment {
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragmentlost,container,false);
        Log.d("My Fragment","__________________OnCreatedView_______________");
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
        textView=(TextView)view.findViewById(R.id.text_lostnews);
        textView.setText("失物");
        Log.d("My Fragment","__________________OnViewCrewated_______________");
    }
}
