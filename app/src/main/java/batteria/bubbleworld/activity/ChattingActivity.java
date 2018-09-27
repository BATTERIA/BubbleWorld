package batteria.bubbleworld.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import batteria.bubbleworld.R;
import batteria.bubbleworld.adapter.ChattingAdapter;
import batteria.bubbleworld.cache.SharedHelper;
import batteria.bubbleworld.entity.Message;
import batteria.bubbleworld.network.presenter.MessagePresenter;
import batteria.bubbleworld.network.presenter.MessagesPresenter;
import batteria.bubbleworld.network.view.MessageView;
import batteria.bubbleworld.network.view.MessagesView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChattingActivity extends AppCompatActivity {
    private static final String TAG="ChattingActivity";
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.chat_fid)
    TextView chatFid;
    @BindView(R.id.messages_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.message_edit)
    EditText messageEdit;
    @BindView(R.id.message_send)
    ImageView messageSend;

    private int uid;
    private int fid;
    private SharedHelper sh;
    private MessagesPresenter messagesPresenter=new MessagesPresenter();
    private MessagePresenter messagePresenter=new MessagePresenter();

    private ChattingAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Message> messages;

    private NewMessagesTask newMessagesTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatting_page);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        fid=intent.getIntExtra("fid", -1);
        sh=new SharedHelper(this);
        uid=Integer.parseInt(sh.read().get("uid"));

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        messages=new LinkedList<>();
        mAdapter=new ChattingAdapter(messages);
        mRecyclerView.setAdapter(mAdapter);

        messagesPresenter.onCreate();
        messagesPresenter.attachView(messagesView);
        messagesPresenter.getAllMessages(uid, fid);
        messagePresenter.onCreate();
        messagePresenter.attachView(messageView);
        messagePresenter.redAllMessages(uid, fid);
    }

    @Override
    protected void onResume() {
        super.onResume();
        newMessagesTask = new NewMessagesTask();
        newMessagesTask.execute();
    }

    @Override
    protected  void onPause() {
        super.onPause();
        newMessagesTask.cancel(true);
    }

    @OnClick({R.id.back, R.id.message_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.message_send:
                String content = messageEdit.getText().toString();
                messagePresenter.sendMessage(content, uid, fid);
                break;
        }
    }

    private MessagesView messagesView=new MessagesView() {
        @Override
        public void onSuccess(List<Message> updateMessages) {
            Log.d(TAG, "onSuccess: MessagesView");
            if (updateMessages.get(0).getContent().equals("null")) {
                Log.d(TAG, "onSuccess: 暂无消息");
//                Toast.makeText(getApplicationContext(), "暂无消息", Toast.LENGTH_SHORT).show();
                return;
            }
            messages.clear();
            messages.addAll(updateMessages);
            mAdapter.notifyDataSetChanged();
            mRecyclerView.scrollToPosition(mAdapter.getItemCount()-1);
        }

        @Override
        public void onError(String result) {
            Log.d(TAG, "onError: connect_error");
            //Toast.makeText(getApplicationContext(), "connect_error", Toast.LENGTH_SHORT).show();
        }
    };

    private MessageView messageView=new MessageView() {
        @Override
        public void onSuccess(Message message) {
            Log.d(TAG, "onSuccess: MessageView");
            if(messageEdit.getText().toString().equals(""))
                return;
            Message temp = new Message();
            temp.setContent(messageEdit.getText().toString());
            messageEdit.setText("");
            temp.setSpeakerid(uid);
            temp.setReceiverid(fid);
            messages.add(temp);
            mAdapter.notifyDataSetChanged();
//            mRecyclerView.scrollToPosition(0);
        }

        @Override
        public void onError(String result) {
            Log.d(TAG, "onError: connect_error");
            //没有消息可读也会报错，暂时注释
//            Toast.makeText(getApplicationContext(), "connect_error", Toast.LENGTH_SHORT).show();
        }
    };

    class NewMessagesTask extends AsyncTask<Void , List<Message>, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: ********************************");
            Log.d(TAG, "doInBackground: " + Thread.currentThread().getName());
            Log.d(TAG, "doInBackground: ********************************");
            while(true){
                if(this.isCancelled())
                    return null;
                try {
                    Thread.sleep(2000);
                    messagesPresenter.getAllMessages(uid, fid);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        messagesPresenter.onStop();
        messagePresenter.onStop();
    }
}
