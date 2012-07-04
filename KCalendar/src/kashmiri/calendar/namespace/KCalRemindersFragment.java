package kashmiri.calendar.namespace;


import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class KCalRemindersFragment extends DialogFragment {
    static KCalFragment newInstance() {
        return new KCalFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
            View mainpage = (View)new MainPage(getActivity().getApplicationContext());
    	return mainpage;
    }
}