package com.hivescm.codeid.error;

import static feign.FeignException.errorStatus;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignError implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {
        System.out.println("error");
        FeignException exception = errorStatus(methodKey, response);
		return exception;
	}

}
