package batteria.bubbleworld.network.view;

import batteria.bubbleworld.entity.User;

public interface UserView extends View {
    void onSuccess(User user);
    void onError(String result);
}
