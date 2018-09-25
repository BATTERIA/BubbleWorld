package batteria.bubbleworld.network.presenter;

import android.content.Intent;

import java.util.List;

import batteria.bubbleworld.entity.User;
import batteria.bubbleworld.network.manager.UserManager;
import batteria.bubbleworld.network.view.FriendsView;
import batteria.bubbleworld.network.view.UserView;
import batteria.bubbleworld.network.view.View;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class FriendsPresenter implements Presenter {
    private UserManager manager;
    private CompositeSubscription subscription;
    private FriendsView view;
    private List<User> mFriends;
    private Observer<List<User>> friendsObserve  = new  Observer<List<User>>() {
        @Override
        public void onCompleted() {
            if(mFriends!=null){
               view.onSuccess(mFriends);
            }

        }
        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            view.onError("请求失败！");
        }
        @Override
        public void onNext(List<User> friends) {
            mFriends = friends;
        }
    };

    @Override
    public void onCreate() {
        manager = new UserManager();
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
        this.view =(FriendsView) view;
    }

    @Override
    public void attachIncomingIntent(Intent intent) {

    }

    public void getAllFriends(int uid){
        subscription.add(manager.getAllFriends(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(friendsObserve)
        );
    }

    public void getAddingFriends(int uid){
        subscription.add(manager.getAddingFriends(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(friendsObserve)
        );
    }
}
