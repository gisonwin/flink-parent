package com.gisonwin.micro.controller;

import com.alibaba.fastjson.JSONObject;
import com.gisonwin.micro.entity.ResultMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="mailto:gisonwin@qq.com">GisonWin</a>
 * @date 2019/11/23 20:55
 */
@RestController
@RequestMapping("info")
public class InfoInController {

    @GetMapping(value="helloword")
    public String helloworld(HttpServletRequest req){
        String ip = req.getRemoteAddr();
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setMessage("hello:"+ip);
        resultMessage.setStatus("success");
        return JSONObject.toJSONString(resultMessage);
    }
}
