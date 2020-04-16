package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.repository.TagRepository;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag findByName(String name) {
        return ("".equals(name) || name == null) ? null : tagRepository.findByName(name);
    }

    public void save(Tag tag) {
        if(tag != null) {
            tagRepository.save(tag);
        }
    }
}
