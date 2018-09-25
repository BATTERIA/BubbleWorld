package batteria.bubbleworld.network.view;

import batteria.bubbleworld.entity.Message;
import batteria.bubbleworld.entity.User;

public interface MessageView extends View {
    void onSuccess(Message message);
    void onError(String result);
}
