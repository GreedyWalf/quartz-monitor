## 实现动态管理quartz定时任务
### 1、添加定时任务配置表、定时任务执行明细表
```
-- 创建定时任务配置表
DROP TABLE IF EXISTS `task_scheduler`;
CREATE TABLE `task_scheduler` (
  `job_id` varchar(32) NOT NULL,
  `job_name` varchar(255) DEFAULT NULL,
  `job_group` varchar(255) DEFAULT NULL,
  `bean_class` varchar(255) DEFAULT NULL,
  `job_status` varchar(255) DEFAULT NULL,
  `cron_expression` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_concurrent` varchar(255) DEFAULT NULL COMMENT '1',
  `spring_id` varchar(255) DEFAULT NULL,
  `method_name` varchar(255) NOT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`job_id`),
  UNIQUE KEY `name_group` (`job_name`,`job_group`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `task_scheduler` VALUES ('1267847583883345922', 'test', 'testGroup', 'com.qs.quartz.service.impl.MyQuartzServiceImpl', '0', '0/10 * * * * ?', '10s执行一次', '0', 'myQuartzService', 'test', '2020-06-02 23:56:34', '2020-06-09 21:24:10');
INSERT INTO `task_scheduler` VALUES ('1268378596264333314', 'test2', 'test2Group', 'com.qs.quartz.service.impl.MyQuartzServiceImpl', '1', '0/10 * * * * ?', '10s执行一次', '1', 'myQuartzService', 'test2', '2020-06-04 11:06:37', '2020-06-07 23:54:23');


-- 创建定时任务执行明细表
DROP TABLE IF EXISTS `task_execute_detail`;
CREATE TABLE `task_execute_detail` (
  `detail_id` varchar(32) COLLATE utf8_bin NOT NULL,
  `job_id` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `thread_id` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `start_time` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `end_time` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `total_time` mediumtext COLLATE utf8_bin,
  `status` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
```



### 2、执行流程
- 项目启动时，注入QuartzSchedulerStarter对象时候会先执行init()方法，通过init()方法将task_scheduler表中配置的定时任务加载到quartz
中，并完成任务初始化调度。
- 通过定时任务管理页面，通过quartz提供的api，可以实现对运行中的定时任务暂停和恢复；可以实现动态的添加定时任务和修改定时任务表达式；
- 定时任务通过反射调用，在执行时会记录执行明细，通过对定时任务执行明细数据分析，可以监控每个定时任务执行情况，从而达到定时任务监控目的；

### 备注
- 项目使用的quartz版本为1.8.5，这里使用的为quartz的低版本api实现，高版本的api有些出入，具体以实际使用的版本为准；
- 项目采用springMvc + mybatis-plus + freemarker实现；
- 时间：2020-06-09 22:21:36