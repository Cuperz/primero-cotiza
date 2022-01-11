package com.jumpitt.primero_cotiza.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jumpitt.primero_cotiza.R;
import com.jumpitt.primero_cotiza.model.LeadInfo;
import com.jumpitt.primero_cotiza.ui.lead.LeadActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LeadAdapter extends RecyclerView.Adapter<LeadAdapter.ViewHolder> {

    private List<LeadInfo> infoList;
    private LeadActivity leadActivity;

    public LeadAdapter(List<LeadInfo> clientList, LeadActivity activity){
        this.infoList = clientList;
        this.leadActivity = activity;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lead_detail,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LeadAdapter.ViewHolder viewholder, int i) {
        viewholder.leadTitle.setText(infoList.get(i).getTittle());
        if (infoList.get(i).getTittle().equals("Ingreso Mensual") || infoList.get(i).getTittle().equals("Sueldo")){
            viewholder.leadSubtitle.setText("$"+infoList.get(i).getSubTittle());
        }else {
            viewholder.leadSubtitle.setText(infoList.get(i).getSubTittle());
        }
    }


    @Override
    public int getItemCount() {
        return infoList.size();
    }

    public void setDataList(List<LeadInfo> leadInfoList){
        this.infoList = leadInfoList;
        notifyDataSetChanged();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lead_title)
        TextView leadTitle;
        @BindView(R.id.lead_subtitle)
        TextView leadSubtitle;


        public ViewHolder(View v){
            super(v);
            ButterKnife.bind(this,v);
        }

    }
}
