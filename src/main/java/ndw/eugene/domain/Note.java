package ndw.eugene.domain;

import java.util.Date;

public class Note {


    private int id;
    private String header;
    private String text;
    private boolean isRead;
    private Date createTime;
    private User user;

    public Note() {
        this.createTime = new Date();
    }

    public Note(String header, String text, int id) {
        this();
        this.id = id;
        this.header = header;
        this.text = text;
        this.isRead = false;
    }

    public Note(String header, String text, boolean isRead, int id){
        this();
        this.id = id;
        this.header = header;
        this.text = text;
        this.isRead = isRead;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setIsRead(boolean read) {
        isRead = read;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString(){
        return "Note: header: " + header + " text: " + text + " createTime: " + createTime + ";";
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }

        if(obj == null){
            return false;
        }

        if(getClass() != obj.getClass()){
            return false;
        }

        Note n =(Note)obj;

        if(this.getId()!=n.getId()){
            return false;
        }

        if (!this.getHeader().equals(n.getHeader())){
            return false;
        }

        if (!this.getText().equals(n.getText())){
            return false;
        }

        if(this.isRead()!=n.isRead()){
            return false;
        }

        return true;
    }
}
