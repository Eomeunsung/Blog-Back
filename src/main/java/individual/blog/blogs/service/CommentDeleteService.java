package individual.blog.blogs.service;

import individual.blog.domain.entity.Comment;

import java.util.List;
import java.util.Set;

public interface CommentDeleteService {

    Boolean commentDelete(Set<Comment> commentList);
}
