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

public class RegistActivity extends AppCompatActivity {
    private  static  final String TAG = "RegistActivity";
    private UserPresenter presenter = new UserPresenter();
    private SharedHelper sh;

    @BindView(R.id.regist_nickname)
    EditText registNickname;
    @BindView(R.id.regist_password)
    EditText registPassword;
    @BindView(R.id.regist_confirm)
    EditText registConfirm;
    @BindView(R.id.regist)
    Button regist;
    @BindView(R.id.regist_cancel)
    Button registCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist_page);
        ButterKnife.bind(this);
        sh = new SharedHelper(this);

        presenter.onCreate();
        presenter.attachView(userView);
    }

    @OnClick({R.id.regist, R.id.regist_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.regist:
                //判断
                String nickname = registNickname.getText().toString();
                String password = registPassword.getText().toString();
                String confirm = registConfirm.getText().toString();
                if(nickname.equals("")){
                    Toast.makeText(this,"请输入用户名", Toast.LENGTH_SHORT).show();
                    break;
                }else if(password.equals("")){
                    Toast.makeText(this,"请输入密码", Toast.LENGTH_SHORT).show();
                    break;
                }
                if(!password.equals(confirm)){
                    Toast.makeText(this,"两次输入密码不同，请确认", Toast.LENGTH_SHORT).show();
                    break;
                }else if(password.length()<6||nickname.length()>6) {
                    Toast.makeText(this,"密码至少六位，用户名不得多于6个字", Toast.LENGTH_SHORT).show();
                    break;
                }else{
                    presenter.regist(nickname,password);
                    break;
                }
            case R.id.regist_cancel:
                finish();
                Intent intent = new Intent(this, LoginActivity.class);
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
            Toast.makeText(getApplicationContext(),result, Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    protected void onDestroy(){
        super.onDestroy();
        presenter.onStop();
    }
}
