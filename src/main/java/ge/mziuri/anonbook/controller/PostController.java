package ge.mziuri.anonbook.controller;

import ge.mziuri.anonbook.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
public class PostController {
    @Autowired
    private PostService postService;


}
