package org.siddardha.dto;

import java.math.BigDecimal;

public record ProductResponse(
        int id,
        String name,
        String description,
        BigDecimal price,
        String category,
        boolean active
) {
}
