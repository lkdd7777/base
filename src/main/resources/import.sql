# insert  into `tb_role`(`id`,`create_time`,`description`,`role_key`,`name`,`status`,`update_time`) values (1,'2017-01-09 17:25:30','超级管理员','administrator','administrator',0,'2017-01-09 17:26:25');
# insert  into `tb_user`(`id`,`address`,`birthday`,`create_time`,`delete_status`,`description`,`email`,`locked`,`nick_name`,`password`,`sex`,`telephone`,`update_time`,`user_name`) values (1,'成都','2017-01-09 17:26:39','2017-01-09 17:26:41',0,'超级管理员','whoismy8023@163.com',0,'admin','3931MUEQD1939MQMLM4AISPVNE',1,'15923930000','2017-01-09 17:27:11','admin');
# insert  into `tb_user_role`(`user_id`,`role_id`) values (1,1);
# insert into `tb_resource` ( `id`, `create_time`, `description`, `icon`, `is_hide`, `level`, `name`, `sort`, `source_key`, `source_url`, `type`, `update_time`, `parent_id` ) values ( 1, '2017-01-10 13:56:51', '用户管理', null, 0, 2, '用户管理', 1, 'system:user:index', '/admin/user/index', 1, '2017-01-10 13:59:01', null ), ( 2, '2017-01-10 13:56:51', '用户编辑', null, 0, 3, '用户编辑', 1, 'system:user:edit', '/admin/user/edit*', 2, '2017-01-10 16:26:42', 1 ), ( 3, '2017-01-11 16:48:48', '用户添加', null, 0, 3, '用户添加', 2, 'system:user:add', '/admin/user/add', 2, '2017-01-11 16:49:26', 1 ), ( 4, '2017-01-11 16:48:48', '用户删除', null, 0, 3, '用户删除', 3, 'system:user:deletebatch', '/admin/user/deletebatch', 2, '2017-01-18 14:11:41', 1 ), ( 5, '2017-01-11 16:48:48', '角色分配', null, 0, 3, '角色分配', 4, 'system:user:grant', '/admin/user/grant/**', 2, '2017-01-18 14:11:51', 1 ), ( 6, '2017-01-12 16:45:10', '角色管理', null, 0, 2, '角色管理', 2, 'system:role:index', '/admin/role/index', 1, '2017-01-12 16:46:52', null ), ( 7, '2017-01-12 16:47:02', '角色编辑', null, 0, 3, '角色编辑', 1, 'system:role:edit', '/admin/role/edit*', 2, '2017-01-18 10:24:06', 1 ), ( 8, '2017-01-12 16:47:23', '角色添加', null, 0, 3, '角色添加', 2, 'system:role:add', '/admin/role/add', 2, '2017-01-12 16:49:16', 6 ), ( 9, '2017-01-12 16:47:23', '角色删除', null, 0, 3, '角色删除', 3, 'system:role:deletebatch', '/admin/role/deletebatch', 2, '2017-01-18 14:12:03', 6 ), ( 10, '2017-01-12 16:47:23', '资源分配', null, 0, 3, '资源分配', 4, 'system:role:grant', '/admin/role/grant/**', 2, '2017-01-18 14:12:11', 6 ), ( 11, '2017-01-17 11:21:12', '资源管理', null, 0, 2, '资源管理', 3, 'system:resource:index', '/admin/resource/index', 1, '2017-01-17 11:21:42', null ), ( 12, '2017-01-17 11:21:52', '资源编辑', null, 0, 3, '资源编辑', 1, 'system:resource:edit', '/admin/resource/edit*', 2, '2017-01-17 11:22:36', 11 ), ( 13, '2017-01-17 11:21:54', '资源添加', null, 0, 3, '资源添加', 2, 'system:resource:add', '/admin/resource/add', 2, '2017-01-17 11:22:39', 11 ), ( 14, '2017-01-17 11:21:54', '资源删除', null, 0, 3, '资源删除', 3, 'system:resource:deletebatch', '/admin/resource/deletebatch', 2, '2017-01-18 14:12:31', 11 ), ( 15, '2017-01-17 11:21:54', '文件管理', null, 0, 2, '文件管理', 3, 'web:file:index', '/file/index', 1, '2017-01-18 14:12:31', null ), ( 16, '2017-01-17 11:21:54', '文件添加', null, 0, 3, '文件添加', 1, 'web:file:add', '/file/add', 2, '2017-01-18 14:12:31', 15 ), ( 17, '2017-01-17 11:21:54', '文件编辑', null, 0, 3, '文件编辑', 2, 'web:file:edit', '/file/edit/*', 2, '2017-01-18 14:12:31', 15 ), ( 18, '2017-01-17 11:21:54', '文件删除', null, 0, 3, '文件删除', 3, 'web:file:deletebatch', '/file/deletebatch', 2, '2017-01-18 14:12:31', 15 ), ( 19, '2017-01-17 11:21:54', '文件下载', null, 0, 3, '文件下载', 4, 'web:file:download', '/file/download/*', 2, '2017-01-18 14:12:31', 15 );
# insert into `tb_role_resource` (`role_id`, `resource_id`) values (1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8), (1, 9), (1, 10), (1, 11), (1, 12), (1, 13), (1, 14), (1, 15), (1, 16), (1, 17), (1, 18), (1, 19);
#
# insert  into `tb_user`(`id`,`address`,`birthday`,`create_time`,`delete_status`,`description`,`email`,`locked`,`nick_name`,`password`,`sex`,`telephone`,`update_time`,`user_name`) values (2,'武汉','2018-04-03 00:00:00','2018-04-29 11:13:41',0,'文件管理员','tt@163.com',0,'文件管理员','3931MUEQD1939MQMLM4AISPVNE',1,'13000000000','2018-04-29 11:13:41','test');
# insert  into `tb_role`(`id`,`create_time`,`description`,`role_key`,`name`,`status`,`update_time`) values (2,'2018-04-29 11:16:59','文件管理员','文件管理员','test',0,'2018-04-29 11:16:59');
# insert  into `tb_role_resource` (`role_id`, `resource_id`) values (2, 15), (2, 16), (2, 17), (2, 18), (2, 19);
# insert  into `tb_user_role`(`user_id`,`role_id`) values (2,2);

INSERT INTO `tb_resource` ( `id`, `create_time`, `description`, `icon`, `is_hide`, `level`, `name`, `sort`, `source_key`, `source_url`, `type`, `update_time`, `parent_id` ) VALUES ( 0, '2017-01-10 13:56:51', '系统菜单', NULL, 0, 0, '系统菜单', NULL, 'root', NULL, NULL, '2017-01-10 13:59:01', NULL );
UPDATE `tb_resource` SET `id`='0';
insert into `tb_role` ( `id`, `create_time`, `description`, `role_key`, `name`, `status`, `update_time` ) values ( 1, '2017-01-09 17:25:30', '超级管理员', 'administrator', 'administrator', 0, '2017-01-09 17:26:25' ), ( 2, '2018-04-29 11:16:59', '文件管理员', '文件管理员', 'test', 0, '2018-04-29 11:16:59' );
insert into `tb_user` ( `id`, `address`, `birthday`, `create_time`, `delete_status`, `description`, `email`, `locked`, `nick_name`, `password`, `sex`, `telephone`, `update_time`, `user_name` ) values ( 1, '武汉', '2017-01-09 17:26:39', '2017-01-09 17:26:41', 0, '超级管理员', 'lkddpc520@vip.qq.com', 0, '超级管理员', '3931MUEQD1939MQMLM4AISPVNE', 1, '18607134542', '2017-01-09 17:27:11', 'superadmin' ), ( 2, '武汉', '2018-04-03 00:00:00', '2018-04-29 11:13:41', 0, '管理员', 'tt@163.com', 0, '管理员', '3931MUEQD1939MQMLM4AISPVNE', 1, '13000000000', '2018-04-29 11:13:41', 'admin' );;
insert into `tb_user_role` (`user_id`, `role_id`) values (1, 1), (2, 2);
insert into `tb_resource` ( `id`, `create_time`, `description`, `icon`, `is_hide`, `level`, `name`, `sort`, `source_key`, `source_url`, `type`, `update_time`, `parent_id` ) values ( 1, '2017-01-10 13:56:51', '系统管理', null, 0, 1, '系统管理', 1, 'system', null, 0, '2017-01-10 13:59:01', 0 ), ( 2, '2017-01-10 13:56:51', '用户管理', null, 0, 2, '用户管理', 1, 'system:user:index', '/admin/user/index', 1, '2017-01-10 13:59:01', 1 ), ( 3, '2017-01-10 13:56:51', '用户编辑', null, 0, 3, '用户编辑', 1, 'system:user:edit', '/admin/user/edit*', 2, '2017-01-10 16:26:42', 2 ), ( 4, '2017-01-11 16:48:48', '用户添加', null, 0, 3, '用户添加', 2, 'system:user:add', '/admin/user/add', 2, '2017-01-11 16:49:26', 2 ), ( 5, '2017-01-11 16:48:48', '用户删除', null, 0, 3, '用户删除', 3, 'system:user:deletebatch', '/admin/user/deletebatch', 2, '2017-01-18 14:11:41', 2 ), ( 6, '2017-01-11 16:48:48', '角色分配', null, 0, 3, '角色分配', 4, 'system:user:grant', '/admin/user/grant/**', 2, '2017-01-18 14:11:51', 2 ), ( 7, '2017-01-12 16:45:10', '角色管理', null, 0, 2, '角色管理', 2, 'system:role:index', '/admin/role/index', 1, '2017-01-12 16:46:52', 1 ), ( 8, '2017-01-12 16:47:02', '角色编辑', null, 0, 3, '角色编辑', 1, 'system:role:edit', '/admin/role/edit*', 2, '2017-01-18 10:24:06', 7 ), ( 9, '2017-01-12 16:47:23', '角色添加', null, 0, 3, '角色添加', 2, 'system:role:add', '/admin/role/add', 2, '2017-01-12 16:49:16', 7 ), ( 10, '2017-01-12 16:47:23', '角色删除', null, 0, 3, '角色删除', 3, 'system:role:deletebatch', '/admin/role/deletebatch', 2, '2017-01-18 14:12:03', 7 ), ( 11, '2017-01-12 16:47:23', '资源分配', null, 0, 3, '资源分配', 4, 'system:role:grant', '/admin/role/grant/**', 2, '2017-01-18 14:12:11', 7 ), ( 12, '2017-01-17 11:21:12', '资源管理', null, 0, 2, '资源管理', 3, 'system:resource:index', '/admin/resource/index', 1, '2017-01-17 11:21:42', 1 ), ( 13, '2017-01-17 11:21:52', '资源编辑', null, 0, 3, '资源编辑', 1, 'system:resource:edit', '/admin/resource/edit*', 2, '2017-01-17 11:22:36', 12 ), ( 14, '2017-01-17 11:21:54', '资源添加', null, 0, 3, '资源添加', 2, 'system:resource:add', '/admin/resource/add', 2, '2017-01-17 11:22:39', 12 ), ( 15, '2017-01-17 11:21:54', '资源删除', null, 0, 3, '资源删除', 3, 'system:resource:deletebatch', '/admin/resource/deletebatch', 2, '2017-01-18 14:12:31', 12 ), ( 16, '2017-01-10 13:56:51', '功能', null, 0, 1, '功能', 1, 'func', null, 0, '2017-01-10 13:59:01', 0 ), ( 17, '2017-01-17 11:21:54', '公共文件', null, 0, 2, '公共文件', 1, 'web:file:index', '/file/index', 1, '2017-01-18 14:12:31', 16 ), ( 18, '2017-01-17 11:21:54', '公共文件添加', null, 0, 3, '公共文件添加', 1, 'web:file:add', '/file/add', 2, '2017-01-18 14:12:31', 17 ), ( 19, '2017-01-17 11:21:54', '公共文件编辑', null, 0, 3, '公共文件编辑', 2, 'web:file:edit', '/file/edit/*', 2, '2017-01-18 14:12:31', 17 ), ( 20, '2017-01-17 11:21:54', '公共文件逻辑删除', null, 0, 3, '公共文件逻辑删除', 3, 'web:file:logicdeletebatch', '/file/logicdeletebatch', 2, '2017-01-18 14:12:31', 17 ), ( 21, '2017-01-17 11:21:54', '公共文件下载', null, 0, 3, '公共文件下载', 4, 'web:file:download', '/file/download/*', 2, '2017-01-18 14:12:31', 17 ), ( 22, '2017-01-12 16:45:10', '部门管理', null, 0, 2, '部门管理', 4, '/admin/dept/index', '/admin/dept/index', 1, '2017-01-12 16:46:52', 1 ), ( 23, '2017-01-12 16:47:02', '部门编辑', null, 0, 3, '部门编辑', 1, 'system:dept:edit', '/admin/dept/edit*', 2, '2017-01-18 10:24:06', 22 ), ( 24, '2017-01-12 16:47:23', '部门添加', null, 0, 3, '部门添加', 2, 'system:dept:add', '/admin/dept/add', 2, '2017-01-12 16:49:16', 22 ), ( 25, '2017-01-12 16:47:23', '部门删除', null, 0, 3, '部门删除', 3, 'system:dept:deletebatch', '/admin/dept/deletebatch', 2, '2017-01-18 14:12:03', 22 ), ( 26, '2017-01-12 16:47:23', '关联部门', null, 0, 3, '关联部门', 3, 'system:user:grantdept', '/admin/user/grantdept/**', 2, '2017-01-18 14:12:03', 2 ), ( 27, '2017-01-17 11:21:54', '公共文件回收站', null, 0, 2, '公共文件回收站', 3, 'web:file:recycle', '/file/recycle', 1, '2017-01-18 14:12:31', 16 ), ( 28, '2017-01-17 11:21:54', '公共文件还原', null, 0, 3, '公共文件还原', 1, 'web:file:reductionbatch', '/file/reductionbatch/*', 2, '2017-01-18 14:12:31', 27 ), ( 29, '2017-01-17 11:21:54', '公共文件删除', null, 0, 3, '公共文件删除', 2, 'web:file:deletebatch', '/file/deletebatch/*', 2, '2017-01-18 14:12:31', 27 ), ( 30, '2017-01-17 11:21:54', '部门文件', null, 0, 2, '部门文件', 2, 'web:deptfile:index', '/deptfile/index', 1, '2017-01-18 14:12:31', 16 ), ( 31, '2017-01-17 11:21:54', '部门文件添加', null, 0, 3, '部门文件添加', 1, 'web:deptfile:add', '/deptfile/add', 2, '2017-01-18 14:12:31', 30 ), ( 32, '2017-01-17 11:21:54', '部门文件编辑', null, 0, 3, '部门文件编辑', 2, 'web:deptfile:edit', '/deptfile/edit/*', 2, '2017-01-18 14:12:31', 30 ), ( 33, '2017-01-17 11:21:54', '部门文件逻辑删除', null, 0, 3, '部门文件逻辑删除', 3, 'web:deptfile:logicdeletebatch', '/deptfile/logicdeletebatch', 2, '2017-01-18 14:12:31', 30 ), ( 34, '2017-01-17 11:21:54', '部门文件下载', null, 0, 3, '部门文件下载', 4, 'web:deptfile:download', '/deptfile/download/*', 2, '2017-01-18 14:12:31', 30 ), ( 35, '2017-01-17 11:21:54', '部门文件回收站', null, 0, 4, '部门文件回收站', 4, 'web:deptfile:recycle', '/deptfile/recycle', 1, '2017-01-18 14:12:31', 16 ), ( 36, '2017-01-17 11:21:54', '部门文件还原', null, 0, 3, '部门文件还原', 1, 'web:deptfile:reductionbatch', '/deptfile/reductionbatch/*', 2, '2017-01-18 14:12:31', 35 ), ( 37, '2017-01-17 11:21:54', '部门文件删除', null, 0, 3, '部门文件删除', 2, 'web:deptfile:deletebatch', '/deptfile/deletebatch/*', 2, '2017-01-18 14:12:31', 35 );
insert into `tb_role_resource` (`role_id`, `resource_id`) values (1, 0), (1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8), (1, 9), (1, 10), (1, 11), (1, 12), (1, 13), (1, 14), (1, 15),(1, 22), (1, 23), (1, 24), (1, 25), (1, 26), (2, 0), (2, 16), (2, 17), (2, 18), (2, 19), (2, 20), (2, 21), (2, 27), (2, 28), (2, 29), (2, 30), (2, 31), (2, 32), (2, 33), (2, 34), (2, 35), (2, 36), (2, 37);
