CREATE TABLE `employee` (
  `emp_id` bigint NOT NULL AUTO_INCREMENT,
  `emp_name` varchar(45) NOT NULL,
  `emp_phone_no` varchar(20) NOT NULL,
  `emp_dept_id` bigint DEFAULT NULL,
  `emp_status` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`emp_id`),
  KEY `FKge0be6htuviv2ymqi817l43qd` (`emp_dept_id`),
  CONSTRAINT `FKge0be6htuviv2ymqi817l43qd` FOREIGN KEY (`emp_dept_id`) REFERENCES `department` (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
