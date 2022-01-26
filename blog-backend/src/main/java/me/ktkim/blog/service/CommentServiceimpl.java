package me.ktkim.blog.service;

import me.ktkim.blog.model.domain.Comment;
import me.ktkim.blog.model.domain.Post;
import me.ktkim.blog.model.dto.CommentDto;
import me.ktkim.blog.security.service.CustomUserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author Kim Keumtae
 */

public interface CommentServiceimpl {

//    @Autowired
//    private CommentRepository commentRepository;
//
//    @Autowired
//    private PostRepository postRepository;

    public Optional<Comment> findForId(Long id) ;

    public Optional<Post> findPostForId(Long id) ;

    public Optional<List<Comment>> findCommentsByPostId(Long id);

    public CommentDto registerComment(CommentDto commentDto, CustomUserDetails customUserDetails) ;
    public Optional<CommentDto> editPost(CommentDto editCommentDto);

    public void deletePost(Long id) ;
}
