/*
 * Metadata Editor
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2014  Jiri Kremser (kremser@mzk.cz)
 * Moravian Library in Brno
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * 
 */
package cz.mzk.editor.server.handler;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

import cz.mzk.editor.shared.rpc.NewDigitalObject;

/**
 * @author Jiri Kremser
 * @version 08.03.2014
 */
public class InsertNewDigitalObjectHandlerTest {
    private Method processPostIngestHook;
    private NewDigitalObject ob;
    private InsertNewDigitalObjectHandler handler;

    @Before
    public void setUp() {
	if (null == processPostIngestHook) {
	    try {
		processPostIngestHook = InsertNewDigitalObjectHandler.class.getDeclaredMethod("doTheSubstitution",
			String.class, NewDigitalObject.class);
	    } catch (NoSuchMethodException e) {
		e.printStackTrace();
	    } catch (SecurityException e) {
		e.printStackTrace();
	    }
	    processPostIngestHook.setAccessible(true);
	}
	if (null == ob) {
	    ob = new NewDigitalObject("123456789");
	    ob.setUuid("uuid:b86a0b83-408d-4f07-aa5e-f70f0dcd39a1");
	    ob.setSysno("KN43940000000013");
	}
	if (null == handler) {
	    handler = new InsertNewDigitalObjectHandler();
	}
    }

    private String invoke(String url) {
	String result = null;
	try {
	    result = (String) processPostIngestHook.invoke(handler, url, ob);
	} catch (IllegalAccessException e) {
	    e.printStackTrace();
	} catch (IllegalArgumentException e) {
	    e.printStackTrace();
	} catch (InvocationTargetException e) {
	    e.printStackTrace();
	}
	return result;
    }

    @Test
    public void testNormalSubstitution1() {
	String result = invoke("http://192.168.0.25:8080/katalog/l.dll?bqkram2clav~clid=${sysno}");
	assertEquals("Normal substitution without any substring ops is not working properly",
		"http://192.168.0.25:8080/katalog/l.dll?bqkram2clav~clid=KN43940000000013", result);
    }

    @Test
    public void testNormalSubstitution2() {
	String result = invoke("http://192.168.0.25:8080/katalog/l.dll?bqkram2clav~clid=${sysno}&uuid=${pid}&name=${name}");
	assertEquals(
		"Normal substitution without any substring ops is not working properly",
		"http://192.168.0.25:8080/katalog/l.dll?bqkram2clav~clid=KN43940000000013&uuid=uuid:b86a0b83-408d-4f07-aa5e-f70f0dcd39a1&name=123456789",
		result);
    }

    @Test
    public void testSubstitutionWithoutFirst5Chars() {
	String result = invoke("http://192.168.0.25:8080/katalog/l.dll&name=${name:5}");
	assertEquals("${name:5} substitution is not working properly, it should omit first 5 chars.",
		"http://192.168.0.25:8080/katalog/l.dll&name=6789", result);
    }

    @Test
    public void testSubstitutionWithoutFirst2Chars() {
	String result = invoke("http://192.168.0.25:8080/katalog/l.dll&name=${name:2}");
	assertEquals("${name:2} substitution is not working properly, it should omit first 2 chars.",
		"http://192.168.0.25:8080/katalog/l.dll&name=3456789", result);
    }

    @Test
    public void testSubstitutionNoOp1() {
	String result = invoke("http://192.168.0.25:8080/katalog/l.dll&name=${name:}");
	assertEquals("${name:} substitution should just substitute the name.",
		"http://192.168.0.25:8080/katalog/l.dll&name=123456789", result);
    }

    @Test
    public void testSubstitutionNoOp2() {
	String result = invoke("http://192.168.0.25:8080/katalog/l.dll&name=${name::}");
	assertEquals("${name::} substitution should just substitute the name.",
		"http://192.168.0.25:8080/katalog/l.dll&name=123456789", result);
    }

    @Test
    public void testSubstitutionFromTo() {
	String result = invoke("http://192.168.0.25:8080/katalog/l.dll&name=${name:1:5}");
	assertEquals("${name:1:5} substitution should substitute only chars 1 - 5 of the name.",
		"http://192.168.0.25:8080/katalog/l.dll&name=2345", result);
    }

    @Test
    public void testSubstitutionLast2() {
	String result = invoke("http://192.168.0.25:8080/katalog/l.dll&name=${name:-2}");
	assertEquals("${name:-2} substitution should substitute only last 2 chars.",
		"http://192.168.0.25:8080/katalog/l.dll&name=89", result);
    }

    @Test
    public void testSubstitutionWithoutLast2() {
	String result = invoke("http://192.168.0.25:8080/katalog/l.dll&name=${name::-2}");
	assertEquals("${name::-2} substitution should omit last 2 chars.",
		"http://192.168.0.25:8080/katalog/l.dll&name=1234567", result);
    }

    @Test
    public void testSubstitutionCombination1() {
	String result = invoke("http://192.168.0.25:8080/katalog/l.dll?bqkram2clav~clid=${sysno::8}&uuid=${pid:5}");
	assertEquals(
		"Substitution not working as supposed.",
		"http://192.168.0.25:8080/katalog/l.dll?bqkram2clav~clid=KN439400&uuid=b86a0b83-408d-4f07-aa5e-f70f0dcd39a1",
		result);
    }

    @Test
    public void testSubstitutionCombination2() {
	String result = invoke("http://192.168.0.25:8080/katalog/l.dll?bqkram2clav~clid=${sysno:0:2}&uuid=${pid:0:4}");
	assertEquals("Substitution not working as supposed.",
		"http://192.168.0.25:8080/katalog/l.dll?bqkram2clav~clid=KN&uuid=uuid", result);
    }

    @Test
    public void testSubstitutionCombination3() {
	String result = invoke("foo=${sysno:1:2}, bar=${pid:4:5}, baz=${name:1:1}");
	assertEquals("Substitution not working as supposed.", "foo=N, bar=:, baz=", result);
    }
}