package batteria.bubbleworld.network.presenter;

import android.content.Intent;

import batteria.bubbleworld.entity.User;
import batteria.bubbleworld.network.manager.UserManager;
import batteria.bubbleworld.network.view.UserView;
import batteria.bubbleworld.network.view.View;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.observers.Subscribers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class UserPresenter implements Presenter {
    private UserManager manager;
    private CompositeSubscription subscription;
    private UserView view;
    private User mUser;
    private Observer<User> userObserver  = new  Observer<User>() {
        @Override
        public void onCompleted() {
            if(mUser!=null){
                if(mUser.getNickname().equals("repeat"))
                    view.onError("用户名已存在");
                else if(mUser.getNickname().equals("fail"))
                    view.onError("database error");
                else
                    view.onSuccess(mUser);
            }

        }
        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            view.onError("请求失败！");
        }
        @Override
        public void onNext(User user) {
            mUser = user;
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
        this.view =(UserView) view;
    }

    @Override
    public void attachIncomingIntent(Intent intent) {

    }

    public void regist(String nickname, String password){
        subscription.add(manager.regist(nickname, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(userObserver)
        );
    }
    public void login(String nickname, String password){
        subscription.add(manager.login(nickname, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userObserver)
        );
    }
    public void addFriend(int aid, int fid){
        subscription.add(manager.addFriend(aid, fid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userObserver)
        );
    }
    public void agreeAdd(int aid, int fid){
        subscription.add(manager.agreeAdd(aid, fid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userObserver)
        );
    }
}
