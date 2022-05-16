/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2021/5/20 12:17:23                           */
/*==============================================================*/


drop table if exists comment;

drop table if exists favorite;

drop table if exists likecomment;

drop table if exists liketopic;

drop table if exists module;

drop table if exists topic;

drop table if exists user;

/*==============================================================*/
/* Table: comment                                               */
/*==============================================================*/
create table comment
(
   cid                  int not null auto_increment,
   uid                  int not null,
   tid                  int not null,
   content               TEXT,
   ctime                timestamp,
   clike               int default 0,
   num                  int default -1,
   primary key (cid)
);

/*==============================================================*/
/* Table: favorite                                              */
/*==============================================================*/
create table favorite
(
   tid                  int not null,
   uid                  int not null,
   ctime                timestamp,
   primary key (tid, uid)
);

/*==============================================================*/
/* Table: likecomment                                           */
/*==============================================================*/
create table likecomment
(
   cid                  int not null,
   uid                  int not null,
   primary key (cid, uid)
);

/*==============================================================*/
/* Table: liketopic                                             */
/*==============================================================*/
create table liketopic
(
   tid                  int not null,
   uid                  int not null,
   primary key (tid, uid)
);

/*==============================================================*/
/* Table: module                                                */
/*==============================================================*/
create table module
(
   mid                  int not null auto_increment,
   uid                  int not null,
   mname                char(40),
   intro                char(100) default '博主很懒，没有介绍',
   primary key (mid)
);

/*==============================================================*/
/* Table: topic                                                 */
/*==============================================================*/
create table topic
(
   tid                  int not null auto_increment,
   uid                  int not null,
   mid                  int not null,
   tname                char(40),
   intro                char(100) default '博主很懒，没有介绍',
   clike                 int default 0,
   primary key (tid)
);

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   uid                  int not null auto_increment,
   uname                char(20) not null,
   password             char(40) not null,
   sex                  tinyint default 0,
   age                  tinyint default -1,
   motto                char(40),
   email                char(40) unique not null,
   ctime                timestamp,
   primary key (uid)
);

alter table comment add constraint FK_tc foreign key (tid)
      references topic (tid) on delete cascade on update cascade;

alter table comment add constraint FK_uc foreign key (uid)
      references user (uid) on delete cascade on update cascade;

alter table favorite add constraint FK_tfavorite foreign key (tid)
      references topic (tid) on delete cascade on update cascade;

alter table favorite add constraint FK_ufavorite foreign key (uid)
      references user (uid) on delete cascade on update cascade;

alter table likecomment add constraint FK_clikecomment foreign key (cid)
      references comment (cid) on delete cascade on update cascade;

alter table likecomment add constraint FK_ulikecomment foreign key (uid)
      references user (uid) on delete cascade on update cascade;

alter table liketopic add constraint FK_tlike foreign key (tid)
      references topic (tid) on delete cascade on update cascade;

alter table liketopic add constraint FK_ulike foreign key (uid)
      references user (uid) on delete cascade on update cascade;

alter table module add constraint FK_um foreign key (uid)
      references user (uid) on delete cascade on update cascade;

alter table topic add constraint FK_mt foreign key (mid)
      references module (mid) on delete cascade on update cascade;

alter table topic add constraint FK_ut foreign key (uid)
      references user (uid) on delete cascade on update cascade;

