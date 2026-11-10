package org.siddardha.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateProductRequest(
        @NotBlank
        String name,
        @Size(min = 3, max = 30, message = "description should be 3 to 30")
        String description,
        @NotNull
        @PositiveOrZero
        BigDecimal price,
        String category
) {
}
