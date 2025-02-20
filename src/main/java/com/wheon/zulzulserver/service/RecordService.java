package com.wheon.zulzulserver.service;

import com.wheon.zulzulserver.entity.RecordEntity;
import com.wheon.zulzulserver.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;

    public ResponseEntity<String> addNewRecord(RecordEntity record) {
        if (record.getDate().after(new Date()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Invalid date");

        recordRepository.save(record);

        return ResponseEntity.status(HttpStatus.CREATED).body("Record add success");
    }

}
