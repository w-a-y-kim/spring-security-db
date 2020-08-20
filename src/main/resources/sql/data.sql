INSERT INTO admin_member (user_id, name, password) VALUES
('testUser', '테스트', '$2a$10$R4VhumfbRujIp7AjYyxOu.k/r7G1180gP7VAwCDhka1Tf73wQRsde')
,('testUser2', '테스트2', '$2a$10$R4VhumfbRujIp7AjYyxOu.k/r7G1180gP7VAwCDhka1Tf73wQRsde');

INSERT INTO role (role_name) VALUES
('ADMIN'), ('BASIC'), ('GENERAL');

INSERT INTO role_member(admin_member_id, role_id) VALUES
(
    (SELECT id FROM admin_member WHERE user_id = 'testUser')
  , (SELECT id FROM role WHERE role_name = 'ADMIN')
)
,(
    (SELECT id FROM admin_member WHERE user_id = 'testUser2')
  , (SELECT id FROM role WHERE role_name = 'BASIC')
);

INSERT INTO admin_menu(menu_name, resource_url) VALUES
  ('my account', '/api/v1/my-account')
;
INSERT INTO role_menu_group(role_id, admin_menu_id) VALUES
(
    (SELECT id FROM role WHERE role_name = 'ADMIN')
  , (SELECT id FROM admin_menu WHERE resource_url = '/api/v1/my-account')
)
;
