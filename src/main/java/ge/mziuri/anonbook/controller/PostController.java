package ge.mziuri.anonbook.controller;

import ge.mziuri.anonbook.mapper.PostMapper;
import ge.mziuri.anonbook.model.dto.PostDto;
import ge.mziuri.anonbook.model.dto.PostResponseDto;
import ge.mziuri.anonbook.model.entity.Post;
import ge.mziuri.anonbook.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts().stream().map(PostMapper::toResponseDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(PostMapper.toResponseDto(postService.getPostById(id)));
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> addPost(@ModelAttribute PostDto post) {
        if(post.getContent() == null || post.getContent().isBlank())
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body("Post content is needed");

        postService.savePost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping("/image/{fileName}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) {
        try {
            Resource resource = postService.loadImageAsResource(fileName);
            if (resource == null) {
                return ResponseEntity.notFound().build();
            }
            String contentType = postService.getContentType(fileName);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
