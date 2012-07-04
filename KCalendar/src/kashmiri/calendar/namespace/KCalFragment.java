package kashmiri.calendar.namespace;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class KCalFragment extends DialogFragment {
    static KCalFragment newInstance() {
        return new KCalFragment();
    }
    
    
    static ViewFlipper getFlipper() {
        return flipper;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    		flipper = new ViewFlipper(getActivity().getApplicationContext());
    		MainPage mainpage = new MainPage(getActivity().getApplicationContext());
    		flipper.addView(mainpage);
    		//flipper.setBackgroundColor(0xfffbff71);
    		return flipper;
    }
    
    
    
	public static ViewFlipper		flipper;
}