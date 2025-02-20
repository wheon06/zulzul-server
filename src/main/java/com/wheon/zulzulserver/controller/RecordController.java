package com.wheon.zulzulserver.controller;

import com.wheon.zulzulserver.dto.request.NewRecordRequestDto;
import com.wheon.zulzulserver.entity.RecordEntity;
import com.wheon.zulzulserver.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/record")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @PostMapping("")
    public ResponseEntity<String> addNewRecord(@RequestBody NewRecordRequestDto dto) {
        RecordEntity record = RecordEntity.builder()
                .date(dto.getDate())
                .title(dto.getTitle())
                .price(dto.getPrice())
                .memo(dto.getMemo())
                .build();
        return recordService.addNewRecord(record);
    }
}
