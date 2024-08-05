package com.honghao.cloud.basic.common.enums;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author chenhonghao12
 * @version 1.0
 */
public class CommonEnum {
    private static final Map<String, List<? extends CommonInterface<?>>> MAPS = Maps.newConcurrentMap();

    static {
        Class<?>[] declaredClasses = CommonEnum.class.getDeclaredClasses();
        try {
            for (Class<?> declaredClass : declaredClasses) {
                EnumName annotation = declaredClass.getAnnotation(EnumName.class);
                if (Objects.nonNull(annotation)) {
                    Field declaredField = declaredClass.getDeclaredField(annotation.fieldName());
                    declaredField.setAccessible(true);
                    Object o = declaredField.get(null);
                    List<? extends CommonInterface<?>> commonInterfaces = JSON.parseArray(JSON.toJSONString(o), annotation.classes());
                    MAPS.put(annotation.value(), commonInterfaces);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 面试方式枚举
     */
    @Getter
    @AllArgsConstructor
    @EnumName(value = "interviewType", classes = InterviewTypeEnum.class)
    public enum InterviewTypeEnum implements CommonInterface<Integer> {

        /**
         * 面试方式 0:线下叫号,1:线下指定时间面试,2:线上面试
         */
        off_line_sign(0, "线下叫号面试"),
        off_line_time(1, "线下指定时间面试"),
        online(2, "线上指定时间面试"),
        ;
        private final Integer code;
        private final String desc;


        private static final List<InterviewTypeEnum> VALUES = Arrays.stream(InterviewTypeEnum.values()).collect(Collectors.toList());
    }

    /**
     * 投递类型
     */
    @Getter
    @AllArgsConstructor
    @EnumName(value = "deliveryType", classes = DeliveryTypeEnum.class)
    public enum DeliveryTypeEnum implements CommonInterface<Integer> {

        /**
         * 0:专场投递流程，1:活动&标准
         */
        ONLY_NORMAL(0, "专场投递流程"),

        ALL(1, "标准投递流程&专场投递流程"),
        ;
        private final Integer code;
        private final String desc;
        private static final List<DeliveryTypeEnum> VALUES = Arrays.stream(DeliveryTypeEnum.values()).collect(Collectors.toList());
    }

    /**
     * 面试角色
     */
    @Getter
    @AllArgsConstructor
    @EnumName(value = "interviewRole", classes = InterviewRoleEnum.class)
    public enum InterviewRoleEnum implements CommonInterface<String> {

        BUSINESS_A("BUSINESS_A", "业务A"),
        BUSINESS_B("BUSINESS_B", "业务B"),
        HRBP("HR", "HRBP"),
        ELSE("ELSE", "其他"),

        ;
        private final String code;
        private final String desc;
        private static final List<InterviewRoleEnum> VALUES = Arrays.stream(InterviewRoleEnum.values()).collect(Collectors.toList());

        public static InterviewRoleEnum getByCode(String code) {
            return VALUES.stream().filter(each -> Objects.equals(each.getCode(), code)).findFirst().orElse(null);
        }
    }

    /**
     * 投递标签
     */
    @Getter
    @AllArgsConstructor
    @EnumName(value = "deliveryEnum", classes = DeliveryEnum.class)
    public enum DeliveryEnum implements CommonInterface<Integer> {

        ALL(1, "不限"),
        ACTIVITY(2, "专场招聘"),
        NORMAL(3, "常规招聘"),

        ;
        private final Integer code;
        private final String desc;
        private static final List<DeliveryEnum> VALUES = Arrays.stream(DeliveryEnum.values()).collect(Collectors.toList());

        public static DeliveryEnum getByCode(Integer code) {
            return VALUES.stream().filter(each -> Objects.equals(each.getCode(), code)).findFirst().orElse(null);
        }
    }

    /**
     * 操作类型
     */
    @Getter
    @AllArgsConstructor
    @EnumName(value = "operateEnum", classes = OperateEnum.class)
    public enum OperateEnum implements CommonInterface<String> {

        GREATER_THAN(">", "大于"),
        GREATER_THAN_EQUAL(">=", "大于等于"),
        LESS_THAN("<", "小于"),
        LESS_THAN_EQUAL("<=", "小于等于"),
        EQUALS("==", "等于"),
        IN("IN", "IN"),

        ;
        private final String code;
        private final String desc;
        private static final List<OperateEnum> VALUES = Arrays.stream(OperateEnum.values()).collect(Collectors.toList());

        public static OperateEnum getByCode(String code) {
            return VALUES.stream().filter(each -> Objects.equals(each.getCode(), code)).findFirst().orElse(null);
        }
    }

    /**
     * 是否包含面试
     */
    @Getter
    @AllArgsConstructor
    @EnumName(value = "includeInterview", classes = IncludeInterviewEnum.class)
    public enum IncludeInterviewEnum implements CommonInterface<Integer> {

        YES(0, "是"),
        NO(1, "否"),

        ;
        private final Integer code;
        private final String desc;
        private static final List<IncludeInterviewEnum> VALUES = Arrays.stream(IncludeInterviewEnum.values()).collect(Collectors.toList());
    }


    /**
     * 校招官网显示
     */
    @Getter
    @AllArgsConstructor
    @EnumName(value = "campusShowEnum", classes = CampusShowEnum.class)
    public enum CampusShowEnum implements CommonInterface<Integer> {
        SHOW(0, "不显示"),
        NO_SHOW(1, "显示"),

        ;
        private final Integer code;
        private final String desc;
        private static final List<CampusShowEnum> VALUES = Arrays.stream(CampusShowEnum.values()).collect(Collectors.toList());
    }


    /**
     * 是否自定义名称--0:是，1:否
     */
    @Getter
    @AllArgsConstructor
    @EnumName(value = "isCustomName", classes = IsCustomNameEnum.class)
    public enum IsCustomNameEnum implements CommonInterface<Integer> {
        YES(0, "是"),
        NO(1, "否"),

        ;
        private final Integer code;
        private final String desc;
        private static final List<IsCustomNameEnum> VALUES = Arrays.stream(IsCustomNameEnum.values()).collect(Collectors.toList());
    }


    /**
     * 是否仅该校毕业可报名 0:是，1:否
     */
    @Getter
    @AllArgsConstructor
    @EnumName(value = "onlySchoolJoin", classes = OnlySchoolJoinEnum.class)
    public enum OnlySchoolJoinEnum implements CommonInterface<Integer> {
        YES(0, "是"),
        NO(1, "否"),

        ;
        private final Integer code;
        private final String desc;
        private static final List<OnlySchoolJoinEnum> VALUES = Arrays.stream(OnlySchoolJoinEnum.values()).collect(Collectors.toList());
    }

    /**
     * 是否包含宣讲会 0:不包含，1:包含
     */
    @Getter
    @AllArgsConstructor
    @EnumName(value = "includePreach", classes = OnlySchoolJoinEnum.class)
    public enum IncludePreachEnum implements CommonInterface<Integer> {
        YES(0, "不包含"),
        NO(1, "包含"),

        ;
        private final Integer code;
        private final String desc;
        private static final List<OnlySchoolJoinEnum> VALUES = Arrays.stream(OnlySchoolJoinEnum.values()).collect(Collectors.toList());
    }

    public static List<? extends CommonInterface<?>> getAll(String type) {
        return MAPS.getOrDefault(type, Lists.newArrayList());
    }


}
