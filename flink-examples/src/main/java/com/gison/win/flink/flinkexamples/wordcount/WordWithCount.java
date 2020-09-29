package com.gison.win.flink.flinkexamples.wordcount;

import lombok.*;

/**
 * @author <a href="mailto:gisonwin@qq.com">GisonWin</a>
 * @date 2019/10/17 11:33
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WordWithCount {
    public String word;
    public long count;
}
