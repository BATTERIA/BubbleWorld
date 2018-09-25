package batteria.bubbleworld.entity;

public class User {
    private int uid;
    private String nickname;
    private String password;
    private String portrait;
    private String motto;

    public User(int uid, String nickname, String password, String portrait, String motto) {
        this.uid=uid;
        this.nickname=nickname;
        this.password=password;
        this.portrait=portrait;
        this.motto=motto;
    }

    public User(String nickname, String portrait, String motto) {
        this.nickname=nickname;
        this.portrait=portrait;
        this.motto=motto;
    }

    public int getUid() {
        return uid;
    }
    public void setUid(int uid) {
        this.uid=uid;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname=nickname;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password=password;
    }
    public String getPortrait() {
        return portrait;
    }
    public void setPortrait(String portrait) {
        this.portrait=portrait;
    }
    public String getMotto() {
        return motto;
    }
    public void setMotto(String motto) {
        this.motto=motto;
    }
}
