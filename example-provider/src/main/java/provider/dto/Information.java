package provider.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "information")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Information {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String nationality;
    private Integer salary;

}
