package com.basicfu.common.util

import java.util.regex.Matcher
import java.util.regex.Pattern

object RegexUtil {

    val PASSWORD="^[@A-Za-z0-9`~!@#\$%^&*()-_+=,<.>/?*+]{6,20}\$"
    val USERNAME="^(\\w){6,20}\$"
    val AMOUNT="^([1-9]\\d{0,18})([.]?|(\\.\\d{1,2})?)\$"

    /**非0正整数*/
    val INTEGER_POSITIVE="^[1-9]\\d*\$"

    val MOBILE = "^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\\d{8}$"
    /**
     * Integer正则表达式 ^-?(([1-9]\d*$)|0)
     */
    val INTEGER = "^-?(([1-9]\\d*$)|0)"
    /**
     * 正整数正则表达式 >=0 ^[1-9]\d*|0$
     */
    val INTEGER_NEGATIVE = "^[1-9]\\d*|0$"

    /**
     * Double正则表达式 ^-?([1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0)$
     */
    val DOUBLE = "^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$"
    /**
     * 正Double正则表达式 >=0  ^[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0$
     */
    val DOUBLE_NEGATIVE = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0$"
    /**
     * 负Double正则表达式 <= 0  ^(-([1-9]\d*\.\d*|0\.\d*[1-9]\d*))|0?\.0+|0$
     */
    val DOUBLE_POSITIVE = "^(-([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*))|0?\\.0+|0$"
    /**
     * 年龄正则表达式 ^(?:[1-9][0-9]?|1[01][0-9]|120)$ 匹配0-120岁
     */
    val AGE = "^(?:[1-9][0-9]?|1[01][0-9]|120)$"
    /**
     * 邮编正则表达式  [0-9]\d{5}(?!\d) 国内6位邮编
     */
    val CODE = "[0-9]\\d{5}(?!\\d)"
    /**
     * 匹配由数字、26个英文字母或者下划线组成的字符串 ^\w+$
     */
    val STR_ENG_NUM_ = "^\\w+$"
    /**
     * 匹配由数字和26个英文字母组成的字符串 ^[A-Za-z0-9]+$
     */
    val STR_ENG_NUM = "^[A-Za-z0-9]+"
    /**
     * 匹配由26个英文字母组成的字符串  ^[A-Za-z]+$
     */
    val STR_ENG = "^[A-Za-z]+$"
    /**
     * 过滤特殊字符串正则
     * regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
     */
    val STR_SPECIAL = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]"
    /***
     * 日期正则 支持：
     * YYYY-MM-DD
     * YYYY/MM/DD
     * YYYY_MM_DD
     * YYYYMMDD
     * YYYY.MM.DD的形式
     */
    val DATE_ALL = "((^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._]?)(10|12|0?[13578])([-\\/\\._]?)(3[01]|[12][0-9]|0?[1-9])$)" +
            "|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._]?)(11|0?[469])([-\\/\\._]?)(30|[12][0-9]|0?[1-9])$)" +
            "|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._]?)(0?2)([-\\/\\._]?)(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|(^([3579][26]00)" +
            "([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)" +
            "|(^([1][89][0][48])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|(^([2-9][0-9][0][48])([-\\/\\._]?)" +
            "(0?2)([-\\/\\._]?)(29)$)" +
            "|(^([1][89][2468][048])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|(^([2-9][0-9][2468][048])([-\\/\\._]?)(0?2)" +
            "([-\\/\\._]?)(29)$)|(^([1][89][13579][26])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|" +
            "(^([2-9][0-9][13579][26])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$))"
    /***
     * 日期正则 支持：
     * YYYY-MM-DD
     */
    val DATE_FORMAT1 = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)"

    /**
     * URL正则表达式
     * 匹配 http www ftp
     */
    val URL = "^(http|www|ftp|)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?" +
            "(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*" +
            "(\\w*:)*(\\w*\\+)*(\\w*\\.)*" +
            "(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$"

    /**
     * 身份证正则表达式
     */
    val IDCARD = "((11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65)[0-9]{4})" +
            "(([1|2][0-9]{3}[0|1][0-9][0-3][0-9][0-9]{3}" +
            "[Xx0-9])|([0-9]{2}[0|1][0-9][0-3][0-9][0-9]{3}))"

    /**
     * 机构代码
     */
    val JIGOU_CODE = "^[A-Z0-9]{8}-[A-Z0-9]$"

    /**
     * 匹配数字组成的字符串  ^[0-9]+$
     */
    val STR_NUM = "^[0-9]+$"

    private fun match(str: String?, pattern: String): Boolean {
        if(str.isNullOrEmpty()){
            return false
        }
        val p = Pattern.compile(pattern)
        val m = p.matcher(str)
        return m.matches()
    }

    fun isMobile(mobile: String?): Boolean {
        return match(mobile, MOBILE)
    }
    fun isPassword(password:String?):Boolean{
        return match(password, PASSWORD)
    }
    fun isUsername(username:String?):Boolean{
        return match(username, USERNAME)
    }
    fun isIntegerPositive(n:String?):Boolean{
        return match(n, INTEGER_POSITIVE)
    }
    fun isAmount(amount:String?):Boolean{
        return match(amount, AMOUNT)
    }
}