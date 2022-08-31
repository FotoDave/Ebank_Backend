package org.work.ebankbackend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.work.ebankbackend.entities.BankAccount;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
public class CustomerDTO {
    private Long id;
    private String name;
    private String email;
}
