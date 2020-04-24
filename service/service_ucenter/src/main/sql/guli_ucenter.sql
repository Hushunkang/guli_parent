# 说明：此数据库表结构等设计参照阿里巴巴Java开发手册的MySQL规约

# 以下是ucenter模块数据库Schema脚本
create table ucenter_member
(
    id char(19) not null comment '会员ID'
        primary key,
    open_id varchar(128) null comment '微信OPENID',
    mobile varchar(11) null comment '会员手机号',
    password varchar(255) null comment '会员密码',
    nickname varchar(50) null comment '会员昵称',
    sex tinyint(1) unsigned null comment '性别：1女，2男',
    age tinyint unsigned null comment '年龄',
    avatar varchar(255) null comment '用户头像',
    sign varchar(100) null comment '用户签名',
    is_disabled tinyint(1) default 0 not null comment '是否禁用：0（false）未禁用，1（true）已禁用',
    is_deleted tinyint(1) default 0 not null comment '0（false）未删除，1（true）已删除',
    gmt_create datetime not null comment '创建时间',
    gmt_modified datetime not null comment '更新时间'
)
    comment '会员表' charset=utf8mb4;
