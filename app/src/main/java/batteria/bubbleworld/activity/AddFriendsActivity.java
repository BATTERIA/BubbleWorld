package batteria.bubbleworld.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import batteria.bubbleworld.R;
import batteria.bubbleworld.adapter.AddFriendsAdapter;
import batteria.bubbleworld.adapter.ItemInnerListener;
import batteria.bubbleworld.cache.SharedHelper;
import batteria.bubbleworld.entity.User;
import batteria.bubbleworld.network.presenter.FriendsPresenter;
import batteria.bubbleworld.network.presenter.UserPresenter;
import batteria.bubbleworld.network.view.FriendsView;
import batteria.bubbleworld.network.view.UserView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddFriendsActivity extends AppCompatActivity {
    private static final String TAG="AddFriendsActivity";

    @BindView(R.id.my_uid)
    TextView myUid;
    @BindView(R.id.uid)
    EditText uid;
    @BindView(R.id.add)
    TextView add;
    @BindView(R.id.add_friends_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.refresh_tool)
    SwipeRefreshLayout refreshTool;

    private AddFriendsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<User> friends;

    private FriendsPresenter presenter=new FriendsPresenter();
    private UserPresenter addFriendPresenter=new UserPresenter();
    private UserPresenter agreeAddPresenter=new UserPresenter();
    private SharedHelper sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friends);
        ButterKnife.bind(this);
        sh=new SharedHelper(this);
        myUid.setText(sh.read().get("uid"));

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        friends=new LinkedList<>();
        mAdapter=new AddFriendsAdapter(friends);
        mAdapter.setOnItemInnerListener(itemInnerListener);
        mRecyclerView.setAdapter(mAdapter);

        presenter.onCreate();
        presenter.attachView(friendsView);
        presenter.getAddingFriends(Integer.parseInt(sh.read().get("uid")));
        addFriendPresenter.onCreate();
        addFriendPresenter.attachView(addFriendView);
        agreeAddPresenter.onCreate();
        agreeAddPresenter.attachView(agreeAddView);

        refreshTool.setOnRefreshListener(()->{
            presenter.getAddingFriends(Integer.parseInt(sh.read().get("uid")));
        });
    }

    @OnClick({R.id.back, R.id.add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.add:
                String aid=myUid.getText().toString();
                String fid=uid.getText().toString();
                Log.d(TAG, "onViewClicked: " + aid + "  " + fid);
                addFriendPresenter.addFriend(Integer.parseInt(aid), Integer.parseInt(fid));
                break;
        }
    }

    private ItemInnerListener itemInnerListener=new ItemInnerListener() {
        @Override
        public void onItemInnerClick(int position, int uid) {
            int aid=Integer.parseInt(myUid.getText().toString());
            int fid=uid;
            Log.d(TAG, "onItemInnerClick: " + aid + " " + fid);
            agreeAddPresenter.agreeAdd(aid, fid);
            friends.remove(position);
            mAdapter.notifyDataSetChanged();
            mRecyclerView.scrollToPosition(0);
        }
    };

    private FriendsView friendsView=new FriendsView() {
        @Override
        public void onSuccess(List<User> updateFriends) {
            Log.d(TAG, "onSuccess1: FriendsView");
            if (updateFriends.get(0).getNickname().equals("null")) {
                Toast.makeText(getApplicationContext(), "暂无好友请求", Toast.LENGTH_SHORT).show();
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

    private UserView addFriendView=new UserView() {
        @Override
        public void onSuccess(User user) {
            Toast.makeText(getApplicationContext(), "已发出请求", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(String result) {
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        }
    };

    private UserView agreeAddView=new UserView() {
        @Override
        public void onSuccess(User user) {
            Toast.makeText(getApplicationContext(), "已添加", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(String result) {
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onStop();
        addFriendPresenter.onStop();
        agreeAddPresenter.onStop();
    }

}
