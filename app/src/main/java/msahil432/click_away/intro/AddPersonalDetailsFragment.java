package msahil432.click_away.intro;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import msahil432.click_away.R;
import msahil432.click_away.mainActivity.User;
import msahil432.click_away.databinding.FragmentPersonalDetailsBinding;

import static msahil432.click_away.extras.MyApplication.Report;

public class AddPersonalDetailsFragment extends Fragment {

    public AddPersonalDetailsFragment() { }

    private FragmentPersonalDetailsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_personal_details, container, false);
        EventBus.getDefault().register(this);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.setUser(User.Builder(getContext()));
    }

    @Subscribe
    public void fragmentSwipe(Double d){
        if(d.equals(Double.valueOf("1504.2014"))){
            User user = binding.getUser();
            if(user!=null){
                user.saveUser(getContext());
                Report("PersonalDetailsFrag", user.toString());
                return;
            }
            Report("PersonalDetailsFrag", "Null User!");
        }
    }
}
