/*
 * Copyright (c) 2017 Couchbase, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.talend.components.couchbase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.talend.components.api.component.Connector;
import org.talend.components.api.component.PropertyPathConnector;
import org.talend.components.common.FixedConnectorsComponentProperties;
import org.talend.components.common.SchemaProperties;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget;
import org.talend.daikon.properties.property.Property;

import java.util.EnumSet;

import static org.talend.daikon.properties.presentation.Widget.widget;
import static org.talend.daikon.properties.property.PropertyFactory.newProperty;
import static org.talend.daikon.properties.property.PropertyFactory.newString;

public abstract class CouchbaseProperties extends FixedConnectorsComponentProperties {
    private static Logger LOG = LoggerFactory.getLogger(CouchbaseProperties.class);
    public Property<String> bucket = newString("bucket");
    public Property<String> password = newProperty("password").setRequired()
            .setFlags(EnumSet.of(Property.Flags.ENCRYPT, Property.Flags.SUPPRESS_LOGGING));
    public Property<String> bootstrapNodes = newString("bootstrapNodes");
    public SchemaProperties schema = new SchemaProperties("schema");
    protected transient PropertyPathConnector MAIN_CONNECTOR = new PropertyPathConnector(Connector.MAIN_NAME, "schema");

    public CouchbaseProperties(String name) {
        super(name);
    }

    @Override
    public void setupProperties() {
        super.setupProperties();
        bucket.setValue("default");
        password.setValue("");
        bootstrapNodes.setValue("localhost");
    }

    @Override
    public void setupLayout() {
        super.setupLayout();
        Form mainForm = Form.create(this, Form.MAIN);
        mainForm.addRow(bucket);
        mainForm.addRow(widget(password).setWidgetType(Widget.HIDDEN_TEXT_WIDGET_TYPE));
        mainForm.addRow(bootstrapNodes);
        mainForm.addRow(schema.getForm(Form.REFERENCE));
    }
}
