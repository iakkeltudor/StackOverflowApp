package com.utcn.StackOverflow.request;

import com.utcn.StackOverflow.entity.Tag;

import java.util.List;

public class TagFilterRequest {
    private Tag tag;

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
