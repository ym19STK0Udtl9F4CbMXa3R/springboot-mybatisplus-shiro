DROP TABLE IF EXISTS sys_user;

CREATE TABLE sys_user
(
  id BIGINT(20) NOT NULL COMMENT '主键ID',
  nick_name VARCHAR(20) NULL DEFAULT NULL COMMENT '昵称',
  username VARCHAR(20) NOT NULL COMMENT '用户名',
  password VARCHAR(64) NOT NULL COMMENT '密码',
  salt VARCHAR(50) NOT NULL COMMENT '加密盐',
  email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
  status TINYINT(1) NOT NULL DEFAULT '1' COMMENT '0是禁用,1是启用'
  create_time DATETIME DEFAULT NULL COMMENT '创建时间',
  update_time DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY(id),
  UNIQUE KEY username(username)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT='用户';


DROP TABLE IF EXISTS sys_role;

CREATE TABLE sys_role
(
  id BIGINT(20) NOT NULL COMMENT '主键ID',
  name VARCHAR(50) NULL DEFAULT NULL COMMENT '角色名称',
  remark VARCHAR(100) DEFAULT NULL COMMENT '备注',
  status TINYINT(1) NOT NULL DEFAULT '1' COMMENT '0是禁用,1是启用'
  create_time DATETIME DEFAULT NULL COMMENT '创建时间',
  update_time DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY(id),
) ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT '角色';


DROP TABLE IF EXISTS sys_user_role;

CREATE TABLE sys_user_role
(
  id BIGINT(20) NOT NULL COMMENT '主键ID',
  user_id BIGINT(20) NOT NULL COMMENT '用户ID',
  role_id BIGINT(20) NOT NULL COMMENT '角色ID',
  status TINYINT(1) NOT NULL DEFAULT '1' COMMENT '0是禁用,1是启用'
  create_time DATETIME DEFAULT NULL COMMENT '创建时间',
  update_time DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY(id),
) ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT '用户与角色关系表';


DROP TABLE IF EXISTS sys_permission;

CREATE TABLE sys_permission
(
  id BIGINT(20) NOT NULL COMMENT '主键ID',
  parent_id BIGINT(20) NOT NULL COMMENT '父级ID',
  name VARCHAR(50) NULL DEFAULT NULL COMMENT '权限名称',
  remark VARCHAR(100) DEFAULT NULL COMMENT '备注',
  url VARCHAR(100) DEFAULT NULL COMMENT '链接地址',
  resource varchar(50) DEFAULT NULL COMMENT '资源名称',
  status TINYINT(1) NOT NULL DEFAULT '1' COMMENT '0是禁用,1是启用'
  create_time DATETIME DEFAULT NULL COMMENT '创建时间',
  update_time DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY(id),
) ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT '权限表';


DROP TABLE IF EXISTS sys_role_permission;

CREATE TABLE sys_role_permission
(
  id BIGINT(20) NOT NULL COMMENT '主键ID',
  role_id BIGINT(20) NOT NULL COMMENT '角色ID',
  permission_id BIGINT(20) NOT NULL COMMENT '权限ID',
  status TINYINT(1) NOT NULL DEFAULT '1' COMMENT '0是禁用,1是启用'
  create_time DATETIME DEFAULT NULL COMMENT '创建时间',
  update_time DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY(id),
) ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT '角色与权限关系表';