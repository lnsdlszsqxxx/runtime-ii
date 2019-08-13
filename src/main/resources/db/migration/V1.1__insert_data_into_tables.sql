insert into department (name, description, location) values
('AOES','Atmospheric, Oceanic and Earth Sciences','Research Hall'),
('GIS','Geography and GeoInformation Science', 'Exploring Hall'),
('EE','Electronic Engineering','Engineering Building'),
('CS', 'Computer Science', 'Research Hall');

commit;

insert into employee (name, first_name, last_name, email, address, department_id) values
('lyu', 'Liang', 'Yu', 'davey.wang@ascending.com', '123 Fairfax, VA 22030', 1),
('mli', 'Moming', 'Li', 'ryo.hang@ascending.com', '456 Vienna, VA 22030', 2),
('yli', 'Yun', 'Li', 'gloria.zhang@ascending.com', '789 Dunn Loring, VA 22030', 2),
('fyang', 'Guangyang', 'Fang', 'xingyue.Hang@ascending.com', '234 Burk, VA 22030', 1);

commit;

insert into account(account_type, balance, employee_id) values
('checking', 123.23, 1),
('saving', 234.34, 2),
('checking', 345.45, 3),
('saving', 456.56, 4);

commit;