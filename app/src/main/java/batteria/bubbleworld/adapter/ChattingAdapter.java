package batteria.bubbleworld.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MultiAutoCompleteTextView;

import java.util.List;

import batteria.bubbleworld.R;
import batteria.bubbleworld.cache.SharedHelper;
import batteria.bubbleworld.entity.Message;
import batteria.bubbleworld.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ChattingAdapter extends RecyclerView.Adapter<ChattingAdapter.ChattingViewHandler> {
    private static final String TAG="ChattingAdapter";
    private SharedHelper sh;
    private List<Message> messages;
    private int uid;

    public ChattingAdapter(List<Message> messages) {
        this.messages = messages;
        sh = new SharedHelper(Utils.getContext());
        uid = Integer.parseInt(sh.read().get("uid"));
    }

    @Override
    public ChattingViewHandler onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.chatting_content, parent, false);
        return new ChattingAdapter.ChattingViewHandler(v);
    }

    @Override
    public void onBindViewHolder(ChattingViewHandler holder, int position) {

        Message temp = messages.get(position);
        Log.d(TAG, "onBindViewHolder: "+ temp.getContent());

            if(temp.getReceiverid()==uid){
                holder.leftChat.setVisibility(View.VISIBLE);
                holder.leftChat.setText(temp.getContent());
                holder.rightChat.setVisibility(View.INVISIBLE);
            }else if(temp.getSpeakerid()==uid){
                holder.rightChat.setVisibility(View.VISIBLE);
                holder.rightChat.setText(temp.getContent());
                holder.leftChat.setVisibility(View.INVISIBLE);
            }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


    public class ChattingViewHandler extends RecyclerView.ViewHolder {
        @BindView(R.id.right_chat)
        MultiAutoCompleteTextView rightChat;
        @BindView(R.id.left_chat)
        MultiAutoCompleteTextView leftChat;

        public ChattingViewHandler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}