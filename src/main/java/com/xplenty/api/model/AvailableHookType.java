package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Author: Xardas
 * Date: 23.02.16
 * Time: 20:15
 */
public class AvailableHookType extends XplentyObject<AvailableHookType> {

    @JsonProperty
    protected String type;
    @JsonProperty
    protected String name;
    @JsonProperty
    protected String description;
    @JsonProperty("icon_url")
    protected String iconUrl;
    @JsonProperty
    protected List<HookTypeGroup> groups;


    protected AvailableHookType() {
        super(AvailableHookType.class);
    }

    /**
     *
     * @return hook type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @return hook name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return hook description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return url of hook icon
     */
    public String getIconUrl() {
        return iconUrl;
    }

    /**
     *
     * @return list of groups this hook belongs to
     */
    public List<HookTypeGroup> getGroups() {
        return groups;
    }

    public static class HookTypeGroup {
        @JsonProperty("group_type")
        protected String groupType;
        @JsonProperty("group_name")
        protected String groupName;

        /**
         *
         * @return type of hook group
         */
        public String getGroupType() {
            return groupType;
        }

        /**
         *
         * @return name of hook group
         */
        public String getGroupName() {
            return groupName;
        }
    }
}
