package com.jumpitt.primero_cotiza.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.jumpitt.primero_cotiza.App;
import com.jumpitt.primero_cotiza.R;
import com.jumpitt.primero_cotiza.model.Client;
import com.jumpitt.primero_cotiza.ui.clients.ClientsFragment;
import com.jumpitt.primero_cotiza.utils.DateDifference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClientsAdapter extends RecyclerView.Adapter<ClientsAdapter.Viewholder> {

    private List<Client> clientList;
    private ClientsFragment mFragment;

    public ClientsAdapter(List<Client> clientList, ClientsFragment fragment) {
        this.clientList = clientList;
        this.mFragment = fragment;

    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_client, parent, false);
        return new Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder viewholder, int i) {
        viewholder.clientDate.setText(parseDate(clientList.get(i).getAttDate()));
        viewholder.dateDiff.setText(getdiffDays(clientList.get(i).getAttDate()));
        viewholder.clientName.setText(clientList.get(i).getLeadName()!= null ? clientList.get(i).getLeadName() : clientList.get(i).getForenames());
        viewholder.clientService.setText(clientList.get(i).getService());
        if (clientList.get(i).getOfferStatus() == 8){
            viewholder.clientCard.setBackgroundColor(App.getContext().getResources().getColor(R.color.white));
        }
        else {
            viewholder.clientCard.setBackgroundColor(App.getContext().getResources().getColor(R.color.notSoDarkGray));
        }
        viewholder.clientCard.setOnClickListener(v -> {
            viewholder.clientCard.setEnabled(false);
            cardClick(clientList.get(i).getOfferID());
        });

    }

    private void cardClick(int clientId) {
        mFragment.onClientClicked(clientId);
    }

    @Override
    public int getItemCount() {
        return clientList.size();
    }

    public void setDataList(List<Client> clientList) {
        this.clientList = clientList;
        notifyDataSetChanged();
    }

    private String parseDate(String dtStart) {
        Date date;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat finalFormat = new SimpleDateFormat("dd MMMM yyyy");

        try {
            date = format.parse(dtStart);
            return finalFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    private String getdiffDays(String date){
        DateDifference mDateDiff = new DateDifference();
        return mDateDiff.getDaysBetween(date);
    }

    static class Viewholder extends RecyclerView.ViewHolder {

        @BindView(R.id.client_date)
        TextView clientDate;
        @BindView(R.id.client_name)
        TextView clientName;
        @BindView(R.id.client_service)
        TextView clientService;
        @BindView(R.id.client_card)
        ConstraintLayout clientCard;
        @BindView(R.id.client_date_diff)
        TextView dateDiff;


        Viewholder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

    }
}
