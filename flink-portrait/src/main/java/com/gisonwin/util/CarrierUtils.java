package com.gisonwin.util;


import java.util.regex.Pattern;

/**
 * @author <a href="mailto:gisonwin@qq.com">GisonWin</a>
 * @date 2019/11/22 9:09
 */
public class CarrierUtils {
    //133 153 180 181  189 177 1700 173 199
    private static final String CHINA_TELECOM_PATTERN="(^1(33|53|77|73|99|8[019])\\d{8}$)|(^1700\\d{7}$)";
    // 130 131 132 155 156 185 186 145 176 1709
    private static final String CHINA_UNICOM_PATTERN="(^1(3[0-2]|4[5]|5[56]|7[6]|8[56]))\\d{8}$ | (^1709\\d{7}$)";
    //134 135 136 137 138 139 147 150 151 152 157 158 159 178 1705 182 183 184 187 188
    private static final String CHINA_MOBILE_PATTERN="(^1(3[4-9]|4[7]|5[0-27-9]|7[8]|8[2-478])\\d{8}$)|(^1705\\d{7}$)";

    public static int getCarrierByTel(String tel){
        //中国电信
        if (tel == null || tel.trim().equals("")? false :match(CHINA_TELECOM_PATTERN,tel)) {
            return 1;
        }
        //中国联通
        if (tel == null || tel.trim().equals("")? false :match(CHINA_UNICOM_PATTERN,tel)) {
            return 2;
        }
        //中国移动
        if (tel == null || tel.trim().equals("")? false :match(CHINA_MOBILE_PATTERN,tel)) {
            return 3;
        }
        //未知运营商
        return 0;
    }

    /***
     *
     * @param regex
     * @param tel
     * @return
     */
    private static boolean match(String regex,String tel){
        return Pattern.matches(regex,tel);
    }


}
