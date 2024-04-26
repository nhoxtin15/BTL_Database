drop database hotpot;
Create database hotpot;

use hotpot;

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
	SSN		varchar(9),
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
	available bool,
	Room_code char(6) primary key,
    Min_guest_number INT,
    Max_guest_number INT,
    Area_ID char(7),
    constraint Area_ID_VIP foreign key (Area_ID) references Area(Area_ID)
 );
 
create table Table_book (
	available bool,
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
    image BLOB default null,
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
    foreign key (product_id) references soup_base(product_id) on delete cascade
);

create table Combo_include(
	Food_drink_id VARChar(6),
	Combo_ID VARChar(6),
    foreign key (Food_drink_id) references food_and_drinks(product_id),
    foreign key (Combo_ID) references Combo(product_id)
);

create table evaluate(
	Receipt_ID char(6),
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
	receipt_id char(6),
    room_code char(6),
    product_id varchar(6),
    quantity int,
    primary key (receipt_id, room_code, product_id),
    foreign key (receipt_id) references Receipt(receipt_id),
    foreign key (room_code) references vip_room(room_code),
    foreign key (product_id) references product(product_id)
);

create table include_table(
	receipt_id char(6),
    area_id char(6),
    order_num int,
    product_id varchar(6),
    quantity int,
    primary key (receipt_id, area_id, order_num, product_id),
	foreign key (receipt_id) references Receipt(receipt_id),
    foreign key (area_id,order_num) references table_book(area_id,order_num),
	foreign key (product_id) references product(product_id)
);