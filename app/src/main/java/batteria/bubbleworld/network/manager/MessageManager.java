package batteria.bubbleworld.network.manager;

import java.util.List;

import batteria.bubbleworld.entity.Message;
import batteria.bubbleworld.entity.User;
import batteria.bubbleworld.network.RetrofitService;
import batteria.bubbleworld.network.WebService;
import rx.Observable;

public class MessageManager {
    private RetrofitService service;

    public MessageManager() {
        this.service=WebService.getInstance().getServer();
    }

    public Observable<Message> sendMessage(String content, int speakerid, int receiverid){
        return service.sendMessage(content, speakerid, receiverid);
    }

    public Observable<Message> redAllMessages(int uid, int fid){
        return service.redAllMessages(uid,fid);
    }

    public Observable<List<Message>> getAllMessages(int uid, int fid){
        return service.getAllMessages(uid,fid);
    }
}
