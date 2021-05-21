import java.io.Serializable;

public class Message implements Serializable {

    private Integer number;
    private String content;

    public Message(Integer number, String content) {
        this.number = number;
        this.content = content;
    }

    public void showMessage() {
        System.out.println( "Message nr "+number+": "+content);
    }

}
