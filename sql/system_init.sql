USE `system`;

DELETE FROM `user`;
INSERT INTO `user` (`id`, `username`,`nickname`, `password`, `salt`, `mobile`, `cdate`, `udate`) VALUES
  (10000, 'superadmin','超级管理员' ,'493ea57f96d3efb88cc409f5f4ea4620', 'b573a831d14a75f5413bc5152f49b8eb', '18600000000', UNIX_TIMESTAMP(),UNIX_TIMESTAMP()),
  (10001, 'test','test', '3d9c9916bed56711c429c404b75018e4', 'b573a831d14a75f5413bc5152f49b8eb', '18600000001', UNIX_TIMESTAMP(),UNIX_TIMESTAMP());

DELETE FROM `role`;
INSERT INTO `role` (`id`, `name`) VALUES
  (1, '管理员'),
  (2, '访客'),
  (3, '普通用户');
DELETE FROM `user_role`;
INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
  (10000, 1),
  (10001, 1);

DELETE FROM `permission`;
INSERT INTO `permission` (`id`, `name`) VALUES
  (1, '公共权限'),
  (2, '系统管理');

DELETE FROM `role_permission`;
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES
  (1, 1),
  (1, 2),
  (2, 1);

DELETE FROM `resource`;
INSERT INTO `resource` VALUES ('10000', '/user/login', '登陆');
INSERT INTO `resource` VALUES ('10001', '/user/logout', '退出');
INSERT INTO `resource` VALUES ('10002', '/user/list', '查询用户');
INSERT INTO `resource` VALUES ('10003', '/user/insert', '添加用户');
INSERT INTO `resource` VALUES ('10004', '/user/update', '更新用户');
INSERT INTO `resource` VALUES ('10005', '/user/delete', '删除用户');
INSERT INTO `resource` VALUES ('10100', '/role/list', '查询角色');
INSERT INTO `resource` VALUES ('10101', '/role/all', '查询所有角色');
INSERT INTO `resource` VALUES ('10102', '/role/insert', '添加角色');
INSERT INTO `resource` VALUES ('10103', '/role/update', '更新角色');
INSERT INTO `resource` VALUES ('10104', '/role/delete', '删除角色');
INSERT INTO `resource` VALUES ('10200', '/permission/list', '查询权限');
INSERT INTO `resource` VALUES ('10201', '/permission/all', '查询所有权限');
INSERT INTO `resource` VALUES ('10202', '/permission/insert', '添加权限');
INSERT INTO `resource` VALUES ('10203', '/permission/update', '更新权限');
INSERT INTO `resource` VALUES ('10204', '/permission/delete', '删除权限');
INSERT INTO `resource` VALUES ('10300', '/resource/list', '查询资源');
INSERT INTO `resource` VALUES ('10301', '/resource/all', '查询所有资源');
INSERT INTO `resource` VALUES ('10302', '/resource/insert', '添加资源');
INSERT INTO `resource` VALUES ('10303', '/resource/update', '更新资源');
INSERT INTO `resource` VALUES ('10304', '/resource/delete', '删除资源');
INSERT INTO `resource` VALUES ('10400', '/menu/list', '查询菜单');
INSERT INTO `resource` VALUES ('10401', '/menu/rlist', '根据角色查询菜单');
INSERT INTO `resource` VALUES ('10402', '/menu/all', '查询所有菜单');
INSERT INTO `resource` VALUES ('10403', '/menu/insert', '添加菜单');
INSERT INTO `resource` VALUES ('10404', '/menu/update', '更新菜单');
INSERT INTO `resource` VALUES ('10405', '/menu/delete', '删除菜单');

DELETE FROM `permission_resource`;
INSERT INTO `permission_resource` VALUES ('1', '10000');
INSERT INTO `permission_resource` VALUES ('1', '10001');
INSERT INTO `permission_resource` VALUES ('2', '10002');
INSERT INTO `permission_resource` VALUES ('2', '10003');
INSERT INTO `permission_resource` VALUES ('2', '10004');
INSERT INTO `permission_resource` VALUES ('2', '10005');
INSERT INTO `permission_resource` VALUES ('2', '10100');
INSERT INTO `permission_resource` VALUES ('2', '10101');
INSERT INTO `permission_resource` VALUES ('2', '10102');
INSERT INTO `permission_resource` VALUES ('2', '10103');
INSERT INTO `permission_resource` VALUES ('2', '10104');
INSERT INTO `permission_resource` VALUES ('2', '10200');
INSERT INTO `permission_resource` VALUES ('2', '10201');
INSERT INTO `permission_resource` VALUES ('2', '10202');
INSERT INTO `permission_resource` VALUES ('2', '10203');
INSERT INTO `permission_resource` VALUES ('2', '10204');
INSERT INTO `permission_resource` VALUES ('2', '10300');
INSERT INTO `permission_resource` VALUES ('2', '10301');
INSERT INTO `permission_resource` VALUES ('2', '10302');
INSERT INTO `permission_resource` VALUES ('2', '10303');
INSERT INTO `permission_resource` VALUES ('2', '10304');
INSERT INTO `permission_resource` VALUES ('2', '10400');
INSERT INTO `permission_resource` VALUES ('2', '10401');
INSERT INTO `permission_resource` VALUES ('2', '10402');
INSERT INTO `permission_resource` VALUES ('2', '10403');
INSERT INTO `permission_resource` VALUES ('2', '10404');
INSERT INTO `permission_resource` VALUES ('2', '10405');

DELETE FROM `menu`;
INSERT INTO `menu` VALUES ('10000', '0', '系统管理', '', '1', 'setting');
INSERT INTO `menu` VALUES ('10001', '10000', '用户管理', '/system/user', '1', 'user');
INSERT INTO `menu` VALUES ('10002', '10000', '角色管理', '/system/role', '2', 'book');
INSERT INTO `menu` VALUES ('10003', '10000', '权限管理', '/system/permission', '3', 'tag-o');
INSERT INTO `menu` VALUES ('10004', '10000', '资源管理', '/system/resource', '4', 'shop');
INSERT INTO `menu` VALUES ('10005', '10000', '菜单管理', '/system/menu', '5', 'menu-fold');

DELETE FROM `role_menu`;
INSERT INTO `role_menu` VALUES ('1', '10000');
INSERT INTO `role_menu` VALUES ('1', '10001');
INSERT INTO `role_menu` VALUES ('1', '10002');
INSERT INTO `role_menu` VALUES ('1', '10003');
INSERT INTO `role_menu` VALUES ('1', '10004');
INSERT INTO `role_menu` VALUES ('1', '10005');
