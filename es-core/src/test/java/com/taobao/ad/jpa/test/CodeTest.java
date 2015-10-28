/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.jpa.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.taobao.ad.easyschedule.bo.code.ICodeBO;
import com.taobao.ad.easyschedule.dataobject.CodeDO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springbeans-es-test-*.xml" })
public class CodeTest {

	@Resource
	ICodeBO codeBO;

	@Test
	public void testInsertCode() {
		CodeDO code = new CodeDO();
		code.setCodekey("222111");
		code.setKeycode("2");
		code.setKeydesc("1");
		code.setKeyname("2");
		code.setSortnum(1L);
		Assert.assertEquals(codeBO.insertCode(code).longValue(), 1L);
		codeBO.deleteCodeByKeyAndKeyCode(code);
	}

	@Test
	public void testGetCodesByKey() {
		Assert.assertNotNull(codeBO.getCodesByKey("DATATRACKINGJOB_JOBDATA"));

	}

	@Test
	public void testFindCodes() {
		Assert.assertNotNull(codeBO.findCodes(new CodeDO()));
	}

	@Test
	public void deleteCodeByKeyAndKeyCode() {
		CodeDO code = new CodeDO();
		code.setCodekey("DATATRACKINGJOB_JOBDATA");
		code.setKeycode("isGreaterEqualLevel2");
		codeBO.deleteCodeByKeyAndKeyCode(code);
	}

	@Test
	public void updateCode() {
		CodeDO code = new CodeDO();
		code.setCodekey("DATATRWACKINGJOB_JOBDATA");
		code.setKeycode("isGreaterEqualLevel1");
		code.setSortnum(100L);
		codeBO.updateCode(code);
	}

	@Test
	public void getCodeByKeyAndKeyCode() {

		CodeDO code = new CodeDO();
		code.setCodekey("DATATRACKINGJOB_JOBDATA");
		code.setKeycode("completeTime");
		code = codeBO.getCodeByKeyAndKeyCode(code);
		Assert.assertNotNull(code);
		Assert.assertEquals(code.getCodekey(), "DATATRACKINGJOB_JOBDATA");
		Assert.assertEquals(code.getKeycode(), "completeTime");
		Assert.assertEquals(code.getKeydesc(), "异步任务最大超时时间");
		Assert.assertEquals(code.getKeyname(), "30000");
		Assert.assertEquals(code.getSortnum().longValue(), 20L);
	}

	@Test
	public void testInsertExistCode() {
		CodeDO code = new CodeDO();
		code.setCodekey("DATATRACKINGJOB_JOBDATA");
		code.setKeycode("completeTime");
		Assert.assertEquals(-1, codeBO.insertCode(code).longValue());
	}

}
