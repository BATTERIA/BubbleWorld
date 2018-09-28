package batteria.bubbleworld.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import batteria.bubbleworld.R;
import batteria.bubbleworld.adapter.FriendsAdapter;
import batteria.bubbleworld.adapter.ItemListener;
import batteria.bubbleworld.cache.SharedHelper;
import batteria.bubbleworld.entity.User;
import batteria.bubbleworld.network.presenter.FriendsPresenter;
import batteria.bubbleworld.network.view.FriendsView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FriendsListActivity extends AppCompatActivity {
    private static final String TAG="FriendsListActivity";
    @BindView(R.id.friends_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.logout)
    TextView logout;
    @BindView(R.id.add)
    TextView add;
    @BindView(R.id.refresh_tool)
    SwipeRefreshLayout refreshTool;
    private FriendsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<User> friends;

    private FriendsPresenter presenter=new FriendsPresenter();
    private SharedHelper sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_list);
        ButterKnife.bind(this);
        sh=new SharedHelper(this);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        friends=new LinkedList<>();
        mAdapter=new FriendsAdapter(friends);
        mAdapter.setOnItemListener(itemListener);
        mRecyclerView.setAdapter(mAdapter);

        presenter.onCreate();
        presenter.attachView(friendsView);
        presenter.getAllFriends(Integer.parseInt(sh.read().get("uid")));

        refreshTool.setOnRefreshListener(()->{
            presenter.getAllFriends(Integer.parseInt(sh.read().get("uid")));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getAllFriends(Integer.parseInt(sh.read().get("uid")));
    }

    @OnClick({R.id.logout, R.id.add})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.logout:
                sh.clear();
                finish();
                intent=new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.add:
                intent=new Intent(this, AddFriendsActivity.class);
                startActivity(intent);
                break;
        }
    }

    private ItemListener itemListener=new ItemListener() {
        @Override
        public void onItemClick(int position, User user) {
            Intent intent=new Intent(getApplicationContext(), ChattingActivity.class);
            intent.putExtra("fid", user.getUid());
            startActivity(intent);
        }
    };

    private FriendsView friendsView=new FriendsView() {
        @Override
        public void onSuccess(List<User> updateFriends) {
            Log.d(TAG, "onSuccess1: FriendsView");
            if (updateFriends.get(0).getNickname().equals("null")) {
                Toast.makeText(getApplicationContext(), "暂无好友", Toast.LENGTH_SHORT).show();
                return;
            }
            friends.clear();
            friends.addAll(updateFriends);
            mAdapter.notifyDataSetChanged();
            mRecyclerView.scrollToPosition(0);
            refreshTool.setRefreshing(false);
        }

        @Override
        public void onError(String result) {
            Log.d(TAG, "onError: connect_error");
            Toast.makeText(getApplicationContext(), "connect_error", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onStop();
    }
}
