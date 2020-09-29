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
public class BatchWordCountJavaLambda_CN {
    /***
     * 判断传进来的是中文还是英文,并分别返回数组.
     * 每个文件编码格式不一致，如UTF-8、GBK等。其中，UTF-8 中文用三个字节表示，GBK 中文用两个字节表示。
     * 并且中文的字节是负数的。可以根据这个原理，把字符串转化为字节数组，判断最后字符是否为中文。
     * 如果是英文，则直接分割返回。否则，循环遍历字节数组，并作相应的负数统计并进行求模。
     * @param value
     * @return
     */
    static String[] split(String value) throws Exception {
        byte[] bytes = value.getBytes("UTF-8");
        int byteLeng = 3;
        int size = bytes.length / byteLeng;
        byte[] word = new byte[byteLeng];
        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            System.arraycopy(bytes, i * byteLeng, word, 0, byteLeng);
            String wordStr = new String(word, "UTF-8");
            log.debug("==> " + wordStr);
            sb.append(wordStr).append(",");
        }
        String trim = sb.toString().trim();
        int length = trim.length();
        trim = trim.substring(0, length - 1);
        return trim.split(",");
    }

    public static void main(String[] args) {
        try {
            URL filePath = Thread.currentThread().getClass().getResource("/data/chineses");
            String inputPath = filePath.getFile();
            int index = inputPath.lastIndexOf("/");
            String outputPath = inputPath.substring(0, index + 1) + "out";
            batchWordCountLabmda(inputPath, outputPath);
//            String value = "，。，。";
//            System.out.println(split(value));
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

    }

    static void batchWordCountLabmda(String inputPath, String outputPath) {
        ExecutionEnvironment charEnv = ExecutionEnvironment.getExecutionEnvironment();
        DataSource<String> source = charEnv.readTextFile(inputPath);
        DataSet<Tuple2<String, Long>> charCounts =
                //这里是lambda的形式,要注意returns返回值
                source.flatMap(
                        (FlatMapFunction<String, Tuple2<String, Long>>) (value, out) -> {
                            Stream.of(split(value)).filter(w -> w.length() > 0)
                                    .forEach(w -> out.collect(new Tuple2<String, Long>(w, 1L)));
                        }
                ).returns(Types.TUPLE(Types.STRING, Types.LONG))
                        .groupBy(0).sum(1);
        //按计数倒序排列,这里要注意setParallelism(1),此处是用一个核处理,然后结果写到一个文件中.
        charCounts.sortPartition(1, Order.DESCENDING).setParallelism(1).writeAsCsv(outputPath, "\n", "===>", FileSystem.WriteMode.OVERWRITE);
        try {
            charEnv.execute("batch word count chinese ");
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}


