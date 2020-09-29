package com.gison.win.flink.flinkexamples.wordcount.lambda;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.operators.Order;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.core.fs.FileSystem;

import java.net.URL;
import java.util.stream.Stream;


/**
 * @author <a href="mailto:gisonwin@qq.com">GisonWin</a>
 * @date 2019/10/16 17:04
 */
@Slf4j
@ToString
public class BatchWordCountJavaLambda {
    /***
     * @param value
     * @return
     */
    static String[] split(String value) throws Exception {
        return value.toLowerCase().split("\\W+");
    }

    public static void main(String[] args) {
        URL filePath = Thread.currentThread().getClass().getResource("/data/gatsby");
        String inputPath = filePath.getFile();
        int index = inputPath.lastIndexOf("/");
        String outputPath = inputPath.substring(0, index + 1) + "out";
        try {
            batchWordCountLabmda(inputPath, outputPath);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

    }

    static void batchWordCountLabmda(String inputPath, String outputPath) {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        DataSource<String> source = env.readTextFile(inputPath);
        DataSet<Tuple2<String, Integer>> counts =
                //这里是lambda的形式,要注意returns返回值
                source.flatMap(
                        (FlatMapFunction<String, Tuple2<String, Integer>>) (value, out) -> {
                            Stream.of(split(value)).filter(word -> word.length() > 0).forEach(word -> out.collect(new Tuple2<String, Integer>(word, 1)));
                        }
                ).returns(Types.TUPLE(Types.STRING, Types.INT))
                        .groupBy(0).sum(1);
        //按计数倒序排列,这里要注意setParallelism(1),此处是用一个核处理,然后结果写到一个文件中.
        counts.sortPartition(1, Order.DESCENDING).setParallelism(1).writeAsCsv(outputPath, "\n", " ", FileSystem.WriteMode.OVERWRITE);
        try {
            env.execute("batch word count lambda");
        } catch (
                Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}


