package com.spring.market.domain.alarm;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AlarmMapper {

    int countByUserId(int userId);

    void saveAlarm(Alarm alarm);

    void updateReadYn(int alarmId);
}
