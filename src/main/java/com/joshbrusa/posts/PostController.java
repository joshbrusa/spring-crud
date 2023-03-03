package com.joshbrusa.posts;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createPost(@RequestBody Post post) {
        postRepository.save(post);
        return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
    }

    @GetMapping("/read")
    public Iterable<Post> readPosts() {
        return postRepository.findAll();
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<?> readPost(@PathVariable Integer id) {
        Optional<Post> postOptional = postRepository.findById(id);

        if (postOptional.isEmpty()) {
            return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
        }

        Post post = postOptional.get();
        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HttpStatus> updatePost(@PathVariable Integer id, @RequestBody Post newPost) {
        Optional<Post> postOptional = postRepository.findById(id);

        if (postOptional.isEmpty()) {
            return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
        }

        Post post = postOptional.get();
        post.setTitle(newPost.getTitle());
        post.setBody(newPost.getBody());
        postRepository.save(post);
        return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deletePost(@PathVariable Integer id) {
        Optional<Post> postOptional = postRepository.findById(id);

        if (postOptional.isEmpty()) {
            return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
        }
        
        postRepository.deleteById(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
    }
}
