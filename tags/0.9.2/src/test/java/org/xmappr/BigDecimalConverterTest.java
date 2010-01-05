/*
 * This software is released under the BSD license. Full license available at http://xmappr.googlecode.com
 *
 * Copyright (c) 2008, 2009, Peter Knego & Xmappr contributors
 * All rights reserved.
 */
package org.xmappr;

import org.custommonkey.xmlunit.XMLAssert;
import org.custommonkey.xmlunit.XMLUnit;
import org.testng.Assert;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;

public class BigDecimalConverterTest {

    private static String inXml = "" +
            "<test>" +
            "<node>123.456</node>" +
            "</test>";

    @org.testng.annotations.Test
    public void test() throws IOException, SAXException {
        StringReader reader = new StringReader(inXml);

        // Double step to make Xmappr work harder (not necessary normally - do not copy)
        // Reads Class configuration, produces XML configuration from it and then feeds it to Xmappr
        StringReader configuration = XmlConfigTester.reader(Test.class);
        Xmappr xmappr = new Xmappr(configuration);

        Test test = (Test) xmappr.fromXML(reader);

        // writing back to XML
        StringWriter sw = new StringWriter();
        xmappr.toXML(test, sw);
        String ssw = sw.toString();
        System.out.println("");
        System.out.println(inXml);
        System.out.println("");
        System.out.println(ssw);

        Assert.assertEquals(test.node, new BigDecimal("123.456"));
        XMLUnit.setIgnoreWhitespace(true);
        XMLAssert.assertXMLEqual(inXml, ssw);

    }

    @RootElement("test")
    public static class Test {
        @Element
        public BigDecimal node;
    }
}
