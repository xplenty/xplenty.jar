package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xplenty.api.Xplenty;

/**
 * Interface representing specific hook settings
 * Author: Xardas
 * Date: 24.01.16
 * Time: 19:22
 */
public interface HookSettings {
    @JsonIgnore
    Xplenty.HookType getType();
}
