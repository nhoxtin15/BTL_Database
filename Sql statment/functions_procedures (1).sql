DELIMITER //
create procedure get_top5order()
deterministic
begin
		select product_id, count(*) as num_order
        from (
			select product_id from include_room
            union all 
            select product_id from include_table
        ) as combined_tables
		group by p.product_id
        order by num_order desc
        limit 5;
end//
DELIMITER ;
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
    if room_code not like 'VIP%' then
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

delimiter //
create procedure revenue_by_week()
deterministic
begin
	select year(receipt_date) as nam,
    week(receipt_date) as tuan,
    sum(total_price) as revenue
    from receipt
    group by year(receipt_date), week(receipt_date)
    order by nam, tuan;
end //
delimiter ;

delimiter //
create procedure revenue_by_month()
deterministic
begin
	select month(receipt_date) as thang,
    sum(total_price) as revenue
    from receipt
    group by month(receipt_date)
    order by thang;
end //
delimiter ;

delimiter //
create procedure revenue_by_quarter()
deterministic
begin
	select year(receipt_date) as nam,
    quarter(receipt_date) as quy,
    sum(total_price) as revenue
    from receipt
    group by year(receipt_date), quarter(receipt_date)
    order by nam, quy;
end //
delimiter ;

delimiter //
create procedure revenue_by_year()
deterministic
begin
	select year(receipt_date) as nam,
    sum(total_price) as revenue
    from receipt
    group by year(receipt_date)
    order by nam;
end //
delimiter ;

delimiter //
create procedure revenue_this_month()
deterministic
begin
	select 
    sum(total_price) as revenue
    from receipt
    where month(receipt_date) = month(curdate());
end //
delimiter ;

delimiter //
create procedure viproom_list(num_guest int)
deterministic
begin
	select * from vip_room
    where available = true and num_guest >= min_guest_number and num_guest <= max_guest_number;
end//
delimiter ;

delimiter //
create procedure tablebook_list(num_guest int)
deterministic
begin
	select * from table_book
    where available = true and num_guest <= no_seat;
end//
delimiter ;


delimiter //
create procedure insert_soupbase(product_name varchar(20), price int,spicy int)
deterministic
begin
	declare new_id int;
    set new_id = (select ifnull(max(substring(product_id,2)),0) + 1 from soup_base where product_id like 'SB%');
    insert into product
    value (new_id, product_name, price);
    insert into soup_base
    value (new_id, spicy);
end//
delimiter ;

delimiter //
create procedure insert_food_and_drinks(product_name varchar(20), price int)
deterministic
begin
	declare new_id int;
    set new_id = (select ifnull(max(substring(product_id,2)),0) + 1 from food_and_drinks where product_id like 'FD%');
    insert into product
    value (new_id, product_name, price);
    insert into food_and_drinks
    value (new_id);
end//
delimiter ;

delimiter //
create procedure insert_combo(product_name varchar(20), price int, number_guest int, time_start decimal(4,2), time_in decimal(4,2), ratio int)
deterministic
begin
	declare new_id int;
    set new_id = (select ifnull(max(substring(product_id,2)),0) + 1 from combo where product_id like 'CB%');
    insert into product
    value (new_id, product_name, price);
    insert into combo
    value (new_id, number_guest, time_start, time_in, ratio);
end//
delimiter ;


delimiter //
create procedure apply_point(rec_id char(9))
deterministic
begin
	declare cur_point int;
    declare customer_id char(6);
    select acc_point, cus_id into cur_point, customer_id
    from customer c , receipt r
    where c.cus_id = r.cus_id and r.receipt_id = rec_id;
	start transaction;
		if cur_point > 0 then
			update customer
            set acc_point = 0
            where cus_id = customer_id;
            
            update receipt
            set total_price = total_price - cur_point
            where receipt_id = rec_id;
            
            commit;
            
		else
			signal sqlstate '45000'
            set message_text = 'Customer does not have enough points to apply the discount.';
            rollback;
		end if;
end //
delimiter ;