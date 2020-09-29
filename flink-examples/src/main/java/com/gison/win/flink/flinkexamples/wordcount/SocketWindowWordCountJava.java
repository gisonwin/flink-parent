package com.gison.win.flink.flinkexamples.wordcount;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * @author <a href="mailto:gisonwin@qq.com">GisonWin</a>
 * @date 2019/10/16 16:03
 */
@Slf4j
public class SocketWindowWordCountJava {
    static void streamWordCountJava(int port) {
        //get flink running environment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //连接socket获取输入的数据.
        String delimiter = "\n";
        DataStreamSource<String> text = env.socketTextStream("localhost", port, delimiter);

        DataStream<WordWithCount> windowCounts = text.flatMap(
                new FlatMapFunction<String, WordWithCount>() {
                    @Override
                    public void flatMap(String value, Collector<WordWithCount> out) throws Exception {
                        String[] splits = value.split("\\s");
                        for (String word : splits) {
                            out.collect(new WordWithCount(word, 1L));
                        }
                    }
                }
        ).keyBy("word").timeWindow(Time.seconds(2), Time.seconds(1)).sum("count");
        windowCounts.print().setParallelism(1);
        try {
            env.execute("word count flink job");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }

    }

    public static void main(String[] args) {
        int port;
        try {
            ParameterTool parameterTool = ParameterTool.fromArgs(args);
            port = parameterTool.getInt("port");
        } catch (Exception ex) {
            port = 9000;
            log.error(ex.getMessage(), ex);
        }
        streamWordCountJava(port);
    }
}

