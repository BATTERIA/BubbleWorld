package batteria.bubbleworld.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import batteria.bubbleworld.R;
import batteria.bubbleworld.entity.User;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddFriendsAdapter extends RecyclerView.Adapter<AddFriendsAdapter.FriendsViewHandler> {
    private List<User> friends;
    private ItemInnerListener itemInnerListener;

    public AddFriendsAdapter(List<User> friends) {
        this.friends=friends;
    }

    public void setOnItemInnerListener(ItemInnerListener itemInnerListener) {
        this.itemInnerListener=itemInnerListener;
    }

    @Override
    public AddFriendsAdapter.FriendsViewHandler onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.add_friend_outline, parent, false);
        return new FriendsViewHandler(v);
    }

    @Override
    public void onBindViewHolder(AddFriendsAdapter.FriendsViewHandler holder, int position) {
        final int fposition = position;
        final int uid = friends.get(position).getUid();
        holder.outlineMotto.setText(friends.get(position).getMotto());
        holder.outlineNickname.setText(friends.get(position).getNickname());
        //图片最后处理
        //holder.outlinePortrait;
        holder.agreeAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                itemInnerListener.onItemInnerClick(fposition, uid);
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
        @BindView(R.id.agree_add)
        ImageButton agreeAdd;

        public FriendsViewHandler(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }
}
