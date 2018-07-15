package msahil432.click_away.list.base;

import android.arch.paging.PagedListAdapter;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import msahil432.click_away.R;
import msahil432.click_away.database.Institute;

public class RecyclerAdapter extends PagedListAdapter<Institute, RecyclerAdapter.ViewHolder> {

    RecyclerAdapter() {
        super(new MyDiffCallBack());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_institute_item,
                parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.institute_name) AppCompatTextView nameView;
        @BindView(R.id.institute_address) AppCompatTextView addressView;
        @BindView(R.id.institute_call_btn) FloatingActionButton callButton;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setData(final Institute institute){
            if(institute==null)
                return;

            nameView.setText(institute.getName());
            addressView.setText(institute.getAddress());
            callButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_DIAL);
                    i.setData(Uri.parse("tel:"+institute.getPhone()));
                    view.getContext().startActivity(i);
                }
            });
        }
    }

    private static class MyDiffCallBack extends DiffUtil.ItemCallback<Institute>{
        @Override
        public boolean areItemsTheSame(Institute oldItem, Institute newItem) {
            return oldItem.getUid()==newItem.getUid();
        }

        @Override
        public boolean areContentsTheSame(Institute oldItem, Institute newItem) {
            return oldItem==newItem;
        }
    }
}
