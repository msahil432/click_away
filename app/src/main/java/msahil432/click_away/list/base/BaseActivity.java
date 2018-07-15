package msahil432.click_away.list.base;

import android.arch.lifecycle.Observer;
import android.arch.paging.PagedList;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.webkit.WebView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import msahil432.click_away.R;
import msahil432.click_away.connections.MyGPSLocService;
import msahil432.click_away.database.Institute;
import msahil432.click_away.extras.MyExceptionHandler;
import msahil432.click_away.extras.RetroFitService;

public abstract class BaseActivity extends AppCompatActivity {

    @BindView(R.id.online_webview) protected WebView webView;
    @BindView(R.id.local_list) protected RecyclerView listView;

    protected BaseViewModel viewModel;
    protected RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_bank);
        setTitle(setType().activityName);
        viewModel = setViewModel();
        adapter = new RecyclerAdapter();
        listView.setAdapter(adapter);
        listView.setHasFixedSize(true);
        listView.setLayoutManager(new LinearLayoutManager(this));
        ButterKnife.bind(this);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        viewModel.institues.observe(this, new Observer<PagedList<Institute>>() {
            @Override
            public void onChanged(@Nullable PagedList<Institute> institutes) {
                adapter.submitList(institutes);
            }
        });

        webView.canGoBackOrForward(0);
    }

    protected abstract BaseViewModel setViewModel();

    protected abstract Types setType();

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void locationChanged(MyGPSLocService.LocationData locationData){
        webView.loadUrl(RetroFitService.baseUrl+"/"+setType().urlPart+
                "?long="+locationData.getLongitude()+
                "&lat="+locationData.getLatitude());
    }

    public enum Types{
        BloodBanks("Blood Banks", "b1", "bloodbanks"),
        Hospitals("Hospitals", "h1", "hospitals"),
        Chemists("Chemists", "c1", "chemists");

        public String activityName, urlPart, dbField;
        Types(String activityName, String urlPart, String dbField){
            this.activityName = activityName;
            this.urlPart = urlPart;
            this.dbField = dbField;
        }
    }
}
