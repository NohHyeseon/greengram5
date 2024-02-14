package com.green.greengram4.entity;

import com.green.greengram4.common.ProviderTypeEnum;
import com.green.greengram4.common.RoleEnum;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

@Data
@Entity //entity를 주면 PK를 무조건 적용해야함
@Table(name = "t_user", uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"provider_type", "uid"} //컬럼명으로 적어야함

        )
})
public class UserEntity extends BaseEntity {
    @Id //pk줄 때
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)//autoIncrement Key>>mysql, mariadb에서만 사용
    private Long iuser;

    @Column(length = 10, name = "provider_type", nullable = false) //columdefinition<-coment남길수 있음
    @Enumerated(value = EnumType.STRING) //Enum에서 쓰는 이름 그대로 사용하겠다
    @ColumnDefault("'LOCAl'")
    private ProviderTypeEnum providerType;

    @Column(length = 100, nullable = false)
    private String uid;

    @Column(length = 10, nullable = false)
    @Enumerated(value = EnumType.STRING)
    @ColumnDefault("'USER'")
    private RoleEnum role;

    @Column(length = 300, nullable = false)
    private String upw;

    @Column(length = 25, nullable = false)
    private String nm;

    @Column(length = 2100)
    private String pic;

    @Column(length = 2100, name = "firebase_token")
    private String fireBaseToken;


}
