package com.kamthan.InventoryPro.dto.dashboard;

import com.kamthan.InventoryPro.model.enums.MovementType;
import com.kamthan.InventoryPro.model.enums.ReferenceType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class RecentStockMovementDTO {

    private LocalDateTime movementDate;
    private Long productId;
    private String productName;

    private MovementType movementType;
    private Integer quantity;

    private Integer beforeQuantity;
    private Integer afterQuantity;

    private ReferenceType referenceType;
    private Long referenceId;
}