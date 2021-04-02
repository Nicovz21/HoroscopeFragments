package com.hfad.horoscope;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
*/
public class HoroscopeDetail extends Fragment {
    private String starSignFact = "";
    public HoroscopeDetail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_horoscope_detail, container, false);
    }
    public void setHoroscopeFact(String starSignFact) {
        this.starSignFact= starSignFact;
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if(view != null && starSignFact != ""){
            TextView horoscopeFactView = (TextView) view.findViewById(R.id.textView_fragment);
            horoscopeFactView.setText(starSignFact);
            Log.i("textWentThrough", "fragmented");
         //   starSignFact.setText(HoroscopeHandler.horoscopes[starSignId]);

        }else{
            Log.i("textWentThrough", "fragmentFailed");
        }

    }
}