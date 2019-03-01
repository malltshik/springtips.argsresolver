package ru.malltshik.springtester.persistense.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
public abstract class BaseEntity {
    private Long accountId;
}
