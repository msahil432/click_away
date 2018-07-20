package msahil432.click_away.list.base;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import msahil432.click_away.R;
import msahil432.click_away.connections.MyGPSLocService;
import msahil432.click_away.database.Institute;
import msahil432.click_away.database.MyDatabase;
import msahil432.click_away.extras.MyApplication;
import msahil432.click_away.forceClose.MyExceptionHandler;

import static msahil432.click_away.extras.MyApplication.Report;
import static msahil432.click_away.extras.RetroFitService.baseUrl;

public abstract class BaseActivity extends AppCompatActivity {

    @BindView(R.id.local_list) protected RecyclerView listView;

    protected BaseViewModel viewModel;
    protected RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        setTitle(setType().activityName);
        viewModel = setViewModel();
        initializeList();
        EventBus.getDefault().register(this);
    }

    protected void initializeList(){
        Report("BaseListAct", "Initializing List");
        adapter = new RecyclerAdapter();
        listView.setAdapter(adapter);
        listView.setHasFixedSize(true);
        listView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        viewModel.getInstitutes().observe(this, new Observer<PagedList<Institute>>() {
            @Override
            public void onChanged(@Nullable PagedList<Institute> institutes) {
                Report("BaseListAct", "New Data of institutes, Size:"+institutes.size());
                adapter.submitList(institutes);
            }
        });
    }

    protected BaseViewModel setViewModel(){
        Report("BaseListAct", "getting ViewModel");
        return ViewModelProviders.of(this,
                new MyViewModelFactory(MyDatabase.instance(this).getDao(), setType()))
                .get(BaseViewModel.class);
    }

    protected abstract Types setType();

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
