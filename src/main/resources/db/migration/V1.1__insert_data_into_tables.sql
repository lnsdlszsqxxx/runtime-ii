insert into department (name, description, location) values
('HR', 'Human Resource', 'Room 100, 450 N Washington Ave. Falls Church, VA'),
('R&D', 'Research and Development', 'Room 101, 450 N Washington Ave. Falls Church, VA'),
('Tech', 'Technical Support', 'Room 102, 450 N Washington Ave. Falls Church, VA'),
('CS', 'Custom Service', 'Room 103, 450 N Washington Ave. Falls Church, VA');

commit;

insert into employee (name, first_name, last_name, email, address, department_id) values
('dwang', 'David', 'Wang', 'davey.wang@ascending.com', '123 Fairfax, VA 22030', 2),
('rhang', 'Ryo', 'Hang', 'ryo.hang@ascending.com', '456 Vienna, VA 22030', 2),
('gzhang', 'Gloria', 'Zhang', 'gloria.zhang@ascending.com', '789 Dunn Loring, VA 22030', 1),
('xhuang', 'Xingyue', 'Huang', 'xingyue.Hang@ascending.com', '234 Merrifield, VA 22030', 3);

commit;

insert into account (account_type, balance, employee_id) values
('checking', 99999999.99, 1),
('saving', 99999999.99, 2),
('checking', 99999999.99, 3),
('saving', 99999999.99, 4);

commit;