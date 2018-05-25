package com.sppan.base.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.sppan.base.entity.support.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tb_log")
public class Log extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     *  id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     *  action
     */
    private String action;

    /**
     * 分类
     */
    private String targetType;

    /**
     *  备注
     */
    private String remark;

    /**
     *  ip
     */
    private String ip;

    /**
     * 操作日期
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @OneToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
    @JoinTable(name = "tb_log_user", joinColumns = { @JoinColumn(name = "log_id") }, inverseJoinColumns = { @JoinColumn(name = "user_id") })
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
