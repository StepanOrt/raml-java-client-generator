/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.raml.impl.v10.model;

import com.google.common.base.Function;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.mule.raml.model.*;
import org.raml.v2.api.model.v10.api.Api;
import org.raml.v2.api.model.v10.datamodel.*;
import org.raml.v2.api.model.v10.system.types.AnnotableSimpleType;

import javax.annotation.Nullable;
import java.util.*;

import static com.google.common.collect.Lists.transform;
import static org.mule.raml.ApiModelLoader.nullSafe;

public class ApiModelImpl implements ApiModel {

    private Api api;

    public ApiModelImpl(Api api) {
        this.api = api;
    }

    @Override
    public Map<String, Resource> getResources() {
        Map<String, Resource> map = new LinkedHashMap<>();
        List<org.raml.v2.api.model.v10.resources.Resource> resources = api.resources();
        for (org.raml.v2.api.model.v10.resources.Resource resource : resources) {
            map.put(resource.relativeUri().value(), new ResourceImpl(resource));
        }
        return map;
    }

    @Override
    public String getBaseUri() {
        return nullSafe(api.baseUri());
    }

    @Override
    public String getVersion() {
        return nullSafe(api.version());
    }

    @Override
    public List<Map<String, String>> getSchemas() {
        Map<String, String> map = new LinkedHashMap<>();
        List<TypeDeclaration> types = api.types();
        if (types.isEmpty()) {
            types = api.schemas();
        }
        for (TypeDeclaration typeDeclaration : types) {
            map.put(typeDeclaration.name(), getTypeAsString(typeDeclaration));
        }
        List<Map<String, String>> result = new ArrayList<>();
        result.add(map);
        return result;
    }

    @Override
    public List<DocumentationItem> getDocumentation() {
        return transform(api.documentation(), new Function<org.raml.v2.api.model.v10.api.DocumentationItem, DocumentationItem>() {
            @Nullable
            @Override
            public DocumentationItem apply(@Nullable org.raml.v2.api.model.v10.api.DocumentationItem input) {
                return new DocumentationItemImpl(input);
            }
        });
    }

    static String getTypeAsString(TypeDeclaration typeDeclaration) {
        JsonParser jsonParser = new JsonParser();
        if (typeDeclaration instanceof ObjectTypeDeclaration || typeDeclaration instanceof ArrayTypeDeclaration) {
            return replaceRefs(typeDeclaration.toJsonSchema());
        }
        if (typeDeclaration instanceof ExternalTypeDeclaration) {
            return ((ExternalTypeDeclaration) typeDeclaration).schemaContent();
        }
        if (typeDeclaration instanceof AnyTypeDeclaration) {
            return null;
        }
        //return non-null value in order to detect that a schema was defined
        return "[yaml-type-flag]";
    }

    private static String replaceRefs(String jsonSchemaString) {
        JsonParser jsonParser = new JsonParser();
        JsonObject rootObject = (JsonObject) jsonParser.parse(jsonSchemaString);
        JsonObject definitions = rootObject.getAsJsonObject("definitions");
        replaceRefs(rootObject, definitions);
        rootObject.remove("definitions");
        return rootObject.toString();
    }

    private static void replaceRefs(JsonElement base, JsonObject definitions) {
        if (base instanceof JsonArray) {
            for (JsonElement item : base.getAsJsonArray()) {
                replaceRefs(item, definitions);
            }
        }
        if (base instanceof JsonObject) {
            JsonElement ref = base.getAsJsonObject().get("$ref");
            if (ref != null) {
                base.getAsJsonObject().remove("$ref");
                String refString = ref.getAsString();
                    if (refString.startsWith("#/definitions/")) {
                        JsonObject rf = definitions;
                        for (String prop : refString.substring("#/definitions/".length()).split("/")) {
                            rf = rf.get(prop).getAsJsonObject();
                        }
                        ;
                        for (Iterator<Map.Entry<String, JsonElement>> iterator1 = rf.entrySet().iterator();iterator1.hasNext();) {
                            Map.Entry<String, JsonElement> refEntry = iterator1.next();
                            base.getAsJsonObject().add(refEntry.getKey(), refEntry.getValue());
                        }
                    }
            }
            for (Iterator<Map.Entry<String, JsonElement>> iterator = base.getAsJsonObject().entrySet().iterator(); iterator.hasNext();) {
                Map.Entry<String, JsonElement> elementEntry = iterator.next();
                if (elementEntry.getValue() != definitions) {
                    replaceRefs(elementEntry.getValue(), definitions);
                }
            }
        }
    }

    @Override
    public Map<String, TypeFieldDefinition> getBaseUriParameters() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<SecurityScheme> getSecuritySchemes() {
        return transform(api.securitySchemes(), new Function<org.raml.v2.api.model.v10.security.SecurityScheme, SecurityScheme>() {
            @Nullable
            @Override
            public SecurityScheme apply(@Nullable org.raml.v2.api.model.v10.security.SecurityScheme input) {
                return new SecuritySchemeImpl(input);
            }
        });

    }

    @Override
    public String getTitle() {
        final AnnotableSimpleType<String> title = api.title();
        return title != null ? title.value() : null;
    }

}
