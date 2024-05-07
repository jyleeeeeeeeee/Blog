package jylee.blog.app.repository;

import jylee.blog.app.entity.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TagRepositoryTest {
    @Autowired
    TagRepository repository;
    @Autowired
    private TagRepository tagRepository;

    @BeforeEach
    public void init() {
        tagRepository.save(new Tag("asdfasdf"));
    }
    @Test
    public void findTags() {
//        Optional<Tag> asdfasdf = repository.findFirstByContent("asdfasdf");
//        Optional<Tag> asdqqqqqfasdf = repository.findFirstByContent("qqqqq");
//        Tag asdfasdf1 = asdfasdf.orElse(tagRepository.save(new Tag("asdfasdf")));
//        Tag qqqqq2 = asdqqqqqfasdf.orElse(tagRepository.save(new Tag("qqqqq")));
//        System.out.println("asdqqqqqfasdf = " + asdqqqqqfasdf);
    }
}