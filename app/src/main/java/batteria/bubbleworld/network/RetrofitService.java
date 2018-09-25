package batteria.bubbleworld.network;

import java.util.List;

import batteria.bubbleworld.entity.Message;
import batteria.bubbleworld.entity.User;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface RetrofitService {
    @GET("bubbleWeb/user?method=regist")
    Observable<User> regist(
            @Query("nickname")String nickname,
            @Query("password")String password
            //(@QueryMap Map<String, String> options)
    );
    @GET("bubbleWeb/user?method=login")
    Observable<User> login(
            @Query("nickname")String nickname,
            @Query("password")String password
    );
    @GET("bubbleWeb/user?method=getAllFriends")
    Observable<List<User>> getAllFriends(
            @Query("uid")int uid
    );
    @GET("bubbleWeb/user?method=getAddingFriends")
    Observable<List<User>> getAddingFriends(
            @Query("uid")int uid
    );
    @GET("bubbleWeb/user?method=addFriend")
    Observable<User> addFriend(
            @Query("aid")int aid,
            @Query("fid")int fid
    );
    @GET("bubbleWeb/user?method=agreeAdd")
    Observable<User> agreeAdd(
            @Query("aid")int aid,
            @Query("fid")int fid
    );
    @GET("bubbleWeb/message?method=getAllMessages")
    Observable<List<Message>> getAllMessages(
            @Query("uid")int uid,
            @Query("fid")int fid
    );
    @GET("bubbleWeb/message?method=sendMessage")
    Observable<Message> sendMessage(
            @Query("content")String content,
            @Query("speakerid")int speakerid,
            @Query("receiverid")int receiverid
    );
    @GET("bubbleWeb/message?method=redAllMessages")
    Observable<Message> redAllMessages(
            @Query("uid")int uid,
            @Query("fid")int fid
    );
}
