# 说明：此数据库表结构等设计参照阿里巴巴Java开发手册的MySQL规约

# 以下是cms模块数据库Schema脚本
create table cms_banner
(
    id char(19) not null comment 'ID'
        primary key,
    title varchar(20) not null comment '标题',
    image_url varchar(500) not null comment '图片地址',
    link_url varchar(500) null comment '链接地址',
    sort int unsigned default 0 not null comment '排序',
    is_deleted tinyint(1) unsigned default 0 not null comment '0（false）未删除，1（true）已删除',
    gmt_create datetime not null comment '创建时间',
    gmt_modified datetime not null comment '更新时间',
    constraint uk_name
        unique (title)
)
    comment '首页banner表' charset=utf8mb4;
