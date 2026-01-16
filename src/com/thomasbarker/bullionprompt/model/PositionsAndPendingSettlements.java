package com.thomasbarker.bullionprompt.model;

import java.util.List;

public record PositionsAndPendingSettlements(
	List<Position> positions,
	List<PendingSettlement> pendingSettlements
) {}
