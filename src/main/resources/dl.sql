create table TBL_USER (
                          uno bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
                          userId varchar(255)  ,
                          userNickName varchar(255),
                          userPassword varchar(255),
                          userEmail varchar(255)
);

CREATE TABLE TBL_BOARD(
                          bno bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
                          uno bigint NOT NULL,
                          content varchar(5000),
                          date datetime default current_timestamp,
                          thumbnail varchar(500)
);
ALTER TABLE TBL_BOARD ADD CONSTRAINT FK_uno FOREIGN KEY (uno) REFERENCES TBL_USER(uno) on DELETE CASCADE;

insert into tbl_board(bno,uno,content) values (NULL,1 , 'contentTest');


create table tbl_boardPhoto (
                                pno bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                bno bigint ,
                                photoRandomName varchar(1000)
);
ALTER TABLE TBL_boardPhoto ADD CONSTRAINT FK_bno FOREIGN KEY (bno) REFERENCES tbl_board(bno)
    on DELETE CASCADE;





create table tbl_reply(
                          rno bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
                          bno bigint ,
                          content varchar(255),
                          userId varchar(255),
                          date datetime default current_timestamp
);
ALTER TABLE TBL_reply ADD CONSTRAINT FK_rno FOREIGN KEY (bno) REFERENCES TBL_board(bno)
    on DELETE CASCADE;

select * from tbl_user;
select * from tbl_boardPhoto;
select * from tbl_board;

drop table tbl_user;
drop table tbl_boardPhoto;
drop table tbl_board;

insert into tbl_user (userId, userNickName, userPassword, userEmail) value ('a' , 'scott' , 'a' , 'sdm0313@naver.com');
insert into tbl_user (userId, userNickName, userPassword, userEmail) value ('b' , 'scott1' , 'b' , 'sdm0314@naver.com');
insert into tbl_user (userId, userNickName, userPassword, userEmail) value ('c' , 'scott2' , 'c' , 'sdm0315@naver.com');

insert into tbl_board (uno, content, thumbnail) value (1 , 'dummyBoardContent' , 'binary');
insert into tbl_board (uno, content, thumbnail) value (1 , 'dummyBoardContent1' , 'binary1');
insert into tbl_board (uno, content, thumbnail) value (2 , 'dummyBoardContent2' , 'binary2');
insert into tbl_board (uno, content, thumbnail) value (2 , 'dummyBoardContent3' , 'binary3');

insert into tbl_boardPhoto (bno, photoRandomName) value (1, 'photoRandomNameBinary1');
insert into tbl_boardPhoto (bno, photoRandomName) value (1 , 'photoRandomNameBinary2');
insert into tbl_boardPhoto (bno, photoRandomName) value (1 , 'photoRandomNameBinary3');

SELECT * FROM tbl_board A INNER JOIN tbl_boardPhoto B ON A.bno = B.bno where  A.bno = 13;

truncate tbl_reply;
truncate tbl_board;
truncate tbl_boardPhoto;

select * from tbl_board;
select * from tbl_boardPhoto;
delete from tbl_board where bno = 12;