package individual_0;

public class Message {
    private int id;
    private String data;
    private int sender_id;
    private int receiver_id;
    private String date;
    

//    Constructors
    public Message() {
    }

    public Message(int id, String data, int sender_id, int receiver_id, String date) {
        this.id = id;
        this.data = data;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.date = date;
    }

    public Message(String data, int sender_id, int receiver_id, String date) {
        this.data = data;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.date = date;
    }

//    Setters & Getters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public int getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(int receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message: " + "id=" + id + ", data=" + data + ", sender_id=" + sender_id + ", receiver_id=" + receiver_id + ", date=" + date;
    }
    
    
}
