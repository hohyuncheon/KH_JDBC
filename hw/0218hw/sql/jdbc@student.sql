create table stock(
    no number,
    product varchar2(100) not null,
    Quantity number default 0,
    constraint pk_no primary key(no)
);

--컬럼 주석달기
comment on column stock.no is '품목번호';
comment on column stock.product is '품목';
comment on column stock.Quantity is '현재재고';


--
create table upsert_stock(
    no number,
    product varchar2(100) not null,
    Quantity number default 0,
    modification_date  date default sysdate
);

drop table upsert_stock;
----------------
select * from upsert_stock;
select * from stock;


insert into stock 
values (1, '품목1', 2);
insert into stock 
values (2, '품목2', 3);


update stock
set Quantity = 160
where no = '1';
--------------
--시퀀스 처리

create sequence seq_stock_no
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
cache 20;


--insert into tb_names 
--values(seq_stock_no.nextval, '홍길동');
---------------
--트리거처리
create or replace trigger trg_stock
    before
    insert or update of quantity on stock
    for each row
begin
       insert into upsert_stock
       values (:new.no, :new.product, :new.quantity, sysdate);
end;
/
drop trigger trg_stock;


