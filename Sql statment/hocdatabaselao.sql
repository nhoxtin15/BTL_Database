drop database hotpot;

Create database hotpot;
use hotpot;

SET FOREIGN_KEY_CHECKs=0;
SET GLOBAL FOREIGN_KEY_CHECKs=0; 

create table Employee (
	SSN		varchar(9)	primary key,
	Sex		char,
	Work_start_date 	datetime,
	FName	varchar(20)	not null,
	LName		varchar(20),
	Salary		int,
	Birthdate	datetime	
);

create table Employee_Phone_number (
	SSN	varchar(9),
	Phone_number	varchar(10),
	primary key (SSN, Phone_number),
	foreign key (SSN) references Employee (SSN)
);

create table Address (
	SSN	varchar(9),
	street_address varchar(50),
	district		varchar(50),
	Province	varchar(50),
	primary key	(SSN, street_address, district, Province),
	foreign key (SSN) references Employee (SSN)
);

create table Manage (
	SSN_waiter	varchar(9)	primary key,
	SSN_manager	varchar(9) not null,
	foreign key (SSN_waiter) references Employee (SSN),
	foreign key (SSN_manager) references Employee (SSN)
);

create table Customer (
	Cus_ID	char(9)	primary key,
	SSN		varchar(9) unique not null,
	Sex		char,
	FName	varchar(20)	not null,
	LName		varchar(20),
	DOB		date,
    acc_point int default 0
);

create table Customer_Phone_number (
	Cus_ID	char(9),
	Phone_number	varchar(10),
	primary key (Cus_ID, Phone_number),
	foreign key (Cus_ID) references Customer (Cus_ID) on delete cascade
);

create table receipt (
	Receipt_ID char(9) primary key,
    Date_receipt datetime,
    Number_of_customer INT,
    receipt_status char,
    total_price int not null default 0,
    Ssn char(9),
    Cus_ID char(9),
    foreign key (Ssn) references Employee(Ssn),
    foreign key (Cus_ID) references Customer(Cus_ID)
);

create table area (
	Area_ID char(7) primary key,
    area_name varchar(20)
);

create table Vip_room (
	available bool default true,
	Room_code char(6) primary key,
    Min_guest_number INT,
    Max_guest_number INT,
    Area_ID char(7),
    constraint Area_ID_VIP foreign key (Area_ID) references Area(Area_ID)
 );
 
create table Table_book (
	available bool default true,
	Order_num INT,
    Area_ID char(7),
    No_seat INT,
    primary key (Order_num, Area_ID),
    foreign key (Area_ID) references Area(Area_ID) on delete cascade
);

create table review(
	review_ID int primary key auto_increment,
	rv_comment varchar(255),
    score int(1) not null check (score >= 1 and score <= 5),
    rv_date date
);


create table product(
	product_id varchar(6),
    product_name varchar(50),
    image varchar(100),
    price integer,
    primary key (product_id)
);

create table food_and_drinks(
	product_id varchar(6),
    foreign key (product_id) references product(product_id) on delete cascade
);

create table soup_base(
	product_id varchar(6),
    spicy_level integer,
    foreign key (product_id) references product(product_id) on delete cascade
);

create table flavor(
	product_id varchar(6),
    flavor varchar(20),
    foreign key (product_id) references soup_base(product_id) on delete cascade
);

create table Combo(
	product_id VARChar(6),
    Number_guest_recommend integer,
    time_start decimal(4,2),
    time_end decimal(4,2),
    ratio integer,
    foreign key (product_id) references product(product_id) on delete cascade
);

create table Combo_include(
	Food_drink_id VARChar(6),
	Combo_ID VARChar(6),
    foreign key (Food_drink_id) references food_and_drinks(product_id),
    foreign key (Combo_ID) references Combo(product_id)
);
create table evaluate(
	Receipt_ID char(9),
    Product_ID varchar(6),
    Review_ID int not null,
    primary key (receipt_ID, product_id),
	constraint sk1 unique (receipt_ID, review_id),
	constraint sk2 unique (product_ID, review_id),
    foreign key (receipt_id) references receipt(receipt_id),
	foreign key (product_id) references product(product_id),
	foreign key (review_id) references review(review_id)
);

create table include_room(
	receipt_id char(9),
    room_code char(6),
    product_id varchar(6),
    quantity int,
    primary key (receipt_id, room_code, product_id),
    foreign key (receipt_id) references Receipt(receipt_id),
    foreign key (room_code) references vip_room(room_code),
    foreign key (product_id) references product(product_id)
);

create table include_table(
	receipt_id char(9),
    area_id char(6),
    order_num int,
    product_id varchar(6),
    quantity int,
    primary key (receipt_id, area_id, order_num, product_id),
	foreign key (receipt_id) references Receipt(receipt_id),
    foreign key (area_id,order_num) references table_book(area_id,order_num),
	foreign key (product_id) references product(product_id)
);

-- create user 'sManager'@'localhost' identified by '123456' ;
-- grant all privileges on hotpot.* to 'sManager'@'localhost';

-- table for auto increment for customer --
create table customer_sequencing(
	Num int not null auto_increment primary key
);

create table receipt_sequencing(
	Num int auto_increment primary key
);

delimiter //
create trigger insert_receipt_id
before insert on receipt
for each row
begin 
	insert into receipt_sequencing values (NULL);
    set new.receipt_id = concat ('R', LPAD(last_insert_id(), 8 ,'0'));
end //
delimiter ;

-- ok

delimiter //
create trigger insert_customer_id
before insert on customer
for each row
begin 
	insert into customer_sequencing values(NULL);
    set new.cus_id = concat ('C', LPAD(last_insert_id(), 8, '0'));
end//


delimiter ;
-- ok
delimiter //
create trigger before_insert_evaluate
before insert on evaluate
for each row
begin 
	declare cnt int;
	select count(*) into cnt 
    from (
		select product_id, receipt_id
        from include_room
        union all
        select product_id, receipt_id
        from include_table
    ) as combine_tables
    where new.product_id = combine_tables.product_id and new.receipt_id = combine_tables.receipt_id;
    if cnt = 0 then
		signal sqlstate '45000'
        set message_text = 'Evaluation cannot be inserted for products that are not included in the receipt';
	end if;
end //
delimiter ;


delimiter //
create trigger after_insert_include_room
after insert on include_room
for each row
begin
	declare product_price int;
    select price into product_price
    from product
    where new.product_id = product_id;
	update receipt
    set	total_price = total_price + (product_price * new.quantity) 
    where receipt_id = new.receipt_id;
end //
delimiter ;

delimiter //
create trigger after_insert_include_table
after insert on include_table
for each row
begin
	declare product_price int;
    select price into product_price
    from product
    where new.product_id = product.product_id;
	update receipt
    set	total_price = total_price + (product_price * new.quantity)
    where receipt_id = new.receipt_id;
end //
delimiter ;
-- checkkk
delimiter //

create trigger phu_thu
before update on receipt
for each row
begin 
	declare temp_price int;
    declare numguest int;
    declare codee int default -1;
    declare areaa char(6);
    declare codeee char(6);
    if old.receipt_status = 'N' and new.receipt_status = 'E' then 
		select Number_of_customer into numguest
		from receipt r
		where r.receipt_id = old.receipt_id;
		
		if numguest*250000 > old.total_price then
			set new.total_price = old.total_price + (numguest*250000 - old.total_price)*0.2;
		end if;
        -- change status of the table/vip_room of the receipt into available
		UPDATE table_book 
		SET available = true
		WHERE EXISTS (
			SELECT 1
			FROM receipt r 
			LEFT OUTER JOIN include_table i ON r.receipt_id = i.receipt_id 
			WHERE old.receipt_id = r.receipt_id
			AND table_book.order_num = i.order_num
			AND table_book.area_id = i.area_id);

		
		update vip_room 
		set available = true
		where vip_room.room_code in (select room_code 
								from receipt r left outer join include_room i
								on r.receipt_id = i.receipt_id 
								where old.receipt_id = r.receipt_id);
		
    end if;
end//
delimiter ;
delimiter //

create trigger update_acc_point
before update on receipt
for each row
begin 
	declare p int;
	if old.receipt_status = 'N' and new.receipt_status = 'E' then
		select acc_point into p
		from customer c
        where old.cus_id = c.cus_id;
    
		update customer 
		set acc_point = p + new.total_price/100
		where cus_id = old.cus_id;
	end if;
end// 
delimiter ;

delimiter //

create trigger check_manager_experience
before insert on manage
for each row
begin
    declare manager_work_duration int;
    
    select timestampdiff(year, (select work_start_date from employee where ssn = new.ssn_manager), now()) into manager_work_duration;
    
    if manager_work_duration < 2 then
        signal sqlstate '45000' set message_text = 'manager must have more than 2 years of experience.';
    end if;
end //

delimiter ;


INSERT INTO Employee 
VALUES ('079345678', 'F', '2022-01-01', 'Huong', 'Vu Quynh', 52000, '2002-01-01');
INSERT INTO Employee 
VALUES('079456789', 'M', '2022-01-02', 'Loc', 'Tuan Ly', 55000, '2001-01-01');
INSERT INTO Employee 
VALUES('079567890', 'F', '2022-01-03', 'Nga', 'Huynh', 52000, '2000-01-01');
INSERT INTO Employee 
VALUES('070678901', 'M', '2022-01-04', 'Thinh', 'Phuong Xuong', 52000, '1999-01-01');
INSERT INTO Employee 
VALUES('081234567', 'M', '2022-01-10', 'Minh', 'Nguyen Thanh', 39000, '1993-01-01');
INSERT INTO Employee 
VALUES('056789012', 'M', '2022-01-05', 'Trung', 'Tran Quoc', 34000, '1998-01-01');
INSERT INTO Employee 
VALUES('067890123', 'M', '2022-01-06', 'Hieu', 'Tran Quoc', 33000, '1998-01-01');
INSERT INTO Employee 
VALUES('078901234', 'F', '2022-01-07', 'Thuong', 'Nguyen Ngoc Song', 36000, '1996-01-01');
INSERT INTO Employee 
VALUES('089012345', 'F', '2022-01-08', 'Phuong', 'Huynh Lan', 37000, '1995-01-01');
INSERT INTO Employee 
VALUES('090123456', 'M', '2022-01-09', 'Hoang', 'Huynh Van Anh', 38000, '1994-01-01');
INSERT INTO Employee 
VALUES('012345688', 'M', '2022-01-11', 'Thanh', 'Nguyen Huu', 40000, '1992-01-01');
INSERT INTO Employee 
VALUES('023456789', 'M', '2022-01-12', 'Tam', 'Nguyen Duc', 31000, '1991-01-01');
INSERT INTO Employee 
VALUES('074567890', 'M', '2022-01-13', 'Khang', 'Truong Minh', 32000, '1990-01-01');
INSERT INTO Employee 
VALUES('075678901', 'M', '2022-01-14', 'Phat', 'Thai Quang', 33000, '1989-01-01');
INSERT INTO Employee 
VALUES('076789012', 'F', '2022-01-15', 'Nhi', 'Phan Tran Y', 34000, '1988-01-01');
INSERT INTO Employee 
VALUES('077890123', 'F', '2022-01-16', 'Uyen', 'Nguyen Ngoc Nha', 35000, '1987-01-01');
INSERT INTO Employee 
VALUES('078901734', 'M', '2022-01-17', 'Phu', 'Nguyen Quang', 36000, '1986-01-01');
INSERT INTO Employee 
VALUES('019012345', 'M', '2022-01-18', 'Khoa', 'Nguyen Ngoc Dinh', 37000, '1985-01-01');
INSERT INTO Employee 
VALUES('070123456', 'M', '2022-01-19', 'Khoi', 'Nguyen Ngoc', 38000, '1984-01-01');
INSERT INTO Employee 
VALUES('071234567', 'M', '2022-01-20', 'Khoa', 'Nguyen Anh', 36000, '1983-01-01');



INSERT INTO Employee_Phone_number VALUES ('079345678', '0123456789');
INSERT INTO Employee_Phone_number VALUES ('079456789', '0123456788');
INSERT INTO Employee_Phone_number VALUES ('079567890', '0123456787');
INSERT INTO Employee_Phone_number VALUES ('070678901', '0123456786');
INSERT INTO Employee_Phone_number VALUES ('081234567', '0123456785');
INSERT INTO Employee_Phone_number VALUES ('056789012', '0123456784');
INSERT INTO Employee_Phone_number VALUES ('067890123', '0123456783');
INSERT INTO Employee_Phone_number VALUES ('078901234', '0123456782');
INSERT INTO Employee_Phone_number VALUES ('089012345', '0123456781');
INSERT INTO Employee_Phone_number VALUES ('090123456', '0123456780');
INSERT INTO Employee_Phone_number VALUES ('012345688', '0123456779');
INSERT INTO Employee_Phone_number VALUES ('023456789', '0123456778');
INSERT INTO Employee_Phone_number VALUES ('074567890', '0123456777');
INSERT INTO Employee_Phone_number VALUES ('075678901', '0123456776');
INSERT INTO Employee_Phone_number VALUES ('076789012', '0123456775');
INSERT INTO Employee_Phone_number VALUES ('077890123', '0123456774');
INSERT INTO Employee_Phone_number VALUES ('078901734', '0123456773');
INSERT INTO Employee_Phone_number VALUES ('019012345', '0123456772');
INSERT INTO Employee_Phone_number VALUES ('070123456', '0123456771');
INSERT INTO Employee_Phone_number VALUES ('071234567', '0123456770');



INSERT INTO Address
VALUES ('079345678', '123 Nguyen Hue Street', 'District 1', 'Ho Chi Minh City');
INSERT INTO Address 
VALUES ('079456789', '456 Le Loi Street', 'District 2', 'Ho Chi Minh City');
INSERT INTO Address 
VALUES ('079567890', '789 Vo Van Tan Street', 'District 3', 'Ho Chi Minh City');
INSERT INTO Address 
VALUES ('070678901', '101 Nguyen Van Cu Street', 'District 4', 'Ho Chi Minh City');
INSERT INTO Address
VALUES ('081234567', '222 Tran Hung Dao Street', 'District 5', 'Ho Chi Minh City');
INSERT INTO Address
VALUES ('056789012', '333 Nguyen Trai Street', 'District 6', 'Ho Chi Minh City');
INSERT INTO Address
VALUES ('067890123', '444 Tran Phu Street', 'District 7', 'Ho Chi Minh City');
INSERT INTO Address
VALUES ('078901234', '555 Le Van Sy Street', 'District 8', 'Ho Chi Minh City');
INSERT INTO Address 
VALUES ('089012345', '666 Cach Mang Thang Tam Street', 'District 9', 'Ho Chi Minh City');
INSERT INTO Address 
VALUES ('090123456', '777 Pham Ngu Lao Street', 'District 10', 'Ho Chi Minh City');
INSERT INTO Address 
VALUES ('012345688', '888 Dien Bien Phu Street', 'District 11', 'Ho Chi Minh City');
INSERT INTO Address 
VALUES ('023456789', '999 Vo Thi Sau Street', 'District 12', 'Ho Chi Minh City');
INSERT INTO Address 
VALUES ('074567890', '1010 Phan Van Tri Street', 'Binh Thanh District', 'Ho Chi Minh City');
INSERT INTO Address 
VALUES ('075678901', '1111 Nguyen Huu Tho Street', 'Thu Duc District', 'Ho Chi Minh City');
INSERT INTO Address 
VALUES ('076789012', '1212 Pham Van Dong Street', 'Tan Binh District', 'Ho Chi Minh City');
INSERT INTO Address
VALUES ('077890123', '1313 Nguyen Thi Minh Khai Street', 'Go Vap District', 'Ho Chi Minh City');
INSERT INTO Address 
VALUES ('078901734', '1414 Hoang Dieu Street', 'Tan Phu District', 'Ho Chi Minh City');
INSERT INTO Address 
VALUES ('019012345', '1515 Le Quang Dinh Street', 'Binh Tan District', 'Ho Chi Minh City');
INSERT INTO Address 
VALUES ('070123456', '1616 Vo Van Kiet Street', 'Cu Chi District', 'Ho Chi Minh City');
INSERT INTO Address
VALUES ('071234567', '1717 Nguyen Van Linh Street', 'Nha Be District', 'Ho Chi Minh City');

-- Huong manager
INSERT INTO Manage (SSN_waiter, SSN_manager) 
VALUES ('081234567', '079345678'); 
INSERT INTO Manage (SSN_waiter, SSN_manager) 
VALUES ('056789012', '079345678');
INSERT INTO Manage (SSN_waiter, SSN_manager) 
VALUES ('067890123', '079345678'); 
INSERT INTO Manage (SSN_waiter, SSN_manager) 
VALUES ('078901234', '079345678'); 
-- Loc manager
INSERT INTO Manage (SSN_waiter, SSN_manager) 
VALUES ('089012345', '079456789'); 
INSERT INTO Manage (SSN_waiter, SSN_manager) 
VALUES ('090123456', '079456789'); 
INSERT INTO Manage (SSN_waiter, SSN_manager) 
VALUES ('012345688', '079456789'); 
INSERT INTO Manage (SSN_waiter, SSN_manager) 
VALUES ('023456789', '079456789'); 
-- Nga manager
INSERT INTO Manage (SSN_waiter, SSN_manager) 
VALUES ('074567890', '079567890'); 
INSERT INTO Manage (SSN_waiter, SSN_manager) 
VALUES ('075678901', '079567890'); 
INSERT INTO Manage (SSN_waiter, SSN_manager) 
VALUES ('076789012', '079567890');
INSERT INTO Manage (SSN_waiter, SSN_manager) 
VALUES ('077890123', '079567890');
-- Thinh manager
INSERT INTO Manage (SSN_waiter, SSN_manager) 
VALUES ('078901734', '070678901'); 
INSERT INTO Manage (SSN_waiter, SSN_manager) 
VALUES ('019012345', '070678901'); 
INSERT INTO Manage (SSN_waiter, SSN_manager) 
VALUES ('070123456', '070678901'); 
INSERT INTO Manage (SSN_waiter, SSN_manager) 
VALUES ('071234567', '070678901'); 
-- Insert 30  Customer(ssn, sex, FName, Lname, dob)s into the  Customer(ssn, sex, FName, Lname, dob) table
INSERT INTO  Customer(ssn, sex, FName, Lname, dob)
VALUES ('111111111', 'M', 'Phuc', 'Tran Pham Gia', '2002-05-15');
INSERT INTO  Customer(ssn, sex, FName, Lname, dob) 
VALUES ('222222222', 'F', 'Khue', 'Nong Thuc', '1985-08-20');
INSERT INTO  Customer(ssn, sex, FName, Lname, dob) 
VALUES ('333333333', 'M', 'Duy', 'Pham Dinh Bao', '1992-02-10');
INSERT INTO  Customer(ssn, sex, FName, Lname, dob) 
VALUES ('444444444', 'F', 'Phuong', 'Cao Que', '1988-11-25');
INSERT INTO  Customer(ssn, sex, FName, Lname, dob) 
VALUES ('555555555', 'F', 'Linh', 'Tran Gia', '1994-07-30');
INSERT INTO  Customer(ssn, sex, FName, Lname, dob) 
VALUES ('666666666', 'M', 'Kiet', 'Truong Tuan', '1987-04-12');
INSERT INTO  Customer(ssn, sex, FName, Lname, dob) 
VALUES ('777777777', 'M', 'Kien', 'Le Trung', '1991-09-05');
INSERT INTO  Customer(ssn, sex, FName, Lname, dob) 
VALUES ('888888888', 'M', 'Anh', 'Chu Thai', '1986-12-18');
INSERT INTO  Customer(ssn, sex, FName, Lname, dob) 
VALUES ('999999999', 'M', 'Khang', 'Nguyen Tien', '1993-03-22');
INSERT INTO  Customer(ssn, sex, FName, Lname, dob) 
VALUES ('000000000', 'F', 'Chau', 'Vo Thi Ngoc', '1989-06-28');
INSERT INTO  Customer(ssn, sex, FName, Lname, dob) 
VALUES ('111111112', 'F', 'Thu', 'Le Thi Bao', '1995-01-07');
INSERT INTO  Customer(ssn, sex, FName, Lname, dob) 
VALUES ('222222223', 'M', 'Khang', 'Mac Ho Do', '1984-10-14');
INSERT INTO  Customer(ssn, sex, FName, Lname, dob) 
VALUES ('333333334', 'M', 'Quan', 'Phan Hong', '1990-05-15');
INSERT INTO  Customer(ssn, sex, FName, Lname, dob) 
VALUES ('444444445', 'F', 'Nguyen', 'Nguyen Thi Quoc', '1985-08-20');
INSERT INTO  Customer(ssn, sex, FName, Lname, dob) 
VALUES ('555555556', 'M', 'Khuong', 'Nguyen An', '1992-02-10');
INSERT INTO  Customer(ssn, sex, FName, Lname, dob) 
VALUES ('666666667', 'M', 'Thinh', 'Nguyen Tien', '1988-11-25');
INSERT INTO  Customer(ssn, sex, FName, Lname, dob) 
VALUES ('777777778', 'M', 'Du', 'Nguyen Hao Hong', '1994-07-30');
INSERT INTO  Customer(ssn, sex, FName, Lname, dob) 
VALUES ('888888889', 'F', 'Van', 'Le Thanh', '1987-04-12');
INSERT INTO  Customer(ssn, sex, FName, Lname, dob) 
VALUES ('999999990', 'M', 'Thinh', 'Tran Ngoc', '1991-09-05');
INSERT INTO  Customer(ssn, sex, FName, Lname, dob) 
VALUES ('000000001', 'M', 'Tri', 'Nguyen Huu', '1986-12-18');
INSERT INTO  Customer(ssn, sex, FName, Lname, dob) 
VALUES ('111111113', 'F', 'Minh', 'Tu Khanh', '1993-03-22');
INSERT INTO  Customer(ssn, sex, FName, Lname, dob) 
VALUES ('222222224', 'F', 'Huong', 'Nguyen Thi Minh', '1989-06-28');
INSERT INTO  Customer(ssn, sex, FName, Lname, dob) 
VALUES ('333333335', 'M', 'Khang', 'Mac Ho Do', '1995-01-07');
INSERT INTO  Customer(ssn, sex, FName, Lname, dob) 
VALUES ('444444446', 'M', 'Kiet', 'Tran Tuan', '1984-10-14');
INSERT INTO  Customer(ssn, sex, FName, Lname, dob) 
VALUES ('555555557', 'M', 'Phuc', 'Dao Anh', '1990-05-15');
INSERT INTO  Customer(ssn, sex, FName, Lname, dob) 
VALUES ('666666668', 'F', 'Ariana', 'Grande', '1985-08-20');
INSERT INTO  Customer(ssn, sex, FName, Lname, dob) 
VALUES ('777777779', 'M', 'Loc', 'Nguyen Thien', '1992-02-10');
INSERT INTO  Customer(ssn, sex, FName, Lname, dob) 
VALUES ('888888880', 'F', 'Nhi', 'Nguyen Duc Hanh', '1988-11-25');
INSERT INTO  Customer(ssn, sex, FName, Lname, dob) 
VALUES ('999999991', 'M', 'Hung', 'Huynh Gia', '1994-07-30');
INSERT INTO  Customer(ssn, sex, FName, Lname, dob) 
VALUES ('000000002', 'M', 'Gordon', 'Ramsay', '1987-04-12');




INSERT INTO  Customer_Phone_number (Cus_ID, Phone_number)
VALUES 
    ('000000001', '1234567890'),
    ('000000002', '2345678901'),
    ('000000003', '3456789012'),
    ('000000004', '4567890123'),
    ('000000005', '5678901234'),
    ('000000006', '6789012345'),
    ('000000007', '7890123456'),
    ('000000008', '8901234567'),
    ('000000009', '9012345678'),
    ('000000010', '0123456789'),
    ('000000011', '9876543210'),
    ('000000012', '8765432109'),
    ('000000013', '7654321098'),
    ('000000014', '6543210987'),
    ('000000015', '5432109876'),
    ('000000016', '4321098765'),
    ('000000017', '3210987654'),
    ('000000018', '2109876543'),
    ('000000019', '1098765432'),
    ('000000020', '0987654321'),
    ('000000021', '9876543210'),
    ('000000022', '8765432109'),
    ('000000023', '7654321098'),
    ('000000024', '6543210987'),
    ('000000025', '5432109876'),
    ('000000026', '4321098765'),
    ('000000027', '3210987654'),
    ('000000028', '2109876543'),
    ('000000029', '1098765432'),
    ('000000030', '0987654321');



INSERT INTO area (Area_ID, area_name) VALUES
('GARD01', 'Garden Area 1'),
('GARD02', 'Garden Area 2'),
('INDO01', 'Indoor Area 1'),
('OUTD01', 'Outdoor Area 1'),
('POOL01', 'Pool Area 1'),
('POOL02', 'Pool Area 2');



INSERT INTO VIP_room (Room_code, Min_guest_number, Max_guest_number, Area_ID)
VALUES
('VIP000', 10, 14, 'GARD01'),
('VIP001', 3, 7, 'GARD01'),
('VIP002', 7, 17, 'GARD01'),
('VIP003', 6, 21, 'GARD01'),
('VIP004', 7, 10, 'GARD01'),
('VIP005', 2, 8, 'GARD01'),
('VIP006', 4, 10, 'GARD01'),
('VIP007', 4, 14, 'GARD01'),
('VIP008', 5, 13, 'GARD01'),
('VIP009', 15, 19, 'GARD01'),
('VIP010', 7, 9, 'GARD02'),
('VIP011', 5, 16, 'GARD02'),
('VIP012', 5, 10, 'GARD02'),
('VIP013', 9, 19, 'GARD02'),
('VIP014', 11, 18, 'GARD02'),
('VIP015', 5, 9, 'GARD02'),
('VIP016', 14, 21, 'GARD02'),
('VIP017', 12, 15, 'GARD02'),
('VIP018', 5, 22, 'GARD02'),
('VIP019', 13, 17, 'GARD02'),
('VIP020', 11, 14, 'INDO01'),
('VIP021', 5, 16, 'INDO01'),
('VIP022', 6, 7, 'INDO01'),
('VIP023', 9, 14, 'INDO01'),
('VIP024', 5, 11, 'INDO01'),
('VIP025', 5, 7, 'INDO01'),
('VIP026', 10, 20, 'INDO01'),
('VIP027', 15, 22, 'INDO01'),
('VIP028', 4, 11, 'INDO01'),
('VIP029', 7, 21, 'INDO01'),
('VIP030', 4, 18, 'OUTD01'),
('VIP031', 5, 8, 'OUTD01'),
('VIP032', 6, 9, 'OUTD01'),
('VIP033', 5, 13, 'OUTD01'),
('VIP034', 7, 11, 'OUTD01'),
('VIP035', 8, 14, 'OUTD01'),
('VIP036', 8, 12, 'OUTD01'),
('VIP037', 13, 13, 'OUTD01'),
('VIP038', 4, 9, 'OUTD01'),
('VIP039', 6, 11, 'OUTD01'),
('VIP040', 6, 12, 'POOL01'),
('VIP041', 12, 17, 'POOL01'),
('VIP042', 3, 9, 'POOL01'),
('VIP043', 4, 19, 'POOL01'),
('VIP044', 11, 14, 'POOL01'),
('VIP045', 12, 22, 'POOL01'),
('VIP046', 6, 14, 'POOL01'),
('VIP047', 5, 21, 'POOL01'),
('VIP048', 5, 11, 'POOL01'),
('VIP049', 4, 12, 'POOL01'),
('VIP050', 6, 18, 'POOL02'),
('VIP051', 7, 21, 'POOL02'),
('VIP052', 12, 13, 'POOL02'),
('VIP053', 9, 15, 'POOL02'),
('VIP054', 9, 11, 'POOL02'),
('VIP055', 8, 14, 'POOL02'),
('VIP056', 15, 17, 'POOL02'),
('VIP057', 3, 12, 'POOL02'),
('VIP058', 5, 16, 'POOL02'),
('VIP059', 8, 15, 'POOL02');




INSERT INTO Table_book (Order_num, Area_ID, No_seat) VALUES
(0000, 'GARD01', 10),
(0001, 'GARD01', 3),
(0002, 'GARD01', 7),
(0003, 'GARD01', 6),
(0004, 'GARD01', 7),
(0005, 'GARD01', 8),
(0006, 'GARD01', 4),
(0007, 'GARD01', 4),
(0008, 'GARD01', 5),
(0009, 'GARD01', 15),
(0010, 'GARD01', 7),
(0011, 'GARD01', 5),
(0012, 'GARD01', 5),
(0013, 'GARD01', 9),
(0014, 'GARD01', 11),
(0015, 'GARD01', 5),
(0016, 'GARD01', 14),
(0017, 'GARD01', 12),
(0018, 'GARD01', 5),
(0019, 'GARD01', 13),
(0020, 'GARD01', 11),
(0000, 'GARD02', 5),
(0001, 'GARD02', 6),
(0002, 'GARD02', 9),
(0003, 'GARD02', 11),
(0004, 'GARD02', 5),
(0005, 'GARD02', 10),
(0006, 'GARD02', 15),
(0007, 'GARD02', 4),
(0008, 'GARD02', 7),
(0009, 'GARD02', 4),
(0010, 'GARD02', 5),
(0011, 'GARD02', 6),
(0012, 'GARD02', 5),
(0013, 'GARD02', 7),
(0014, 'GARD02', 8),
(0015, 'GARD02', 8),
(0016, 'GARD02', 13),
(0017, 'GARD02', 4),
(0018, 'GARD02', 6),
(0019, 'GARD02', 6),
(0020, 'GARD02', 12),
(0000, 'INDO01', 3),
(0001, 'INDO01', 4),
(0002, 'INDO01', 11),
(0003, 'INDO01', 12),
(0004, 'INDO01', 6),
(0005, 'INDO01', 5),
(0006, 'INDO01', 5),
(0007, 'INDO01', 4),
(0008, 'INDO01', 6),
(0009, 'INDO01', 7),
(0010, 'INDO01', 12),
(0011, 'INDO01', 9),
(0012, 'INDO01', 9),
(0013, 'INDO01', 8),
(0014, 'INDO01', 15),
(0015, 'INDO01', 3),
(0016, 'INDO01', 5),
(0017, 'INDO01', 15),
(0018, 'INDO01', 4),
(0019, 'INDO01', 14),
(0020, 'INDO01', 8),
(0000, 'OUTD01', 3),
(0001, 'OUTD01', 14),
(0002, 'OUTD01', 8),
(0003, 'OUTD01', 7),
(0004, 'OUTD01', 4),
(0005, 'OUTD01', 14),
(0006, 'OUTD01', 10),
(0007, 'OUTD01', 10),
(0008, 'OUTD01', 6),
(0009, 'OUTD01', 8),
(0010, 'OUTD01', 7),
(0011, 'OUTD01', 11),
(0012, 'OUTD01', 5),
(0013, 'OUTD01', 13),
(0014, 'OUTD01', 6),
(0015, 'OUTD01', 3),
(0016, 'OUTD01', 15),
(0017, 'OUTD01', 13),
(0018, 'OUTD01', 12),
(0019, 'OUTD01', 6),
(0020, 'OUTD01', 4),
(0000, 'POOL01', 5),
(0001, 'POOL01', 4),
(0002, 'POOL01', 3),
(0003, 'POOL01', 4),
(0004, 'POOL01', 6),
(0005, 'POOL01', 6),
(0006, 'POOL01', 4),
(0007, 'POOL01', 11),
(0008, 'POOL01', 6),
(0009, 'POOL01', 3),
(0010, 'POOL01', 10),
(0011, 'POOL01', 11),
(0012, 'POOL01', 6),
(0013, 'POOL01', 4),
(0014, 'POOL01', 3),
(0015, 'POOL01', 16),
(0016, 'POOL01', 17),
(0017, 'POOL01', 8),
(0018, 'POOL01', 8),
(0019, 'POOL01', 7),
(0020, 'POOL01', 6),
(0000, 'POOL02', 11),
(0001, 'POOL02', 12),
(0002, 'POOL02', 5),
(0003, 'POOL02', 3),
(0004, 'POOL02', 6),
(0005, 'POOL02', 10),
(0006, 'POOL02', 6),
(0007, 'POOL02', 3),
(0008, 'POOL02', 6),
(0009, 'POOL02', 3),
(0010, 'POOL02', 4),
(0011, 'POOL02', 17),
(0012, 'POOL02', 7),
(0013, 'POOL02', 5),
(0014, 'POOL02', 15),
(0015, 'POOL02', 11),
(0016, 'POOL02', 13),
(0017, 'POOL02', 11),
(0018, 'POOL02', 3),
(0019, 'POOL02', 4),
(0020, 'POOL02', 5);



select * from customer;

-- Sample data for receipt table
INSERT INTO receipt (date_receipt, number_of_customer, receipt_status,ssn, cus_id)
VALUES
('2024-04-01 10:00:00', 2, 'N', '079456789', 'C00000001'),
('2024-04-01 11:30:00', 1, 'N', '079456789', 'C00000002'),
( '2024-03-01 12:45:00', 3, 'N', '079567890', 'C00000003'),
('2024-02-01 14:00:00', 2, 'N', '070678901', 'C00000004'),
('2024-01-01 15:30:00', 1, 'N', '081234567', 'C00000005'),
( '2024-04-01 17:00:00', 2, 'N', '056789012', 'C00000006'),
('2024-02-01 18:30:00', 1, 'N', '067890123', 'C00000007'),
('2024-03-01 20:00:00', 3, 'N', '078901234', 'C00000008'),
('2024-04-01 21:30:00', 1, 'N',  '089012345', 'C00000009'),
('2024-04-02 10:00:00', 2, 'N',  '090123456', 'C00000010'),
( '2024-03-02 11:30:00', 1, 'N', '012345688', 'C00000011');

INSERT INTO product (product_id, product_name,image, price)
VALUES
    ('SB0001', 'Mala Spicy','Image/ProductImage/SB0001.png', 70000),
    ('SB0002', 'Tomato Hot Pot','Image/ProductImage/SB0002.png' , 50000),
    ('SB0003', 'Thai Soup Base','Image/ProductImage/SB0003.png' , 50000),
    ('SB0004', 'Pig Bone Pot','Image/ProductImage/SB0004.png' , 60000),
    ('SB0005', 'Mushroom','Image/ProductImage/SB0005.png' , 60000),
    ('SB0006', 'Herbalist','Image/ProductImage/SB0006.png' , 40000),
    ('SB0007', 'Water Soup Base','Image/ProductImage/SB0007.png' , 0),
    ('FD0001', 'Short Rib Boneless','Image/ProductImage/FD0001.png' , 400000),
    ('FD0002', 'Beef Belly','Image/ProductImage/FD0002.png' , 200000),
    ('FD0003', 'HCMUT-flavoured beef','Image/ProductImage/FD0003.png' , 150000),
    ('FD0004', 'Shrimp','Image/ProductImage/FD0004.png' , 180000),
    ('FD0005', 'Pork Collar Butt','Image/ProductImage/FD0005.png' , 120000),
    ('FD0006', 'Squid','Image/ProductImage/FD0006.png' , 180000),
    ('FD0007', 'Cheese Tofu','Image/ProductImage/FD0007.png' , 100000),
    ('FD0008', 'Crab Sticks','Image/ProductImage/FD0008.png' , 50000),
    ('FD0009', 'Basha Fish Maw','Image/ProductImage/FD0009.png' , 70000),
    ('FD0010', 'Baby Octopus','Image/ProductImage/FD0010.png' , 120000),
    ('FD0011', 'Scallop' ,'Image/ProductImage/FD0011.png', 70000),
    ('FD0012', 'Corn' ,'Image/ProductImage/FD0012.png', 8000),
    ('FD0013', 'Instant Noodles' ,'Image/ProductImage/FD0013.png', 5000),
    ('FD0014', 'Spinach','Image/ProductImage/FD0014.png' , 40000),
    ('FD0015', 'Golden Mushroom' ,'Image/ProductImage/FD0015.png', 60000),
    ('FD0016', 'Seasoning' ,'Image/ProductImage/FD0016.png', 40000),
    ('FD0017', 'Auto refill water (plain, cola, boba)','Image/ProductImage/FD0017.png' , 20000),
    ('FD0018', 'Tropical Raspberry Tea' ,'Image/ProductImage/FD0018.png', 60000),
    ('FD0019', 'Toscana Bianco Vergine White Wine' ,'Image/ProductImage/FD0019.png', 720000),
    ('CB0001', 'Combo A' ,'Image/ProductImage/CB0001.png', 600000),
    ('CB0002', 'Combo B' ,'Image/ProductImage/CB0002.png', 650000),
    ('CB0003', 'Combo C' ,'Image/ProductImage/CB0003.png', 700000),
    ('CB0004', 'Combo Special' ,'Image/ProductImage/CB0004.png', 750000);

INSERT INTO food_and_drinks (product_id)
VALUES
    ('FD0001'),
    ('FD0002'),
    ('FD0003'),
    ('FD0004'),
    ('FD0005'),
    ('FD0006'),
    ('FD0007'),
    ('FD0008'),
    ('FD0009'),
    ('FD0010'),
    ('FD0011'),
    ('FD0012'),
    ('FD0013'),
    ('FD0014'),
    ('FD0015'),
    ('FD0016'),
    ('FD0017'),
    ('FD0018'),
    ('FD0019');


INSERT INTO soup_base (product_id, spicy_level)
VALUES
    ('SB0001', 2),
    ('SB0002', 0),
    ('SB0003', 1),
    ('SB0004', 0),
    ('SB0005', 0),
    ('SB0006', 0),
    ('SB0007', 0);


INSERT INTO flavor (product_id, flavor)
VALUES
    ('SB0001', 'Mala'),
    ('SB0001', 'Spicy'),
    ('SB0002', 'Tomato'),
	('SB0002', 'Light sour'),
    ('SB0003', 'Thai'),
    ('SB0003', 'Sour'),
    ('SB0003', 'Spicy'),
    ('SB0004', 'Savory'),
    ('SB0004', 'Fatty'),
    ('SB0005', 'Mushroom'),
    ('SB0005', 'Elegant'),
    ('SB0006', 'Herbalist'),
    ('SB0007', 'No flavor');

-- Insert data into Combo table
INSERT INTO Combo (product_id, Number_guest_recommend, time_start, time_end, ratio)
VALUES
    ('CB0001', 4,18.00, 22.00, 30),
    ('CB0002', 6,11.00, 13.00, 35),
    ('CB0003', 8,18.00,20.30, 40),
    ('CB0004', 10,16.00,23.00, 45);
    
INSERT INTO include_room (receipt_id, room_code, product_id, quantity) 
VALUES
    ('R00000001', 'VIP001', 'SB0001', 1),
    ('R00000001', 'VIP001', 'SB0002', 1),
    ('R00000001', 'VIP001', 'SB0003', 2),
    ('R00000001', 'VIP001', 'FD0001', 2),
    ('R00000001', 'VIP001', 'FD0002', 6),
    ('R00000001', 'VIP002', 'SB0001', 2),
    ('R00000001', 'VIP002', 'SB0003', 2),
    ('R00000001', 'VIP002', 'FD0011', 2),
    ('R00000001', 'VIP002', 'FD0012', 6),
    ('R00000002', 'VIP002', 'SB0004', 2),
    ('R00000002', 'VIP002', 'SB0005', 2),
    ('R00000002', 'VIP002', 'FD0006', 2),
    ('R00000002', 'VIP002', 'FD0003', 3),
    ('R00000002', 'VIP002', 'FD0004', 1),
    ('R00000003', 'VIP012', 'SB0001', 2),
    ('R00000003', 'VIP012', 'SB0002', 2),
    ('R00000003', 'VIP012', 'FD0005', 1),
    ('R00000003', 'VIP012', 'FD0006', 9),
    ('R00000003', 'VIP012', 'FD0007', 6),
    ('R00000003', 'VIP012', 'FD0008', 4),
    ('R00000003', 'VIP012', 'FD0009', 3),
    ('R00000004', 'VIP010', 'SB0002', 2),
    ('R00000004', 'VIP010', 'SB0003', 2),
    ('R00000004', 'VIP010', 'FD0010', 8),
    ('R00000004', 'VIP010', 'FD0011', 2),
    ('R00000004', 'VIP010', 'FD0012', 3),
    ('R00000004', 'VIP010', 'FD0013', 2),
    ('R00000004', 'VIP010', 'FD0014', 1),
    ('R00000005', 'VIP021', 'SB0001', 2),
    ('R00000005', 'VIP021', 'SB0002', 2),
    ('R00000005', 'VIP021', 'FD0015', 1),
    ('R00000005', 'VIP021', 'FD0001', 3),
    ('R00000005', 'VIP021', 'FD0010', 1),
    ('R00000005', 'VIP021', 'FD0006', 2),
    ('R00000005', 'VIP021', 'FD0005', 3),
    ('R00000006', 'VIP012', 'SB0001', 2),
    ('R00000006', 'VIP012', 'SB0004', 2),
    ('R00000006', 'VIP012', 'FD0001', 4),
    ('R00000006', 'VIP012', 'FD0002', 8),
    ('R00000006', 'VIP012', 'CB0003', 2),
    ('R00000006', 'VIP012', 'FD0004', 5);

insert into include_table (receipt_id, area_id, order_num, product_id, quantity)
values
('R00000006','GARD01',0001, 'SB0002', 4),
('R00000006','GARD01',0001, 'FD0002', 4),
('R00000006','GARD01',0001, 'FD0008', 1),
('R00000006','GARD01',0001, 'FD0004', 3),
('R00000006','GARD01',0001, 'CB0002', 2),
('R00000006','GARD01',0001, 'FD0005', 2),

('R00000007','GARD01',0002, 'SB0001', 4),
('R00000007','GARD01',0002, 'FD0001', 4),
('R00000007','GARD01',0002, 'FD0002', 1),
('R00000007','GARD01',0002, 'FD0007', 3),
('R00000007','GARD01',0002, 'CB0001', 2),
('R00000007','GARD01',0002, 'FD0003', 2),

('R00000008','INDO01',0002, 'SB0003', 4),
('R00000008','INDO01',0002, 'FD0002', 4),
('R00000008','INDO01',0002, 'FD0006', 1),
('R00000008','INDO01',0002, 'FD0004', 3),
('R00000008','INDO01',0002, 'CB0003', 2),
('R00000008','INDO01',0002, 'FD0005', 2),

('R00000009','POOL01',0002, 'SB0002', 4),
('R00000009','POOL01',0002, 'FD0012', 4),
('R00000009','POOL01',0002, 'FD0008', 1),
('R00000009','POOL01',0002, 'FD0004', 3),
('R00000009','POOL01',0002, 'CB0002', 2),
('R00000009','POOL01',0002, 'FD0015', 2),

('R00000010','OUTD01',0002, 'SB0003', 4),
('R00000010','OUTD01',0002, 'FD0006', 4),
('R00000010','OUTD01',0002, 'FD0008', 1),
('R00000010','OUTD01',0002, 'FD0014', 3),
('R00000010','OUTD01',0002, 'CB0004', 2),
('R00000010','OUTD01',0002, 'FD0010', 2),

('R00000011','GARD01',0002, 'SB0001', 4),
('R00000011','GARD01',0002, 'FD0002', 4),
('R00000011','GARD01',0002, 'FD0008', 1),
('R00000011','GARD01',0002, 'FD0001', 3),
('R00000011','GARD01',0002, 'CB0001', 2),
('R00000011','GARD01',0002, 'FD0003', 2);

insert into review (rv_comment, score, rv_date)
values
('ngon', 5, curdate()),
('kha ngon', 4, curdate()),
('tam duoc', 3, curdate()),
('hop khau vi', 4, curdate()),
('khong hop khau vi', 1, curdate()),
('tam on', 3, curdate()),
('hoi te', 2, curdate()),
('ngon', 5, curdate()),
('kha ngon', 4, curdate()),
('tam duoc', 3, curdate()),
('hop khau vi', 4, curdate()),
('khong hop khau vi', 1, curdate()),
('tam on', 3, curdate()),
('hoi te', 2, curdate()),

('ngon', 5, curdate()),
('kha ngon', 4, curdate()),
('tam duoc', 3, curdate()),
('hop khau vi', 4, curdate()),
('khong hop khau vi', 1, curdate()),
('tam on', 3, curdate()),
('hoi te', 2, curdate()),

('ngon', 5, curdate()),
('kha ngon', 4, curdate()),
('tam duoc', 3, curdate()),
('hop khau vi', 4, curdate()),
('khong hop khau vi', 1, curdate()),
('tam on', 3, curdate()),
('hoi te', 2, curdate()),

('ngon', 5, curdate()),
('kha ngon', 4, curdate()),
('tam duoc', 3, curdate()),
('hop khau vi', 4, curdate()),
('khong hop khau vi', 1, curdate()),
('tam on', 3, curdate()),
('hoi te', 2, curdate()),

('ngon', 5, curdate()),
('kha ngon', 4, curdate()),
('tam duoc', 3, curdate()),
('hop khau vi', 4, curdate()),
('khong hop khau vi', 1, curdate()),
('tam on', 3, curdate()),
('hoi te', 2, curdate()),

('ngon', 5, curdate()),
('kha ngon', 4, curdate()),
('tam duoc', 3, curdate()),
('hop khau vi', 4, curdate()),
('khong hop khau vi', 1, curdate()),
('tam on', 3, curdate()),
('hoi te', 2, curdate()),

('ngon', 5, curdate()),
('kha ngon', 4, curdate()),
('tam duoc', 3, curdate()),
('hop khau vi', 4, curdate()),
('khong hop khau vi', 1, curdate()),
('tam on', 3, curdate()),
('hoi te', 2, curdate()),

('ngon', 5, curdate()),
('kha ngon', 4, curdate()),
('tam duoc', 3, curdate()),
('hop khau vi', 4, curdate()),
('khong hop khau vi', 1, curdate()),
('tam on', 3, curdate()),
('hoi te', 2, curdate()),

('ngon', 5, curdate()),
('kha ngon', 4, curdate()),
('tam duoc', 3, curdate()),
('hop khau vi', 4, curdate()),
('khong hop khau vi', 1, curdate()),
('tam on', 3, curdate()),
('hoi te', 2, curdate()),

('ngon', 5, curdate()),
('kha ngon', 4, curdate()),
('tam duoc', 3, curdate()),
('hop khau vi', 4, curdate()),
('khong hop khau vi', 1, curdate()),
('tam on', 3, curdate()),
('hoi te', 2, curdate());

insert into evaluate
values
('R00000001', 'SB0001', 1),
('R00000001', 'SB0002', 2),
('R00000001', 'SB0003', 3),
('R00000001', 'FD0001', 4),
('R00000001', 'FD0002', 5),
('R00000001', 'FD0011', 8),
('R00000001', 'FD0012', 9),
('R00000002', 'SB0004', 10),
('R00000002', 'SB0005', 11),
('R00000002', 'FD0006', 12),
('R00000002', 'FD0003', 13),
('R00000002', 'FD0004', 14);
insert into evaluate
values
('R00000003', 'SB0001', 15),
('R00000003', 'SB0002', 16),
('R00000003', 'FD0005', 17),
('R00000003', 'FD0006', 18),
('R00000003', 'FD0007', 19),
('R00000003', 'FD0008', 20),
('R00000003', 'FD0009', 21),
('R00000004', 'SB0002', 22),
('R00000004', 'SB0003', 23),
('R00000004', 'FD0010', 24),
('R00000004', 'FD0011', 25),
('R00000004', 'FD0012', 26),
('R00000004', 'FD0013', 27),
('R00000004', 'FD0014', 28),
('R00000005', 'SB0001', 29),
('R00000005', 'SB0002', 30),
('R00000005', 'FD0015', 31),
('R00000005', 'FD0001', 32),
('R00000005', 'FD0010', 33),
('R00000005', 'FD0006', 34),
('R00000005', 'FD0005', 35);
insert into evaluate
values
('R00000006', 'SB0001', 36),
('R00000006', 'SB0004', 37),
('R00000006', 'FD0001', 38),
('R00000006', 'FD0002', 39),
('R00000006', 'CB0003', 40),
('R00000006', 'FD0004', 41),
('R00000006', 'SB0002', 42),
('R00000006', 'FD0008', 44),
('R00000006', 'CB0002', 46),
('R00000006', 'FD0005', 47),
('R00000007', 'SB0001', 48),
('R00000007', 'FD0001', 49),
('R00000007', 'FD0002', 50),
('R00000007', 'FD0007', 51),
('R00000007', 'CB0001', 52),
('R00000007', 'FD0003', 53),
('R00000008', 'SB0003', 54),
('R00000008', 'FD0002', 55),
('R00000008', 'FD0006', 56),
('R00000008', 'FD0004', 57),
('R00000008', 'CB0003', 58),
('R00000008', 'FD0005', 59),
('R00000009', 'SB0002', 60),
('R00000009', 'FD0012', 61),
('R00000009', 'FD0008', 62),
('R00000009', 'FD0004', 63),
('R00000009', 'CB0002', 64),
('R00000009', 'FD0015', 65),
('R00000010', 'SB0003', 66),
('R00000010', 'FD0006', 67),
('R00000010', 'FD0008', 68),
('R00000010', 'FD0014', 69),
('R00000010', 'CB0004', 70),
('R00000010', 'FD0010', 71),
('R00000011', 'SB0001', 72),
('R00000011', 'FD0002', 73),
('R00000011', 'FD0008', 74),
('R00000011', 'FD0001', 75),
('R00000011', 'CB0001', 76),
('R00000011', 'FD0003', 77);

DELIMITER //
create procedure get_top5order()
deterministic
begin
		select product_id, (sum(quantity)) as num_order
        from (
			select product_id, quantity from include_room
            union all 
            select product_id, quantity from include_table
        ) as combined_tables
		group by product_id
        order by num_order desc
        limit 5;
end//
DELIMITER ;
-- call get_top10rating();
delimiter //
create function rating(product_id varchar(6))
returns float
deterministic
begin
	declare ans float;
    declare cnt int;
    select sum(r.score), count(*) into ans, cnt
    from review r, evaluate e
    where r.review_id = e.review_id and e.product_id = product_id;
    set ans = ans / cnt;
    return ans;
end//
delimiter ;

delimiter //
create procedure get_top10rating()
deterministic
begin
	select *, rating(product_id) from product
    order by rating(product_id) desc
    limit 10;
end//
delimiter ;

delimiter //
create function check_vip_room(room_code char(6))
returns BOOL
deterministic
begin
	declare flag bool;
    if room_code like 'VIP%' then
        select available into flag
		from vip_room v where v.room_code = room_code;
		if flag is NULL then
			signal sqlstate '45000'
            set message_text = 'There is no room with the given room code';
		end if;
        return flag;
    else
		signal sqlstate '45000'
        set message_text = 'The room code is invalid. The room code must have the type: VIP***';
	end if;
end //
delimiter ;

-- select check_vip_room('VIP006');

delimiter // 
create function check_table(area_id char(6),order_num int)
returns BOOL
deterministic
begin
	declare flag bool;
    select available into flag
    from table_book t where t.area_id = area_id and t.order_num = order_num;
    if flag is null then 
		signal sqlstate '45000'
        set message_text = 'The table is not exist in my restaurant. Please choose another table';
	end if;
    return flag;
end //
delimiter ;

-- select check_table('POOL01', 0);

delimiter //
create procedure revenue_by_week()
deterministic
begin
	select year(date_receipt) as "year",
    week(date_receipt) as "week",
    sum(total_price) as revenue
    from receipt
    group by year(date_receipt), week(date_receipt)
    order by "year", "week";
end //
delimiter ;

-- call revenue_by_week();
delimiter //
create procedure revenue_by_month()
deterministic
begin
	select month(date_receipt) as "month",
    year(date_receipt) as "year",
    sum(total_price) as revenue
    from receipt
    group by year(date_receipt), month(date_receipt)
    order by  "year", "month";
end //
delimiter ;

-- call revenue_by_month();

delimiter //
create procedure revenue_by_quarter()
deterministic
begin
	select year(date_receipt) as "year",
    quarter(date_receipt) as "quarter",
    sum(total_price) as revenue
    from receipt
    group by year(date_receipt), quarter(date_receipt)
    order by "year", "quarter";
end //
delimiter ;

call revenue_by_quarter();
select * from hotpot.product;

delimiter //
create procedure revenue_by_year()
deterministic
begin
	select year(date_receipt) as "year",
    sum(total_price) as revenue
    from receipt
    group by year(date_receipt)
    order by "year";
end //
delimiter ;
-- call revenue_by_year();

delimiter //
create procedure viproom_list(num_guest int)
deterministic
begin
	select * from vip_room
    where available = true and num_guest >= min_guest_number and num_guest <= max_guest_number;
end//
delimiter ;

-- call viproom_list(4);

delimiter //
create procedure tablebook_list(num_guest int)
deterministic
begin
	select * from table_book
    where available = true and num_guest <= no_seat;
end//
delimiter ;

-- call tablebook_list(5);
delimiter //
create procedure insert_soupbase(product_id varchar(6), product_name varchar(50), price int,spicy int)
deterministic
begin
	if product_id not like 'SB%' then
		signal sqlstate '45000'
        set message_text = 'The product_id is invalid. The product_id must have the type: SB****';
    else 
		insert into product (product_id, product_name, price)
		value (product_id, product_name, price);
		insert into soup_base (product_id, spicy_level)
		value (product_id, spicy);
    end if;
end//
delimiter ;

-- drop procedure insert_soupbase;
-- drop procedure insert_food_and_drinks;
-- drop procedure insert_combo;
delimiter //
create procedure insert_food_and_drinks(product_id varchar(6), product_name varchar(50), price int)
deterministic
begin
	if product_id not like 'FD%' then
		signal sqlstate '45000'
        set message_text = 'The product_id is invalid. The product_id must have the type: FD****';
	else
		insert into product (product_id, product_name, price)
		values (product_id, product_name, price);
		insert into food_and_drinks (product_id)
		values (product_id);
    end if;
end//
delimiter ;
-- call insert_food_and_drinks('FD0099','Lobster', 500000);
delimiter //
create procedure insert_combo(product_id varchar(6), product_name varchar(50), price int, number_guest int, time_start decimal(4,2), time_end decimal(4,2), ratio int)
deterministic
begin
	if product_id not like 'CB%' then
		signal sqlstate '45000'
        set message_text = 'The product_id is invalid. The product_id must have the type: CB****';
	else
		insert into product (product_id, product_name, price)
		value (product_id, product_name, price);
		insert into combo (product_id, number_guest_recommend, time_start, time_end, ratio)
		value (product_id, number_guest, time_start, time_end, ratio);
    end if;
end//
delimiter ;

-- call insert_combo('CB0006','Combo tiet kiem',500000, 4, 15.00, 18.30, 50);

delimiter //
create procedure apply_point(rec_id char(9), point_use int)
deterministic
begin
	declare cur_point int;
    declare customer_id char(9);
    select acc_point, c.cus_id into cur_point, customer_id
    from customer c , receipt r
    where c.cus_id = r.cus_id and r.receipt_id = rec_id;
	start transaction;
		if cur_point > point_use then
			update customer
            set acc_point = cur_point - point_use
            where cus_id = customer_id;
            
            update receipt
            set total_price = total_price - point_use
            where receipt_id = rec_id;
            
            commit;
            
		else
			signal sqlstate '45000'
            set message_text = 'Customer does not have enough points to apply the discount.';
            rollback;
		end if;
end //
delimiter ;
-- drop procedure apply_point;
-- call apply_point('R00000001', 30000);
-- call revenue_by_month();

update receipt
set receipt_status = 'E'
where receipt_id = 'R00000001';

update receipt
set receipt_status = 'E'
where receipt_id = 'R00000001';

update receipt
set receipt_status = 'E'
where receipt_id = 'R00000002';

update receipt
set receipt_status = 'E'
where receipt_id = 'R00000003';

update receipt
set receipt_status = 'E'
where receipt_id = 'R00000004';

update receipt
set receipt_status = 'E'
where receipt_id = 'R00000005';

update receipt
set receipt_status = 'E'
where receipt_id = 'R00000006';

update receipt
set receipt_status = 'E'
where receipt_id = 'R00000007';

update receipt
set receipt_status = 'E'
where receipt_id = 'R00000008';

update receipt
set receipt_status = 'E'
where receipt_id = 'R00000009';

update receipt
set receipt_status = 'E'
where receipt_id = 'R00000010';

update receipt
set receipt_status = 'E'
where receipt_id = 'R00000011';


-- create user 'sManager'@'localhost'
-- identified by '123456';
-- grant all privileges
-- on hotpot.*
-- to 'sManager'@'localhost';