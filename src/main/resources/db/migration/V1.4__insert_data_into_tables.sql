INSERT INTO role (name, allowed_resource, allowed_read, allowed_create, allowed_update, allowed_delete) VALUES
('Admin', '/', 'Y', 'Y', 'Y', 'Y'),
('Manager', '/depts,/departments,/employees,/ems,/acnts,/accounts', 'Y', 'Y', 'Y', 'N'),
('user', '/employees,/ems,/acnts,/accounts', 'Y', 'N', 'N', 'N')
;
COMMIT;

INSERT INTO users (name, password, first_name, last_name, email) VALUES
('dwang', '25f9e794323b453885f5181f1b624d0b', 'David', 'Wang', 'dwang@ascending.com'),
('rhang', '25f9e794323b453885f5181f1b624d0b', 'Ryo', 'Hang', 'rhang@ascending.com'),
('xyhuang', '25f9e794323b453885f5181f1b624d0b', 'Xinyue', 'Huang', 'xyhuang@ascending.com')
;
COMMIT;

INSERT INTO users_role VALUES
(1, 1),
(2, 2),
(3, 3),
(1, 2),
(1, 3)
;
COMMIT;