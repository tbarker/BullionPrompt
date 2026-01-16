package com.thomasbarker.bullionprompt.model;

import lombok.Data;

import java.util.List;

@Data
public final class PositionsAndPendingSettlements {
	private final List<Position> positions;
	private final List<PendingSettlement> pendingSettlements;

	public PositionsAndPendingSettlements( List<Position> positions, List<PendingSettlement> pendingSettlements ) {
		this.positions = positions;
		this.pendingSettlements = pendingSettlements;
	}
}
