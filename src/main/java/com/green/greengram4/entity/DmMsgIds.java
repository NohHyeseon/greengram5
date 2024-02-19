package com.green.greengram4.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@Embeddable
@EqualsAndHashCode//같은레코드 셀렉시 true가 되게끔 해줌
public class DmMsgIds implements Serializable {
    private Long idm;//foreign key걸 때 주기 때문에 여기서 안적어도됨
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long seq; //복합키 만들어주는?
    // foreign key를 안걸기 때문에
    //bigint unsigned 를 주기때문에
}
