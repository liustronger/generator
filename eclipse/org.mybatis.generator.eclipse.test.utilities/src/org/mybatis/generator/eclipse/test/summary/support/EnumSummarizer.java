/*
 *    Copyright 2006-2025 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */
package org.mybatis.generator.eclipse.test.summary.support;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;

public class EnumSummarizer extends AbstractTypeOrEnumSummarizer {

    private EnumDeclaration node;
    
    private EnumSummarizer(EnumDeclaration node) {
        super(node);
        this.node = node;
    }
    
    public static EnumSummarizer from(EnumDeclaration node) {
        return new EnumSummarizer(node);
    }
    
    @SuppressWarnings("unchecked")
    public List<String> getEnumConstants() {
        return getEnumConstants(node.enumConstants());
    }
    
    private List<String> getEnumConstants(List<EnumConstantDeclaration> enums) {
        List<String> enumConstants = new ArrayList<>();
        for (EnumConstantDeclaration enumConstant : enums) {
            enumConstants.add(enumConstant.getName().getFullyQualifiedName());
        }
        return enumConstants;
    }
}
