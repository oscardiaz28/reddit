package com.spring.reddit.exceptions;

public class RedditException  extends RuntimeException{
    public RedditException(String exMessage){
        super(exMessage);
    }
}
