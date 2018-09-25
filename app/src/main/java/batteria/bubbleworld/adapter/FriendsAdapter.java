package batteria.bubbleworld.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import batteria.bubbleworld.R;
import batteria.bubbleworld.entity.User;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendsViewHandler> {
    private List<User> friends;
    private ItemListener itemListener;

    public FriendsAdapter(List<User> friends) {
        this.friends=friends;
    }
    public void setOnItemListener(ItemListener itemListener) {
        this.itemListener=itemListener;
    }
    @Override
    public FriendsAdapter.FriendsViewHandler onCreateViewHolder(ViewGroup parent, int viewType) {
        View v =LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_outline, parent, false);
        return new FriendsViewHandler(v);
    }

    @Override
    public void onBindViewHolder(FriendsAdapter.FriendsViewHandler holder, int position) {
        final User user = friends.get(position);
        final int fposition = position;
        holder.outlineMotto.setText(friends.get(position).getMotto());
        holder.outlineNickname.setText(friends.get(position).getNickname());
        //图片最后处理
        //holder.outlinePortrait;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemListener.onItemClick(fposition, user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public class FriendsViewHandler extends RecyclerView.ViewHolder {
        @BindView(R.id.outline_portrait)
        ImageView outlinePortrait;
        @BindView(R.id.outline_nickname)
        TextView outlineNickname;
        @BindView(R.id.outline_motto)
        TextView outlineMotto;

        public FriendsViewHandler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
