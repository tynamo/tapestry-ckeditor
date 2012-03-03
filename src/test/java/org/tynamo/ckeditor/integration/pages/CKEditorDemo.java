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

package org.tynamo.ckeditor.integration.pages;

import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.TextArea;
import org.apache.tapestry5.corelib.components.Zone;
import org.tynamo.ckeditor.integration.components.Layout;

public class CKEditorDemo
{
	@PageActivationContext
	@Property
	@Persist
	private String text;

	@InjectComponent
	private Zone mainZone;

	@InjectComponent
	private Layout layout;

	@InjectComponent
	private TextArea textArea;

	public static final String NEW_TEXT = "new text";

	public static final String SET_NEW_TEXT_LINK_ID = "setNewText";

	public String getTextAreaId()
	{
		return textArea.getClientId();
	}

	Object onSuccess()
	{
		layout.updateInfoZone();
		return mainZone.getBody();
	}
}
