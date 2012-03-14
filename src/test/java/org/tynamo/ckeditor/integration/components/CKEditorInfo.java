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
		jss.addInitializerCall(InitializationPriority.LATE, "countCKEditorInstances",
				instanceCountId);
		jss.addInitializerCall(InitializationPriority.LATE, "printCKEditorInstancesInfo",
				instanceInfoId);
	}
}
