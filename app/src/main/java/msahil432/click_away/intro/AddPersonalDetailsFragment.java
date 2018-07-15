package msahil432.click_away.intro;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import msahil432.click_away.R;
import msahil432.click_away.mainActivity.User;
import msahil432.click_away.databinding.FragmentPersonalDetailsBinding;

public class AddPersonalDetailsFragment extends Fragment {

    public AddPersonalDetailsFragment() { }

    private FragmentPersonalDetailsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_personal_details, container, false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.setUser(User.Builder(getContext()));
    }

    @Override
    public void onPause() {
        User user = binding.getUser();
        if(user!=null)
            user.saveUser(getContext());
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        User user = binding.getUser();
        if(user!=null)
            user.saveUser(getContext());
        binding.unbind();
        super.onDestroyView();
    }
}
