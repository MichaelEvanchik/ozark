/*
 * Copyright © 2017, 2018 Ivar Grimstad
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package org.eclipse.krazo.ext.mustache;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheFactory;
import org.eclipse.krazo.engine.ViewEngineConfig;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Producer for the MustacheFactory used by MustacheViewEngine
 *
 * @author Christian Kaltepoth
 */
public class DefaultMustacheFactoryProducer {

    @Inject
    private ServletContext servletContext;

    @Produces
    @ViewEngineConfig
    public MustacheFactory getMustacheFactory() {
        return new KrazoMustacheFactory();
    }

    private class KrazoMustacheFactory extends DefaultMustacheFactory {
        @Override
        public Reader getReader(String resourceName) {
            InputStream is = servletContext.getResourceAsStream(resourceName);
            if (is != null) {
                return new BufferedReader(new InputStreamReader(is, UTF_8));
            }
            return super.getReader(resourceName);
        }
    }

}
