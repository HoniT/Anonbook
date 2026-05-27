package ge.mziuri.anonbook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Getter
public class PostResponseDto {
    private String content;
    private String imagePath;
    private Date createdAt;
    private List<CommentResponseDto> comments;
}
