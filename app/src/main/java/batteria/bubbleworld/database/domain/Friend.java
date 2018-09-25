package batteria.bubbleworld.database.domain;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Friend{
    @Id(autoincrement = true) private Long id;
    private int uid;
    private String nickname;
    private String password;
    private String portrait;
    private String motto;
    @Generated(hash = 39382860)
    public Friend(Long id, int uid, String nickname, String password,
            String portrait, String motto) {
        this.id = id;
        this.uid = uid;
        this.nickname = nickname;
        this.password = password;
        this.portrait = portrait;
        this.motto = motto;
    }
    @Generated(hash = 287143722)
    public Friend() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getUid() {
        return this.uid;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }
    public String getNickname() {
        return this.nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPortrait() {
        return this.portrait;
    }
    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
    public String getMotto() {
        return this.motto;
    }
    public void setMotto(String motto) {
        this.motto = motto;
    }

}
