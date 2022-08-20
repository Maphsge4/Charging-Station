package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Station对象", description="")
public class Station implements Serializable {
    private static final long serialVersionUID = 201716520053210993L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    private Integer type;
    
    private Integer state;
    
    private Integer totnum;
    
    private Integer tottime;
    
    private Integer totele;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTotnum() {
        return totnum;
    }

    public void setTotnum(Integer totnum) {
        this.totnum = totnum;
    }

    public Integer getTottime() {
        return tottime;
    }

    public void setTottime(Integer tottime) {
        this.tottime = tottime;
    }

    public Integer getTotele() {
        return totele;
    }

    public void setTotele(Integer totele) {
        this.totele = totele;
    }

}

