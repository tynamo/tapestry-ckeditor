package org.tynamo.ckeditor.integration.components;

import javax.inject.Inject;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.services.javascript.InitializationPriority;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Import(library = "init-ckeditor-info.js")
public class CKEditorInfo
{
	@Inject
	private JavaScriptSupport jss;

	@Property
	private String instanceCountId;

	@Property
	private String instanceInfoId;

	public static final String INSTANCE_COUNT_CLIENT_ID_PREFIX = "ckeditor-instance-count";
	public static final String INSTANCE_INFO_CLIENT_ID_PREFIX = "ckeditor-instance-info";

	void setupRender()
	{
		instanceCountId = jss.allocateClientId(INSTANCE_COUNT_CLIENT_ID_PREFIX);
		instanceInfoId = jss.allocateClientId(INSTANCE_INFO_CLIENT_ID_PREFIX);
	}

	void afterRender()
	{
		jss.addInitializerCall(InitializationPriority.LATE, "countCKEditorInstances", instanceCountId);
		jss.addInitializerCall(InitializationPriority.LATE, "printCKEditorInstancesInfo", instanceInfoId);
	}
}
