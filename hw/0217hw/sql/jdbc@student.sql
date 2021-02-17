--====================================
-- 관리자 계정
--====================================
--student 계정 생성 및 권한부여
create user student
identified by student
default tablespace users;

grant connect, resource to student;

--====================================
-- STUDENT 계정
--====================================

create table member(
    member_id varchar2(20),
    password varchar2(20) not null,
    member_name varchar2(100) not null,
    gender char(1),
    age number,
    email varchar2(200),
    phone char(11) not null,
    address varchar2(1000),
    hobby varchar2(100),            --농구, 음악감상, 영화
    enroll_date date default sysdate,
    constraint pk_member_id primary key(member_id),
    constraint ck_member_gender check(gender in('M','F'))
);

insert into member
values(
    'honggd','1234','홍길동','M',33,'honggd@naver.com','01012341234',
    '서울 강남구 테헤란로', '등산,그림,요리', default
);
insert into member
values(
    'sinsa','1234','신사임당','F',48,'honggd@naver.com','01012344321',
    '서울 강동구 테헤란로', '음악감상', default
);
insert into member
values(
    'sejong','1234','세종','M',76,'honggd@naver.com','01099889988',
    '서울 중구', '육식', default
);
insert into member
values(
    'less','1234','이순신','M',45,'honggd@naver.com','01045452134',
    '전남여수', '목공예', default
);

select * from member;


--member 삭제 테이블 생성
--기존 컬럼에서 del_date컬럼 추가
create table member_del
as
select member.*, sysdate del_date from member
where 1=0;

--drop table member_del;

    
select *
from member_del;

select* from member;



--트리거 생성
create or replace trigger trig_member_del
 before
 delete on member
 for each row

begin
    dbms_output.put_line('이전하였습니다');
    
    insert into member_del
    values (:old.MEMBER_ID, :old.PASSWORD, :old.MEMBER_NAME, :old.GENDER, :old.AGE, :old.EMAIL, :old.PHONE,:old.ADDRESS,:old.HOBBY,
    :old.ENROLL_DATE, sysdate);

end;
/

--CK조건걸기
alter table member_del
add constraints CK_GENDER check(GENDER in('M','F'));

--SYSDATE조건걸기
alter table member_del
modify DEL_DATE DATE  default SYSDATE;

--제약조건검사
select constraint_name,
            uc.table_name,
            ucc.column_name,
            uc.constraint_type,
            uc.search_condition
from user_constraints uc
    join user_cons_columns ucc
        using(constraint_name)
where uc.table_name = 'MEMBER_DEL';


delete from member
    where member_id = '1';
