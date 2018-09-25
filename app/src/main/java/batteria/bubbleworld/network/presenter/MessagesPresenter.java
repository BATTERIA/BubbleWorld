package batteria.bubbleworld.network.presenter;

import android.content.Intent;

import java.util.List;

import batteria.bubbleworld.entity.Message;
import batteria.bubbleworld.network.manager.MessageManager;
import batteria.bubbleworld.network.view.MessageView;
import batteria.bubbleworld.network.view.MessagesView;
import batteria.bubbleworld.network.view.View;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MessagesPresenter implements Presenter {
    private MessageManager manager;
    private CompositeSubscription subscription;
    private MessagesView view;
    private List<Message> mMessages;
    private Observer<List<Message>> messagesObserver  = new  Observer<List<Message>>() {
        @Override
        public void onCompleted() {
            if(mMessages!=null){
                view.onSuccess(mMessages);
            }
        }
        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            view.onError("请求失败！");
        }
        @Override
        public void onNext(List<Message> messages) {
            mMessages = messages;
        }
    };

    @Override
    public void onCreate() {
        manager = new MessageManager();
        subscription = new CompositeSubscription();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if(subscription.hasSubscriptions()){
            subscription.unsubscribe();
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void attachView(View view) {
        this.view =(MessagesView) view;
    }

    @Override
    public void attachIncomingIntent(Intent intent) {

    }

    public void getAllMessages(int uid, int fid){
        subscription.add(manager.getAllMessages(uid, fid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(messagesObserver)
        );
    }

}
