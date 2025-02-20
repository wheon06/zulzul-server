package com.wheon.zulzulserver.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class NewRecordRequestDto {
    Date date;
    String title;
    int price;
    Long categoryId;
    String memo;
}
