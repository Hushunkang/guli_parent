# 说明：此数据库表结构等设计参照阿里巴巴Java开发手册的MySQL规约

# 特别强调一点
# edu_course_description表本身可以和edu_course表合二为一，但是为什么不这么做
# 参照《阿里巴巴Java开发手册》MySQL规约里面的一条说明
# varchar是可变长字符串，不预先分配存储空间，长度不要超过5000
# 如果存储长度大于此值，定义字段类型为text
# 独立出来一张表，用主键来对应，避免影响其它字段索引效率

# 以下是edu模块数据库Schema脚本
create schema guli collate utf8_general_ci;

create table edu_chapter
(
    id char(19) not null comment '章节ID'
        primary key,
    course_id char(19) not null comment '课程ID',
    title varchar(50) not null comment '章节名称',
    sort int unsigned default 0 not null comment '排序',
    gmt_create datetime not null comment '创建时间',
    gmt_modified datetime not null comment '更新时间'
)
    comment '课程章节表' charset=utf8mb4;

create index idx_course_id
    on edu_chapter (course_id);

create table edu_comment
(
    id char(19) not null comment '评价ID'
        primary key,
    teacher_id char(19) not null comment '讲师ID',
    course_id varchar(19) not null comment '课程id',
    member_id varchar(19) not null comment '会员ID',
    nickname varchar(50) null comment '会员昵称',
    avatar varchar(255) null comment '会员头像',
    content varchar(500) null comment '评价内容',
    is_deleted tinyint(1) unsigned default 0 not null comment '0（false）未删除，1（true）已删除',
    gmt_create datetime not null comment '创建时间',
    gmt_modified datetime not null comment '更新时间'
)
    comment '评价表' charset=utf8mb4;

create index idx_course_id
    on edu_comment (course_id);

create index idx_member_id
    on edu_comment (member_id);

create index idx_teacher_id
    on edu_comment (teacher_id);

create table edu_course
(
    id char(19) not null comment '课程ID'
        primary key,
    teacher_id char(19) not null comment '课程讲师ID',
    subject_parent_id char(19) null comment '一级课程分类ID',
    subject_id char(19) not null comment '二级课程分类ID',
    title varchar(50) not null comment '课程标题',
    price decimal(10,2) unsigned default 0.00 not null comment '课程销售价格，设置为0.00则可免费观看',
    lesson_num int unsigned default 0 not null comment '总课时',
    cover varchar(255) charset utf8 not null comment '课程封面图片路径',
    buy_count bigint(10) unsigned default 0 not null comment '销售数量',
    view_count bigint(10) unsigned default 0 not null comment '浏览数量',
    version bigint unsigned default 1 not null comment '乐观锁',
    status varchar(10) default 'Draft' not null comment '课程状态：Draft未发布，Normal已发布',
    is_deleted tinyint(1) unsigned default 0 null comment '0（false）未删除，1（true）已删除',
    gmt_create datetime not null comment '创建时间',
    gmt_modified datetime not null comment '更新时间'
)
    comment '课程表' charset=utf8mb4;

create index idx_subject_id
    on edu_course (subject_id);

create index idx_teacher_id
    on edu_course (teacher_id);

create index idx_title
    on edu_course (title);

create table edu_course_collect
(
    id char(19) not null comment '收藏ID'
        primary key,
    course_id char(19) not null comment '课程讲师ID',
    member_id char(19) default '' not null comment '课程专业ID',
    is_deleted tinyint(3) default 0 not null comment '逻辑删除 1（true）已删除， 0（false）未删除',
    gmt_create datetime not null comment '创建时间',
    gmt_modified datetime not null comment '更新时间'
)
    comment '课程收藏表' charset=utf8mb4;

create table edu_course_description
(
    id char(19) not null comment '课程ID'
        primary key,
    description mediumtext null comment '课程简介',
    gmt_create datetime not null comment '创建时间',
    gmt_modified datetime not null comment '更新时间'
)
    comment '课程简介表' charset=utf8mb4;

create table edu_subject
(
    id char(19) not null comment '课程科目ID'
        primary key,
    title varchar(10) not null comment '科目名称',
    parent_id char(19) default '0' not null comment '父ID，0表示课程科目为一级课程分类',
    sort int unsigned default 0 not null comment '排序',
    gmt_create datetime not null comment '创建时间',
    gmt_modified datetime not null comment '更新时间'
)
    comment '课程科目表' charset=utf8mb4;

create index idx_parent_id
    on edu_subject (parent_id);

create table edu_teacher
(
    id char(19) not null comment '讲师ID'
        primary key,
    name varchar(20) not null comment '讲师姓名',
    intro varchar(500) not null comment '讲师简介',
    career varchar(500) not null comment '讲师资历，一句话说明讲师',
    level int unsigned not null comment '头衔：1高级讲师，2首席讲师',
    avatar varchar(255) null comment '讲师头像',
    sort int unsigned default 0 not null comment '排序',
    is_deleted tinyint(1) unsigned default 0 not null comment '0（false）未删除，1（true）已删除',
    gmt_create datetime not null comment '创建时间',
    gmt_modified datetime not null comment '更新时间'
)
    comment '讲师表' charset=utf8mb4;

create table edu_video
(
    id char(19) not null comment '小节ID'
        primary key,
    course_id char(19) not null comment '课程ID',
    chapter_id char(19) not null comment '章节ID',
    title varchar(50) not null comment '小节名称',
    video_source_id varchar(100) null comment '云端视频ID',
    video_original_name varchar(100) null comment '原始视频文件名称',
    sort int unsigned default 0 not null comment '排序',
    play_count bigint unsigned default 0 not null comment '播放次数',
    is_free tinyint(1) unsigned default 0 not null comment '是否可以试听：0收费，1免费',
    duration float default 0 not null comment '视频时长（秒）',
    status varchar(20) default 'Empty' not null comment '视频状态：Empty未上传，Transcoding转码中，Normal正常',
    size bigint unsigned default 0 not null comment '视频源文件大小（字节）',
    version bigint unsigned default 1 not null comment '乐观锁',
    gmt_create datetime not null comment '创建时间',
    gmt_modified datetime not null comment '更新时间'
)
    comment '课程小节表' charset=utf8mb4;

create index idx_chapter_id
    on edu_video (chapter_id);

create index idx_course_id
    on edu_video (course_id);

# 以下是edu模块部分数据库Data脚本
INSERT INTO guli.edu_teacher (id, name, intro, career, level, avatar, sort, is_deleted, gmt_create, gmt_modified) VALUES ('1247566222313246722', '艾萨克·牛顿', '我不是巨人，我只是站在巨人的肩膀上。', '英格兰物理学家、数学家、天文学家、自然哲学家和炼金术士', 1, 'https://hsk-virtuoso-edu-guli.oss-cn-hangzhou.aliyuncs.com/305452.jpg', 1, 0, '2020-04-08 00:45:40', '2020-04-08 00:46:40');
INSERT INTO guli.edu_teacher (id, name, intro, career, level, avatar, sort, is_deleted, gmt_create, gmt_modified) VALUES ('1247567253004734466', '阿基米德', '给我一个支点，我可以举起整个地球。', '希腊化时代的数学家、物理学家、发明家、工程师、天文学家', 1, 'https://hsk-virtuoso-edu-guli.oss-cn-hangzhou.aliyuncs.com/305452.jpg', 1, 0, '2020-04-08 00:49:46', '2020-04-08 00:49:46');
INSERT INTO guli.edu_teacher (id, name, intro, career, level, avatar, sort, is_deleted, gmt_create, gmt_modified) VALUES ('1247567768535027713', '阿尔伯特·爱因斯坦', 'Try not to become a man of success,but rather try to become a man of value.', '犹太裔理论物理学家，他创立了现代物理学的两大支柱之一的相对论，也是质能等价公式的发现者', 1, 'https://hsk-virtuoso-edu-guli.oss-cn-hangzhou.aliyuncs.com/305452.jpg', 1, 0, '2020-04-08 00:51:49', '2020-04-08 00:51:49');
INSERT INTO guli.edu_teacher (id, name, intro, career, level, avatar, sort, is_deleted, gmt_create, gmt_modified) VALUES ('1247568595177820161', '卡尔·弗里德里希·高斯', '数学是科学的女王。', '德国数学家、物理学家、天文学家、大地测量学家，生于布伦瑞克，卒于哥廷根', 1, 'https://hsk-virtuoso-edu-guli.oss-cn-hangzhou.aliyuncs.com/305452.jpg', 1, 0, '2020-04-08 00:55:06', '2020-04-08 00:55:06');
INSERT INTO guli.edu_teacher (id, name, intro, career, level, avatar, sort, is_deleted, gmt_create, gmt_modified) VALUES ('1247569542729814017', '苏格拉底', '凡人都会死（大前提）。苏格拉底是人（小前提）。所以：苏格拉底是会死的（结论）。
Java是人发明的，人可以学的通Java（大前提）。你也是人（小前提）。所以：你一定可以学通Java（结论）。', '古希腊哲学家，和其追随者柏拉图及柏拉图的学生亚里士多德被并称为希腊三贤', 1, 'https://hsk-virtuoso-edu-guli.oss-cn-hangzhou.aliyuncs.com/305452.jpg', 1, 0, '2020-04-08 00:58:52', '2020-04-08 00:58:52');
INSERT INTO guli.edu_teacher (id, name, intro, career, level, avatar, sort, is_deleted, gmt_create, gmt_modified) VALUES ('1247570172139655169', '莱昂哈德·欧拉', '因为宇宙的结构是最完善的而且是最明智的上帝的创造，因此，如果在宇宙里没有某种极大的或极小的法则，那就根本不会发生任何事情。', '一位瑞士数学家和物理学家，近代数学先驱之一，他一生大部分时间在俄国和普鲁士度过', 1, 'https://hsk-virtuoso-edu-guli.oss-cn-hangzhou.aliyuncs.com/305452.jpg', 1, 0, '2020-04-08 01:01:22', '2020-04-08 01:01:40');

INSERT INTO guli.edu_subject (id, title, parent_id, sort, gmt_create, gmt_modified) VALUES ('1247728560211247106', '数学', '0', 0, '2020-04-08 11:30:45', '2020-04-08 11:30:45');
INSERT INTO guli.edu_subject (id, title, parent_id, sort, gmt_create, gmt_modified) VALUES ('1247728560471293954', '高等数学', '1247728560211247106', 0, '2020-04-08 11:30:45', '2020-04-08 11:30:45');
INSERT INTO guli.edu_subject (id, title, parent_id, sort, gmt_create, gmt_modified) VALUES ('1247728560748118018', '数学分析', '1247728560211247106', 0, '2020-04-08 11:30:45', '2020-04-08 11:30:45');
INSERT INTO guli.edu_subject (id, title, parent_id, sort, gmt_create, gmt_modified) VALUES ('1247728561041719298', '线性代数', '1247728560211247106', 0, '2020-04-08 11:30:45', '2020-04-08 11:30:45');
INSERT INTO guli.edu_subject (id, title, parent_id, sort, gmt_create, gmt_modified) VALUES ('1247728561234657281', '概率论与数理统计', '1247728560211247106', 0, '2020-04-08 11:30:45', '2020-04-08 11:30:45');
INSERT INTO guli.edu_subject (id, title, parent_id, sort, gmt_create, gmt_modified) VALUES ('1247728561410818050', '物理', '0', 0, '2020-04-08 11:30:45', '2020-04-08 11:30:45');
INSERT INTO guli.edu_subject (id, title, parent_id, sort, gmt_create, gmt_modified) VALUES ('1247728561595367425', '经典物理', '1247728561410818050', 0, '2020-04-08 11:30:45', '2020-04-08 11:30:45');
INSERT INTO guli.edu_subject (id, title, parent_id, sort, gmt_create, gmt_modified) VALUES ('1247728562018992129', '量子物理', '1247728561410818050', 0, '2020-04-08 11:30:45', '2020-04-08 11:30:45');
INSERT INTO guli.edu_subject (id, title, parent_id, sort, gmt_create, gmt_modified) VALUES ('1247728562484559873', '化学', '0', 0, '2020-04-08 11:30:45', '2020-04-08 11:30:45');
INSERT INTO guli.edu_subject (id, title, parent_id, sort, gmt_create, gmt_modified) VALUES ('1247728562660720642', '有机化学', '1247728562484559873', 0, '2020-04-08 11:30:45', '2020-04-08 11:30:45');
INSERT INTO guli.edu_subject (id, title, parent_id, sort, gmt_create, gmt_modified) VALUES ('1247728562870435842', '无机化学', '1247728562484559873', 0, '2020-04-08 11:30:45', '2020-04-08 11:30:45');
