select *
from user;

# 시퀀스 생성 프로시저 , 함수

create table sequence (
                          name varchar(32),
                          currVal bigint unsigned
);

delimiter $$
create procedure `create_sequence` (IN the_name text)
    MODIFIES SQL DATA
    DETERMINISTIC
begin
    delete from sequence where name = the_name;
    insert into sequence values (the_name ,0);
end $$

delimiter $$
create function `nextVal` (the_name varchar(32))
    returns bigint unsigned
    modifies sql data
    deterministic
begin
    declare ret bigint unsigned;
    update sequence set currVal = currVal +1 where name = the_name;
    select currVal into  ret from sequence where name = the_name limit 1;
    return ret;
end $$

# set global log_bin_trust_function_creators=1; << super(root)로부터 권한을 받아야 function이 생성이 됨 따라서 ,
# root로 접속해서 해당 부분 설정해주어야함 cmd , workbench로 ,,

# modifies sql data deterministic >> sql최적화

# sequnce를 생성할 프로시저를 실행함
call create_sequence('sequence');

# dual은 더미테이블이고 값만 가져옴 .
select nextVal('sequence') from dual;

# --------테스트----------

create table test (
                      num bigint,
                      userId varchar(50)
);

select * from test;

drop table test;

insert into test values ((select nextVal('sequence') from dual) , '아이디');

insert into user values ((select nextVal('sequence') from dual) , 'test' , 'test2' , 'userPassword22' , 'userEmaildd');

select *
from user;

select * from user;

drop table user;

delete from user where userNum = 2;

# mysql 추후 수정하기
# 기본키로 설정
create table user (
                      userNum bigint primary key ,
                      userId varchar(50),
                      userNickName varchar(50) unique ,
                      userPassword varchar(50),
                      userEmail varchar(50)
);

create table board (
              boardNum bigint primary key ,
              userNum bigint,
              userNickName varchar(50),
              writeUser varchar(50),
                   foreign key (userNum)
                   references user(userNum) ,
                   foreign key (userNickName)
                   references user(userNickName)
);

create table img(
    imgNum bigint primary key ,
    boardNum bigint ,
    imgBinary varchar(255) ,
    foreign key (boardNum)
        references board(boardNum)
);

/**
  댓글 구현은 나중에
 */





# 제약조건 이름 걸기
# 추후수정하기
alter table user modify column userNum bigint primary key ;

select userId from user where userEmail = 'userEmailddd';

insert into user values ((select nextVal('sequence') from dual) , 'test' , 'test2' , 'userPassword22' , 'sdm0313@naver.com');

delete from user where userNum = 7;

select * from user;

drop table user;
drop table board;

select count(*) from user where userId = 'test';

select count(*) from user where userId = 'test';

create table board (
                      boardNum bigint primary key,
                      userNum bigint ,
                      userNickName varchar(100),
                      write varchar(100),
                      reply varchar(100)
);



