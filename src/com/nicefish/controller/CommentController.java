package com.nicefish.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicefish.model.Comment;
import com.nicefish.model.User;
import com.nicefish.service.CommentService;
import com.nicefish.util.base.IPUtil;
import com.nicefish.util.base.UUIDUtil;
import com.nicefish.util.consts.ConstSessionName;
import com.nicefish.util.page.Result;

@Controller
@RequestMapping("/comments")
public class CommentController {
	
	private final static ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	private CommentService commentService;

	@RequestMapping(value = "/postid/{postid}", method = RequestMethod.GET)
	@ResponseBody
	public String PostFindByPid(@PathVariable("postid")String postid) throws Exception {
        List<Comment> list = commentService.findByPostId(postid);
        return objectMapper.writeValueAsString(list);
    }
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public Result<String> CommentAdd(Comment comment,HttpServletRequest request,HttpSession session){
		Result<String> result = new Result<String>();
		User user = (User)session.getAttribute(ConstSessionName.UserInfo);
		comment.setCommentId(UUIDUtil.generate());
		comment.setUserId(user.getUserId());
		comment.setUserName(user.getUserName());
		comment.setNickName(user.getNickName());
		comment.setCommentIp(IPUtil.getIpAddress(request));
		int flag = commentService.insert(comment);
		if(flag > 0){
			result.setStatus(flag);
			result.setMsg("评论成功！");
			return result;
		}
		result.setStatus(-1);
		result.setMsg("评论失败！");
		return result;
	}
}
