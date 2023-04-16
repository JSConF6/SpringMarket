package com.spring.market.service;

import com.spring.market.domain.alarm.Alarm;
import com.spring.market.domain.alarm.AlarmMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AlarmService {
    private final AlarmMapper alarmMapper;

    public int getAlarmCount(int userId) {
        return alarmMapper.countByUserId(userId);
    }

    @Transactional
    public void saveAlarm(Alarm alarm) {
        alarmMapper.saveAlarm(alarm);
    }

    @Transactional
    public void updateReadYn(int alarmId) {
        alarmMapper.updateReadYn(alarmId);
    }
}
