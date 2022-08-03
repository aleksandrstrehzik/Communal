package org.example.dto;

import lombok.*;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsumerDto {

    private Integer id;
    private OperatorDto operator;
    private ElectricityTariffDto electricityTariff;
    private HeatTariffDto heatTariff;
    private GasTariffDto gasTariff;

    @NotBlank(message = "Введите имя")
    @Size(min = 2, max = 15, message = "Имя не может быть короче 2 или длиннее 15 символов")
    @Pattern(regexp = "^[а-яА-ЯеЁa-zA-Z]+$", message = "В имени не должно быть цифр")
    private String name;

    @NotBlank(message = "Введите фамилию")
    @Size(min = 2, max = 15, message = "Фамилия не может быть короче 2 или длиннее 15 символов")
    @Pattern(regexp = "^[а-яА-ЯеЁa-zA-Z]+$", message = "В фамилии не должно быть цифр")
    private String surname;

    @NotNull(message = "Введите площадь")
    @Max(value = 1000, message = "Плошадь не может быть меньше 2 или больше 1000 метров квадратных")
    @Min(value = 9, message = "Плошадь не может быть меньше 2 или больше 1000 метров квадратных")
    private Double apartmentSquare;

    @NotNull(message = "Введите количество жильцов")
    @Min(value = 1, message = "Значение должно быть в пределах от 1 до 20")
    @Max(value = 20, message = "Значение должно быть в пределах от 1 до 20")
    private Integer numberOfResidents;
    private BigDecimal sw;
}
