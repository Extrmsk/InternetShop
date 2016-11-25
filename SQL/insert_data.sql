-- ALL INSERT's only into clean DB!

insert into customers (login, password, name, address, phone, email, credit_card) values 
	('Admin', '123', 'Administrator', 'Novosibirsk', '+8 923 122 12 08', 'admin@internetshop.com', '1234 1234 1234 1234'),
	('User1', '234', 'Alex', 'Bangok', '+3 105 069 89 89', 'alex@bangoknet.com', '4685 6484 1001 5470'),
	('User2', '345', 'Mary', 'NewYork', '+2 339 320 22 00', 'mary@newyorknet.com', '0022 2200 3322 2233');
	
insert into groups (group_name, group_parent_id) values 
	('Auto', 0),
	('Electronics', 0),
	('Clothes', 0),
	('Sport', 0),
	('Tires', 1),
	('Oils', 1),
	('Parts', 1),
	('Photo&Video', 2),
	('Computers', 2),
	('Womens', 3),
	('Mens', 3),
	('Football', 4),
	('Swimming', 4),
	('Sky', 4),
	('Winter tires', 5),
	('Summer tires', 5),
	('Laptops', 9),
	('Desktops', 9);
	
insert into goods (goods_name, price, amount, group_id) values
	('Nokian Hakka Green', 4800, 12, 16),
	('Toyo Proxes T1-R', 4400, 18, 16),
	('Michelin X-Ice Xi3', 7200, 8, 15),
	('Goodyear Ultra Grip Ice 2', 5600, 6, 15),
	('Gislaved NordFrost 100', 5400, 48, 15),
	('Nikon D700', 85000, 1, 8),
	('Lenovo IdeaPad300', 35000, 3, 17),
	('Sony Vaio as8550', 78112, 2, 17),
	('Fischer speedmax sk c-special', 28450, 14, 14),
	('Rossignol Temptation 78', 22990, 54, 14),
	('ATOMIC Redster XT-15', 46290, 7, 14),
	('Nike Mercurial', 3220, 88, 12);
	



