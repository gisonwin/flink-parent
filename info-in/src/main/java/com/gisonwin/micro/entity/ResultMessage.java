package com.gisonwin.micro.entity;

import lombok.*;

import java.io.Serializable;

/**
 * @author <a href="mailto:gisonwin@qq.com">GisonWin</a>
 * @date 2019/11/23 20:57
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResultMessage implements Serializable {
    private String status;//fail success
    private String message;//message content
}
