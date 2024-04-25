DELIMITER $$  -- Define a delimiter for the trigger

CREATE TRIGGER before_insert_receipt
BEFORE INSERT ON receipt
FOR EACH ROW
BEGIN
  DECLARE new_id int;
  SET new_id = (select ifnull(max(substring(receipt_id,2)),0) + 1 from receipt where receipt_id like 'R%');
  SET NEW.receipt_id = concat('R',LPAD(new_id, 8,'0');
END;
$$

DELIMITER ;  -- Reset the delimiter

DELIMITER $$  -- Define a delimiter for the trigger

CREATE TRIGGER before_insert_customer
BEFORE INSERT ON customer
FOR EACH ROW
BEGIN
  DECLARE new_id int;
  SET new_id = (select ifnull(max(substring(cus_id,2)),0) + 1 from receipt where cus_id like 'C%');
  SET NEW.cus_id = concat('R',LPAD(new_id, 8,'0');
END;
$$

DELIMITER ;  -- Reset the delimiter

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

create trigger update_acc_point
after update on receipt
for each row
begin 
	declare p int;
	if old.receipt_status = 'N' and new.receipt_status = 'E' then
		select accumulated_point into p
		from receipt r left outer join customer c on r.cus_id = c.cus_id;
    
		update customer 
		set accumulated_point = p + new.total_price/100
		where receipt.cus_id = customer.cus_id;
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

delimiter //

create trigger phu_thu
before update on receipt
for each row
begin 
	declare temp_price int;
    declare numguest int;
    if old.receipt_status = 'N' and new.receipt_status = 'E' then 
		select Number_of_customer into numguest
		from receipt r
		where r.receipt_id = id;
    
		if numguest*250 > new.total_price then
			set new.total_price = new.total_price + (numguest*250 - new.total_price)*0.2;
		end if;
    end if;
end//
delimiter ;
