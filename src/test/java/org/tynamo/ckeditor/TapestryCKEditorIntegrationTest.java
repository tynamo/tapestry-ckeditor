// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.tynamo.ckeditor;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.mortbay.jetty.webapp.WebAppContext;
import org.testng.annotations.Test;
import org.tynamo.ckeditor.integration.components.CKEditorInfo;
import org.tynamo.ckeditor.integration.pages.CKEditorDemo;
import org.tynamo.ckeditor.integration.pages.Index;
import org.tynamo.test.AbstractContainerTest;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomText;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class TapestryCKEditorIntegrationTest extends AbstractContainerTest
{
	private HtmlPage page;

	protected final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_3);

	@Test
	public void test_ckeditor_with_prefilled_text() throws Exception
	{
		clickOnBasePage("CKEditorDemoWithContext");

		final String textAreaId = "textarea";
		final String textAreaText = Index.CKEDITOR_DEMO_CONTEXT;

		assertEquals(getElementTextByIdPrefix(textAreaId), textAreaText);

		assertOneCKEditorInstance(textAreaId, textAreaText);
	}

	@Test
	public void test_ckeditor_ajax_update() throws Exception
	{
		clickOnBasePage("CKEditorDemo");

		final String textAreaIdPrefix = "textarea";

		page = page.getElementById(CKEditorDemo.SET_NEW_TEXT_LINK_ID).click();
		webClient.waitForBackgroundJavaScript(500);

		// ajax update
		page = ((HtmlElement) page.getForms().get(0).getLastChild()).click();
		webClient.waitForBackgroundJavaScriptStartingBefore(500);

		assertOneCKEditorInstance(textAreaIdPrefix, CKEditorDemo.NEW_TEXT);
	}

	private void assertOneCKEditorInstance(final String textAreaId, final String textAreaText)
	{
		assertCKEditorInstanceCount(1);

		HtmlElement infoContainer = getElementByIdPrefix(CKEditorInfo.INSTANCE_INFO_CLIENT_ID_PREFIX);

		DomNode dl = infoContainer.getChildNodes().get(0);

		String ddId = dl.getChildNodes().get(0).getFirstChild().getTextContent();
		String dtId = dl.getChildNodes().get(1).getFirstChild().getTextContent();
		String dtData = dl.getChildNodes().get(2).getFirstChild().getTextContent();
		String ddData = dl.getChildNodes().get(3).getFirstChild().getTextContent();

		assertEquals(ddId, "id");
		// the id may have changed due to ajax updates
		assertTrue(dtId.startsWith(textAreaId));
		assertEquals(dtData, "data");
		assertEquals(ddData, textAreaText);
	}

	private void assertCKEditorInstanceCount(int instanceCount)
	{
		int numberOfCKEditorInstances = Integer.valueOf(getElementTextByIdPrefix(CKEditorInfo.INSTANCE_COUNT_CLIENT_ID_PREFIX));
		assertEquals(numberOfCKEditorInstances, instanceCount);
	}

	private String getElementTextByIdPrefix(String prefix)
	{
		return page.<DomText> getFirstByXPath(String.format("//*[starts-with(@id, '%s')]/text()", prefix)).getTextContent();
	}

	private HtmlElement getElementByIdPrefix(String prefix)
	{
		return page.<HtmlElement> getFirstByXPath(String.format("//*[starts-with(@id, '%s')]", prefix));
	}

	@Override
	public WebAppContext buildContext()
	{
		WebAppContext context = new WebAppContext("src/test/webapp", "/");
		/*
		 * Sets the classloading model for the context to avoid an strange
		 * "ClassNotFoundException: org.slf4j.Logger"
		 */
		context.setParentLoaderPriority(true);
		return context;
	}

	protected void openBase() throws Exception
	{
		openPage("");
	}

	protected void clickOnBasePage(String url) throws Exception
	{
		openBase();
		page = page.getElementById(url).click();
	}

	protected void openPage(String url) throws Exception
	{
		page = webClient.getPage(BASEURI + url);
	}
}
