package cn.heikaqiu.booktt.bean;

/**
 * @author HeiKaQiu
 * @create 2020-01-31 16:52
 */
public class BookType {

    private int typeid;
    private String typename;

    @Override
    public String toString() {
        return "BookType{" +
                "typeid=" + typeid +
                ", typename='" + typename + '\'' +
                '}';
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }
}
