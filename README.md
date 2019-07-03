### 数据库

```
CREATE TABLE IF NOT EXISTS user(
	uid INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(255) UNIQUE NOT NULL,
	passwd VARCHAR(255) NOT NULL,
	phone VARCHAR(255) NOT NULL,
	email VARCHAR(255) DEFAULT NULL
);

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
