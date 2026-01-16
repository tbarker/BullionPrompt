package com.thomasbarker.bullionprompt.error;

import java.util.List;

public final class BullionVaultErrors extends RuntimeException {

	private final List<String> errors;

	public BullionVaultErrors(List<String> errors) {
		super();
		this.errors = errors;
	}

	public List<String> getErrors() {
		return errors;
	}
}
