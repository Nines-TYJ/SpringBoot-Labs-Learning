package com.nines.springboot.springdatajest.dataobject;

import com.nines.springboot.springdatajest.constant.FieldAnalyzer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author tanyujie
 * @classname ESProductDO
 * @description TODO
 * @date 2022/10/11 9:52
 * @since 1.0
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Document(
        // 索引名
        indexName = "product",
        // 类型。未来的版本即将废弃
        type = "",
        // 默认索引分区数
        shards = 1,
        // 每个分区的备份数
        replicas = 0,
        // 刷新间隔
        refreshInterval = "-1"
)
public class ESProductDO {
    /**
     * ID 主键
     */
    @Id
    private Integer id;
    /**
     * SPU 名字
     */
    @Field(analyzer = FieldAnalyzer.IK_MAX_WORD, type = FieldType.Text)
    private String name;
    /**
     * 卖点
     */
    @Field(analyzer = FieldAnalyzer.IK_MAX_WORD, type = FieldType.Text)
    private String sellPoint;
    /**
     * 描述
     */
    @Field(analyzer = FieldAnalyzer.IK_MAX_WORD, type = FieldType.Text)
    private String description;
    /**
     * 分类编号
     */
    private Integer cid;
    /**
     * 分类名
     */
    @Field(analyzer = FieldAnalyzer.IK_MAX_WORD, type = FieldType.Text)
    private String categoryName;

}
