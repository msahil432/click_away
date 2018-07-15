package msahil432.click_away.mainActivity;

import android.app.Activity;
import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.view.Window;

import java.util.List;

import msahil432.click_away.R;
import msahil432.click_away.databinding.DialogHelpAlertBinding;

/**
 * Created by sahil on 11/2/17.
 */

public class HelpMeAlertDialog extends Dialog implements View.OnClickListener {

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
        DialogHelpAlertBinding binding = DataBindingUtil
                .setContentView(activity, R.layout.dialog_help_alert);
        binding.setUser(User.Builder(getContext()));
    }

    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data,
                                           Menu menu, int deviceId) {
    }

    @Override
    public void onClick(View v) {
    }
}
