package ge.mziuri.anonbook.controller;

import ge.mziuri.anonbook.model.dto.CommentDto;
import ge.mziuri.anonbook.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<?> addComment(@RequestBody CommentDto comment) {
        if(comment.getContent() == null || comment.getContent().isBlank())
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body("Comment content is needed");

        commentService.addComment(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
}
