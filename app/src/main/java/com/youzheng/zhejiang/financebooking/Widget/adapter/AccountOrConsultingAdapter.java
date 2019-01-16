package com.youzheng.zhejiang.financebooking.Widget.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.youzheng.zhejiang.financebooking.Model.user.AccountOrConsultingEntity;
import com.youzheng.zhejiang.financebooking.R;
import com.youzheng.zhejiang.financebooking.UI.My.OnRecyclerViewAdapterItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class AccountOrConsultingAdapter extends RecyclerView.Adapter<AccountOrConsultingAdapter.ConsultHolder> {
    private List<AccountOrConsultingEntity.DataBean> list;
    private Context context;
    private LayoutInflater inflater;
    private OnRecyclerViewAdapterItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnRecyclerViewAdapterItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


    public AccountOrConsultingAdapter(List<AccountOrConsultingEntity.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    public void setUI(List<AccountOrConsultingEntity.DataBean> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public ConsultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         View view=inflater.inflate(R.layout.item_account_or_consulting,parent,false);
        final ConsultHolder consultHolder=new ConsultHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = consultHolder.getLayoutPosition();
                //设置监听
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v ,position );
                }

            }
        });

        return consultHolder;
    }

    @Override
    public void onBindViewHolder(ConsultHolder holder, int position) {
        AccountOrConsultingEntity.DataBean bean=list.get(position);
        holder.tv_name.setText(bean.getInfoTitle());
        holder.tv_date.setText(bean.getReleaseTime());




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ConsultHolder extends RecyclerView.ViewHolder{
        private TextView tv_name,tv_date;

        public ConsultHolder(View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_date=itemView.findViewById(R.id.tv_date);

        }
    }

}
