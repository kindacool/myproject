-- 게시판
select * from tab;
select * from seq;
select * from myboard;
select count(*) from myboard;

insert into myboard values(myboard_seq.nextval,'Lay','1234','Practice1',
	'Content',0,sysdate);

-- 테이블명 : myboard
create table myboard(
	  no number primary key,
	  writer varchar2(20),
      passwd varchar2(20),
	  subject varchar2(50),
	  content varchar2(100),
	  readcount number,
	  register date );

-- 시퀀스명 : myboard_seq
create sequence myboard_seq;