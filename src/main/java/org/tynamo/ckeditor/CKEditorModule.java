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

import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.services.ClasspathAssetAliasManager;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.LibraryMapping;
import org.tynamo.common.ModuleProperties;

/**
 * This module is automatically included as part of the Tapestry IoC Registry, it's a good place to
 * configure and extend Tapestry, or to place your own service definitions.
 */
public class CKEditorModule
{
	private static final String PATH_PREFIX = "ckeditor";
	private static final String version = ModuleProperties.getVersion(CKEditorModule.class);

	@Contribute(ComponentClassResolver.class)
	public static void setupCkEditorLibrary(Configuration<LibraryMapping> configuration)
	{
		configuration.add(new LibraryMapping("tynamo", "org.tynamo.ckeditor"));
	}

	@Contribute(ClasspathAssetAliasManager.class)
	public static void setupAssetsAliases(MappedConfiguration<String, String> configuration)
	{
		configuration.add(PATH_PREFIX + "-" + version, "org/tynamo/ckeditor");
	}
}
