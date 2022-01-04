
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- 缴费信息
-- ----------------------------
DROP TABLE IF EXISTS `drive_payMessage`;
CREATE TABLE `drive_payMessage`  (
      `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
      `pay_way` tinyint(1) NOT NULL DEFAULT 0 COMMENT '支付方式（0微信、1支付宝、2现金、3其他）',
      `content` tinyint(1) NOT NULL DEFAULT 0 COMMENT '缴费内容(0报名费、1科目一、2科目二、3科目三、4科目四、5其他)',
      `check` tinyint(1) NOT NULL DEFAULT 0 COMMENT '收费认定（0未受理、1已确认、2驳回）',
      `fk_student_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '外键student_id',


      `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除，0默认，1表示删除',
      `version` int(11) NULL DEFAULT 0 COMMENT '乐观锁',
      `memo` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
      `create_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人id',
      `create_user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
      `update_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人id',
      `update_user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
      `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
      `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

ALTER TABLE drive_payMessage COMMENT '缴费信息';
