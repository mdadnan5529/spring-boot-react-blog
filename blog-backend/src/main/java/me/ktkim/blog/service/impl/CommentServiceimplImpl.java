package me.ktkim.blog.service.impl;

import me.ktkim.blog.common.Exception.BadRequestException;
import me.ktkim.blog.model.domain.Comment;
import me.ktkim.blog.model.domain.Post;
import me.ktkim.blog.model.domain.User;
import me.ktkim.blog.model.dto.CommentDto;
import me.ktkim.blog.repository.CommentRepository;
import me.ktkim.blog.repository.PostRepository;
import me.ktkim.blog.security.service.CustomUserDetails;
import me.ktkim.blog.service.CommentServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommentServiceimplImpl implements CommentServiceimpl {



    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    public CommentRepository getCommentRepository() {
        return commentRepository;
    }

    public void setCommentRepository(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public PostRepository getPostRepository() {
        return postRepository;
    }

    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Optional<Comment> findForId(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public Optional<Post> findPostForId(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public Optional<List<Comment>> findCommentsByPostId(Long id) {
        return commentRepository.findByPostId(id);
    }

    @Override
    public CommentDto registerComment(CommentDto commentDto, CustomUserDetails customUserDetails) {
        Optional<Post> postForId = this.findPostForId(commentDto.getPostId());
        if (postForId.isPresent()) {
            Comment newComment = new Comment();
            newComment.setBody(commentDto.getBody());
            newComment.setPost(postForId.get());
            newComment.setUser(new User(customUserDetails.getId(), customUserDetails.getName()));
            return new CommentDto(commentRepository.saveAndFlush(newComment));
        } else {
            throw new BadRequestException("Not exist post.");
        }
    }

    @Override
    public Optional<CommentDto> editPost(CommentDto editCommentDto) {
        return this.findForId(editCommentDto.getId())
                .map(comment -> {
                    comment.setBody(editCommentDto.getBody());
                    return comment;
                })
                .map(CommentDto::new);
    }

    @Override
    public void deletePost(Long id) {
        commentRepository.findById(id).ifPresent(comment -> {
            commentRepository.delete(comment);
        });
    }
}
