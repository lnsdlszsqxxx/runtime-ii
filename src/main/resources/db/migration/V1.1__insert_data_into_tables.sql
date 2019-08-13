insert into department (name, description, location) values
('AOES','Atmospheric, Oceanic and Earth Sciences','Research Hall'),
('GIS','Geography and GeoInformation Science', 'Exploring Hall'),
('EE','Electronic Engineering','Engineering Building'),
('CS', 'Computer Science', 'Research Hall');

commit;

insert into student (name, first_name, last_name, email, address, department_id) values
('lyu', 'Liang', 'Yu', 'lyu@gmu.edu', '123 Sideburn Rd', 1),
('mli', 'Moming', 'Li', 'mli@gmu.edu', '123 Sideburn Rd', 3),
('yli', 'Yun', 'Li', 'yli@gmu.edu', '123 Sideburn Rd', 2),
('fyang', 'Guangyang', 'Fang', 'gfang@gmu.edu', '111 Burke Lake Rd', 1),
('ljiao','Long','Jiao','ljiao@gmu.edu','234 Braddock Rd',4),
('jwang','Junxiang','Wang','jwang@gmu.edu','123 Sideburn Rd',4);

commit;

insert into account(account_type, balance, student_id) values
('checking', 900.00, 1),
('saving', 910.00, 1),
('checking', 700.00, 2),
('saving', 710.00, 2),
('checking',600.00, 3),
('saving', 610.00, 3),
('checking', 500.00, 4),
('saving', 510.00, 4),
('checking', 400.00, 5),
('saving', 410.00, 5),
('checking', 300.00, 6),
('saving', 310.00, 6);

commit;