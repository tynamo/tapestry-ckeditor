package org.tynamo.ckeditor.integration.components;

import javax.inject.Inject;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

@Import(stylesheet = "style.css")
public class Layout
{
	@InjectComponent
	private Zone infoZone;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	public void updateInfoZone()
	{
		ajaxResponseRenderer.addRender(infoZone);
	}
}
