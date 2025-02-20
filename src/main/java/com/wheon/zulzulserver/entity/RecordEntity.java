package com.wheon.zulzulserver.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "tb_record")
public class RecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Date date;

    @Column
    private String title;

    @Column
    private int price;

    @Column
    private String memo;

    @Builder
    public RecordEntity(Date date, String title, int price, String memo) {
        this.date = date;
        this.title = title;
        this.price = price;
        this.memo = memo;
    }

}
