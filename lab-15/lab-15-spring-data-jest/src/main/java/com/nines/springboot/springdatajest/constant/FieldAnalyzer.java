package com.nines.springboot.springdatajest.constant;

/**
 * @author tanyujie
 * @classname FieldAnalyzer
 * @description 必须给 Elasticsearch 安装 IK 插件
 * @date 2022/10/11 9:53
 * @since 1.0
 */
public class FieldAnalyzer {

    /**
     * IK 最大化分词
     *
     * 会将文本做最细粒度的拆分
     */
    public static final String IK_MAX_WORD = "ik_max_word";

    /**
     * IK 智能分词
     *
     * 会做最粗粒度的拆分
     */
    public static final String IK_SMART = "ik_smart";

}
