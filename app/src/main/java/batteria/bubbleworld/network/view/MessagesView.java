package batteria.bubbleworld.network.view;

import java.util.List;

import batteria.bubbleworld.entity.Message;
import batteria.bubbleworld.entity.User;

public interface MessagesView extends View {
    void onSuccess(List<Message> messages);
    void onError(String result);
}
