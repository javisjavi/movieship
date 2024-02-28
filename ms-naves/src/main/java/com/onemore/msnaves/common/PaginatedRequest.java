package com.onemore.msnaves.common;

import lombok.Data;

@Data
public class PaginatedRequest {
	
    private int pageNumber;
    private int pageSize;

	public PaginatedRequest(int pageNumber, int pageSize) {
		super();
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}

}

