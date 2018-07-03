package com.fsats.mianshi.dao;

import com.fsats.mianshi.entity.RecordLog;
import org.springframework.stereotype.Repository;

@Repository("recodeLogMapper")
public interface RecordLogMapper {

    int insertLogger(RecordLog recordLog);
}
