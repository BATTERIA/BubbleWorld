package batteria.bubbleworld.network.presenter;

import android.content.Intent;

import batteria.bubbleworld.entity.Message;
import batteria.bubbleworld.entity.User;
import batteria.bubbleworld.network.manager.MessageManager;
import batteria.bubbleworld.network.manager.UserManager;
import batteria.bubbleworld.network.view.MessageView;
import batteria.bubbleworld.network.view.UserView;
import batteria.bubbleworld.network.view.View;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MessagePresenter implements Presenter {
    private MessageManager manager;
    private CompositeSubscription subscription;
    private MessageView view;
    private Message mMessage;
    private Observer<Message> messageObserver  = new  Observer<Message>() {
        @Override
        public void onCompleted() {
            if(mMessage!=null){
                if(mMessage.getContent().equals("fail"))
                    view.onError("database error");
                else
                    view.onSuccess(mMessage);
            }
        }
        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            view.onError("请求失败！");
        }
        @Override
        public void onNext(Message message) {
            mMessage = message;
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
        this.view =(MessageView) view;
    }

    @Override
    public void attachIncomingIntent(Intent intent) {

    }

    public void sendMessage(String content, int speakerid, int receiverid){
        subscription.add(manager.sendMessage(content, speakerid, receiverid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(messageObserver)
        );
    }
    public void redAllMessages(int uid, int fid){
        subscription.add(manager.redAllMessages(uid, fid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(messageObserver)
        );
    }
}
