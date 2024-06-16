create database if not exists bbs;

use bbs;

set foreign_key_checks = 1;
drop table if exists user;
create table user
(
    user_id  int unsigned not null primary key auto_increment,
    username varchar(50)  not null comment '用户名',
    password varchar(50)  not null comment '密码',
    phone    char(11) comment '电话号码（11位）',
    email    varchar(30) comment '电子邮箱',
    job      varchar(20) comment '工作性质',
    company  varchar(30) comment '工作地点'
) comment '用户表' character set = utf8mb4
                   collate = utf8mb4_0900_ai_ci;

insert into user
VALUES (null, 'wxs', '111111', '18279970860', '2217567783@qq.com', '后端开发', null);
insert into user
VALUES (null, 'cwt', '111111', '', '', '后端开发', null);
insert into user
VALUES (null, 'szq', '111111', '', '2217567783@qq.com', '前端开发', null);

drop table if exists category;
create table category
(
    category_id int unsigned not null primary key auto_increment,
    name        varchar(10)  not null comment '板块名'
) comment '板块表' character set = utf8mb4
                   collate = utf8mb4_0900_ai_ci;

insert into category
values (null, '日常');
insert into category
values (null, '技术');
insert into category
values (null, '分享');
insert into category
values (null, '需求');

drop table if exists post;
create table post
(
    post_id     int unsigned not null primary key auto_increment,
    user_id     int unsigned not null comment '发帖用户ID',
    category_id int unsigned not null comment '帖子板块ID',
    is_pinned   tinyint default 0 comment '0表示不置顶，1表示置顶',
    is_featured tinyint default 0 comment '0表示非精选，1表示精选',
    title       varchar(50)  not null comment '标题',
    content     longtext     not null comment '内容',
    create_time datetime     not null comment '发表时间',
    update_time datetime comment '更新时间',
    foreign key (user_id) references user (user_id),
    foreign key (category_id) references category (category_id)
) comment '帖子表' character set = utf8mb4
                   collate = utf8mb4_0900_ai_ci;

insert into post VALUES (null, 1, 2, 1, 1, '后端开发', 'TEST', '2024-6-14', null);

drop table if exists comment;
create table comment
(
    comment_id  int unsigned not null primary key auto_increment,
    user_id     int unsigned not null comment '评论用户ID',
    post_id     int unsigned not null comment '评论的帖子id',
    parent_id   int comment '父评论id（null为直接对帖子评论）',
    content     text         not null comment '评论内容',
    create_time datetime     not null comment '评论时间',
    foreign key (user_id) references user (user_id),
    foreign key (post_id) references post (post_id)
) comment '评论表' character set = utf8mb4
                   collate = utf8mb4_0900_ai_ci;

drop table if exists admin;
create table admin
(
    user_id  int unsigned not null primary key auto_increment,
    username varchar(50)  not null comment '管理员用户名',
    password varchar(50)  not null comment '管理员密码'
) comment '管理员表' character set = utf8mb4
                     collate = utf8mb4_0900_ai_ci;

insert into admin
VALUES (null, 'admin', '111111');
set foreign_key_checks = 1;
