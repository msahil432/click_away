package msahil432.click_away.listActivity.base;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
import static msahil432.click_away.extras.MyApplication.getLastLocation;

public abstract class BaseActivity extends AppCompatActivity {

    @BindView(R.id.local_list) protected RecyclerView listView;

    protected BaseViewModel viewModel;
    protected RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        setColors();
        ButterKnife.bind(this);
        setTitle(setType().activityName);
        viewModel = setViewModel();
        initializeList();
        EventBus.getDefault().register(this);
    }

    protected void setColors(){
        if(getSupportActionBar()!=null){
            ColorDrawable color;
            if(Build.VERSION.SDK_INT>Build.VERSION_CODES.M){
                color = new ColorDrawable(getColor(getColorRes()));
                getWindow().setStatusBarColor(getColor(getColorRes()));
            }else{
                color = new ColorDrawable(getResources().getColor(getColorRes()));
            }
            getSupportActionBar().setBackgroundDrawable(color);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }

    protected void initializeList(){
        Report("BaseListAct", "Initializing List");
        adapter = new RecyclerAdapter(getColorRes(),
                getLastLocation(this).getLastLocatino());
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
                new MyViewModelFactory(
                        MyDatabase.instance(this).getDao(),
                        setType(),
                        MyApplication.getLastLocation(this).getLastLocatino()
                )).get(BaseViewModel.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getLocationUpdates(MyGPSLocService.LocationData locationData){
        viewModel.setLocation(locationData);
        adapter = new RecyclerAdapter(getColorRes(), locationData);
        viewModel.getInstitutes().observe(this, new Observer<PagedList<Institute>>() {
            @Override
            public void onChanged(@Nullable PagedList<Institute> institutes) {
                adapter.submitList(institutes);
            }
        });
        listView.setAdapter(adapter);
    }

    protected abstract Types setType();

    protected abstract int getColorRes();

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
