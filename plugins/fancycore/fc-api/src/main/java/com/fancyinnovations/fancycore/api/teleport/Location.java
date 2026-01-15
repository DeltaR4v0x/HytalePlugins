package com.fancyinnovations.fancycore.api.teleport;

import com.google.gson.annotations.SerializedName;
import com.hypixel.hytale.math.vector.Transform;
import com.hypixel.hytale.math.vector.Vector3f;

public record Location(
        @SerializedName("world_name") String worldName,
        double x,
        double y,
        double z,
        float yaw,
        float pitch
) {

    public Transform toTransform() {
        return new Transform(
                x, y, z,
                Vector3f.FORWARD.getYaw(), Vector3f.FORWARD.getPitch(), 0
        );
    }
}
