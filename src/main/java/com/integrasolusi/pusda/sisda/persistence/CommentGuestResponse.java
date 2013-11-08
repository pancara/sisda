package com.integrasolusi.pusda.sisda.persistence;

import java.io.Serializable;
import java.util.Date;

/**
 * Programmer : pancara
 * Date       : 4/14/13
 * Time       : 10:42 PM
 */
public class CommentGuestResponse implements Serializable {
    private Long id;
    private int version;
    private CommentGuest commentGuest;
    private String text;
    private Date postDate;
    private String by;

    public CommentGuestResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public CommentGuest getCommentGuest() {
        return commentGuest;
    }

    public void setCommentGuest(CommentGuest commentGuest) {
        this.commentGuest = commentGuest;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }
}
