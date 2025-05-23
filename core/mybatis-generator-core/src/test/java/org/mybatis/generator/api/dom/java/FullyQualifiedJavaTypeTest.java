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
 */
package org.mybatis.generator.api.dom.java;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mybatis.generator.api.dom.java.render.TopLevelInterfaceRenderer;

/**
 * @author Jeff Butler
 */
class FullyQualifiedJavaTypeTest {

    @Test
    void testJavaType() {
        FullyQualifiedJavaType fqjt =
            new FullyQualifiedJavaType("java.lang.String"); //$NON-NLS-1$
        assertFalse(fqjt.isExplicitlyImported());
        assertEquals("String", fqjt.getShortName()); //$NON-NLS-1$
        assertEquals("java.lang.String", fqjt.getFullyQualifiedName()); //$NON-NLS-1$
        assertEquals("java.lang", fqjt.getPackageName()); //$NON-NLS-1$
        assertEquals(0, fqjt.getImportList().size());
    }

    @Test
    void testSimpleType() {
        FullyQualifiedJavaType fqjt =
            new FullyQualifiedJavaType("com.foo.Bar"); //$NON-NLS-1$
        assertTrue(fqjt.isExplicitlyImported());
        assertEquals("Bar", fqjt.getShortName()); //$NON-NLS-1$
        assertEquals("com.foo.Bar", fqjt.getFullyQualifiedName()); //$NON-NLS-1$
        assertEquals("com.foo", fqjt.getPackageName()); //$NON-NLS-1$
        assertEquals(1, fqjt.getImportList().size());
        assertEquals("com.foo.Bar", fqjt.getImportList().get(0));
    }

    @Test
    void testSimpleType2() {
        FullyQualifiedJavaType fqjt =
            new FullyQualifiedJavaType("com.foo.bar"); //$NON-NLS-1$
        assertTrue(fqjt.isExplicitlyImported());
        assertEquals("bar", fqjt.getShortName()); //$NON-NLS-1$
        assertEquals("com.foo.bar", fqjt.getFullyQualifiedName()); //$NON-NLS-1$
        assertEquals("com.foo", fqjt.getPackageName()); //$NON-NLS-1$
        assertEquals(1, fqjt.getImportList().size());
        assertEquals("com.foo.bar", fqjt.getImportList().get(0));
    }

    @Test
    void testSimpleType3() {
        FullyQualifiedJavaType fqjt =
            new FullyQualifiedJavaType("int"); //$NON-NLS-1$
        assertFalse(fqjt.isExplicitlyImported());
        assertEquals("int", fqjt.getShortName()); //$NON-NLS-1$
        assertEquals("int", fqjt.getFullyQualifiedName()); //$NON-NLS-1$
        assertEquals("", fqjt.getPackageName()); //$NON-NLS-1$
        assertEquals(0, fqjt.getImportList().size());
    }

    @Test
    void testGenericType1() {
        FullyQualifiedJavaType fqjt =
            new FullyQualifiedJavaType("java.util.List<java.lang.String>"); //$NON-NLS-1$
        assertTrue(fqjt.isExplicitlyImported());
        assertEquals("List<String>", fqjt.getShortName()); //$NON-NLS-1$
        assertEquals("java.util.List<java.lang.String>", fqjt.getFullyQualifiedName()); //$NON-NLS-1$
        assertEquals("java.util", fqjt.getPackageName()); //$NON-NLS-1$
        assertEquals(1, fqjt.getImportList().size());
        assertEquals("java.util.List", fqjt.getImportList().get(0));
        assertEquals("java.util.List", fqjt.getFullyQualifiedNameWithoutTypeParameters()); //$NON-NLS-1$
    }

    @Test
    void testGenericType2() {
        FullyQualifiedJavaType fqjt =
            new FullyQualifiedJavaType("java.util.Map<java.lang.String, java.util.List<java.lang.String>>"); //$NON-NLS-1$
        assertTrue(fqjt.isExplicitlyImported());
        assertEquals("Map<String, List<String>>", fqjt.getShortName()); //$NON-NLS-1$
        assertEquals("java.util.Map<java.lang.String, java.util.List<java.lang.String>>", fqjt.getFullyQualifiedName()); //$NON-NLS-1$
        assertEquals("java.util", fqjt.getPackageName()); //$NON-NLS-1$
        assertEquals(2, fqjt.getImportList().size());
        assertEquals("java.util.Map", fqjt.getFullyQualifiedNameWithoutTypeParameters()); //$NON-NLS-1$
    }

    @Test
    void testGenericType3() {
        FullyQualifiedJavaType listOfStrings = new FullyQualifiedJavaType("java.util.List"); //$NON-NLS-1$
        listOfStrings.addTypeArgument(new FullyQualifiedJavaType("java.lang.String")); //$NON-NLS-1$

        FullyQualifiedJavaType fqjt =
            new FullyQualifiedJavaType("java.util.Map"); //$NON-NLS-1$
        fqjt.addTypeArgument(new FullyQualifiedJavaType("java.lang.String")); //$NON-NLS-1$
        fqjt.addTypeArgument(listOfStrings);

        assertTrue(fqjt.isExplicitlyImported());
        assertEquals("Map<String, List<String>>", fqjt.getShortName()); //$NON-NLS-1$
        assertEquals("java.util.Map<java.lang.String, java.util.List<java.lang.String>>", fqjt.getFullyQualifiedName()); //$NON-NLS-1$
        assertEquals("java.util", fqjt.getPackageName()); //$NON-NLS-1$
        assertEquals(2, fqjt.getImportList().size());
        assertEquals("java.util.Map", fqjt.getFullyQualifiedNameWithoutTypeParameters()); //$NON-NLS-1$
    }

    @Test
    void testGenericType4() {
        FullyQualifiedJavaType fqjt =
            new FullyQualifiedJavaType("java.util.List<java.util.Map<java.lang.String, java.lang.Object>>"); //$NON-NLS-1$
        assertTrue(fqjt.isExplicitlyImported());
        assertEquals("List<Map<String, Object>>", fqjt.getShortName()); //$NON-NLS-1$
        assertEquals("java.util.List<java.util.Map<java.lang.String, java.lang.Object>>", fqjt.getFullyQualifiedName()); //$NON-NLS-1$
        assertEquals("java.util", fqjt.getPackageName()); //$NON-NLS-1$
        assertEquals(2, fqjt.getImportList().size());
        assertEquals("java.util.List", fqjt.getFullyQualifiedNameWithoutTypeParameters()); //$NON-NLS-1$
    }

    @Test
    void testWildcardType1() {
        FullyQualifiedJavaType fqjt =
            new FullyQualifiedJavaType("java.util.Map<java.lang.String, ? extends com.foo.Bar>");
        assertTrue(fqjt.isExplicitlyImported());
        assertEquals("Map<String, ? extends Bar>", fqjt.getShortName()); //$NON-NLS-1$
        assertEquals("java.util.Map<java.lang.String, ? extends com.foo.Bar>", fqjt.getFullyQualifiedName()); //$NON-NLS-1$
        assertEquals("java.util", fqjt.getPackageName()); //$NON-NLS-1$
        assertEquals(2, fqjt.getImportList().size());
        assertEquals("java.util.Map", fqjt.getFullyQualifiedNameWithoutTypeParameters()); //$NON-NLS-1$
    }

    @Test
    void testWildcardType2() {
        FullyQualifiedJavaType fqjt =
            new FullyQualifiedJavaType("java.util.Map<java.lang.String, ?>");
        assertTrue(fqjt.isExplicitlyImported());
        assertEquals("Map<String, ?>", fqjt.getShortName()); //$NON-NLS-1$
        assertEquals("java.util.Map<java.lang.String, ?>", fqjt.getFullyQualifiedName()); //$NON-NLS-1$
        assertEquals("java.util", fqjt.getPackageName()); //$NON-NLS-1$
        assertEquals(1, fqjt.getImportList().size());
        assertEquals("java.util.Map", fqjt.getImportList().get(0));
        assertEquals("java.util.Map", fqjt.getFullyQualifiedNameWithoutTypeParameters()); //$NON-NLS-1$
    }

    @Test
    void testWildcardType3() {
        FullyQualifiedJavaType fqjt =
            new FullyQualifiedJavaType("java.util.Map<? extends java.util.List<?>, ?>");
        assertTrue(fqjt.isExplicitlyImported());
        assertEquals("Map<? extends List<?>, ?>", fqjt.getShortName()); //$NON-NLS-1$
        assertEquals("java.util.Map<? extends java.util.List<?>, ?>", fqjt.getFullyQualifiedName()); //$NON-NLS-1$
        assertEquals("java.util", fqjt.getPackageName()); //$NON-NLS-1$
        assertEquals(2, fqjt.getImportList().size());
        assertEquals("java.util.Map", fqjt.getFullyQualifiedNameWithoutTypeParameters()); //$NON-NLS-1$
    }

    @Test
    void testWildcardType4() {
        FullyQualifiedJavaType fqjt =
            new FullyQualifiedJavaType("java.util.Map<?, ?>");
        assertTrue(fqjt.isExplicitlyImported());
        assertEquals("Map<?, ?>", fqjt.getShortName()); //$NON-NLS-1$
        assertEquals("java.util.Map<?, ?>", fqjt.getFullyQualifiedName()); //$NON-NLS-1$
        assertEquals("java.util", fqjt.getPackageName()); //$NON-NLS-1$
        assertEquals(1, fqjt.getImportList().size());
        assertEquals("java.util.Map", fqjt.getImportList().get(0));
        assertEquals("java.util.Map", fqjt.getFullyQualifiedNameWithoutTypeParameters()); //$NON-NLS-1$
    }

    @Test
    void testWildcardType5() {
        FullyQualifiedJavaType fqjt =
            new FullyQualifiedJavaType("java.util.List<? extends java.util.Map<? super java.lang.Object, ?>>");
        assertTrue(fqjt.isExplicitlyImported());
        assertEquals("List<? extends Map<? super Object, ?>>", fqjt.getShortName()); //$NON-NLS-1$
        assertEquals("java.util.List<? extends java.util.Map<? super java.lang.Object, ?>>", fqjt.getFullyQualifiedName()); //$NON-NLS-1$
        assertEquals("java.util", fqjt.getPackageName()); //$NON-NLS-1$
        assertEquals(2, fqjt.getImportList().size());
        assertEquals("java.util.List", fqjt.getFullyQualifiedNameWithoutTypeParameters()); //$NON-NLS-1$
    }

    @Test
    void testUppercasePackage1() {
        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("org.foo.Bar.Inner");
        assertTrue(fqjt.isExplicitlyImported());
        assertEquals("Inner", fqjt.getShortName()); //$NON-NLS-1$
        assertEquals("org.foo.Bar.Inner", fqjt.getFullyQualifiedName()); //$NON-NLS-1$
        assertEquals("org.foo.Bar", fqjt.getPackageName()); //$NON-NLS-1$
        assertEquals(1, fqjt.getImportList().size());
        assertEquals("org.foo.Bar.Inner", fqjt.getImportList().get(0));
    }

    @Test
    void testUppercasePackage2() {
        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("org.foo.Bar.Inner.Inner");
        assertTrue(fqjt.isExplicitlyImported());
        assertEquals("Inner", fqjt.getShortName()); //$NON-NLS-1$
        assertEquals("org.foo.Bar.Inner.Inner", fqjt.getFullyQualifiedName()); //$NON-NLS-1$
        assertEquals("org.foo.Bar.Inner", fqjt.getPackageName()); //$NON-NLS-1$
        assertEquals(1, fqjt.getImportList().size());
        assertEquals("org.foo.Bar.Inner.Inner", fqjt.getImportList().get(0));
    }

    @Test
    void testUppercasePackage3() {
        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("java.util.List<org.foo.Bar.Inner>");
        assertTrue(fqjt.isExplicitlyImported());
        assertEquals("List<Inner>", fqjt.getShortName()); //$NON-NLS-1$
        assertEquals("java.util.List<org.foo.Bar.Inner>", fqjt.getFullyQualifiedName()); //$NON-NLS-1$
        assertEquals("java.util", fqjt.getPackageName()); //$NON-NLS-1$
        assertEquals(2, fqjt.getImportList().size());
        assertTrue(fqjt.getImportList().contains("java.util.List"));
        assertTrue(fqjt.getImportList().contains("org.foo.Bar.Inner"));
    }

    @Test
    void testByteArray1() {
        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("byte[]");
        assertFalse(fqjt.isPrimitive());
        assertTrue(fqjt.isArray());
    }

    @Test
    void testByteArray2() {
        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("byte[ ]");
        assertFalse(fqjt.isPrimitive());
        assertTrue(fqjt.isArray());
    }

    @Test
    void testStringArray() {
        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("java.lang.String[]");
        assertFalse(fqjt.isPrimitive());
        assertTrue(fqjt.isArray());
    }

    @Test
    void testStringArray2() {
        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("java.math.BigDecimal[]");
        assertEquals(1, fqjt.getImportList().size());
        assertEquals("java.math.BigDecimal", fqjt.getImportList().get(0));
        assertEquals("BigDecimal[]", fqjt.getShortName());
        assertFalse(fqjt.isPrimitive());
        assertTrue(fqjt.isArray());
    }

    @Test
    void testComplexArray() {
        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("java.util.List<String>[]");
        assertFalse(fqjt.isPrimitive());
        assertTrue(fqjt.isArray());
    }

    @Test
    void testComplexArrayWithoutGenerics() {
        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("java.util.List[]");
        assertFalse(fqjt.isPrimitive());
        assertTrue(fqjt.isArray());
        assertTrue(fqjt.getImportList().contains("java.util.List"));
        assertFalse(fqjt.getImportList().contains("java.util.List[]"));
    }

    @Test
    void gh1288Test1() {
        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("Class<?>");
        assertThat(fqjt.getFullyQualifiedName()).isEqualTo("Class<?>");

        Method method = new Method("setConverter");
        Parameter parameter = new Parameter(fqjt, "converterType");
        method.addParameter(parameter);
        method.setAbstract(true);

        Interface interfaze = new Interface("foo.Bar");
        interfaze.setVisibility(JavaVisibility.PUBLIC);
        interfaze.addMethod(method);

        TopLevelInterfaceRenderer renderer = new TopLevelInterfaceRenderer();
        String out = renderer.render(interfaze);

        assertThat(out).isEqualToNormalizingNewlines("package foo;\n\npublic interface Bar {\n    void setConverter(Class<?> converterType);\n}");
    }

    @Test
    void gh1288Test2() {
        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("Class<? extends String>");
        assertThat(fqjt.getFullyQualifiedName()).isEqualTo("Class<? extends String>");

        Method method = new Method("setConverter");
        Parameter parameter = new Parameter(fqjt, "converterType");
        method.addParameter(parameter);
        method.setAbstract(true);

        Interface interfaze = new Interface("foo.Bar");
        interfaze.setVisibility(JavaVisibility.PUBLIC);
        interfaze.addMethod(method);

        TopLevelInterfaceRenderer renderer = new TopLevelInterfaceRenderer();
        String out = renderer.render(interfaze);

        assertThat(out).isEqualToNormalizingNewlines("package foo;\n\npublic interface Bar {\n    void setConverter(Class<? extends String> converterType);\n}");
    }

    @Test
    void gh1288Test3() {
        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("Class");
        fqjt.addTypeArgument(new FullyQualifiedJavaType("? extends HttpMessageConverter<?>"));
        assertThat(fqjt.getFullyQualifiedName()).isEqualTo("Class<? extends HttpMessageConverter<?>>");

        Method method = new Method("setConverter");
        Parameter parameter = new Parameter(fqjt, "converterType");
        method.addParameter(parameter);
        method.setAbstract(true);

        Interface interfaze = new Interface("foo.Bar");
        interfaze.setVisibility(JavaVisibility.PUBLIC);
        interfaze.addMethod(method);

        TopLevelInterfaceRenderer renderer = new TopLevelInterfaceRenderer();
        String out = renderer.render(interfaze);

        assertThat(out).isEqualToNormalizingNewlines("package foo;\n\npublic interface Bar {\n    void setConverter(Class<? extends HttpMessageConverter<?>> converterType);\n}");
    }

    @Test
    void gh1288Test3Minified() {
        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("Class");
        fqjt.addTypeArgument(new FullyQualifiedJavaType("? extends HttpMessageConverter<?>"));
        assertThat(fqjt.getFullyQualifiedName()).isEqualTo("Class<? extends HttpMessageConverter<?>>");

        Parameter parameter = new Parameter(fqjt, "converterType");
        String out = parameter.toString();
        assertThat(out).isEqualTo("Class<? extends HttpMessageConverter<?>> converterType");
    }
}
