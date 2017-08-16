package com.monad.example.state;

import java.text.MessageFormat;

/**
 * Created by iurii.dziuban on 10.01.2017.
 */
public class Scp<S, A> {

    public S state;
    public A content;

    public Scp(S state, A content) {
        this.state = state;
        this.content = content;
    }

    @Override
    public String toString() {

        String template = "StateT: {0}, Content: {1}";
        return MessageFormat.format(template, state, content.toString());
    }
}
