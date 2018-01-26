package cn.v1.kanglewanjia.util;

import java.util.regex.Pattern;

/**
 * Created by qy on 2018/1/4.
 */

public class RegularUtil {

    public static boolean chechIdNum(String s_aStr) {//验正身份证
        String has_x="([0-9]{17}([0-9]|X|x))|([0-9]{15})";
        Pattern p = Pattern.compile(has_x);
        return p.matcher(s_aStr).matches();
    }
}
