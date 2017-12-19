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

public class FragmentAll extends android.support.v4.app.Fragment{

    private TextView textView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragmentall,container,false);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        textView=(TextView) view.findViewById(R.id.text_allnews);
        Log.d("","________________MAinFragmentOFALL__________________");
    }
}
