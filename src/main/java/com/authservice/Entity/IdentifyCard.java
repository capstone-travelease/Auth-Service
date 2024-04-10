package com.authservice.Entity;


import jakarta.persistence.*;

@Entity(name = "IdentifyCard")
@Table(name = "identify_card")
public class IdentifyCard {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Integer card_id;
     private Integer user_id;
     private String front;
     private String back;

    public IdentifyCard() {
    }

    public IdentifyCard(Integer card_id, Integer user_id, String front, String back) {
        this.card_id = card_id;
        this.user_id = user_id;
        this.front = front;
        this.back = back;
    }

    public Integer getCard_id() {
        return card_id;
    }

    public void setCard_id(Integer card_id) {
        this.card_id = card_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getFront() {
        return front;
    }

    public void setFront(String front) {
        this.front = front;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }

    @Override
    public String toString() {
        return "IdentifyCard{" +
                "card_id=" + card_id +
                ", user_id=" + user_id +
                ", front='" + front + '\'' +
                ", back='" + back + '\'' +
                '}';
    }
}
