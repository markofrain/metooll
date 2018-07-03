package com.fsats.mianshi.service.impl;

import com.fsats.mianshi.dao.RecordLogMapper;
import com.fsats.mianshi.entity.RecordLog;
import com.fsats.mianshi.service.RecordLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("recordLogService")
public class RecordLogServiceImpl implements RecordLogService {

    @Autowired
    private RecordLogMapper recordLogMapper;

    @Override
    public boolean addLogger(RecordLog recordLog) {
        int count = 0;
        try {
            count = recordLogMapper.insertLogger(recordLog);
        }catch (Throwable throwable){
            throwable.printStackTrace();
        }
        return count>0?true:false;
    }
}
