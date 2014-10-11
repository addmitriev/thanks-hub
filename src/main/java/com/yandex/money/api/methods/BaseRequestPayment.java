package com.yandex.money.api.methods;

import com.yandex.money.api.model.Error;
import com.yandex.money.api.net.MethodResponse;

import java.math.BigDecimal;

/**
 * Base class for request payment operations.
 *
 * @author Slava Yasevich (vyasevich@yamoney.ru)
 */
public abstract class BaseRequestPayment implements MethodResponse {

    protected static final String MEMBER_STATUS = "status";
    protected static final String MEMBER_ERROR = "error";
    protected static final String MEMBER_REQUEST_ID = "request_id";
    protected static final String MEMBER_CONTRACT_AMOUNT = "contract_amount";

    private final Status status;
    private final Error error;
    private final String requestId;
    private final BigDecimal contractAmount;

    /**
     * Constructor.
     *
     * @param status status of the request
     * @param error error code
     * @param requestId unique request id
     * @param contractAmount contract amount
     */
    protected BaseRequestPayment(Status status, Error error, String requestId,
                                 BigDecimal contractAmount) {

        if (status == Status.SUCCESS && requestId == null) {
            throw new IllegalArgumentException("requestId is null when status is success");
        }
        this.status = status;
        this.error = error;
        this.requestId = requestId;
        this.contractAmount = contractAmount;
    }

    public Status getStatus() {
        return status;
    }

    public Error getError() {
        return error;
    }

    public String getRequestId() {
        return requestId;
    }

    public BigDecimal getContractAmount() {
        return contractAmount;
    }

    public enum Status {
        SUCCESS(CODE_SUCCESS),
        REFUSED(CODE_REFUSED),
        HOLD_FOR_PICKUP(CODE_HOLD_FOR_PICKUP),
        UNKNOWN(CODE_UNKNOWN);

        private final String status;

        private Status(String status) {
            this.status = status;
        }

        public static Status parse(String status) {
            for (Status value : values()) {
                if (value.status.equals(status)) {
                    return value;
                }
            }
            return UNKNOWN;
        }
    }
}
