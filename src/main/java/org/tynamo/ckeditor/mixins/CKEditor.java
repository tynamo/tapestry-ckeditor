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

package org.tynamo.ckeditor.mixins;

import java.util.Map;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.components.TextArea;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * A mixin that replaces a {@link TextArea} component with a CKEditor instance. The initial value in
 * the ckeditor will be the textarea value.
 * 
 * @tapestrydoc
 */
@Import(library =
{ "../ckeditor/ckeditor.js", "init-ckeditor.js" })
public class CKEditor
{
	/**
	 * The specific configurations to apply to this editor instance. Configurations set here will
	 * override global CKEditor settings.
	 * <p>
	 * See the <a href="http://docs.cksource.com/ckeditor_api/index.html">ckeditor documentation</a>
	 * for more details.
	 */
	@Parameter
	private Map<String, ?> parameters;

	@InjectContainer
	private TextArea textArea;

	@Inject
	private JavaScriptSupport javaScriptSupport;

	void afterRender(MarkupWriter writer)
	{
		String id = textArea.getClientId();

		JSONObject json = new JSONObject();
		if (parameters != null)
			for (String paramName : parameters.keySet())
				json.put(paramName, parameters.get(paramName));

		javaScriptSupport.addInitializerCall("initCKEditor", new JSONArray(id, json));
	}
}
