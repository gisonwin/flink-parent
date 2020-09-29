package com.gison.win.flink.flinkexamples.wordcount.lambda;


import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;

import java.util.concurrent.TimeUnit;

public class ElementWindowWordCount {

    public static void main(String[] args) throws Exception {


        // get the execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // get input data by connecting to the socket
        DataStream<String> text = env.fromElements( "To be, or not to be,--that is the question:--", "Whether 'tis nobler in the mind to suffer", "The slings and arrows of outrageous fortune", "Or to take arms against a sea of troubles," );


        // parse the data, group it, window it, and aggregate the counts
        DataStream<WordWithCount> windowCounts = text
                .flatMap((FlatMapFunction<String, WordWithCount>) (value, out) -> {
                    for (String word : value.split("\\s")) {
                        out.collect(new WordWithCount(word, 1L));
                        TimeUnit.SECONDS.sleep(1);
                    }
                })
                .returns(Types.POJO(WordWithCount.class))
                .keyBy(x->x.word)
                .timeWindow(Time.seconds(4), Time.seconds(1))
                .reduce((ReduceFunction<WordWithCount>) (a, b) -> new WordWithCount(a.word, a.count + b.count));

        // print the results with a single thread, rather than in parallel
        windowCounts.print().setParallelism(1);

        env.execute("Element Window WordCount");
    }

    // Data type for words with count
    public static class WordWithCount {

        public String word;
        public long count;

        public WordWithCount() {}

        public WordWithCount(String word, long count) {
            this.word = word;
            this.count = count;
        }

        @Override
        public String toString() {
            return word + " : " + count;
        }

        @Override
        public int hashCode() {
            StringBuilder sb = new StringBuilder();
            sb.append(word);
            sb.append(count);
            char[] charArr = sb.toString().toCharArray();
            int hash = 0;
            for(char c : charArr) {
                hash = hash * 131 + c;
            }
            return hash;
        }
    }
}