package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Author: Xardas
 * Date: 09.01.16
 * Time: 20:53
 */
public class ClusterBootstrapAction {
    @JsonProperty("script_path")
    protected String scriptPath;
    @JsonProperty
    protected List<String> args;

    protected ClusterBootstrapAction() {
    }

    public ClusterBootstrapAction(String scriptPath, List<String> args) {
        this.scriptPath = scriptPath;
        this.args = args;
    }

    /**
     *
     * @return the path of the bootstrap action script
     */
    public String getScriptPath() {
        return scriptPath;
    }

    /**
     *
     * @return the array of script parameters. It is an optional field.
     */
    public List<String> getArgs() {
        return args;
    }
}
