package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xplenty.api.Xplenty;

import java.io.IOException;
import java.util.List;

/**
 * Data model for Xplenty hook
 * An Xplenty hook is kind of notification that will be fired when state of resource (cluster or job) is changed.
 * Types of notifications can be specified with events variable.
 * Notification request contains hash which is generated using SHA-1 from string created by concatenation of the following fields:
 * <ul>
 * <li>id</li>
 * <li>event name</li>
 * <li>url</li>
 * <li>salt</li>
 * </ul>
 * Notified application can verify with salt if request was sent by Xplenty.
 * Author: Xardas
 * Date: 04.01.16
 * Time: 17:42
 */
@JsonDeserialize(using = Hook.HookDeserializer.class)
public class Hook extends XplentyObject<Hook> {
    @JsonProperty
    protected Long id;
    @JsonProperty
    protected String name;
    @JsonProperty
    protected Boolean active;
    @JsonProperty
    protected HookSettings settings;
    @JsonProperty
    protected String salt;
    @JsonProperty
    protected List<HookEvent> events;
    @JsonProperty
    protected Xplenty.HookType type;

    protected Hook() {
        super(Hook.class);
    }

    public Hook(String name, HookSettings settings, List<HookEvent> events) {
        super(Hook.class);
        this.settings = settings;
        this.events = events;
        this.name = name;
    }

    public Hook(Long id, String name, HookSettings settings, List<HookEvent> events) {
        super(Hook.class);
        this.id = id;
        this.settings = settings;
        this.events = events;
        this.name = name;
    }

    public Hook(Long id, Boolean active) {
        super(Hook.class);
        this.id = id;
        this.active = active;
    }

    /**
     *
     * @return the numeric hook ID
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @return Name of the hook
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return indicates whether the web hook is active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     *
     * @return settings specific for the web hook. It contains the following attributes
     */
    public HookSettings getSettings() {
        return settings;
    }

    /**
     *
     * @return list of notification events.
     */
    public List<HookEvent> getEvents() {
        return events;
    }

    /**
     *
     * @return salt needed for verification
     */
    public String getSalt() {
        return salt;
    }

    /**
     *
     * @return the type of this hook. Based on it {@link #settings} can be safely cast to one of the implementations
     */
    public Xplenty.HookType getType() {
        if (type != null) {
            return type;
        } else if (settings != null) {
            return settings.getType();
        }
        return null;
    }

    protected static class HookDeserializer extends JsonDeserializer<Hook> {

        @Override
        public Hook deserialize(JsonParser jp, DeserializationContext desCtx) throws IOException, JsonProcessingException {
            JsonNode root = jp.readValueAsTree();
            Hook hook;
            if (!root.has("id") && root.has("salt")) {
                hook = new Hook();
                hook.salt = root.get("salt").asText();
                return hook;
            }
            Long id = root.get("id").asLong();
            Boolean active = root.get("active").asBoolean();
            Xplenty.HookType hookType;
            try {
                hookType = Xplenty.HookType.valueOf(root.get("type").asText());
            } catch (IllegalArgumentException ex) {
                throw new IOException("This hook type not supported yet", ex);
            }

            hook = new Hook(id, active);
            HookSettings settings;
            JsonParser settingsParser = root.get("settings").traverse();
            // as it's created with empty codec, we need to explicitly set it (or objects won't be deserialized correctly
            settingsParser.setCodec(jp.getCodec());
            switch (hookType) {
                case email:
                    settings = settingsParser.readValueAs(EmailHookSettings.class);
                    break;
                case hipchat:
                    settings = settingsParser.readValueAs(HipChatHookSettings.class);
                    break;
                case pagerduty:
                    settings = settingsParser.readValueAs(PagerDutyHookSettings.class);
                    break;
                case slack:
                    settings = settingsParser.readValueAs(SlackHookSettings.class);
                    break;
                case web:
                    settings = settingsParser.readValueAs(WebHookSettings.class);
                    break;
                default:
                    throw new IOException("This hook type not supported yet");
            }
            hook.settings = settings;
            if (root.has("events")) {
                JsonParser eventParser = root.get("events").traverse();
                eventParser.setCodec(jp.getCodec());
                hook.events = eventParser.readValueAs(new TypeReference<List<HookEvent>>() {});
            }

            if (root.has("salt")) {
                hook.salt = root.get("salt").asText();
            }

            if (root.has("name")) {
                hook.name = root.get("name").asText();
            }

            return hook;
        }
    }
}
