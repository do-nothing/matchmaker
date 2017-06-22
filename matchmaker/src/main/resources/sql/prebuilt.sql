drop table if exists event_types;

CREATE TABLE `event_types` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

truncate table event_types;
INSERT INTO event_types VALUES (1, '通信中断', null, now(), now());
INSERT INTO event_types VALUES (2, '通信恢复', null, now(), now());
INSERT INTO event_types VALUES (3, '关机', null, now(), now());
INSERT INTO event_types VALUES (4, '安装应用', null, now(), now());
INSERT INTO event_types VALUES (5, '启动应用', null, now(), now());
INSERT INTO event_types VALUES (6, '应用有人使用', null, now(), now());
INSERT INTO event_types VALUES (7, '应用空闲', null, now(), now());
INSERT INTO event_types VALUES (8, '电源开启', null, now(), now());
INSERT INTO event_types VALUES (9, '电源关闭', null, now(), now());

drop table if exists device_types;

CREATE TABLE `device_types` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO device_types VALUES (1, 'general', null, now(), now());
INSERT INTO device_types VALUES (2, 'kenict', null, now(), now());
INSERT INTO device_types VALUES (3, 'leapmotion', null, now(), now());
INSERT INTO device_types VALUES (4, 'touch', null, now(), now());
INSERT INTO device_types VALUES (5, 'vr', null, now(), now());
INSERT INTO device_types VALUES (6, 'guide', null, now(), now());
INSERT INTO device_types VALUES (7, 'monitor', null, now(), now());
INSERT INTO device_types VALUES (8, 'power_switch', null, now(), now());