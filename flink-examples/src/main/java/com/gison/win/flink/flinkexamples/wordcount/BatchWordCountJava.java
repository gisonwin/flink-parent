package com.gison.win.flink.flinkexamples.wordcount;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

import java.net.URL;

/**
 * @author <a href="mailto:gisonwin@qq.com">GisonWin</a>
 * @date 2019/10/16 17:04
 */
@Slf4j
public class BatchWordCountJava {

    static void batchWordCountJava(String inputPath, String outputPath) {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        DataSource<String> source = env.readTextFile(inputPath);

        DataSet<Tuple2<String, Integer>> counts =
                source.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
                    @Override
                    public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception {
                        String[] split = value.toLowerCase().split("\\W+");
                        for (String word : split) {
                            if (word.length() > 0) {
                                out.collect(new Tuple2<String, Integer>(word, 1));
                            }
                        }
                    }
                }).groupBy(0).sum(1);

        counts.writeAsCsv(outputPath, "\n", " ").setParallelism(1);
        try {
            env.execute("batch word count");
        } catch (
                Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }


    public static void main(String[] args) {
        URL filePath = Thread.currentThread().getClass().getResource("/data/gatsby");
        String inputPath = filePath.getFile();
        int index = inputPath.lastIndexOf("/");
        String outputPath = inputPath.substring(0,index+1) +"out";
        log.debug(inputPath+"==> " + outputPath);
        try {
            batchWordCountJava(inputPath,outputPath);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

}
