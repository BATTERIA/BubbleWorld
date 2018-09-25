package batteria.bubbleworld.network.view;

import java.util.List;

import batteria.bubbleworld.entity.User;

public interface FriendsView extends View {
    void onSuccess(List<User> friends);
    void onError(String result);
}
