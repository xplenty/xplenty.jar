package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xplenty.api.Xplenty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Settings specific for the email hook
 * Author: Xardas
 * Date: 24.01.16
 * Time: 19:32
 */
public class EmailHookSettings implements HookSettings {
    @JsonProperty
    @JsonSerialize(using = EmailsSerializer.class)
    @JsonDeserialize(using = EmailsDeserializer.class)
    protected List<String> emails;

    protected EmailHookSettings() {
    }

    public EmailHookSettings(List<String> emails) {
        this.emails = emails;
    }

    /**
     *
     * @return list of emails
     */
    public List<String> getEmails() {
        return emails;
    }

    @Override
    public Xplenty.HookType getType() {
        return Xplenty.HookType.email;
    }

    protected static class EmailsSerializer extends JsonSerializer<List<String>> {
        @Override
        public void serialize(List<String> emails, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
            if (emails == null || emails.size() == 0) {
                return;
            }
            // For more flexible solution, right now don't see anything against direct field name:
            // http://stackoverflow.com/questions/33519354/how-to-get-property-or-field-name-in-a-custom-json-serializer
            StringBuilder emailsBuilder = new StringBuilder();
            for (String email : emails) {
                emailsBuilder.append(email).append(",");
            }
            emailsBuilder.setLength(emailsBuilder.length() - 1);
            jgen.writeString(emailsBuilder.toString());
        }
    }

    protected static class EmailsDeserializer extends JsonDeserializer<List<String>> {

        @Override
        public List<String> deserialize(JsonParser json, DeserializationContext deserializeCtx) throws IOException, JsonProcessingException {
            JsonNode rawEmails = json.getCodec().readTree(json);
            if (rawEmails.isNull()) {
                return null;
            }
            String[] emailArray = rawEmails.asText().split(",");
            List<String> emails = new ArrayList<>(emailArray.length);
            for (String email :emailArray) {
                emails.add(email);
            }
            return emails;
        }
    }
}
