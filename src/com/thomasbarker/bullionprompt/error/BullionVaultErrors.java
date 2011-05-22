package com.thomasbarker.bullionprompt.error;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class BullionVaultErrors extends RuntimeException {

	@Getter final private List<String> errors;

}
