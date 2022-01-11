package com.jumpitt.primero_cotiza.ui.clients;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jumpitt.primero_cotiza.R;
import com.jumpitt.primero_cotiza.adapter.ClientsAdapter;
import com.jumpitt.primero_cotiza.model.Client;
import com.jumpitt.primero_cotiza.ui.home.HomeActivity;
import com.todkars.shimmer.ShimmerRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jumpitt.primero_cotiza.utils.Constants.TYPE_NEGATIVE;


public class ClientsFragment extends Fragment implements ClientsContract.View {

    private static final String TAG = ClientsFragment.class.getSimpleName();
    private ClientsContract.Presenter mPresenter;
    private ClientsAdapter mAdapter;
    private LinearLayoutManager layoutManager;
    private Boolean loading = false;

    @BindView(R.id.clients_recycler)
    ShimmerRecyclerView mRecycler;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeLayout;


    public static ClientsFragment newInstance() {
        ClientsFragment fragment = new ClientsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ClientsPresenter(this);

    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstaceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_clients, container, false);

        ButterKnife.bind(this, rootView);

        mRecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext());

        mRecycler.setLayoutManager(layoutManager);
        mAdapter = new ClientsAdapter(new ArrayList<>(), this);
        mRecycler.setAdapter(mAdapter);

        if (!loading) {
            mRecycler.showShimmer();
            mPresenter.getClients();
            loading = true;
        }

        mSwipeLayout.setOnRefreshListener(() -> onRefresh());

        return rootView;
    }

    public void onClientClicked(int clientId) {
        mPresenter.toClient(clientId);
    }

    @Override
    public void setClients(List<Client> clientList) {
        mRecycler.hideShimmer();
        mAdapter.setDataList(clientList);
        loading = false;
    }

    @Override
    public void clientsFailed(String message) {
        View view = this.getView();
        if (getActivity() != null) {
            ((HomeActivity) (getActivity())).showSbMessage(view, message, TYPE_NEGATIVE);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!loading) {
            mPresenter.getClients();
        }
    }

    public void onRefresh() {
        onResume();

        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (mSwipeLayout.isRefreshing()) {
                mSwipeLayout.setRefreshing(false);
            }
        }, 2000);

    }


}
