package ge.mziuri.anonbook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class CommentResponseDto {
    private String content;
    private Date createdAt;
}
