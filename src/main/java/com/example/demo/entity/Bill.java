package com.example.demo.entity;

import java.util.Date;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * (Bill)实体类
 *
 * @author makejava
 * @since 2022-06-18 17:42:45
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Bill对象", description="")
public class Bill implements Serializable {
    private static final long serialVersionUID = -37377062205907241L;

    private String carId;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    private String genaTime;
    
    private Integer chaId;
    
    private Integer chaNeed;
    
    private Integer chaDur;
    
    private String staTime;
    
    private String endTime;
    
    private Double chaFee;
    
    private Double serFee;
    
    private Double tolFee;

    public Double getTolFee() {
        return tolFee;
    }

    public void setTolFee(Double tolFee) {
        this.tolFee = tolFee;
    }

}

