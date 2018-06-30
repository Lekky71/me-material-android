package com.teammusa.mematerial.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.teammusa.mematerial.models.Metadata;

/**
 * Created by HashCode on 5:58 PM 27/05/2018.
 */
public class AuthResponse {

    @SerializedName("success")
    @Expose
    private String success;

    @SerializedName("metadata")
    @Expose
    private Metadata metadata;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
}
