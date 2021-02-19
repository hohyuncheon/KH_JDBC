--PRODUCT_STOCK
create table PRODUCT_STOCK(
PRODUCT_ID  VARCHAR2(30),
PRODUCT_NAME  VARCHAR2(30)  NOT NULL,
PRICE NUMBER(10)  NOT NULL,
DESCRIPTION VARCHAR2(50),
STOCK NUMBER DEFAULT 0,
constraint pk_pd_id primary key(PRODUCT_ID)
);

--PRODUCT_IO
create table PRODUCT_IO(
    IO_NO NUMBER,
    PRODUCT_ID VARCHAR2(30),
    IODATE DATE DEFAULT SYSDATE,
    AMOUNT NUMBER,
    STATUS CHAR(1),
    constraint CK_STATUS check(STATUS IN ('I', 'O')),
    constraint fk_pr_id foreign key(PRODUCT_ID) references PRODUCT_STOCK(PRODUCT_ID),
    constraint pk_io_no primary key(IO_NO)
);

--drop table PRODUCT_IO;
--drop table PRODUCT_STOCK;

--조회
select * from PRODUCT_STOCK;
select * from PRODUCT_IO;

--시퀀스 처리

create sequence seq_pro_stock_no
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
cache 20;

--제약조건
select constraint_name,
            uc.table_name,
            ucc.column_name,
            uc.constraint_type,
            uc.search_condition
from user_constraints uc
    join user_cons_columns ucc
        using(constraint_name)
where uc.table_name = 'PRODUCT_IO';


--트리거처리
--입출고 데이터가 insert되면, 해당상품의 재고수량을 변경하는 트리거
create or replace trigger trg_product
    before
    insert on PRODUCT_IO
    for each row
begin
    --입고
    if :new.status = 'I' then
        update PRODUCT_STOCK
        set STOCK = STOCK  + :new.amount
        where PRODUCT_ID = :new.PRODUCT_ID;
    --출고
    else 
        update PRODUCT_STOCK
        set STOCK = STOCK  - :new.amount
        where PRODUCT_ID = :new.PRODUCT_ID;
    end if;
end;
/


drop trigger trg_product;

commit;



