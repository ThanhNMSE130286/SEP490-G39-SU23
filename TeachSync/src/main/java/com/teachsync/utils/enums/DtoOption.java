package com.teachsync.utils.enums;

public enum DtoOption {
    /** Clazz */
    COURSE_SEMESTER,
    /** Clazz */
    CLAZZ_SCHEDULE,
    /** Clazz */
    SESSION_LIST,
    /** Clazz */
    MEMBER_LIST,
    /** Clazz */
    STAFF,
    /** Clazz */
    HOMEWORK_LIST,
    /** Clazz, Course */
    TEST_LIST,

    /** ClazzSchedule */
    CLAZZ_NAME,
    /** ClazzSchedule */
    ROOM_NAME,

    /** ClazzMember, Staff */
    USER,

    /** User */
    ROLE,
    /** User, Center */
    ADDRESS,

    /** Center */
    ROOM_LIST,
    /** Center */
    STAFF_LIST,

    /** Course */
    CLAZZ_LIST,
    /** Course */
    MATERIAL_LIST,
    /** Course */
    CURRENT_PRICE,

    /** CourseSemester */
    COURSE_NAME,
    /** CourseSemester */
    COURSE_ALIAS,
    /** CourseSemester, Staff */
    CENTER,
    /** CourseSemester */
    SEMESTER;
}