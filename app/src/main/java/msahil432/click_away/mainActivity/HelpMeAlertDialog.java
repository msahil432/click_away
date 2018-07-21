package msahil432.click_away.mainActivity;

import android.app.Activity;
import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Dimension;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;

import com.skyfishjy.library.RippleBackground;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import msahil432.click_away.R;
import msahil432.click_away.databinding.DialogHelpAlertBinding;
import msahil432.click_away.extras.MyApplication;

import static msahil432.click_away.extras.MyApplication.Report;

/**
 * Created by sahil on 11/2/17.
 */

public class HelpMeAlertDialog extends Dialog implements View.OnClickListener {

    @BindView(R.id.background_ripple)
    RippleBackground rippleBackground;
    DialogHelpAlertBinding binding;
    private Activity activity;

    HelpMeAlertDialog(Activity a){
        super(a);
        this.activity = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(getContext()), R.layout.dialog_help_alert, null, false);
        binding.setUser(User.Builder(getContext()));
        setContentView(binding.getRoot());
        ButterKnife.bind(this, binding.getRoot());
    }

    @Override
    public void show() {
        super.show();
        List<Integer> dimensions = MyApplication.getScreenDimensions(activity);
        getWindow().setLayout(dimensions.get(0), dimensions.get(1));
    }

    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data,
                                           Menu menu, int deviceId) { }

    @Override
    public void onClick(View v) { }

    @Override
    protected void onStart() {
        super.onStart();
        rippleBackground.startRippleAnimation();
        Report("Help dialog","Starting Bg Ripples");
    }

    @Override
    protected void onStop() {
        rippleBackground.stopRippleAnimation();
        binding.unbind();
        super.onStop();
    }
}
