package clases;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.TreeSet;

@Entity
@Table(name = "messages")
public class Message implements Serializable,Comparable<Message> {

    public Message() {
    }

    public Message(int id, int from, int to, String message, Time date) {
        this.id = id;
        this.fromid = from;
        this.toid = to;
        this.message = message;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", from=" + fromid +
                ", to=" + toid +
                ", message='" + message + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "fromId")
    int fromid;
    @Column(name = "toId")
    int toid;
    @Column(name = "message")
    String message;
    @Column( name = "time")
    Time date;

    public int getFrom() {
        return fromid;
    }


    public void setFrom(int from) {

        this.fromid = from;
    }

    public int getFromid() {
        return fromid;
    }

    public void setFromid(int fromid) {
        this.fromid = fromid;
    }

    public int getToid() {
        return toid;
    }

    public void setToid(int toid) {
        this.toid = toid;
    }

    public Time getDate() {
        return date;
    }

    public void setDate(Time date) {
        this.date = date;
    }

    public int getTo() {
        return toid;
    }

    public void setTo(int to) {
        this.toid = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Message(int from, int to, String message) {
        this.fromid = from;
        this.toid = to;
        this.message = message;
    }


public int compareTo(Message message){



    if (message.getId() == this.id){
        return 0;
    }


    if (this.date.compareTo(message.getDate()) >= 1){

        return 1 ;
    } else
    if (this.date.compareTo(message.getDate()) < 0){

        return -1 ;
    } else
        if(this.date.compareTo(message.getDate())  == 0){

            return 1;
        }


    return 1;
}


}
