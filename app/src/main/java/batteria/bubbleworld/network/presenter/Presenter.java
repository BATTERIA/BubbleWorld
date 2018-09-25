package batteria.bubbleworld.network.presenter;

import android.content.Intent;

import batteria.bubbleworld.network.view.View;

public interface Presenter {
    void onCreate();

    void onStart();

    void onStop();

    void pause();

    void attachView(View view);

    void attachIncomingIntent(Intent intent);

}
