### 数据库

```
CREATE TABLE IF NOT EXISTS user(
	uid INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(255) UNIQUE NOT NULL,
	passwd VARCHAR(255) NOT NULL,
	phone VARCHAR(255) NOT NULL,
	email VARCHAR(255) DEFAULT NULL
)DEFAULT CHARSET=UTF8;

+--------+--------------+------+-----+---------+----------------+
| Field  | Type         | Null | Key | Default | Extra          |
+--------+--------------+------+-----+---------+----------------+
| uid    | int(11)      | NO   | PRI | NULL    | auto_increment |
| name   | varchar(255) | NO   | UNI | NULL    |                |
| passwd | varchar(255) | NO   |     | NULL    |                |
| phone  | varchar(255) | NO   |     | NULL    |                |
| email  | varchar(255) | YES  |     | NULL    |                |
+--------+--------------+------+-----+---------+----------------+

```

```
CREATE TABLE IF NOT EXISTS house(
	id INT AUTO_INCREMENT, 
	name VARCHAR(255) NOT NULL, 		#房屋名称
	pn INT NOT NULL,			#可住人数
	time_short INT NOT NULL,
	time_long INT NOT NULL,
	area DOUBLE NOT NULL,			#面积
	price DOUBLE NOT NULL,			#单价
	address VARCHAR(255) NOT NULL,		#地址
	info TEXT(65535) NOT NULL,		#描述
	tel_name VARCHAR(255) NOT NULL, 	#联系人
	tel_num VARCHAR(255) NOT NULL, 		#联系电话
	enable BOOLEAN DEFAULT FALSE, 		#审核通过
	uid_master INT NOT NULL,		#发布人
	image TEXT(65535),			#图片文件名, 以:分割
	PRIMARY KEY(id)
)DEFAULT CHARSET=UTF8;

+------------+--------------+------+-----+---------+----------------+
| Field      | Type         | Null | Key | Default | Extra          |
+------------+--------------+------+-----+---------+----------------+
| id         | int(11)      | NO   | PRI | NULL    | auto_increment |
| name       | varchar(255) | NO   |     | NULL    |                |
| pn         | int(11)      | NO   |     | NULL    |                |
| time_short | int(11)      | NO   |     | NULL    |                |
| time_long  | int(11)      | NO   |     | NULL    |                |
| area       | double       | NO   |     | NULL    |                |
| price      | double       | NO   |     | NULL    |                |
| address    | varchar(255) | NO   |     | NULL    |                |
| info       | mediumtext   | NO   |     | NULL    |                |
| tel_name   | varchar(255) | NO   |     | NULL    |                |
| tel_num    | varchar(255) | NO   |     | NULL    |                |
| enable     | tinyint(1)   | YES  |     | 0       |                |
| uid_master | int(11)      | NO   |     | NULL    |                |
| image      | mediumtext   | YES  |     | NULL    |                |
+------------+--------------+------+-----+---------+----------------+
```


```

CREATE TABLE `user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `passwd` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

CREATE TABLE `house` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `pn` int(11) NOT NULL,
  `time_short` int(11) NOT NULL,
  `time_long` int(11) NOT NULL,
  `area` double NOT NULL,
  `price` double NOT NULL,
  `address` varchar(255) NOT NULL,
  `info` mediumtext NOT NULL,
  `tel_name` varchar(255) NOT NULL,
  `tel_num` varchar(255) NOT NULL,
  `enable` tinyint(1) DEFAULT '0',
  `uid_master` int(11) NOT NULL,
  `image` mediumtext,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=141 DEFAULT CHARSET=utf8

 CREATE TABLE `orde` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hid` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `times` varchar(255) NOT NULL,
  `timee` varchar(255) NOT NULL,
  `n` int(11) NOT NULL,
  `price` double NOT NULL,
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8

```








