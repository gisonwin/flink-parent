package com.gison.win.flink.flinkexamples.wordcount.lambda;

import com.gison.win.flink.flinkexamples.wordcount.WordWithCount;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;

import java.util.stream.Stream;

/**
 * @author <a href="mailto:gisonwin@qq.com">GisonWin</a>
 * @date 2019/10/16 16:03
 */
@Slf4j
@ToString
public class StreamWindowWordCountJavaLambda {
    public static void main(String[] args) {
        int port;
        try {
            ParameterTool parameterTool = ParameterTool.fromArgs(args);
            port = parameterTool.getInt("port");
        } catch (Exception ex) {
            port = 9000;
            log.error(ex.getMessage(), ex);
        }
        //get flink running environment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //连接socket获取输入的数据.
        String delimiter = "\n";
        DataStreamSource<String> text = env.socketTextStream("192.168.1.113", port, delimiter);

        DataStream<WordWithCount> windowCounts = text.flatMap(
                (FlatMapFunction<String, WordWithCount>) (value, out) -> {
                    Stream.of(value.split("\\s"))
                            .forEach(word -> out.collect(new WordWithCount(word, 1L)));
                }
        ).returns(Types.POJO(WordWithCount.class)).keyBy("word").timeWindow(Time.seconds(2), Time.seconds(1)).sum("count");
        windowCounts.print().setParallelism(1);
        try {
            env.execute("word count flink job");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
    }
}

