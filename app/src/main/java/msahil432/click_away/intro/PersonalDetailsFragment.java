package msahil432.click_away.intro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import msahil432.click_away.R;

public class PersonalDetailsFragment extends Fragment {

    public PersonalDetailsFragment() { }

    TextView.OnEditorActionListener enterListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if(event == null || (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)){
                return true;
            }
            return false;
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.intro_fragment_personal_details, container, false);
        return v;
    }

    
}
