package com.jumpitt.primero_cotiza.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.jumpitt.primero_cotiza.App;
import com.jumpitt.primero_cotiza.R;
import com.jumpitt.primero_cotiza.model.Company;
import com.jumpitt.primero_cotiza.ui.companies.CompaniesFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompaniesAdapter extends RecyclerView.Adapter<CompaniesAdapter.Viewholder> {

    private List<Company> companyList;
    private CompaniesFragment fragment;

    public CompaniesAdapter(List<Company> companyList, CompaniesFragment fragment) {
        this.companyList = companyList;
        this.fragment = fragment;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int view) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_company, parent, false);
        return new Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CompaniesAdapter.Viewholder viewholder, int i) {
        Company company = companyList.get(i);
        viewholder.tittle.setText(company.getName());
        viewholder.subTittle.setText(company.getGlosa());

        if (company.getAreaId() == 2) {
            viewholder.itemImage.setImageResource(R.drawable.ic_health_company);
        } else {
            viewholder.itemImage.setImageResource(R.drawable.ic_shield);
        }

        if (company.isOnline()) {
            viewholder.cardView.setForeground(App.getContext().getResources().getDrawable(R.drawable.card_enabled));
        } else {
            viewholder.cardView.setForeground(App.getContext().getResources().getDrawable(R.drawable.card_disabled));
        }

    }

    @Override
    public int getItemCount() {
        return companyList.size();
    }

    public void setDataList(List<Company> companyList) {
        this.companyList = companyList;
        notifyDataSetChanged();
    }


    static class Viewholder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_title)
        TextView tittle;
        @BindView(R.id.item_subtitle)
        TextView subTittle;
        @BindView(R.id.company_card)
        MaterialCardView cardView;
        @BindView(R.id.item_image)
        ImageView itemImage;

        Viewholder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
