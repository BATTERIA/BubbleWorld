package batteria.bubbleworld.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import batteria.bubbleworld.R;
import batteria.bubbleworld.cache.SharedHelper;
import batteria.bubbleworld.entity.User;
import batteria.bubbleworld.network.presenter.UserPresenter;
import batteria.bubbleworld.network.view.UserView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    private  static  final String TAG = "LoginActivity";
    private UserPresenter presenter = new UserPresenter();
    private SharedHelper sh;

    @BindView(R.id.login_nickname)
    EditText loginNickname;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.new_user)
    Button newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        ButterKnife.bind(this);
        sh = new SharedHelper(this);
        if(sh.read().get("isLogin").equals("1")){
            finish();
            Intent intent = new Intent(this, FriendsListActivity.class);
            startActivity(intent);
        }
        presenter.onCreate();
        presenter.attachView(userView);
    }

    @OnClick({R.id.login, R.id.new_user})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:
                String nickname = loginNickname.getText().toString();
                String password = loginPassword.getText().toString();
                if(nickname.equals("")){
                    Toast.makeText(this,"请输入用户名", Toast.LENGTH_SHORT).show();
                    break;
                }else if(password.equals("")){
                    Toast.makeText(this,"请输入密码", Toast.LENGTH_SHORT).show();
                    break;
                }
                presenter.login(nickname,password);
                break;
            case R.id.new_user:
                finish();
                Intent intent = new Intent(this, RegistActivity.class);
                startActivity(intent);
                break;
        }
    }
    private UserView userView= new UserView() {
        @Override
        public void onSuccess(User user) {
            sh.save(user);
            finish();
            Intent intent = new Intent(getApplicationContext(), FriendsListActivity.class);
            startActivity(intent);
        }

        @Override
        public void onError(String result) {
            Toast.makeText(getApplicationContext(),"用户名或者密码错误，请重新输入", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onDestroy(){
        super.onDestroy();
        presenter.onStop();
    }
}
