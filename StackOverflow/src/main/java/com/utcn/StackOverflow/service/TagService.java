package com.utcn.StackOverflow.service;

import com.utcn.StackOverflow.entity.Tag;
import com.utcn.StackOverflow.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public Tag saveTag(Tag tag) {
        Tag existingTag = tagRepository.findByName(tag.getName());
        if (existingTag == null) {
            return tagRepository.save(tag);
        } else {
            return existingTag;
        }
    }
}
