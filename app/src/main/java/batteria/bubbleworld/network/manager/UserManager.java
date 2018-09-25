package batteria.bubbleworld.network.manager;

import java.util.List;

import batteria.bubbleworld.entity.User;
import batteria.bubbleworld.network.RetrofitService;
import batteria.bubbleworld.network.WebService;
import rx.Observable;
import rx.Observer;

public class UserManager {
    private RetrofitService service;

    public UserManager() {
        this.service=WebService.getInstance().getServer();
    }

    public Observable<User> regist(String nickname, String password){
        return service.regist(nickname, password);
    }

    public Observable<User> login(String nickname, String password){
        return service.login(nickname, password);
    }

    public Observable<List<User>> getAllFriends(int uid){
        return service.getAllFriends(uid);
    }

    public Observable<List<User>> getAddingFriends(int uid){
        return service.getAddingFriends(uid);
    }

    public Observable<User> addFriend(int aid, int fid){
        return service.addFriend(aid, fid);
    }

    public Observable<User> agreeAdd(int aid, int fid){
        return service.agreeAdd(aid, fid);
    }
}
