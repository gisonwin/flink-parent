package com.gisonwin.kmeans;

import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;

import java.util.Random;

/**
 * @author <a href="mailto:gisonwin@qq.com">GisonWin</a>
 * @date 2019/11/22 9:31
 */
public class KMeansMap implements MapFunction<String, KMeansInfo> {
    @Override
    public KMeansInfo map(String value) throws Exception {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        Random random = new Random();
        String[] split = value.split(",");
        String var1 = split[0];
        String var2 = split[1];
        String var3 = split[2];
        KMeansInfo kMeansInfo = new KMeansInfo();
        kMeansInfo.setVariable1(var1);
        kMeansInfo.setVariable2(var2);
        kMeansInfo.setVariable3(var3);

        kMeansInfo.setGroupbyfield("logic" + random.nextInt(10));

        return kMeansInfo;
    }
}
