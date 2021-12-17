package com.ominfo.crm_solution.network;

import static com.ominfo.crm_solution.network.Status.ERROR;
import static com.ominfo.crm_solution.network.Status.LOADING;
import static com.ominfo.crm_solution.network.Status.SUCCESS;

import com.google.gson.JsonElement;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

/*this class is responsible for com.app.teketeke.network response*/
public class ApiResponse {
    public final Status status;

    @Nullable
    public final JsonElement data;

    @Nullable
    public final Throwable error;

    public String accountAlias;

    private ApiResponse(Status status, @Nullable JsonElement data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    private ApiResponse(Status status, @Nullable JsonElement data, @Nullable Throwable error, String accountAlias) {
        this.status = status;
        this.data = data;
        this.error = error;
        this.accountAlias = accountAlias;
    }

    public static ApiResponse loading() {
        return new ApiResponse(LOADING, null, null);
    }

    public static ApiResponse success(@NonNull JsonElement data) {
        return new ApiResponse(SUCCESS, data, null);
    }

    public static ApiResponse error(@NonNull Throwable error) {
        return new ApiResponse(ERROR, null, error);
    }

    public static ApiResponse successBalanceEnquiry(@NonNull JsonElement data, String accountAlias) {
        return new ApiResponse(SUCCESS, data, null, accountAlias);
    }

    public static ApiResponse errorForBalanceEnquiry(@NonNull Throwable error, String accountAlias) {
        return new ApiResponse(ERROR, null, error, accountAlias);
    }

}
